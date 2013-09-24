package se.dat255.bulletinferno.model.mock;

import java.util.List;

import se.dat255.bulletinferno.model.Enemy;
import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.Obstacle;
import se.dat255.bulletinferno.model.PhysicsWorld;
import se.dat255.bulletinferno.model.PhysicsWorldImpl;
import se.dat255.bulletinferno.model.PlayerShip;
import se.dat255.bulletinferno.model.Projectile;
import se.dat255.bulletinferno.model.Timer;

import com.badlogic.gdx.utils.GdxNativesLoader;

public class SimpleMockGame implements Game {
	static {
		GdxNativesLoader.load();
	}

	public final Projectile mockProjectile = new SimpleMockProjectile(null);
	public int numProjectilesSpawned = 0;
	public Timer timer;

	/**
	 * We are using the "real" physicsImpl here as creating our own would be
	 * close to impossible
	 */
	public PhysicsWorld physicsWorld = new PhysicsWorldImpl();

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

}
