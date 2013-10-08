package se.dat255.bulletinferno.model.weapon;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;

import com.badlogic.gdx.utils.Pool;

public class WeaponEnvironmentImpl implements WeaponEnvironment {
	
	private final PhysicsEnvironment physics;
	
	private final List<Projectile> projectiles = new ArrayList<Projectile>();
	private final Map<Class<? extends Projectile>, Pool<Projectile>> projectilePools = 
			new HashMap<Class<? extends Projectile>, Pool<Projectile>>();
	
	public WeaponEnvironmentImpl(PhysicsEnvironment physics) {
		this.physics = physics;
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
					return type.getConstructor(PhysicsEnvironment.class, WeaponEnvironment.class)
							.newInstance(physics, WeaponEnvironmentImpl.this);
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

}