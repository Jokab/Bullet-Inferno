package se.dat255.bulletinferno.model.gui;

/**
 * Handles the score of the game
 */
public abstract class ScoreListener implements Listener<Integer> {
	/** The total amount of score */
	private int score = 0;
	/** The time elapsed since the player last got time-based score */
	private float time;
	
	/** How the score should update the view, given by controller */
	public abstract void updateHudWithScore(int score);
	
	@Override
	public void call(Integer e) {
		score += e;
		updateHudWithScore(score);
	}
	
	/** Update the time, check if the player should receive time-based score */
	public void update(float delta){
		time += delta;
		while(time > 1){
			time -= 1;
			call(20);
		}
	}
	
	/** Gets the score */
	public int getScore(){
		return score;
	}
}
