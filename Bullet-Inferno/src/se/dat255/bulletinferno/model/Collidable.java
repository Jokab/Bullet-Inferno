package se.dat255.bulletinferno.model;

import com.badlogic.gdx.math.Vector2;

public interface Collidable {
	/**
	 * Returns true if the point is inside the object's bounding box.
	 * 
	 * @param point The point to be checked for intersection.
	 * @return True or false.
	 */
	public boolean intersects(Vector2 point);

	/**
	 * Is to be called by some kind of Physics engine on an object with whom the object
	 * collided. The object then takes the appropriate action.
	 * 
	 * @param other The object that was collided with.
	 */
	public void collided(Collidable other);
}
