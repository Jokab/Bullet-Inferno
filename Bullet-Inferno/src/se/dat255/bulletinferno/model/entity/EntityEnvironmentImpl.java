package se.dat255.bulletinferno.model.entity;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.dat255.bulletinferno.model.entity.PlayerShipImpl.ShipType;
import se.dat255.bulletinferno.model.loadout.Loadout;
import se.dat255.bulletinferno.model.loadout.LoadoutImpl;
import se.dat255.bulletinferno.model.loadout.PassiveAbilityImpl;
import se.dat255.bulletinferno.model.loadout.PassiveReloadingTime;
import se.dat255.bulletinferno.model.loadout.SpecialAbilityImpl;
import se.dat255.bulletinferno.model.loadout.SpecialProjectileRain;
import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;
import se.dat255.bulletinferno.model.weapon.Projectile;
import se.dat255.bulletinferno.model.weapon.WeaponData;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class EntityEnvironmentImpl implements EntityEnvironment {

	private final List<Projectile> projectiles = new ArrayList<Projectile>();
	private final Map<Class<? extends Projectile>, Pool<Projectile>> projectilePools;
	
	private final List<Enemy> enemies = new ArrayList<Enemy>();
	private final PlayerShip playerShip;
	private final PhysicsEnvironment physics;
	public EntityEnvironmentImpl(PhysicsEnvironment physics, WeaponData weaponType) {
		projectilePools = new HashMap<Class<? extends Projectile>, Pool<Projectile>>();
		this.physics = physics;
		
		Loadout loadout = new LoadoutImpl(weaponType.getPlayerWeaponForGame(physics, this), 
				null, 
				new SpecialAbilityImpl(new SpecialProjectileRain(physics, this)), 
				new PassiveAbilityImpl(new PassiveReloadingTime(0.5f)));
		playerShip = new PlayerShipImpl(physics, this, new Vector2(0, 0), 1000000, loadout,
				ShipType.PLAYER_DEFAULT);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<? extends Projectile> getProjectiles() {
		return projectiles;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Projectile retrieveProjectile(Class<? extends Projectile> type) {
		// If pool for specified type of projectile doesn't exist
		// create a new pool and but it in the map
		if (!projectilePools.containsKey(type)) {
			projectilePools.put(type, createNewPool(type));
		}

		// Get a projectile from the pool
		Projectile p = projectilePools.get(type).obtain();
		projectiles.add(p);
		return p;
	}

	/** {@inheritDoc} */
	@Override
	public void disposeProjectile(Projectile projectile) {
		projectiles.remove(projectile);
		if (projectilePools.containsKey(projectile.getClass())) {
			projectilePools.get(projectile.getClass()).free(projectile);
		}
	}

	private Pool<Projectile> createNewPool(
			final Class<? extends Projectile> type) {
		return new Pool<Projectile>() {
			@Override
			protected Projectile newObject() {
				try {
					// Create an instance of the specified type
					return type.getConstructor(PhysicsEnvironment.class, EntityEnvironment.class)
							.newInstance(physics, EntityEnvironmentImpl.this);
				} catch (InstantiationException e) {
					throw new RuntimeException(e);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				} catch (NoSuchMethodException e) {
					throw new RuntimeException(e);
				} catch (SecurityException e) {
					throw new RuntimeException(e);
				} catch (IllegalArgumentException e) {
					throw new RuntimeException(e);
				} catch (InvocationTargetException e) {
					throw new RuntimeException(e);
				}
			}
		};
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
