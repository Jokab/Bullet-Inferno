package se.dat255.bulletinferno.model.loadout;

import se.dat255.bulletinferno.model.PassiveEffect;
import se.dat255.bulletinferno.model.PlayerShip;
import se.dat255.bulletinferno.model.Weapon;

public class PassiveReloadingTime implements PassiveEffect {

	private final float percent;
	private float initialReloadingSpeed;

	/**
	 * @param percent
	 *        The percentage that the reloading time will be <b>lowered</b> by. So a value of 0.1
	 *        means that the reloading time
	 *        now is 90% of the original.
	 */
	public PassiveReloadingTime(float percent) {
		this.percent = percent;
	}

	@Override
	public void applyEffect(PlayerShip playerShip) {
		Weapon weapon = playerShip.getWeapon();
		initialReloadingSpeed = weapon.getReloadingTime();
		weapon.setReloadingTime(initialReloadingSpeed * (1 - percent));
	}

	@Override
	public void removeEffect(PlayerShip playerShip) {
		Weapon weapon = playerShip.getWeapon();
		weapon.setReloadingTime(initialReloadingSpeed);
	}
}
