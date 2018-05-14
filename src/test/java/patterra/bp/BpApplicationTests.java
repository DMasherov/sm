package patterra.bp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.test.context.junit4.SpringRunner;
import patterra.bp.config.InventionEvents;
import patterra.bp.config.InventionStates;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BpApplicationTests {

	@Autowired
	StateMachineFactory<InventionStates, InventionEvents> inventionBPFactory;

	@Test
	public void contextLoads() {
	}

	@Test
	public void test() {
		StateMachine<InventionStates, InventionEvents> stateMachine = inventionBPFactory.getStateMachine();

		stateMachine.start();
//
//		Assert.assertEquals(A, stateMachine.getState().getId());
//		stateMachine.sendEvent(E1);
//		Assert.assertEquals(B, stateMachine.getState().getId());
//		stateMachine.sendEvent(E2);
//		Assert.assertEquals(C, stateMachine.getState().getId());
	}
}
