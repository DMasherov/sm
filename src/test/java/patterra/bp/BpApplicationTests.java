package patterra.bp;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.test.context.junit4.SpringRunner;
import patterra.bp.statemachineconfig.UserEvents;
import patterra.bp.statemachineconfig.InventionStates;

import static patterra.bp.statemachineconfig.UserEvents.E1;
import static patterra.bp.statemachineconfig.UserEvents.E2;
import static patterra.bp.statemachineconfig.InventionStates.A;
import static patterra.bp.statemachineconfig.InventionStates.B;
import static patterra.bp.statemachineconfig.InventionStates.C;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BpApplicationTests {

	@Autowired
	StateMachineFactory<InventionStates, UserEvents> inventionBPFactory;

	@Test
	public void contextLoads() {
	}

	@Test
	public void test() {
		StateMachine<InventionStates, UserEvents> stateMachine = inventionBPFactory.getStateMachine();

		stateMachine.start();

		Assert.assertEquals(A, stateMachine.getState().getId());
		stateMachine.sendEvent(E1);
		Assert.assertEquals(B, stateMachine.getState().getId());
		stateMachine.sendEvent(E2);
		Assert.assertEquals(C, stateMachine.getState().getId());
	}
}
