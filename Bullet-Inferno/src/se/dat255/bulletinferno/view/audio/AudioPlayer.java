package se.dat255.bulletinferno.view.audio;

import se.dat255.bulletinferno.util.GameActionEvent;

public interface AudioPlayer {
	/**
	 * Plays a sound effect from the specified event
	 * 
	 * @param event
	 */
	public void playSoundEffect(GameActionEvent event);

	/**
	 * Sets the volume of the audio player, in the range of 0 to 1
	 * 
	 * @param volume
	 */
	public void setVolume(float volume);
}
