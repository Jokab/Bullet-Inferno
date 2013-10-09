package se.dat255.bulletinferno.controller;

import se.dat255.bulletinferno.view.gui.HudView;

/** Handles the update of the score when
 * actions that affects the score happens */
public class ScoreController {
	/** Reference to the score view */
	private final HudView hudView;
	
	/** The score of the player */
	private int score;
	
	/** Sets the required references */
	public ScoreController(HudView hudView) {
		this.hudView = hudView;
	}
	
	/**
	 * Adds score to the player
	 * @param score The score to add
	 */
	public void addScore(int score){
		this.score += score;
		hudView.setScore(this.score);
	}
}
