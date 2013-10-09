package se.dat255.bulletinferno.util;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class ResourceManagerImpl implements ResourceManager {
	public enum TextureType {
		DEFAULT_SHIP("data/defaultEnemy.png"),
		FAST_SHIP("data/defaultEnemy.png"),
		SLOW_SHIP("data/defaultEnemy.png"),
		MAP_MOUNTAIN("images/game/mountain.png"),

		DEFAULT_ENEMY_SHIP("data/defaultEnemy.png"),
		SPECIAL_ENEMY_SHIP("data/specialEnemy.png"),
		BOSS_ENEMY_SHIP("data/boss.png"),

		// Player ship
		PLAYER_DEFAULT("data/playerShip.png"),
		PLAYER_EXPLOSION("data/explosion.gif"),

		// Weapons
		MISSILE_LAUNCHER("data/missileLauncher.png"),
		DISORDERER("data/disorderer.png"),
		MISSILE_LAUNCHER_LARGE("data/missileLauncherLarge.png"),
		DISORDERER_LARGE("data/disordererLarge.png"),
		
		// Projectiles
		RED_PROJECTILE("data/redDotProjectile.png"),
		GREEN_PROJECTILE("data/greenDotProjectile.png"),
		MISSILE("data/missile.png"),
		PLASMA("data/plasma.png"),

		// Buttons
		PAUSE_SCREEN("images/gui/screen_pause.png"),
		BLUE_BACKGROUND("images/game/background.png"),
		GAMEOVER_SCREEN("images/gui/screen_gameover.png"),
		PROJECTILE_RAIN_MENU_ICON("data/projectileRain.png"),
		LOADOUT_START_BUTTON("data/startBtn.png");

		private final String path;

		TextureType(String path) {
			this.path = path;
		}

		private Texture getTexture(AssetManager manager) {
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
	
	private AssetManager manager;
	
	public ResourceManagerImpl() {
		this.textureTypes = TextureType.values();
		manager = new AssetManager();
		Texture.setAssetManager(manager);
	}

	/**
	 * {@inheritDoc}
	 */
	public void startLoad(boolean blocking) {
		loadTextures();
		
		if(blocking) {
			manager.finishLoading();
		}
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

	/**
	 * Adds all our managed textures to the AssetManager's load queue.
	 */
	private void loadTextures() {
		for (TextureType type : TextureType.values()) {
			manager.load(type.path, Texture.class);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void unload(String path) {
		if (manager.isLoaded(path, Texture.class)) {
			manager.unload(path);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ManagedTexture getManagedTexture(TextureType textureType) {
		if (manager.isLoaded(textureType.getPath(), Texture.class)) {
			return new ManagedTextureImpl(textureType.getTexture(manager), textureType);
		} else {
			throw new RuntimeException("Texture " + textureType.name() + " is not loaded.");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ManagedTexture getManagedTexture(ResourceIdentifier identifier) {
		for (TextureType textureType : textureTypes) {
			if (identifier.getIdentifier().equals(textureType.name())) {
				if (manager.isLoaded(textureType.getPath(), Texture.class)) {
					return new ManagedTextureImpl(textureType.getTexture(manager), textureType);
				} else {
					throw new RuntimeException("Texture " + textureType.name() + " is not loaded."); 
				}
			}
		}

		throw new RuntimeException("Texture not found for that identifier.");
	}
	// TODO: Implement loading methods for sound and music

	@Override
	public boolean loadAsync() {
		return manager.update();
	}

	@Override
	public float getLoadProgress() {
		return manager.getProgress();
	}

	@Override
	public void dispose() {
		manager.dispose();
	}
}
