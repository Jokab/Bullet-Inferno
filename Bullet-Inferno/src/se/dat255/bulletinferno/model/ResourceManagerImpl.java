package se.dat255.bulletinferno.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import se.dat255.bulletinferno.model.physics.AccelerationMovementPattern;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;


public class ResourceManagerImpl implements ResourceManager {

	private final AssetManager manager;

	private static final Map<String, String> textures;
	static {
		Map<String, String> map = new HashMap<String, String>();
		
		// Enemies
		map.put("DEFAULT_ENEMY_SHIP", "data/enemyShip.png");
		map.put("SPECIAL_ENEMY_SHIP", "data/enemyShip2.png");
		
		// Player ship
		map.put("PLAYER_DEFAULT", "data/ship.png");
		
		//Projectiles
		map.put("DEFAULT_PROJECTILE", "data/projectile.png");
		map.put("LEFT_ACCELERATING_PROJECTILE", "data/projectile.png");
		map.put("RIGHT_ACCELERATING_PROJECTILE", "data/projectile.png");
		map.put("SINE_PROJECTILE", "data/enemyShip2.png");
		
		// Buttons
		map.put("PAUSE_SCREEN", "images/gui/screen_pause.png");
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
