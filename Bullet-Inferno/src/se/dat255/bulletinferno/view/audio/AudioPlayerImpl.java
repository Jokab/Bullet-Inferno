package se.dat255.bulletinferno.view.audio;

import com.badlogic.gdx.audio.Sound;

import se.dat255.bulletinferno.util.GameActionEvent;
import se.dat255.bulletinferno.util.ResourceManager;

public class AudioPlayerImpl implements AudioPlayer {
	private float volume = 1;
	private ResourceManager resourceManager;
	
	public AudioPlayerImpl(ResourceManager resourceManager) {
		this.resourceManager = resourceManager;
	}
	
	public AudioPlayerImpl(ResourceManager resourceManager, float volume) {
		this.resourceManager = resourceManager;
		this.volume = volume;
	}
	
	@Override
	public void playSoundEffect(GameActionEvent e) {
		Sound sound = resourceManager.getSound(e.getSource(), e.getAction());
		sound.play(volume);
	}
	@Override
	public void setVolume(float volume) {
		this.volume = volume;
	}

}
