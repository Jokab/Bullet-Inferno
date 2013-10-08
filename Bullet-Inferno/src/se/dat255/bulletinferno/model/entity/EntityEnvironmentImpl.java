package se.dat255.bulletinferno.model.entity;

import java.util.ArrayList;
import java.util.List;

import se.dat255.bulletinferno.model.entity.PlayerShipImpl.ShipType;
import se.dat255.bulletinferno.model.loadout.Loadout;
import se.dat255.bulletinferno.model.loadout.LoadoutImpl;
import se.dat255.bulletinferno.model.loadout.PassiveAbilityImpl;
import se.dat255.bulletinferno.model.loadout.PassiveReloadingTime;
import se.dat255.bulletinferno.model.loadout.SpecialAbilityImpl;
import se.dat255.bulletinferno.model.loadout.SpecialProjectileRain;
import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;
import se.dat255.bulletinferno.model.weapon.WeaponData;
import se.dat255.bulletinferno.model.weapon.WeaponEnvironment;

import com.badlogic.gdx.math.Vector2;

public class EntityEnvironmentImpl implements EntityEnvironment {
	
	private final List<Enemy> enemies = new ArrayList<Enemy>();
	private final PlayerShip playerShip;
	private final PhysicsEnvironment physics;
	private final WeaponEnvironment weapons;
	
	public EntityEnvironmentImpl(PhysicsEnvironment physics, WeaponEnvironment weapons,
			WeaponData[] weaponData) {
		this.physics = physics;
		this.weapons = weapons;
		WeaponData[] weaponTypes = weaponData;
		
		
		Loadout loadout = new LoadoutImpl(weaponTypes[0].getPlayerWeaponForGame(physics, weapons),
				weaponTypes[1].getPlayerWeaponForGame(physics, weapons), 
				new SpecialAbilityImpl(new SpecialProjectileRain(physics, weapons)), 
				new PassiveAbilityImpl(new PassiveReloadingTime(0.5f)));
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
