package patterra.bp.interactive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Component;
import patterra.bp.controller.StateMachineService;
import patterra.bp.config.InventionEvents;
import patterra.bp.config.InventionStates;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Интерактивное управление машины состояний из терминала.
 */
@Component
@Profile("demo")
class StateMachineDemoRunner implements ApplicationRunner {

    @Autowired
    private StateMachineService<InventionStates, InventionEvents> stateMachineService;

    private enum CommandType {
        STOP,
        SET_STATE,
        REPEAT,
        SEND_EVENT,
        HELP,
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
        if ("help".equals(command)) {
            return new CommandWithArgs(CommandType.HELP);
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
            StateMachine<InventionStates, InventionEvents> sm = stateMachineService.getStateMachine();

            sm.start();
            System.out.println("All events: " + stateMachineService.getAllEvents());

            while (true) {
                System.out.println("Current state ids: " + stateMachineService.getCurrentStateIds());
                System.out.println("Possible events: " + stateMachineService.getTriggeringApplicableEvents());
                System.out.print("sm>");
                String command = new Scanner(System.in).nextLine();

                CommandWithArgs commandWithArgs = parseCommandType(command);
                CommandType commandType = commandWithArgs.commandType;
                if (commandType.equals(CommandType.STOP)) {
                    return;
                } else if (commandType.equals(CommandType.HELP)) {
                    System.out.println("   type 'EVENT_ID' to send an event.\n"
                            + "   type 'set state STATE_ID' to set a state.\n"
                            + "   type 'repeat' or 'again' to restart the state machine.\n");
                    continue;
                } else if (commandType.equals(CommandType.REPEAT)) {
                    sm.stop();
                    sm = stateMachineService.getStateMachine();
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
