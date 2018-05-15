package patterra.bp.invention.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;
import patterra.bp.invention.config.sm.Events;
import patterra.bp.invention.config.sm.States;
import patterra.bp.StateMachineFacade;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Интерактивное управление машины состояний из терминала.
 */
@Component
@Profile("demo")
class StateMachineDemoRunner implements ApplicationRunner {
    @Autowired
    private StateMachineFacade<States, Events> smf;

    @Override
    public void run(ApplicationArguments args) {
        StateMachine<States, Events> sm = smf.getStateMachine();

        sm.start();
        System.out.println("All events: " + smf.getAllEvents());

        while (true) {
            System.out.println("Current state ids: " + smf.getCurrentStateIds());
            System.out.println("Possible events: " + smf.getTriggeringEvents());
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
                smf.initialize();
                return;
            }
            case TRIGGER_CURRENT_TO_CHECK: {
                // System.out.print("applicable events here: ");
                // System.out.println(smf.getTriggeringApplicableEvents());
                System.out.println("TODO...");
                return;
            }
            case SET_STATE: {
                try {
                    States state = States.valueOf(commandWithArgs.args);
                    smf.initialize(state);
                } catch (IllegalArgumentException e) {
                    System.out.println("no such state!");
                }
                return;
            }
            case SEND_EVENT: {
                try {
                    Events event = Events.valueOf(commandWithArgs.args);
                    smf.sendEvent(event);
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
