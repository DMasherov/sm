package patterra.bp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Component;
import patterra.bp.statemachineconfig.UserEvents;
import patterra.bp.statemachineconfig.InventionStates;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootApplication
public class BpApplication {

    public static void main(String[] args) {
        SpringApplication.run(BpApplication.class, args);
    }
}

@Component
class AppRunner implements ApplicationRunner {

    @Autowired
    StateMachineFactory<InventionStates, UserEvents> factory;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        {
            StateMachine<InventionStates, UserEvents> sm = factory.getStateMachine();

            sm.start();
            Set<UserEvents> events = sm.getTransitions().stream()
                    .map(t -> t.getTrigger().getEvent())
                    .collect(Collectors.toSet());
            System.out.println("All events: " + events);

            while (true) {
                System.out.println("Current state (in hierarchy): " + sm.getState().getIds());
                System.out.print("sm>");
                String command = new Scanner(System.in).nextLine();

                if ("stop".equals(command)) {
                    return;
                }
                if (Arrays.asList("repeat", "again").contains(command)) {
                    sm.stop();
                    sm = factory.getStateMachine();
                    sm.start();
                    continue;
                }

                String eventStr = command;
                UserEvents event = null;
                try {
                    event = UserEvents.valueOf(eventStr);
                } catch (IllegalArgumentException e) {
                    System.out.println("no such event!");
                    continue;
                }

                sm.sendEvent(event);
            }
        }
    }
}
