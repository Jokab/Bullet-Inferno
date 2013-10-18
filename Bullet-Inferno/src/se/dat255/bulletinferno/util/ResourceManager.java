package se.dat255.bulletinferno.util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * A class that holds mappings between types of objects in the game and static resources
 * such as {@link Texture}, {@link Sound} and {@link Music}. Resources
 * are retrieved using a String identifier. <b>To use the
 * textures, they must first be loaded with {@link #startLoad()}</b>, which uses the libGDX built-in
 * {@link AssetManager} to do asynchronous loading.
 * 
 */
public interface ResourceManager extends Disposable {

	/**
	 * Returns a ManagedTexture-object containing the Texture connected
	 * with this TextureType.
	 * 
	 * @param textureDefinition
	 *        The Texture Definition connected with the Texture you want.
	 * @return The ManagedTexture-object.
	 */
	TextureRegion getTexture(TextureDefinition textureDefinition);

	/**
	 * Returns a ManagedTexture-object containing the Texture connected
	 * with this ResourceIdentifier.
	 * 
	 * @param identifier
	 *        The identifier to be used for looking up the Texture.
	 * @return The ManagedTexture-object.
	 */
	TextureRegion getTexture(ResourceIdentifier identifier);

	/**
	 * Returns the loaded {@link Sound} that is mapped to this identifier.
	 * 
	 * @param identifier
	 *        The identifier for this {@link Sound}.
	 * @return The {@link Sound}.
	 */
	Sound getSound(ResourceIdentifier identifier, GameAction action);

	/**
	 * Returns the loaded {@link Music} that is mapped to this identifier.
	 * 
	 * @param identifier
	 *        The identifier for this {@link Music}.
	 * @return The {@link Music}.
	 */
	Music getMusic(String identifier);

	/**
	 * Loads all the resources using an {@link AssetManager} to do asynchronous loading if blocking
	 * is set to false. This method should only be called once.
	 * 
	 * <p>
	 * <b>Note:</b> If called with blocking set to false, the caller must also make sure to call
	 * {@link ResourceManager#loadAsync} until the method returns true before attempting to query the
	 * ResourceManager for resources.
	 * </p>
	 * 
	 * @param blocking
	 *        if the loading should block the thread.
	 */
	void startLoad(boolean blocking);
	
	/**
	 * Tells the ResourceManager to keep loading resources asynchronously. Returns false while
	 * loading is still in progress. It is not safe to query the ResourceManager for resources
	 * before this method returns true.
	 * 
	 * <p>
	 * <b>Note:</b> This method only applies if startLoad was called with blocking set to false.
	 * </p>
	 * 
	 * @return boolean indicating if loading is finished. True meaning loading has finished.
	 */
	boolean loadAsync();
	
	/**
	 * Gets the current percentage of textures loaded, between (inclusive) 0 and 1.
	 * 
	 * 
	 * @return percentage of texture loaded, between (inclusive) 0 and 1.
	 */
	float getLoadProgress();

	/**
	 * Uses the held AssetManager to unload the Texture that is connected with this path.
	 * 
	 * @param path
	 *        The path to unload from.
	 */
	void unload(String path);
}
