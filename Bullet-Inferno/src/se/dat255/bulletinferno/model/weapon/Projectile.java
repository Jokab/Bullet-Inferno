package se.dat255.bulletinferno.model.weapon;

import se.dat255.bulletinferno.model.physics.Collidable;
import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinition;
import se.dat255.bulletinferno.model.physics.PositionEntity;
import se.dat255.bulletinferno.model.team.Teamable;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

public interface Projectile extends PositionEntity, Collidable, Poolable {
	/**
	 * Returns the damage of the Projectile.
	 * 
	 * @return damage The damage that this Projectile deals.
	 */
	public float getDamage();

	/**
	 * Initializes the projectile and attaches a specific movement pattern. 
	 * Call upon acquiring from the Pool.
	 * 
	 * @param position
	 *        the initial position.
	 * @param velocity
	 *        the initial velocity.
	 * @param damage
	 *        the projectile damage coefficient.
	 * @param source
	 *        the team source from which it was fired.
	 * @param bodyDefinition
	 *        the size of the body (definition)
	 */
	public void init(ProjectileType type, Vector2 position, Vector2 velocity, float damage,
			Teamable source, PhysicsBodyDefinition bodyDefinition);

	/**
	 * Sets the velocity of the projectile
	 * 
	 * @param velocity The new velocity of the projectile.
	 */
	public void setVelocity(Vector2 velocity);

	/**
	 * Gets the position of the projectile
	 */
	public Vector2 getPosition();

	/**
	 * Returns the teamable source from which it was fired.
	 * 
	 * @return source
	 */
	public Teamable getSource();
	
	public ProjectileType getType();

}