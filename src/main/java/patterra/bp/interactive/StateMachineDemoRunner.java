package patterra.bp.interactive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Component;
import patterra.bp.statemachineconfig.InventionEvents;
import patterra.bp.statemachineconfig.InventionStates;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Интерактивное управление машины состояний в терминале.
 */
@Component
@Profile("demo")
class StateMachineDemoRunner implements ApplicationRunner {

    @Autowired
    StateMachineFactory<InventionStates, InventionEvents> factory;

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

    private String prompt(StateMachine<InventionStates, InventionEvents> sm) {
        System.out.println("Current state (in hierarchy): " + sm.getState().getIds());
        System.out.print("sm>");
        return new Scanner(System.in).nextLine();
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
            StateMachine<InventionStates, InventionEvents> sm = factory.getStateMachine();

            sm.start();
            Set<InventionEvents> events = sm.getTransitions().stream()
                    .map(t -> t.getTrigger().getEvent())
                    .collect(Collectors.toSet());
            System.out.println("All events: " + events);

            while (true) {
                String command = prompt(sm);
                CommandWithArgs commandWithArgs = parseCommandType(command);
                CommandType commandType = commandWithArgs.commandType;
                if (commandType.equals(CommandType.STOP)) {
                    return;
                } else if (commandType.equals(CommandType.REPEAT)) {
                    sm.stop();
                    sm = factory.getStateMachine();
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
