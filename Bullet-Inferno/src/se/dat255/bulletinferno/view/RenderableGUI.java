package se.dat255.bulletinferno.view;

import com.badlogic.gdx.math.Vector2;

/**
 * Interface for GUI objects that are to be rendered by the graphics class.
 * 
 * @author Marc
 * @version 1.0
 * @since 13-09-18
 */
public interface RenderableGUI extends Renderable {
	
	/** Called when the object was interacted with */
	public void pressed();
	
	/** Gets the position of the GUI element */
	public Vector2 getPosition();
	
	/** Gets the size of the GUI element */
	public Vector2 getSize();
}
