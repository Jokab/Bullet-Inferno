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
		map.put("DEFAULT_ENEMY_SHIP", "data/defaultEnemy.png");
		map.put("SPECIAL_ENEMY_SHIP", "data/specialEnemy.png");
		
		// Player ship
		map.put("PLAYER_DEFAULT", "data/playerShip.png");
		map.put("PLAYER_EXPLOSION", "data/explosion.gif");
		
		//Projectiles
		map.put("RED_PROJECTILE", "data/redDotProjectile.png");
		map.put("GREEN_PROJECTILE", "data/greenDotProjectile.png");
		map.put("PINK_PROJECTILE", "data/pinkDotProjectile.png");
		map.put("YELLOW_PROJECTILE", "data/yellowDotProjectile.png");
		
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
