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
                .source(States.ENTERING_APPLICATION)
                .event(SEND_CONFIRMATION)
                .target(States.CLIENT_INFORMED)
                .and()
                .withExternal()
                .source(States.CLIENT_INFORMED)
                .event(PREPARE_TO_SUBMIT)
                .target(States.READY_TO_SUBMIT)
                .action(Actions.aAction())
                .guard(Guards.checkDocuments(Arrays.asList(2, 3)))
                .and()
                .withExternal()
                .source(States.READY_TO_SUBMIT)
                .event(SUBMIT_APPLICATION)
                .target(States.FORMAL)
                .and()

                // formal
                .withExternal()
                .source(States.FORMAL_IN_PROGRESS)
                .event(REGISTER_OFFICE_REQUEST)
                .target(States.OFFICE_REQUEST_ON_FORMAL)
                .and()
                .withInternal()
                .source(States.OFFICE_REQUEST_ON_FORMAL)
                .event(EXTEND_REQUEST)
                .and()
                .withExternal()
                .source(States.OFFICE_REQUEST_ON_FORMAL)
                .event(REPLY_TO_OFFICE_REQUEST)
                .target(States.FORMAL_IN_PROGRESS)
                .and()
                .withExternal()
                .source(States.FORMAL_IN_PROGRESS)
                .event(FORMAL_COMPLETE)
                .target(States.FORMAL_COMPLETED)
                .and()
                .withExternal()
                .source(States.FORMAL_COMPLETED)
                .event(SUBMIT_APPLICATION)
                .target(States.ESSENTIAL)
                .and()
                .withLocal()
                .source(States.FORMAL)
                .event(WITHDRAW)
                .target(States.APPLICATION_WITHDRAWED_ON_FORMAL)
                .and()
                .withExternal()
                .source(States.APPLICATION_WITHDRAWED_ON_FORMAL)
                .event(RESTORE)
                .target(States.FORMAL_IN_PROGRESS)
                .and()

                // essential
                .withExternal()
                .source(States.ESSENTIAL_IN_PROGRESS)
                .event(REGISTER_OFFICE_REQUEST)
                .target(States.OFFICE_REQUEST_ON_ESSENTIAL)
                .and()
                .withInternal()
                .source(States.OFFICE_REQUEST_ON_ESSENTIAL)
                .event(EXTEND_REQUEST)
                .and()
                .withExternal()
                .source(States.OFFICE_REQUEST_ON_ESSENTIAL)
                .event(REPLY_TO_OFFICE_REQUEST)
                .target(States.ESSENTIAL_IN_PROGRESS)
                .and()
                .withExternal()
                .source(States.ESSENTIAL_IN_PROGRESS)
                .event(REGISTER_GRANTING)
                .target(States.PATENT_GRANTED)
                .and()
                .withExternal()
                .source(States.ESSENTIAL_IN_PROGRESS)
                .event(REGISTER_DENY)
                .target(States.PATENT_DENIED)
                .and()
                .withExternal()
                .source(States.PATENT_DENIED)
                .event(SUBMIT_APPEAL)
                .target(States.ESSENTIAL_IN_PROGRESS)
                .and()
                .withExternal()
                .source(States.PATENT_GRANTED)
                .event(PATENT_RECEIVE)
                .target(States.READY_TO_RECEIVE)
                .and()
                .withLocal()
                .source(States.ESSENTIAL)
                .event(WITHDRAW)
                .target(States.APPLICATION_WITHDRAWED_ON_FORMAL)
                .and()
                .withExternal()
                .source(States.APPLICATION_WITHDRAWED_ON_FORMAL)
                .event(RESTORE)
                .target(States.ESSENTIAL_IN_PROGRESS)
                .and()

                // поддержание
                .withExternal()
                .source(States.READY_TO_RECEIVE)
                .event(RECEIVE_PATENT)
                .target(States.PATENT_READY)
                .and()
                .withInternal()
                .source(States.PATENT_READY)
                .event(SEND_CERTIFICATE)
                .and()
                .withInternal()
                .source(States.PATENT_READY)
                .event(PAY_ANNUAL_FEE)
                .and()
                .withInternal()
                .source(States.PATENT_READY)
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

