package patterra.bp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.test.context.junit4.SpringRunner;
import patterra.bp.invention.config.sm.Events;
import patterra.bp.invention.config.sm.States;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BpInventionTests {

	@Autowired
	StateMachineFactory<States, Events> inventionBPFactory;

	@Test
	public void contextLoads() {
	}

	@Test
	public void test() {
		StateMachine<States, Events> stateMachine = inventionBPFactory.getStateMachine();

		stateMachine.start();
//
//		Assert.assertEquals(A, stateMachine.getState().getId());
//		stateMachine.sendEvent(E1);
//		Assert.assertEquals(B, stateMachine.getState().getId());
//		stateMachine.sendEvent(E2);
//		Assert.assertEquals(C, stateMachine.getState().getId());
	}
}
