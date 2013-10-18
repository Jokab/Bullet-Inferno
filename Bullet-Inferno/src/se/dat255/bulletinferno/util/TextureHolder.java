package se.dat255.bulletinferno.util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * An interface to define a holder class for a texture definition.
 * 
 */
interface TextureHolder extends Disposable {

	/**
	 * Returns the texture held by this texture holder
	 * 
	 * @param manager
	 *        the asset manager
	 * @return
	 */
	public TextureRegion getTexture(AssetManager manager);

	/**
	 * Returns the source of the image
	 * 
	 * @return
	 */
	public String getSource();

	/**
	 * Loads the source the texture held by this holder,
	 * into to the specified asset manager
	 * 
	 * @param manager
	 *        The asset manager
	 */
	public void loadSource(AssetManager manager);

	/**
	 * Unloads the source the texture held by this holder,
	 * into to the specified asset manager
	 * 
	 * @param manager
	 *        The asset manager
	 */
	public void unloadSource(AssetManager manager);
}
