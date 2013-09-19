package se.dat255.bulletinferno.model;


public interface Collidable {
	/**
	 * Is to be called by some kind of Physics engine on an object with whom the object
	 * collided. The object then takes the appropriate action.
	 * 
	 * @param other The object that was collided with.
	 */
	public void collided(Collidable other);
}
