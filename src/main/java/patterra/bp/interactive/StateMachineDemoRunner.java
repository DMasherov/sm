package patterra.bp.interactive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;
import patterra.bp.config.InventionEvents;
import patterra.bp.config.InventionStates;
import patterra.bp.service.InventionStateMachineService;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Интерактивное управление машины состояний из терминала.
 */
@Component
@Profile("demo")
class StateMachineDemoRunner implements ApplicationRunner {
    @Autowired
    private InventionStateMachineService stateMachineService;

    @Override
    public void run(ApplicationArguments args) {
        StateMachine<InventionStates, InventionEvents> sm = stateMachineService.getStateMachine();

        sm.start();
        System.out.println("All events: " + stateMachineService.getAllEvents());

        while (true) {
            System.out.println("Current state ids: " + stateMachineService.getCurrentStateIds());
            System.out.println("Possible events: " + stateMachineService.getTriggeringEvents());
            System.out.print("sm>");
            String command = new Scanner(System.in).nextLine();
            CommandWithArgs commandWithArgs = parseCommandType(command);
            if (commandWithArgs.commandType == CommandType.STOP) {
                return;
            }
            processCommand(commandWithArgs);
        }
    }

    private void processCommand(CommandWithArgs commandWithArgs) {
        CommandType commandType = commandWithArgs.commandType;
        switch (commandType) {
            case HELP: {
                System.out.println("   type 'EVENT_ID' to send an event.\n"
                        + "   type 'set state STATE_ID' to set a state.\n"
                        + "   type 'check' to show every applicable event with guards\n"
                        + "   type 'repeat' or 'again' to restart the state machine.\n");
                return;
            }
            case REPEAT: {
                stateMachineService.initialize();
                return;
            }
            case TRIGGER_CURRENT_TO_CHECK: {
                // System.out.print("applicable events here: ");
                // System.out.println(stateMachineService.getTriggeringApplicableEvents());
                System.out.println("TODO...");
                return;
            }
            case SET_STATE: {
                try {
                    InventionStates state = InventionStates.valueOf(commandWithArgs.args);
                    stateMachineService.initialize(state);
                } catch (IllegalArgumentException e) {
                    System.out.println("no such state!");
                }
                return;
            }
            case SEND_EVENT: {
                try {
                    InventionEvents event = InventionEvents.valueOf(commandWithArgs.args);
                    stateMachineService.sendEvent(event);
                } catch (IllegalArgumentException e) {
                    System.out.println("no such event!");
                }
                return;
            }
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
        if (Arrays.asList("check").contains(command)) {
            return new CommandWithArgs(CommandType.TRIGGER_CURRENT_TO_CHECK);
        }
        return new CommandWithArgs(CommandType.SEND_EVENT, command);
    }

    private enum CommandType {
        STOP,
        SET_STATE,
        REPEAT,
        SEND_EVENT,
        TRIGGER_CURRENT_TO_CHECK,
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
}
