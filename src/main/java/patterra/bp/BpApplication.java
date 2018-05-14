package patterra.bp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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

@SpringBootApplication
public class BpApplication {

    public static void main(String[] args) {
        SpringApplication.run(BpApplication.class, args);
    }
}

