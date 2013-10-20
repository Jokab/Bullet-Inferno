package se.dat255.bulletinferno.model.loadout;

import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;
import se.dat255.bulletinferno.util.Timer;
import se.dat255.bulletinferno.util.Timerable;

public class SpecialEffectTimerHelperImpl implements SpecialEffectTimerHelper {

	private static final float DEFAULT_CHARGE_TIME = 10;
	private static final int DEFAULT_MAX_CHARGE_AMOUNT = 3;
	private final Timer chargeTimer;
	private int charges;
	private final float reloadTime;
	private Timer reloadTimer;
	private boolean ready;
	
	private Timerable reloadTimerable = new Timerable() {
		@Override
		public void onTimeout(Timer source, float timeSinceLast) {
			ready = true;
		}
	};
	
	private Timerable chargeTimerable = new Timerable() {
		@Override
		public void onTimeout(Timer source, float timeSinceLast) {
			if(charges < DEFAULT_MAX_CHARGE_AMOUNT) {
				charges++;
			}
		}
	};

	protected SpecialEffectTimerHelperImpl(PhysicsEnvironment physics, float reloadTime) {
		this.reloadTime = reloadTime;
		this.ready = true;
		this.charges = DEFAULT_MAX_CHARGE_AMOUNT;

		reloadTimer = physics.getTimer();
		reloadTimer.setContinuous(true);
		reloadTimer.setTime(reloadTime);
		reloadTimer.registerListener(reloadTimerable);
		reloadTimer.stop();

		chargeTimer = physics.getTimer();
		chargeTimer.setContinuous(true);
		chargeTimer.setTime(DEFAULT_CHARGE_TIME);
		chargeTimer.registerListener(chargeTimerable);
		chargeTimer.stop();
		chargeTimer.start();
	}

	@Override
	public void startReloadTimer() {
		reloadTimer.stop();
		reloadTimer.setTime(reloadTime);
		reloadTimer.start();

		charges -= 1;
		ready = false;
	}

	@Override
	public boolean isReady() {
		return this.ready && charges > 0;
	}
	
	@Override
	public int getCharges() {
		return this.charges;
	}
}
