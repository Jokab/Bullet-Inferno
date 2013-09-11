package se.dat255.bulletinferno.units;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Holds the basic update and render logic of a single unit
 *  - Ship
 *  - Enemies
 *  - Particles
 *  - Etc
 * @author Marc Jamot
 * @version 1.0
 * @since 2013-09-12
 */
public abstract class BaseUnit {
	
	/**
	 * Updates the logic of a unit, should be called from the game loop before rendering
	 * @param delta The time since last iteration, in seconds
	 */
	public abstract void update(float delta);
	
	/**
	 * Renders the unit to the screen. Should be called in the rendering part of the loop
	 * Since it's mostly same for all renderable units, it doesn't have to be abstract,
	 *   set a, for example, final Texture which the constructor takes as a parameter
	 * @param batch The batch to use for rendering the ship
	 */
	public void render(SpriteBatch batch){
		// What to draw depends on how it's delivered:
		// Separate images: Texture
		// Combined in one image to save rendering time: TextureRegion
		// Convenience class for TextureRegion and geometry: Sprite
		// Read about here: https://code.google.com/p/libgdx/wiki/SpriteBatch
	}
}
