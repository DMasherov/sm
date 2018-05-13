package patterra.bp.statemachineconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.config.configurers.StateConfigurer;

@EnableStateMachineFactory
public class InventionBPConfigFactory
        extends StateMachineConfigurerAdapter<InventionStates, UserEvents> {
    @Override
    public void configure(StateMachineStateConfigurer<InventionStates, UserEvents> config) throws Exception {
        config
            .withStates()
                .initial(DEFAULT_BP, aAction())
                .state(REVOKED)
                .end(END)
                .and()
                .withStates()
                    .parent(DEFAULT_BP)
                    .initial(A)
                    .state(A)
                    .state(B)
                    .state(C)
                    .history(HISTORY, StateConfigurer.History.SHALLOW);

    }

    @Bean
    public Action<InventionStates, UserEvents> aAction() {
        return context -> {
            System.out.println("hey!");
            context.getExtendedState().getVariables().put("qweqwe", 1L);
        };
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<InventionStates, UserEvents> config) throws Exception {
        config
            .withExternal()
                .source(A)
                .target(B)
                .event(E1)
                .action(aAction())
                .and()
            .withExternal()
                .source(B)
                .target(C)
                .event(E2)
                .and()
            .withExternal()
                .source(C)
                .target(D)
                .event(E3)
                .and()
            .withExternal()
                .source(DEFAULT_BP)
                .target(REVOKED)
                .event(REVOKE)
                .and()
            .withExternal()
                .source(REVOKED)
                .target(HISTORY)
                .event(RESUME)
                .and()
           .withExternal()
                .source(DEFAULT_BP)
                .target(END)
                .event(E1);
    }
}

