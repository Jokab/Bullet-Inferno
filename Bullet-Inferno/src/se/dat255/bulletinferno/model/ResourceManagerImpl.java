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

	private static AssetManager manager;
	
	public enum TextureType {
		DEFAULT_SHIP("data/defaultEnemy.png"),
		FAST_SHIP("data/defaultEnemy.png"),
		SLOW_SHIP("data/defaultEnemy.png"),
		MAP_MOUNTAIN("images/game/mountain.png"),
		
		// Enemies
		DEFAULT_ENEMY_SHIP("data/defaultEnemy.png"),
		SPECIAL_ENEMY_SHIP("data/specialEnemy.png"),
		
		// Player ship
		PLAYER_DEFAULT("data/playerShip.png"),
		PLAYER_EXPLOSION("data/explosion.gif"),
		
		//Weapons
		MISSILE_LAUNCHER("data/missileLauncher.png"),
		DISORDERER("data/disorderer.png"),
		
		//Projectiles
		RED_PROJECTILE("data/redDotProjectile.png"),
		GREEN_PROJECTILE("data/greenDotProjectile.png"),
		MISSILE("data/missile.png"),
		PLASMA("data/plasma.png");
		
		private final String path;

		TextureType(String path) {
			this.path = path;
		}
		
		public TextureType getTexture(TextureType texture) {
			return manager.get(texture.path, TextureType.class);
		}
	}

	private static final Map<String, String> textures;
	static {
		Map<String, String> map = new HashMap<String, String>();
		map.put("DEFAULT_SHIP", "data/defaultEnemy.png");
		map.put("FAST_SHIP", "data/defaultEnemy.png");
		map.put("SLOW_SHIP", "data/defaultEnemy.png");
		map.put("PLAYER_DEFAULT", "data/defaultEnemy.png");
		map.put("MAP_MOUNTAIN", "images/game/mountain.png");
		
		// Enemies
		map.put("DEFAULT_ENEMY_SHIP", "data/defaultEnemy.png");
		map.put("SPECIAL_ENEMY_SHIP", "data/specialEnemy.png");
		
		// Player ship
		map.put("PLAYER_DEFAULT", "data/playerShip.png");
		map.put("PLAYER_EXPLOSION", "data/explosion.gif");
		
		//Weapons
		map.put("MISSILE_LAUNCHER", "data/missileLauncher.png");
		map.put("DISORDERER","data/disorderer.png");
		
		//Projectiles
		map.put("RED_PROJECTILE", "data/redDotProjectile.png");
		map.put("GREEN_PROJECTILE", "data/greenDotProjectile.png");
		map.put("MISSILE", "data/missile.png");
		map.put("PLASMA", "data/plasma.png");
		
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
		// TODO: Maybe add a loading screen/bar here of some sort? Maybe this is why my phone stutters (jakob)
		manager.finishLoading();
		// TODO: Add more loading here
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
	
	public void unload(Texture texture) {
//		manager.unload();
		
	}


	@Override
	public Texture getTexture(String identifier) {
		return manager.get(textures.get(identifier), Texture.class);
	}


	// TODO: Implement loading methods for sound and music
}
