package se.dat255.bulletinferno.util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
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
		
		cachedTexture = new TextureRegion(manager.get(this.source, Texture.class));
		cachedTexture.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
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
}
