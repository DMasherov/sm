package patterra.bp.config;

import org.springframework.statemachine.action.Action;

public class InventionActions {

    public static Action<InventionStates, InventionEvents> aAction() {
        return context -> {
            System.out.println("hey!");
            context.getExtendedState().getVariables().put("q", 1L);
        };
    }

}
