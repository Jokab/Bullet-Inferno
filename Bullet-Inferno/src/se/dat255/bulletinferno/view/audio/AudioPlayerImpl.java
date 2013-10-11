package se.dat255.bulletinferno.view.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import se.dat255.bulletinferno.util.GameActionEvent;

public class AudioPlayerImpl implements AudioPlayer {
	private float volume = 1;
	private static Sound sound = Gdx.audio.newSound(Gdx.files.internal("data/explosion.mp3"));
	
	public AudioPlayerImpl() {}
	
	public AudioPlayerImpl(float volume) {
		this.volume = volume;
	}
	
	@Override
	public void playSoundEffect(GameActionEvent e) {
		sound.play(volume);
	}
	@Override
	public void setVolume(float volume) {
		this.volume = volume;
	}

}
