package se.dat255.bulletinferno.model.gui;

public abstract class ScoreListener implements Listener<Integer> {
	private int score = 0;
	private float time;
	
	public ScoreListener() {}
	
	public abstract void updateHudWithScore(int score);
	
	@Override
	public void call(Integer e) {
		score += e;
		updateHudWithScore(score);
	}
	
	public void update(float delta){
		time += delta;
		while(time > 1){
			time -= 1;
			call(20);
		}
	}
	
	public int getScore(){
		return score;
	}
}
