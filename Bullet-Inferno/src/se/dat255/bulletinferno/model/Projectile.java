package se.dat255.bulletinferno.model;

public interface Projectile extends Collidable {
	/**
	 * Returns the damage of the projectile
	 * 
	 * @return damage
	 */
	public int getDamage();

}