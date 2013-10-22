package se.dat255.bulletinferno.view.audio;

import se.dat255.bulletinferno.util.GameActionEvent;

public interface AudioPlayer {
	/**
	 * Plays a sound effect from the specified event
	 * 
	 * @param event
	 *        the event to play a sound for.
	 */
	public void playSoundEffect(GameActionEvent event);

	/**
	 * Sets the volume of the audio player, in the range of 0 to 1
	 * 
	 * @param volume
	 *        the playback volume, between 0 and 1.
	 */
	public void setVolume(float volume);
}
