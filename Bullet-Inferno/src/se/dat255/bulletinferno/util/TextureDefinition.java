package se.dat255.bulletinferno.util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public interface TextureDefinition extends Disposable {
	/**
	 * @param manager
	 *        the asset manager
	 * @return the texture held by this definition.
	 */
	public TextureRegion getTexture(AssetManager manager);

	/**
	 * @return the source of the texture image.
	 */
	public String getSrouce();

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
