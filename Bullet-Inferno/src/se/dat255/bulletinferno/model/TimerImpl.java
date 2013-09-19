package se.dat255.bulletinferno.model;

import java.util.LinkedList;
import java.util.List;

/**
 * A timer class adapted to a game engine
 * @author sebastian
 *
 */
public class TimerImpl implements Timer {
	private float initialTime = 0;
	private float timeLeft = 0;
	private boolean isRunning = false;
	private boolean isContinuous = false;
	private final List<Timerable> timerables = new LinkedList<Timerable>();
	
	/**
	 * Constructs a new timer
	 */
	public TimerImpl() {}
	
	/**
	 * Constructs a new Timer with specified time to 
	 * count down, that is not continuous.
	 * @param time
	 */
	public TimerImpl(float time) {
		timeLeft = initialTime = time;
	}
	
	/**
	 * Constructs a new Timer with specified time
	 * and specified continuity
	 * @param time
	 * @param continuous
	 */
	public TimerImpl(float time, boolean continuous) {
		this(time);
		isContinuous = continuous;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getTimeLeft() {
		return timeLeft;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isFinished() {
		return timeLeft <= 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getInitialValue() {
		return initialTime;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setTime(float time) {
		initialTime = time;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void start() {
		isRunning = true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stop() {
		isRunning = false;
		timeLeft = 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void pause() {
		isRunning = false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void reset() {
		stop();
		timeLeft = initialTime;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void restart() {
		timeLeft = initialTime;
		start();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isContinuous() {
		return isContinuous;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setContinuous(boolean continuous) {
		isContinuous = continuous;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerListener(Timerable listener) {
		if(!timerables.contains(listener)) {
			timerables.add(listener);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deregisterListeneR(Timerable listener) {
		timerables.remove(listener);
	}
	
	private void notifyAllListeners() {
		for(Timerable listener : timerables) {
			listener.onTimeout(this);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(float delta) {
		if(isRunning && timeLeft > 0) {
			timeLeft -= delta;
			if(timeLeft <= 0) {
				notifyAllListeners();
				if(isContinuous()) {
					restart();
				} else {
					isRunning = false;
				}
			}
		}
	}

}
