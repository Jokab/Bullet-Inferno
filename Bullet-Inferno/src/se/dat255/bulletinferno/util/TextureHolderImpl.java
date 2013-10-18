package se.dat255.bulletinferno.util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

class TextureHolderImpl implements TextureHolder {
	private TextureRegion cachedTexture = null;
	private final String source;
	
	public TextureHolderImpl(String source) {
		this.source = source;
	}
	
	@Override
	public TextureRegion getTexture(AssetManager manager) {
		// If the texture is already cached
		if(cachedTexture != null) {
			return cachedTexture;
		}
		
		if(manager.isLoaded(source, Texture.class)) {
			cachedTexture = new TextureRegion(manager.get(this.source, Texture.class));
			cachedTexture.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		} else {
			throw new RuntimeException("Texture " + source + 
					" is not loaded.");
		}
		
		return cachedTexture;
	}

	@Override
	public void dispose() {
		cachedTexture = null;
	}

	@Override
	public String getSource() {
		return source;
	}	
	
	@Override
	public void loadSource(AssetManager manager) {
		manager.load(source, Texture.class);
	}

	@Override
	public void unloadSource(AssetManager manager) {
		if(manager.isLoaded(source, Texture.class)) {
			manager.unload(source);
		}
	}
}
