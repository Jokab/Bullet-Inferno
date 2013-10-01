package se.dat255.bulletinferno.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;


public class ResourceManagerImpl implements ResourceManager {

	private final AssetManager manager;

	private static final Map<String, String> textures;
	static {
		Map<String, String> map = new HashMap<String, String>();
		map.put("DEFAULT_SHIP", "data/enemyShip.png");
		map.put("FAST_SHIP", "data/enemyShip2.png");
		map.put("SLOW_SHIP", "data/enemyShip2.png");
		textures = Collections.unmodifiableMap(map);
	}

	// TODO: Define these maps
	private static final Map<String, String> sounds = new HashMap<String, String>();
	private static final Map<String, String> music = new HashMap<String, String>();

	public ResourceManagerImpl(AssetManager assetManager) {
		this.manager = assetManager;
	}

	/**
	 * {@inheritDoc}
	 */
	public void load() {
		loadTextures();
		manager.finishLoading();
		// TODO: Add more loading here
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Texture getTexture(String identifier) {
		return manager.get(textures.get(identifier), Texture.class);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Sound getSound(String identifier) {
		return manager.get(sounds.get(identifier), Sound.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Music getMusic(String identifier) {
		return manager.get(music.get(identifier), Music.class);
	}

	private void loadTextures() {
		for (String path : textures.values()) {
			manager.load(path, Texture.class);
		}
	}

	// TODO: Implement loading methods for sound and music
}
