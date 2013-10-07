package se.dat255.bulletinferno.util;

import com.badlogic.gdx.graphics.Texture;

public interface ManagedTexture {

	/**
	 * Returns the Texture that this ManagedTexture handles.
	 * 
	 * @return The handled Texture.
	 */
	Texture getTexture();

	/**
	 * Disposes the ManagedTexture's handled Texture from the game, using the 
	 * AssetManager that ResourceManager wraps.
	 * 
	 * @param resourceManager The game's ResourceManager.
	 */
	void dispose(ResourceManager resourceManager);
}
