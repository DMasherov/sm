package patterra.bp.invention.config.sm;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.state.State;
import patterra.bp.DbStateMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class InventionStateMapper implements DbStateMapper<States, Events> {
    @Override
    public States fromDb(String dbState) {
        String[] stateParts = dbState.split("-");
        String lastState = stateParts[stateParts.length - 1];
        return States.valueOf(lastState);
    }

    @Override
    public String fromStateMachine(State<States, Events> smState) {
        List<States> stateIds = new ArrayList<>(smState.getIds());
        return stateIds.stream()
                .map(Enum::name)
                .collect(Collectors.joining("-"));
    }
}


