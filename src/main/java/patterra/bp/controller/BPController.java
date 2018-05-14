package patterra.bp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.state.State;
import org.springframework.stereotype.Component;
import patterra.domain.GroupType;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class BPController<S, E> {
    @Autowired
    private StateMachineFactory<S, E> factory;

    @Autowired
    private Map<GroupType, Set<E>> group2Events;

    private StateMachine<S,E> sm;

    public StateMachine<S, E> getStateMachine() {
        if (sm == null) {
            sm = factory.getStateMachine();
        }
        return sm;
    }

    public Stream<E> getTriggeringEvents(@NotNull S stateId) {
        return getStateMachine().getTransitions().stream()
                .filter(t -> stateId.equals(t.getSource().getId()))
                .map(t -> t.getTrigger().getEvent());
    }

    public List<E> getTriggeringEvents() {
        State<S, E> state = getStateMachine().getState();
        if (state == null) {
            return null;
        }
        return state.getIds().stream()
                .flatMap(this::getTriggeringEvents)
                .collect(Collectors.toList());
    }

    public Stream<E> getEventsAvailableToGroup(@NotNull S stateId, GroupType groupType) {
        Set<E> groupEvents = group2Events.get(groupType);
        return getTriggeringEvents(stateId)
                .filter(groupEvents::contains);
    }

    public List<E> getEventsAvailableToGroup(GroupType groupType) {
        State<S, E> state = getStateMachine().getState();
        if (state == null) {
            return null;
        }
        return state.getIds().stream()
                .flatMap(id -> this.getEventsAvailableToGroup(id, groupType))
                .collect(Collectors.toList());
    }

    public Set<E> getAllEvents() {
        return getStateMachine().getTransitions().stream()
                .map(t -> t.getTrigger().getEvent())
                .collect(Collectors.toSet());
    }

    public Collection<S> getCurrentStates() {
        State<S, E> state = getStateMachine().getState();
        return state != null ? state.getIds() : null;
    }
}

