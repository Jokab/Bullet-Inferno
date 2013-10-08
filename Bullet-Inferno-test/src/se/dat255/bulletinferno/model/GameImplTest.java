package se.dat255.bulletinferno.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import se.dat255.bulletinferno.model.mock.SimpleMockProjectile;
import se.dat255.bulletinferno.model.mock.SimplePlayerShipMock;
import se.dat255.bulletinferno.model.weapon.Projectile;
import se.dat255.bulletinferno.test.Common;

public class GameImplTest {

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
		GameImpl game = new GameImpl(null);
		assertNotNull("A timer should be returned from the getTimer method",
				game.getTimer());
	}

	@Test
	public void testRunLater() {
		// Tests that runLater tasks are run the next update tick.
		
		
		GameImpl game = new GameImpl();
		game.setPlayerShip(new SimplePlayerShipMock());

		RunnableSpy task = new RunnableSpy();

		game.runLater(task);

		assertFalse("The task should not be run when it is first added.", task.wasRun);

		game.update(1);

		assertTrue("The task should have been run after a call to game.update", task.wasRun);
	}

}
