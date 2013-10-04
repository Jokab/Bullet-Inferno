package se.dat255.bulletinferno.model;

import se.dat255.bulletinferno.model.ResourceManagerImpl.TextureType;

import com.badlogic.gdx.graphics.Texture;

public class ManagedTextureImpl implements ManagedTexture {

	private final Texture texture;
	private final TextureType textureType;

	public ManagedTextureImpl(Texture texture, TextureType textureType) {
		this.texture = texture;
		this.textureType = textureType;
	}
	
	@Override
	public Texture getTexture() {
		return this.texture;
	}

	@Override
	public void dispose(ResourceManager resourceManager) {
		resourceManager.unload(textureType.getPath());
	}

}
