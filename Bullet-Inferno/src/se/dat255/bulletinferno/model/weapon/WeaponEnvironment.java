package se.dat255.bulletinferno.model.weapon;

import java.util.List;

public interface WeaponEnvironment {

	/**
	 * Returns a list of all projectiles in the game
	 * 
	 * @return a list of projectiles currently in the game.
	 */
	public List<? extends ProjectileDefinition> getProjectiles();

	/**
	 * Retrieve a projectile
	 */
	public ProjectileDefinition retrieveProjectile(Class<? extends ProjectileDefinition> type);

	/**
	 * Dispose of the specified projectile
	 * 
	 * @param projectile
	 *        The projectile reference to dispose.
	 */
	public void disposeProjectile(ProjectileDefinition projectile);

}