package patterra.bp;

import org.springframework.statemachine.state.State;

/**
 * Интерфейс для сериализации состояний из базы данных в State
 * для Spring State Machine и обратно.
 */
public interface DbStateMapper<S, E> {
    S fromDb(String s);
    String fromStateMachine(State<S, E> state);
}
