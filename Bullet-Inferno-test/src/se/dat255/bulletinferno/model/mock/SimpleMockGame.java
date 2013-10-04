package se.dat255.bulletinferno.model.mock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

import se.dat255.bulletinferno.model.Enemy;
import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.Obstacle;
import se.dat255.bulletinferno.model.PhysicsWorld;
import se.dat255.bulletinferno.model.PlayerShip;
import se.dat255.bulletinferno.model.Projectile;
import se.dat255.bulletinferno.model.map.Segment;
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
	public List<? extends Projectile> getProjectiles() {
		return null;
	}

	@Override
	public Projectile retrieveProjectile(Class<? extends Projectile> type) {
		numProjectilesSpawned++;
		return this.mockProjectile;
	}

	@Override
	public void disposeProjectile(Projectile projectile) {
		projectile.reset(); // Emulate pool.
	}

	@Override
	public PlayerShip getPlayerShip() {
		return null;
	}

	@Override
	public List<? extends Obstacle> getObstacles() {
		return null;
	}

	@Override
	public List<? extends Enemy> getEnemies() {
		return null;
	}

	@Override
	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	@Override
	public void update(float delta) {
		for(Runnable task : runLaters) {
			task.run();
		}
	}

	@Override
	public PhysicsWorld getPhysicsWorld() {
		return physicsWorld;
	}

	@Override
	public void setPlayerShip(PlayerShip ship) {
	}

	@Override
	public void addEnemy(Enemy emeny) {
	}

	@Override
	public void removeEnemy(Enemy enemy) {
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<? extends Segment> getSegments() {
		return Collections.<Segment> emptyList();
	}

	@Override
	public int getRemovedSegmentCount() {
		return 0;
	}

	@Override
	public void setViewport(Vector2 viewportPosition, Vector2 viewportDimensions) {
		// NOP
	}

	@Override
	public void runLater(Runnable task) {
		runLaters.add(task);
	}

	@Override
	public void bossDead() {
		// TODO Auto-generated method stub
		
	}
}
