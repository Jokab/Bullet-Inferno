package se.dat255.bulletinferno.util;

import java.util.LinkedList;
import java.util.List;

/**
 * A timer class adapted to a game engine
 * 
 * @author sebastian
 * 
 */
public class TimerImpl implements Timer {
	private float initialTime = 0;
	private float timeLeft = 0;
	private boolean isRunning = false;
	private boolean isContinuous = false;

	/** Holding a list of all timerable listeners */
	private final List<Timerable> listeners = new LinkedList<Timerable>();

	/** A queue for holding the items to be removed after iteration */
	private final List<Timerable> addQueue = new LinkedList<Timerable>();

	/** A queue for holding the items to be removed after iteration */
	private final List<Timerable> removeQueue = new LinkedList<Timerable>();

	private boolean isIterating = false;

	/**
	 * Constructs a new timer with time to count down = 0
	 */
	public TimerImpl() {
	}

	/**
	 * Constructs a new Timer with specified time to count down, that is not
	 * continuous.
	 * 
	 * @param time The time to count down from.
	 */
	public TimerImpl(float time) {
		if (time < 0) {
			time = 0;
		}
		timeLeft = initialTime = time;
	}

	/**
	 * Constructs a new Timer with specified time and specified continuity
	 * 
	 * @param time The time to count down form.
	 * @param continuous Whether the timer is continuous (looping) or not.
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
		if (time < 0) {
			time = 0;
		}
		initialTime = time;
		timeLeft = initialTime;
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
		if (!listeners.contains(listener)) {
			if (isIterating) {
				addQueue.add(listener);
			} else {
				listeners.add(listener);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void unregisterListener(Timerable listener) {
		if (isIterating) {
			removeQueue.add(listener);
		} else {
			listeners.remove(listener);
		}
	}

	private void notifyAllListeners(float timeSinceLast) {
		// Set flag for iteration, i.e. no one touches the list
		isIterating = true;

		for (Timerable listener : listeners) {
			listener.onTimeout(this, timeSinceLast);
		}

		// Remove and add all items in queue
		if (!removeQueue.isEmpty()) {
			listeners.removeAll(removeQueue);
			removeQueue.clear();
		}
		if (!addQueue.isEmpty()) {
			listeners.addAll(addQueue);
			addQueue.clear();
		}

		isIterating = false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(float delta) {
		if (isRunning && timeLeft >= 0) {
			timeLeft -= delta;

			if (timeLeft <= 0) {
				notifyAllListeners(initialTime - timeLeft); // Note: timeLeft is <= 0 here
				timeLeft = 0;
				if (isContinuous()) {
					restart();
				} else {
					isRunning = false;
				}
			}
		}
	}

}
