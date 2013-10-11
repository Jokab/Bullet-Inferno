package se.dat255.bulletinferno.model;

/**
 * Default implementation of Game, the central type in Bullet Inferno.
 * 
 * <p>
 * Game acts as a single point of entry for the outside environment, as well as central point of
 * lookup for the inside. It handles instance-based object creation and initialization (injection).
 */
public class GameImpl implements Game {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
	}

}