package se.dat255.bulletinferno.model.loadout;

import se.dat255.bulletinferno.model.entity.PlayerShip;
import se.dat255.bulletinferno.model.weapon.Weapon;

public class PassiveReloadingTime implements PassiveEffect {

	private final float percent;
	private float initialReloadingTime;

	/**
	 * @param percent
	 *        The percentage that the reloading time will be multiplied by. So a value of 0.9 will
	 *        set the reloading time to 90% of the original.
	 */
	public PassiveReloadingTime(float percent) {
		this.percent = percent;
	}

	@Override
	public void applyEffect(PlayerShip playerShip) {
		Weapon weapon = playerShip.getWeapon();
		initialReloadingTime = weapon.getReloadingTime();
		weapon.setReloadingTime(initialReloadingTime * percent);
	}

	@Override
	public void removeEffect(PlayerShip playerShip) {
		Weapon weapon = playerShip.getWeapon();
		weapon.setReloadingTime(initialReloadingTime);
	}
}
