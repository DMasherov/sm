package patterra.bp.invention.config.sm;

import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigBuilder;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import patterra.bp.invention.config.sm.Actions;
import patterra.bp.invention.config.sm.Events;
import patterra.bp.invention.config.sm.Guards;
import patterra.bp.invention.config.sm.States;

import java.util.Arrays;

import static patterra.bp.invention.config.sm.Events.*;
import static patterra.bp.invention.config.sm.States.*;

@EnableStateMachineFactory
public class SMConfigFactory extends StateMachineConfigurerAdapter<States, Events> {
    @Override
    public void configure(StateMachineStateConfigurer<States, Events> config)
            throws Exception {
        config
            .withStates()
                .initial(PROCESSING)
                .state(FORMAL)
                .state(ESSENTIAL)
                .state(PATENT_READY)
                .end(END)
                    .and()
                .withStates()
                    .parent(PROCESSING)
                    .initial(ENTERING_APPLICATION)
                    .state(CLIENT_INFORMED)
                    .end(READY_TO_SUBMIT)
                    .and()
                .withStates()
                    .parent(FORMAL)
                    .initial(FORMAL_IN_PROGRESS)
                    .state(OFFICE_REQUEST_ON_FORMAL)
                    .state(APPLICATION_WITHDRAWED_ON_FORMAL)
                    .end(FORMAL_COMPLETED)
                    .and()
                .withStates()
                    .parent(ESSENTIAL)
                    .initial(ESSENTIAL_IN_PROGRESS)
                    .state(OFFICE_REQUEST_ON_ESSENTIAL)
                    .state(PATENT_DENIED)
                    .state(PATENT_GRANTED)
                    .state(APPLICATION_WITHDRAWED_ON_ESSENTIAL)
                    .end(READY_TO_RECEIVE)
        ;
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> config)
            throws Exception {
        config
            // entering
            .withExternal()
                .source(ENTERING_APPLICATION)
                .event(SEND_CONFIRMATION)
                .target(CLIENT_INFORMED)
                .and()
            .withExternal()
                .source(CLIENT_INFORMED)
                .event(PREPARE_TO_SUBMIT)
                .target(READY_TO_SUBMIT)
                .action(Actions.aAction())
                .guard(Guards.checkDocuments(Arrays.asList(2, 3)))
                .and()
            .withExternal()
                .source(READY_TO_SUBMIT)
                .event(FORMAL_SUBMIT)
                .target(FORMAL)
                .and()

            // formal
            .withExternal()
                .source(FORMAL_IN_PROGRESS)
                .event(FORMAL_REGISTER_OFFICE_REQUEST)
                .target(OFFICE_REQUEST_ON_FORMAL)
                .and()
            .withInternal()
                .source(OFFICE_REQUEST_ON_FORMAL)
                .event(FORMAL_PROLONG_REPLYING)
                .and()
           .withExternal()
                .source(OFFICE_REQUEST_ON_FORMAL)
                .event(FORMAL_REPLY_TO_OFFICE_REQUEST)
                .target(FORMAL_IN_PROGRESS)
                .and()
            .withExternal()
                .source(FORMAL_IN_PROGRESS)
                .event(FORMAL_COMPLETE)
                .target(FORMAL_COMPLETED)
                .and()
            .withExternal()
                .source(FORMAL_COMPLETED)
                .event(ESSENTIAL_SUBMIT_APPLICATION)
                .target(ESSENTIAL)
                .and()
            .withLocal()
                .source(FORMAL)
                .event(FORMAL_WITHDRAW)
                .target(APPLICATION_WITHDRAWED_ON_FORMAL)
                .and()
            .withExternal()
                .source(APPLICATION_WITHDRAWED_ON_FORMAL)
                .event(FORMAL_RESTORE)
                .target(FORMAL_IN_PROGRESS)
                .and()

            // essential
            .withExternal()
                .source(ESSENTIAL_IN_PROGRESS)
                .event(ESSENTIAL_REGISTER_OFFICE_REQUEST)
                .target(OFFICE_REQUEST_ON_ESSENTIAL)
                .and()
            .withInternal()
                .source(OFFICE_REQUEST_ON_ESSENTIAL)
                .event(ESSENTIAL_PROLONG_REPLYING)
                .and()
            .withExternal()
                .source(OFFICE_REQUEST_ON_ESSENTIAL)
                .event(ESSENTIAL_REPLY_TO_OFFICE_REQUEST)
                .target(ESSENTIAL_IN_PROGRESS)
                .and()
            .withExternal()
                .source(ESSENTIAL_IN_PROGRESS)
                .event(ESSENTIAL_REGISTER_GRANTING)
                .target(PATENT_GRANTED)
                .and()
            .withExternal()
                .source(ESSENTIAL_IN_PROGRESS)
                .event(ESSENTIAL_REGISTER_DENY)
                .target(PATENT_DENIED)
                .and()
            .withExternal()
                .source(PATENT_DENIED)
                .event(ESSENTIAL_SUBMIT_APPEAL)
                .target(ESSENTIAL_IN_PROGRESS)
                .and()
            .withExternal()
                .source(PATENT_GRANTED)
                .event(ESSENTIAL_PATENT_RECEIVE)
                .target(READY_TO_RECEIVE)
                .and()
            .withLocal()
                .source(ESSENTIAL)
                .event(ESSENTIAL_WITHDRAW)
                .target(APPLICATION_WITHDRAWED_ON_FORMAL)
                .and()
            .withExternal()
                .source(APPLICATION_WITHDRAWED_ON_FORMAL)
                .event(ESSENTIAL_RESTORE)
                .target(ESSENTIAL_IN_PROGRESS)
                .and()

            // поддержание
            .withExternal()
                .source(READY_TO_RECEIVE)
                .event(RECEIVE_PATENT)
                .target(PATENT_READY)
                .and()
            .withInternal()
                .source(PATENT_READY)
                .event(SEND_CERTIFICATE)
                .and()
            .withInternal()
                .source(PATENT_READY)
                .event(PAY_ANNUAL_FEE)
                .and()
            .withInternal()
                .source(PATENT_READY)
                .event(PROLONG_PATENT)
            ;
    }

    @Override
    public void configure(StateMachineConfigBuilder<States, Events> config)
            throws Exception {

//        config.getOrBuild()
//                .getTransitions()
//                .getTransitions()
//                .add(new TransitionData<>(PATENT_READY, PATENT_READY, ESSENTIAL_GRANT));
    }
}

