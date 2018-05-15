package patterra.bp.config;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;
import patterra.domain.Invention;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class InventionGuards {

    private static Invention getInvention(StateContext context) {
        Object obj = context.getExtendedState().getVariables().get("invention");
        if (obj instanceof Invention) {
            return (Invention) obj;
        }
        throw new IllegalStateException("No invention object in the state machine!");
    }

    public static Guard<InventionStates, InventionEvents> checkDocuments(Collection<Integer> documentIds) {
        return context -> {
            Invention invention = getInvention(context);
            Set<Integer> inventionDocIds = invention.getDocuments().stream()
                    .map(d -> d.getId())
                    .collect(Collectors.toSet());
            return inventionDocIds.containsAll(documentIds);
        };
    }
}
