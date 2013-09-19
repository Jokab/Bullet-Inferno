package se.dat255.bulletinferno.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** Interface for objects that are to be rendered
 *  by the graphics class.
 * @author Marc
 * @version 1.0
 * @since 13-09-18
 */
public interface Renderable {
	/** Called when the object should render itself */
	public void render(SpriteBatch batch);
	/** Called when the object should dispose it's graphics */
	public void dispose();
}
