package patterra.bp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.access.StateMachineAccess;
import org.springframework.statemachine.access.StateMachineFunction;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class StateMachineFacade<S, E> {
    @Autowired
    private StateMachineFactory<S, E> factory;

    private StateMachine<S, E> sm;

    // from https://github.com/spring-tips/statemachine/
    public void initialize(S stateId, StateMachineFunction<StateMachineAccess<S, E>> strategy) {
        sm = factory.getStateMachine();
        sm.stop();
        if (stateId != null) {
            sm.getStateMachineAccessor().doWithAllRegions(strategy);
        }
        sm.start();
    }

    public void initialize(S stateId) {
        initialize(stateId, new StateMachineFunction<StateMachineAccess<S, E>>() {
            @Override
            public void apply(StateMachineAccess<S, E> sma) {
                sma.resetStateMachine(new DefaultStateMachineContext<>(stateId, null, null, null));
            }
        });
    }

    public void initialize() {
        initialize(null);
    }

    public StateMachine<S, E> getStateMachine() {
        if (sm == null) {
            initialize();
        }
        return sm;
    }

    public void setVariables(Map<Object, Object> variables) {
        sm.getExtendedState().getVariables().putAll(variables);
    }

    public Stream<E> getTriggeringEvents(@NotNull S stateId) {
        return getStateMachine().getTransitions().stream()
                .filter(t -> stateId.equals(t.getSource().getId()))
                .map(t -> t.getTrigger().getEvent());
    }

    public Collection<E> getTriggeringEvents() {
        State<S, E> state = getStateMachine().getState();
        if (state == null) {
            return null;
        }
        return state.getIds().stream()
                .flatMap(this::getTriggeringEvents)
                .collect(Collectors.toList());
    }

    public Collection<E> getAllEvents() {
        return getStateMachine().getTransitions().stream()
                .map(t -> t.getTrigger().getEvent())
                .collect(Collectors.toSet());
    }

    public Collection<S> getCurrentStateIds() {
        State<S, E> state = getStateMachine().getState();
        return state != null ? state.getIds() : null;
    }

    public boolean sendEvent(E e) {
        return getStateMachine().sendEvent(e);
    }

    // TODO сделать триггер без посылания события
    /**
     * Return all acceptable events.
     *
     * Sends events to the state machine and stores every accepted event.
     */
//    public Stream<E> getTriggeringApplicableEvents(@NotNull S stateId) {
//        getStateMachine().stop();
//        return getTriggeringEvents(stateId)
//                .filter(e -> getStateMachine().sendEvent(e));
//    }
//
//    public List<E> getTriggeringApplicableEvents() {
//        State<S, E> state = getStateMachine().getState();
//        if (state == null) {
//            return null;
//        }
//        return state.getIds().stream()
//                .flatMap(this::getTriggeringApplicableEvents)
//                .collect(Collectors.toList());
//    }
}

