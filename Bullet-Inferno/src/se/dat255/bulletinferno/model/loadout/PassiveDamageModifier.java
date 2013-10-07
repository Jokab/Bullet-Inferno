package se.dat255.bulletinferno.model.loadout;

import se.dat255.bulletinferno.model.entity.PlayerShip;
import se.dat255.bulletinferno.model.weapon.Weapon;

public class PassiveDamageModifier implements PassiveEffect {

	private final float percent;
	private float initialDamage;

	/**
	 * @param percent
	 *        The percentage that the damage will be modified by. So 1.10 means 10% damage increase.
	 */
	public PassiveDamageModifier(float percent) {
		this.percent = percent;
	}

	@Override
	public void applyEffect(PlayerShip playerShip) {
		Weapon weapon = playerShip.getWeapon();
		this.initialDamage = weapon.getProjectileType().getDamage();
		weapon.getProjectileType().setDamage(initialDamage * percent);
	}

	@Override
	public void removeEffect(PlayerShip playerShip) {
		playerShip.getWeapon().getProjectileType().setDamage(initialDamage);
	}

}
