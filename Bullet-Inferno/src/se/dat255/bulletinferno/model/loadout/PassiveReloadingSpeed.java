package se.dat255.bulletinferno.model.loadout;

import se.dat255.bulletinferno.model.PassiveEffect;
import se.dat255.bulletinferno.model.PlayerShip;
import se.dat255.bulletinferno.model.Weapon;

/**
 * Lowers reloading speed by 15%.
 * 
 */
public class PassiveReloadingSpeed implements PassiveEffect {

	private float initialReloadingSpeed;
	
	@Override
	public void applyEffect(PlayerShip playerShip) {
		Weapon weapon = playerShip.getWeapon();
		initialReloadingSpeed = weapon.getReloadingTime();
		weapon.setReloadingTime(initialReloadingSpeed * 0.1f);
	}

	@Override
	public void removeEffect(PlayerShip playerShip) {
		Weapon weapon = playerShip.getWeapon();
		weapon.setReloadingTime(initialReloadingSpeed);
	}
}
