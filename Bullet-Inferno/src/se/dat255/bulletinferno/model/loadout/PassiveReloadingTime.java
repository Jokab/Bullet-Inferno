package se.dat255.bulletinferno.model.loadout;

import se.dat255.bulletinferno.model.PassiveEffect;
import se.dat255.bulletinferno.model.PlayerShip;
import se.dat255.bulletinferno.model.Weapon;

/**
 * Lowers reloading speed by 15%.
 * 
 */
public class PassiveReloadingTime implements PassiveEffect {

	private final float percent;
	private float initialReloadingSpeed;
	
	public PassiveReloadingTime(float percent) {
		this.percent = percent;
	}
	
	@Override
	public void applyEffect(PlayerShip playerShip) {
		Weapon weapon = playerShip.getWeapon();
		initialReloadingSpeed = weapon.getReloadingTime();
		weapon.setReloadingTime(initialReloadingSpeed * percent);
	}

	@Override
	public void removeEffect(PlayerShip playerShip) {
		Weapon weapon = playerShip.getWeapon();
		weapon.setReloadingTime(initialReloadingSpeed);
	}
}
