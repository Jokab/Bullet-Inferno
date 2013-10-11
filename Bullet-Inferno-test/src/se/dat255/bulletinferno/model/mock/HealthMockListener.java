package se.dat255.bulletinferno.model.mock;

import se.dat255.bulletinferno.model.gui.Listener;

public class HealthMockListener implements Listener<Float> {
	float score = 0;
	@Override
	public void call(Float e) {
		score += e;
	}
	
	public float getScore() {
		return score;
	}

}
