package se.dat255.bulletinferno.model.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * A PhysicsBody represents an instance of a simulated physics entity.
 */
public interface PhysicsBody {

	/**
	 * @return the body position right now (in world coordinates).
	 */
	public Vector2 getPosition();

	/**
	 * Sets the (linear) velocity of the body.
	 * 
	 * @param velocity
	 *        the velocity to set.
	 */
	public void setVelocity(Vector2 velocity);

	/**
	 * @return the velocity of the body.
	 */
	public Vector2 getVelocity();

	/**
	 * Returns the wrapped Box2d body
	 * 
	 * @see Body
	 * @return Box2D body
	 */
	public Body getBox2DBody();
	
	/**
	 * Returns the dimensions of the the box surrounding the body.
	 * For example: 
	 * 		If the body is in the shape of a circle with the radius 2
	 * 		this method returns the box around the circle. This box will have a
	 * 		width and a height of 4. (The diameter in each direction).
	 * @return dimension of body
	 */
	public Vector2 getDimensions();

}