package patterra.bp.invention.config.sm;

import org.springframework.statemachine.action.Action;

public class Actions {

    public static Action<States, Events> aAction() {
        return context -> {
            System.out.println("hey!");
            context.getExtendedState().getVariables().put("q", 1L);
        };
    }

}
