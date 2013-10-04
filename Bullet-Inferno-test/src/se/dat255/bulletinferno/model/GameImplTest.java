package se.dat255.bulletinferno.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import se.dat255.bulletinferno.model.mock.SimpleMockProjectile;
import se.dat255.bulletinferno.model.mock.SimplePlayerShipMock;
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
	public void testRetrieveProjectile() {
		// Tests that retrieving a projectile returns the correct
		// type of projectile and that the projectile is added
		// to the current projectiles.

		GameImpl game = new GameImpl(null);
		assertTrue("The list of projectiles of a new game should be empty",
				game.getProjectiles().isEmpty());

		Projectile projectile = game
				.retrieveProjectile(SimpleMockProjectile.class);
		assertTrue(
				"retrieveProjectile should return a Projectile of the wanted class-type",
				projectile.getClass() == SimpleMockProjectile.class);

		assertTrue("The projectile should be added to the list of projectiles",
				game.getProjectiles().contains(projectile));

	}

	@Test
	public void testDisposeProjectile() {
		GameImpl game = new GameImpl(null);
		Projectile projectile = game
				.retrieveProjectile(SimpleMockProjectile.class);
		assertTrue("The projectile should be added to the list of projectiles",
				game.getProjectiles().contains(projectile));

		game.disposeProjectile(projectile);
		assertTrue("The list of projectiles of a new game should be empty",
				game.getProjectiles().isEmpty());
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
