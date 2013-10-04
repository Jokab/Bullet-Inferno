package se.dat255.bulletinferno.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;



import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;


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
		PLASMA("data/plasma.png"),
		
		// Buttons
		PAUSE_SCREEN("images/gui/screen_pause.png"), 
		BLUE_BACKGROUND("images/game/background.png");
		
		private final String path;

		TextureType(String path) {
			this.path = path;
		}
		
		public Texture getTexture() {
			return manager.get(this.path, Texture.class);
		}
		
		public String getPath() {
			return this.path;
		}
	}

	// TODO: Define these maps
	private static final Map<String, String> sounds = new HashMap<String, String>();
	private static final Map<String, String> music = new HashMap<String, String>();
	private final TextureType[] textureTypes;

	public ResourceManagerImpl(AssetManager assetManager) {
		this.manager = assetManager;
		this.textureTypes = TextureType.values();
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
		for (TextureType type : TextureType.values()) {
			manager.load(type.path, Texture.class);
		}
	}
	
	@Override
	public void unload(String path) {
		System.out.println("unloading" + path);
		manager.unload(path);
	}

	@Override
	public ManagedTexture getManagedTexture(TextureType textureType) {
		return new ManagedTextureImpl(textureType.getTexture(), textureType);
	}

	@Override
	public ManagedTexture getManagedTexture(ResourceIdentifier identifier) {
		for(TextureType textureType : textureTypes) {
			if(identifier.getIdentifier().equals(textureType.name())) {
				return new ManagedTextureImpl(textureType.getTexture(), textureType);
			}
		}
		
		throw new RuntimeException("Texture not found for that identifier.");
	}
	// TODO: Implement loading methods for sound and music
}
