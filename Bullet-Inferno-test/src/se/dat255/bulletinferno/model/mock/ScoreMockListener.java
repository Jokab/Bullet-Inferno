package se.dat255.bulletinferno.model.mock;

import se.dat255.bulletinferno.util.Listener;

public class ScoreMockListener implements Listener<Integer> {
	float score = 0;
	@Override
	public void call(Integer e) {
		score += e;
	}
	
	public float getScore() {
		return score;
	}

}
