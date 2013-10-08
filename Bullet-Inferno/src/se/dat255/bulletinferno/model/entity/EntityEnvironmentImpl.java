package se.dat255.bulletinferno.model.entity;

import java.util.ArrayList;
import java.util.List;

import se.dat255.bulletinferno.model.entity.PlayerShipImpl.ShipType;
import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;
import se.dat255.bulletinferno.model.weapon.WeaponDefinitionImpl;
import se.dat255.bulletinferno.model.weapon.WeaponEnvironment;
import se.dat255.bulletinferno.model.weapon.WeaponLoadout;
import se.dat255.bulletinferno.model.weapon.WeaponLoadoutImpl;

import com.badlogic.gdx.math.Vector2;

public class EntityEnvironmentImpl implements EntityEnvironment {
	
	private final List<Enemy> enemies = new ArrayList<Enemy>();
	private PlayerShip playerShip;
	private PhysicsEnvironment physics;
	private WeaponEnvironment weapons;
	
	public EntityEnvironmentImpl(PhysicsEnvironment physics, WeaponEnvironment weapons,

			WeaponLoadout weaponLoadout) {
		this.physics = physics;
		this.weapons = weapons;
		WeaponLoadout loadout = weaponLoadout;
		
		playerShip = new PlayerShipImpl(physics, this, new Vector2(0, 0), 1000000, loadout, ShipType.PLAYER_DEFAULT);
		this.physics = physics;
		this.weapons = weapons;

		playerShip = new PlayerShipImpl(physics, this, new Vector2(0, 0), 1000000, loadout,
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
