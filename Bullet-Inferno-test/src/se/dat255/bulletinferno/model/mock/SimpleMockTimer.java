package se.dat255.bulletinferno.model.mock;

import java.util.ArrayList;
import java.util.List;

import se.dat255.bulletinferno.model.Timer;
import se.dat255.bulletinferno.model.Timerable;

public class SimpleMockTimer implements Timer {
	public float timeLeft = 10;
	public boolean isFinished = false;
	public float initialValue = 10;
	public boolean isContinuous = false;
	public List<Timerable> registeredListeners = new ArrayList<Timerable>();
	
	@Override
	public float getTimeLeft() {
		return this.timeLeft;
	}

	@Override
	public boolean isFinished() {
		return this.timeLeft <= 0;
	}

	@Override
	public float getInitialValue() {
		return this.initialValue;
	}

	@Override
	public void setTime(float time) {
		this.initialValue = time;
	}

	@Override
	public void start() {
	}

	@Override
	public void stop() {
		this.timeLeft = 0;
	}

	@Override
	public void pause() {
	}

	@Override
	public void reset() {
	}

	@Override
	public void restart() {
	}

	@Override
	public boolean isContinuous() {
		return this.isContinuous;
	}

	@Override
	public void setContinuous(boolean continuous) {
		this.isContinuous = continuous;
	}

	@Override
	public void registerListener(Timerable listener) {
		registeredListeners.add(listener);
	}

	@Override
	public void unregisterListener(Timerable listener) {
		registeredListeners.remove(listener);
	}

	@Override
	public void update(float delta) {
	}
}
