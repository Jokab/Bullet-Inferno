package se.dat255.bulletinferno.model.loadout;

import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;
import se.dat255.bulletinferno.util.Timer;
import se.dat255.bulletinferno.util.Timerable;

public class SpecialEffectTimerHelperImpl implements SpecialEffectTimerHelper {

	private static final float DEFAULT_CHARGE_TIME = 28;
	private static final int DEFAULT_MAX_CHARGE_AMOUNT = 3;
	private final Timer chargeTimer;
	private int charges;
	private final Timer reloadTimer;

	private final Timerable chargeTimerable = new Timerable() {
		@Override
		public void onTimeout(Timer source, float timeSinceLast) {
			if (charges < DEFAULT_MAX_CHARGE_AMOUNT) {
				charges++;
			}
		}
	};

	protected SpecialEffectTimerHelperImpl(PhysicsEnvironment physics, float reloadTime) {
		charges = DEFAULT_MAX_CHARGE_AMOUNT;

		reloadTimer = physics.getTimer();
		reloadTimer.setTime(reloadTime);
		reloadTimer.stop();

		chargeTimer = physics.getTimer();
		chargeTimer.setContinuous(true);
		chargeTimer.setTime(DEFAULT_CHARGE_TIME);
		chargeTimer.registerListener(chargeTimerable);
		chargeTimer.start();
	}

	@Override
	public void useCharge() {
		charges -= 1;
	}

	@Override
	public void startReloadTimer() {
		reloadTimer.restart();
	}

	@Override
	public boolean isReady() {
		return reloadTimer.isFinished() && charges > 0;
	}

	@Override
	public float getReadyPercentage() {
		if (isReady()) {
			// If loaded
			return 1;
		} else if (charges > 0) {
			// Have charges, only waiting for the reloading
			return 1 - reloadTimer.getTimeLeft() / reloadTimer.getInitialValue();
		} else {
			// No charges, return the longest of charge and reloading time left
			if (reloadTimer.getTimeLeft() > chargeTimer.getTimeLeft()) {
				return 1 - reloadTimer.getTimeLeft() / reloadTimer.getInitialValue();
			} else {
				return 1 - chargeTimer.getTimeLeft() / chargeTimer.getInitialValue();
			}
		}
	}

	@Override
	public int getCharges() {
		return charges;
	}
}
