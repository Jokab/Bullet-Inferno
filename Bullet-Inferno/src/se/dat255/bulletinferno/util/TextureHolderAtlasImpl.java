package se.dat255.bulletinferno.util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

class TextureHolderAtlasImpl implements TextureHolder {
	private TextureRegion cachedTexture = null;
	private final String source;
	private final String region;
	
	
	public TextureHolderAtlasImpl(String atlasSource, String region) {
		source = atlasSource;
		this.region = region;
	}
	
	@Override
	public TextureRegion getTexture(AssetManager manager) {
		// If the texture is already cached
		if(cachedTexture != null) {
			return cachedTexture;
		}
		if(manager.isLoaded(source, TextureAtlas.class)) {
			cachedTexture = manager.get(this.source, TextureAtlas.class).findRegion(region);
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
		manager.load(source, TextureAtlas.class);
	}	
	
	@Override
	public void unloadSource(AssetManager manager) {
		if(manager.isLoaded(source, TextureAtlas.class)) {
			manager.unload(source);
		}
	}
}
