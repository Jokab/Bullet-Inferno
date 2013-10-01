package se.dat255.bulletinferno.model;

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
		weapon.setReloadingTime(weapon.getReloadingTime() * 0.75f);
	}

	@Override
	public void removeEffect(PlayerShip playerShip) {
		Weapon weapon = playerShip.getWeapon();
		weapon.setReloadingTime(initialReloadingSpeed);
	}
}
