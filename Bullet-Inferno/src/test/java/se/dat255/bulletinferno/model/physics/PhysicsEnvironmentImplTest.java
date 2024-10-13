package se.dat255.bulletinferno.model.physics;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import se.dat255.bulletinferno.test.Common;

public class PhysicsEnvironmentImplTest {

	class RunnableSpy implements Runnable {
		public boolean wasRun = false;

		@Override
		public void run() {
			wasRun = true;
		}
	}

	@BeforeClass
	public static void beforeTests() {
		Common.loadEssentials();
	}



	@Test
	public void testGetTimer() {
		PhysicsEnvironmentImpl physics = new PhysicsEnvironmentImpl();
		assertNotNull("A timer should be returned from the getTimer method",
				physics.getTimer());
		
	}

	@Test
	public void testRunLater() {
		// Tests that runLater tasks are run the next update tick.
				
		PhysicsEnvironmentImpl physics = new PhysicsEnvironmentImpl();

		RunnableSpy task = new RunnableSpy();

		physics.runLater(task);

		assertFalse("The task should not be run when it is first added.", task.wasRun);
		
		physics.update(1);

		assertTrue("The task should have been run after a call to game.update", task.wasRun);
	}

}
