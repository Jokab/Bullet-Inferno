package se.dat255.bulletinferno.model;

import se.dat255.bulletinferno.model.ResourceManagerImpl.TextureType;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

/**
 * A class that holds mappings between types of objects in the game and static resources
 * such as {@link Texture}, {@link Sound} and {@link Music}. Resources
 * are retrieved using a String identifier. To use the
 * textures, they must first be loaded with {@link #load()}, which uses the libGDX built-in
 * {@link AssetManager} to do asynchronous loading.
 * 
 */
public interface ResourceManager {

	/**
	 * Returns a ManagedTexture-object containing the Texture connected
	 * with this TextureType.
	 * 
	 * @param textureType
	 *        The TextureType connected with the Texture you want.
	 * @return The ManagedTexture-object.
	 */
	ManagedTexture getManagedTexture(TextureType textureType);

	/**
	 * Returns a ManagedTexture-object containing the Texture connected
	 * with this ResourceIdentifier.
	 * 
	 * @param identifier
	 *        The identifier to be used for looking up the Texture.
	 * @return The ManagedTexture-object.
	 */
	ManagedTexture getManagedTexture(ResourceIdentifier identifier);

	/**
	 * Returns the loaded {@link Sound} that is mapped to this identifier.
	 * 
	 * @param identifier
	 *        The identifier for this {@link Sound}.
	 * @return The {@link Sound}.
	 */
	Sound getSound(String identifier);

	/**
	 * Returns the loaded {@link Music} that is mapped to this identifier.
	 * 
	 * @param identifier
	 *        The identifier for this {@link Music}.
	 * @return The {@link Music}.
	 */
	Music getMusic(String identifier);

	/**
	 * Loads all the resources using an {@link AssetManager} to do asynchronous loading.
	 */
	void load();

	/**
	 * Uses the held AssetManager to unload the Texture that is connected with this path.
	 * 
	 * @param path
	 *        The path to unload from.
	 */
	void unload(String path);

}
