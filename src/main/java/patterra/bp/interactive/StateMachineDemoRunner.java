package patterra.bp.interactive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Component;
import patterra.bp.controller.BPController;
import patterra.bp.statemachineconfig.InventionEvents;
import patterra.bp.statemachineconfig.InventionStates;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Интерактивное управление машины состояний из терминала.
 */
@Component
@Profile("demo")
class StateMachineDemoRunner implements ApplicationRunner {

    @Autowired
    private BPController<InventionStates, InventionEvents> bpController;

    private enum CommandType {
        STOP,
        SET_STATE,
        REPEAT,
        SEND_EVENT,
    }

    private static class CommandWithArgs {
        CommandType commandType;
        String args;

        CommandWithArgs(CommandType commandType) {
            this.commandType = commandType;
        }

        CommandWithArgs(CommandType commandType, String args) {
            this.commandType = commandType;
            this.args = args;
        }

    }

    private CommandWithArgs parseCommandType(String command) {
        if ("stop".equals(command)) {
            return new CommandWithArgs(CommandType.STOP);
        }
        if (Arrays.asList("repeat", "again").contains(command)) {
            return new CommandWithArgs(CommandType.REPEAT);
        }
        if (command.startsWith("set state ")) {
            return new CommandWithArgs(CommandType.SET_STATE, command.replaceAll("^set state ", ""));
        }
        return new CommandWithArgs(CommandType.SEND_EVENT, command);
    }

    @Override
    public void run(ApplicationArguments args) {
        {
            StateMachine<InventionStates, InventionEvents> sm = bpController.getStateMachine();

            sm.start();
            System.out.println("All events: " + bpController.getAllEvents());

            while (true) {
                System.out.println("Current state (in hierarchy): " + bpController.getCurrentStates());
                System.out.println("Possible events: " + bpController.getTriggeringEvents());
                System.out.print("sm>");
                String command = new Scanner(System.in).nextLine();

                CommandWithArgs commandWithArgs = parseCommandType(command);
                CommandType commandType = commandWithArgs.commandType;
                if (commandType.equals(CommandType.STOP)) {
                    return;
                } else if (commandType.equals(CommandType.REPEAT)) {
                    sm.stop();
                    sm = bpController.getStateMachine();
                    sm.start();
                } else if (commandType.equals(CommandType.SET_STATE)) {
                    try {
                        InventionStates state = InventionStates.valueOf(commandWithArgs.args);
                        sm.stop();
                        sm.getStateMachineAccessor()
                                .doWithAllRegions(access ->
                                        access.resetStateMachine(
                                                new DefaultStateMachineContext<>(state, null, null, null)
                                        )
                                );
                        sm.start();
                    } catch (IllegalArgumentException e) {
                        System.out.println("no such state!");
                    }
                } else if (commandType.equals(CommandType.SEND_EVENT)) {
                    try {
                        InventionEvents event = InventionEvents.valueOf(commandWithArgs.args);
                        sm.sendEvent(event);
                    } catch (IllegalArgumentException e) {
                        System.out.println("no such event!");
                    }

                }
            }
        }
    }
}
