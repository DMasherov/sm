package patterra.bp.service;

import org.springframework.stereotype.Component;
import patterra.bp.config.InventionEvents;
import patterra.bp.config.InventionStates;
import patterra.domain.Invention;

import java.util.Collections;
import java.util.Map;

@Component
public class InventionStateMachineService
        extends StateMachineService<InventionStates, InventionEvents> {
    @Override
    public Map<Object, Object> variablesForBP() {
        Invention invention = new Invention();
        invention.setId(1);
        invention.setDocuments(Collections.emptySet());
        return Collections.singletonMap("invention", invention);
    }
}
