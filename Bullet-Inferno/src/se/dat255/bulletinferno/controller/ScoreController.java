package se.dat255.bulletinferno.controller;

import se.dat255.bulletinferno.view.gui.ScoreView;

/** Handles the update of the score when
 * actions that affects the score happens */
public class ScoreController {
	/** Reference to the score view */
	private final ScoreView scoreView;
	
	/** The score of the player */
	private int score;
	
	/** Sets the required references */
	public ScoreController(ScoreView scoreView) {
		this.scoreView = scoreView;
	}
	
	/**
	 * Adds score to the player
	 * @param score The score to add
	 */
	public void addScore(int score){
		this.score += score;
		scoreView.setScore(this.score);
	}
}
