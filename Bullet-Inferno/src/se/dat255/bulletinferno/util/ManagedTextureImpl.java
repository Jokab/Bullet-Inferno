package se.dat255.bulletinferno.util;

import se.dat255.bulletinferno.model.ManagedTexture;
import se.dat255.bulletinferno.model.ResourceManager;
import se.dat255.bulletinferno.model.ResourceManagerImpl;
import se.dat255.bulletinferno.model.ResourceManagerImpl.TextureType;

import com.badlogic.gdx.graphics.Texture;

public class ManagedTextureImpl implements ManagedTexture {

	private final Texture texture;
	private final TextureType textureType;

	/**
	 * Constructs a ManagedTexture with the Texture that this object should hold.
	 * A TextureType is required to keep track of the path for unloading with
	 * the AssetManager.
	 * 
	 * @param texture The texture to manage.
	 * @param textureType The TextureType that this Texture is retrieved from.
	 */
	public ManagedTextureImpl(Texture texture, TextureType textureType) {
		this.texture = texture;
		this.textureType = textureType;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Texture getTexture() {
		return this.texture;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose(ResourceManager resourceManager) {
		resourceManager.unload(textureType.getPath());
	}

}
