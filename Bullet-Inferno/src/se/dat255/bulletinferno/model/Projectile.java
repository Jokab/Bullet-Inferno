package se.dat255.bulletinferno.model;

public interface Projectile extends Collidable {
	/**
	 * Returns the damage of the Projectile.
	 * 
	 * @return damage The damage that this Projectile deals.
	 */
	public int getDamage();

}