package patterra.bp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.access.StateMachineAccess;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Service;
import patterra.bp.DbStateMapper;
import patterra.bp.StateMachineFacade;
import patterra.bp.invention.config.tasks.InventionTask;
import patterra.bp.tasks.TaskRepository;
import patterra.domain.Invention;
import patterra.domain.RoleType;
import patterra.domain.User;
import patterra.domain.repos.InventionRepository;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BPService<S, E> {
    @Autowired
    private StateMachineFacade<S, E> smf;

    @Autowired
    private InventionRepository inventionRepository;

    @Autowired
    private TaskRepository<InventionTask> taskRepository;

    @Autowired
    private DbStateMapper<S, E> stateIdMapper;

    @Autowired
    private Map<RoleType, Set<E>> role2OperationMapping;

    public Collection<E> getOperations(Invention invention) {
        S stateMachineState = stateIdMapper.fromDb(invention.getStateId());
        smf.initialize(stateMachineState);
        return smf.getTriggeringEvents();
    }

    public Collection<E> getOperations(User user, Invention invention) {
        Collection<E> operations = getOperations(invention);

        Set<E> allowedOperations = user.getRoles().stream()
                .flatMap(r -> role2OperationMapping.get(r).stream())
                .collect(Collectors.toSet());

        operations.retainAll(allowedOperations);

        return operations;
    }

    public boolean runEvent(Invention invention, E event) {
        S stateMachineState = stateIdMapper.fromDb(invention.getStateId());
        smf.initialize(
                stateMachineState,
                (StateMachineAccess<S, E> sma) -> {
                    sma.addStateMachineInterceptor(updateDbAfterStateChange(invention));
                });
        return smf.sendEvent(event);
    }

    private StateMachineInterceptorAdapter<S, E> updateDbAfterStateChange(Invention invention) {
        return new StateMachineInterceptorAdapter<S, E>() {
            @Override
            public void preStateChange(State<S, E> state, Message<E> message, Transition<S, E> transition, StateMachine<S, E> stateMachine) {
                invention.setStateId(stateIdMapper.fromStateMachine(state));
                inventionRepository.update(invention);
            }
        };
    }
}
