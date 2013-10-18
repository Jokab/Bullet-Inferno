package se.dat255.bulletinferno.util;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ResolutionFileResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ResolutionFileResolver.Resolution;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Definition of all the assets that should be handled by the resource manager
 */
public class ResourceManagerImpl implements ResourceManager {
	private static final Resolution[] SUPPORTED_RESOLUTIONS = {
	        new Resolution(450, 800, "800450"),
	        new Resolution(720, 1280, "1280720"),
	        new Resolution(1080, 1920, "19201080")
	};
	
	public enum SoundEffectType {
		KATZE,
		HARD_BOSS_SHIP;
		
		static {
			KATZE.mapping.put("DIED", "data/explosion.mp3");
			HARD_BOSS_SHIP.mapping.put("DIED", "data/explosion.mp3");
		}
		
		private final Map<String, String> mapping = new HashMap<String, String>();
		
		private SoundEffectType() {}
		
		public String getPath(String key) {
			return mapping.get(key);
		}
	}
	
	private AssetManager manager;

	public ResourceManagerImpl() {
		manager = new AssetManager();

		ResolutionFileResolver resolver = new ResolutionFileResolver(
				new InternalFileHandleResolver(), SUPPORTED_RESOLUTIONS);
		
		manager.setLoader(Texture.class, new TextureLoader(resolver));
		manager.setLoader(TextureAtlas.class, new TextureAtlasLoader(resolver));
		Texture.setAssetManager(manager);
	}

	/**
	 * {@inheritDoc}
	 */
	public void startLoad(boolean blocking) {
		loadTextures();
		loadSoundEffects();
		
		if(blocking) {
			manager.finishLoading();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Sound getSound(ResourceIdentifier identifier, GameAction action) {
		for (SoundEffectType soundEffectType : SoundEffectType.values()) {
			if (identifier.getIdentifier().equals(soundEffectType.name())) {
				return manager.get(soundEffectType.getPath(action.getAction()), Sound.class);
			}
		}
		
		throw new RuntimeException(String.format("Sound not found for the identifier:action combination '%s:%s'", 
				identifier.getIdentifier(), action.getAction()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Music getMusic(String identifier) {
		return null;
		//return manager.get(music.get(identifier), Music.class);
	}

	/** Adds all managed textures to the AssetManager's load queue. */
	private void loadTextures() {
		for (TextureDefinition definition : TextureDefinitionImpl.values()) {
			definition.loadSource(manager);
		}
	}
	
	/** Adds all managed sound effects to the AssetManager's load queue. */
	private void loadSoundEffects() {
		for (SoundEffectType type : SoundEffectType.values()) {
			for(String src : type.mapping.values()) {
				manager.load(src, Sound.class);
			}
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
	public TextureRegion getTexture(TextureDefinition textureDefinition) {
		return textureDefinition.getTexture(manager);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TextureRegion getTexture(ResourceIdentifier resourceIndentifier) {
		String identifier = resourceIndentifier.getIdentifier();

		TextureDefinition definition;
		try {
			definition = TextureDefinitionImpl.valueOf(identifier);
		} catch (IllegalArgumentException exception) {
			throw new IllegalArgumentException(
					String.format("Resource with identifier '%s' could not be found", identifier),
					exception);
		}

		return getTexture(definition);
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
		for(TextureDefinition definition : TextureDefinitionImpl.values()){
			definition.unloadSource(manager);
		}
		
		manager.dispose();
		manager = null;
		Texture.setAssetManager(null);
	}

}
