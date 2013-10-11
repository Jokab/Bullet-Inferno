package se.dat255.bulletinferno.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Interface for objects that are to be rendered by the graphics class.
 */
public interface Renderable {
	/** Called when the object should render itself */
	public void render(SpriteBatch batch);

	/** Called when the object should dispose it's graphics */
	public void dispose();
}
