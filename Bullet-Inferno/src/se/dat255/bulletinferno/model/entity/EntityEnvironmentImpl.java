package se.dat255.bulletinferno.model.entity;

import java.util.ArrayList;
import java.util.List;

import se.dat255.bulletinferno.model.entity.PlayerShipImpl.ShipType;
import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;
import se.dat255.bulletinferno.model.weapon.WeaponEnvironment;
import se.dat255.bulletinferno.model.weapon.WeaponLoadout;

import com.badlogic.gdx.math.Vector2;

public class EntityEnvironmentImpl implements EntityEnvironment {
	
	private final List<Enemy> enemies = new ArrayList<Enemy>();
	private final PlayerShip playerShip;
	private final PhysicsEnvironment physics;
	private final WeaponEnvironment weapons;
	
	public EntityEnvironmentImpl(PhysicsEnvironment physics, WeaponEnvironment weapons,
			WeaponLoadout weaponLoadout) {
		this.physics = physics;
		this.weapons = weapons;

		playerShip = new PlayerShipImpl(physics, this, new Vector2(0, 0), 1000000, weaponLoadout,
				ShipType.PLAYER_DEFAULT);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addEnemy(Enemy enemy) {
		enemies.add(enemy);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<? extends Enemy> getEnemies() {
		return enemies;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlayerShip getPlayerShip() {
		return playerShip;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeEnemy(Enemy enemy) {
		enemies.remove(enemy);
	}

}
