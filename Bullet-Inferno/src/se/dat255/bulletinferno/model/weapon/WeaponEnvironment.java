package se.dat255.bulletinferno.model.weapon;

import java.util.List;

public interface WeaponEnvironment {
	
	/**
	 * Returns a list of all projectiles in the game
	 * 
	 * @return a list of projectiles currently in the game.
	 */
	public List<? extends Projectile> getProjectiles();

	/**
	 * Retrieve a projectile
	 */
	public Projectile retrieveProjectile(Class<? extends Projectile> type);

	/**
	 * Dispose of the specified projectile
	 * 
	 * @param projectile
	 *        The projectile reference to dispose.
	 */
	public void disposeProjectile(Projectile projectile);
	
}