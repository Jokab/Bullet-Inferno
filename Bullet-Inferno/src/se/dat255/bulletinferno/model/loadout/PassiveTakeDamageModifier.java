package se.dat255.bulletinferno.model.loadout;

import se.dat255.bulletinferno.model.PassiveEffect;
import se.dat255.bulletinferno.model.PlayerShip;

public class PassiveTakeDamageModifier implements PassiveEffect {
	// TODO: bad name

	private final float percent;
	private static final float DEFAULT_TAKEDAMAGE_MODIFIER = 1;

	/**
	 * @param percent
	 *        The percentage that the damage will be modified by. So 1.10 means 10% damage increase.
	 */
	public PassiveTakeDamageModifier(float percent) {
		this.percent = percent;
	}

	@Override
	public void applyEffect(PlayerShip playerShip) {
		playerShip.setTakeDamageModifier(percent);
	}

	@Override
	public void removeEffect(PlayerShip playerShip) {
		playerShip.setTakeDamageModifier(DEFAULT_TAKEDAMAGE_MODIFIER);
	}

}
