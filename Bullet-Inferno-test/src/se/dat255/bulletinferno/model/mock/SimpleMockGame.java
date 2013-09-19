package se.dat255.bulletinferno.model.mock;

import java.util.List;

import se.dat255.bulletinferno.model.*;


public class SimpleMockGame implements Game {
	public final Projectile mockProjectile = new SimpleMockProjectile(null);
	public int numProjectilesSpawned = 0;
    public Timer timer = new TimerImpl(20);

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

	@Override
	public void update(float delta) {
	}

}
