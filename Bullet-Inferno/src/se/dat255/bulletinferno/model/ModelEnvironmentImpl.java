package se.dat255.bulletinferno.model;

import java.util.List;

import se.dat255.bulletinferno.model.entity.Enemy;
import se.dat255.bulletinferno.model.entity.PlayerShip;
import se.dat255.bulletinferno.model.map.MapEnvironment;
import se.dat255.bulletinferno.model.map.MapEnvironmentImpl;
import se.dat255.bulletinferno.model.map.Segment;
import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;
import se.dat255.bulletinferno.model.physics.PhysicsEnvironmentImpl;
import se.dat255.bulletinferno.model.weapon.Projectile;
import se.dat255.bulletinferno.model.weapon.WeaponData;

import com.badlogic.gdx.math.Vector2;

public class ModelEnvironmentImpl implements ModelEnvironment {

	private final PhysicsEnvironment physics;
	private final MapEnvironment map;

	public ModelEnvironmentImpl(WeaponData[] weaponData) {
		physics = new PhysicsEnvironmentImpl();
		map = new MapEnvironmentImpl(physics, weaponData);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setViewport(Vector2 viewportPosition, Vector2 viewportDimensions) {
		physics.setViewport(viewportPosition, viewportDimensions);
		map.setViewport(viewportPosition, viewportDimensions);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(float deltaTime) {
		physics.update(deltaTime);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
		physics.dispose();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlayerShip getPlayerShip() {
		return map.getEntityEnvironment().getPlayerShip();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<? extends Segment> getSegments() {
		return map.getSegments();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getRemovedSegmentCount() {
		return map.getRemovedSegmentCount();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<? extends Enemy> getEnemies() {
		return map.getEntityEnvironment().getEnemies();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<? extends Projectile> getProjectiles() {
		return map.getProjectiles();
	}

}