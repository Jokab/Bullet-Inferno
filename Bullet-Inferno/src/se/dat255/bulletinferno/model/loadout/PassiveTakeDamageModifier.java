package se.dat255.bulletinferno.model.loadout;

import se.dat255.bulletinferno.model.entity.PlayerShip;

public class PassiveTakeDamageModifier implements PassiveEffect {
	// TODO: bad name

	private final float percent;
	private static final float DEFAULT_TAKEDAMAGE_MODIFIER = 1;

	/**
	 * @param percent
	 *        The percentage which the damage taken will be modified by. So a value of 0.9 will
	 *        make the player take 90% of the original damage, i.e. 10% less.
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
