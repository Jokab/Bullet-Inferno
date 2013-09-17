package se.dat255.bulletinferno.units.particles;

public interface Projectile {
	/**
	 * Returns the damage of the projectile
	 * @return damage
	 */
	public int damage();
	
	/**
	 * To be run on impact with hit target
	 * @param target
	 */
	public void onImpact(Object target);
	
}