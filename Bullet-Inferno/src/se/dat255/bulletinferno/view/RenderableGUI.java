package se.dat255.bulletinferno.view;

import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.view.gui.GuiEvent;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Interface for GUI objects that are to be rendered by the graphics class.
 * 
 * @author Marc
 * @version 1.0
 * @since 13-09-18
 */
public interface RenderableGUI {
	/** Called when the object was interacted with */
	public GuiEvent pressed(float x, float y);

	/** Gets the position of the GUI element */
	public Vector2 getPosition();

	/** Gets the size of the GUI element */
	public Vector2 getSize();
	
	/** Called when the object should render itself */
	public void render(SpriteBatch batch);

	/** Called when the object should dispose it's graphics */
	public void dispose(ResourceManager resourceManager);
}
