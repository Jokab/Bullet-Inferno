package se.dat255.bulletinferno.model.mock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.entity.Enemy;
import se.dat255.bulletinferno.model.entity.PlayerShip;
import se.dat255.bulletinferno.model.map.Obstacle;
import se.dat255.bulletinferno.model.map.Segment;
import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;
import se.dat255.bulletinferno.model.weapon.Projectile;
import se.dat255.bulletinferno.util.Timer;

public class SimpleMockGame implements Game {
	public final Projectile mockProjectile = new SimpleMockProjectile(null);
	public int numProjectilesSpawned = 0;
	public Timer timer;
	
	public List<Runnable> runLaters = new ArrayList<Runnable>();
	
	/**
	 * We are using the "real" physicsImpl here as creating our own would be
	 * close to impossible
	 */
	public PhysicsWorldImplSpy physicsWorld = new PhysicsWorldImplSpy();

	public SimpleMockGame(Timer timer) {
		this.timer = timer;
	}

	public SimpleMockGame() {
		this(new SimpleMockTimer());
	}


	@Override
	public void update(float delta) {
		for(Runnable task : runLaters) {
			task.run();
		}
	}

	@Override
	public List<? extends Segment> getSegments() {
		return Collections.<Segment> emptyList();
	}


	@Override
	public void runLater(Runnable task) {
		runLaters.add(task);
	}
}
