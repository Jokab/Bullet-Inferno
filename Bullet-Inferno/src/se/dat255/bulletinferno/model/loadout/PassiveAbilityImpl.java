package se.dat255.bulletinferno.model.loadout;

import se.dat255.bulletinferno.model.PassiveAbility;
import se.dat255.bulletinferno.model.PassiveEffect;

/**
 * A PassiveAbility holds a {@link PassiveEffect}.
 *
 */
public class PassiveAbilityImpl implements PassiveAbility {

	private final PassiveEffect effect;

	public PassiveAbilityImpl(PassiveEffect effect) {
		this.effect = effect;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public PassiveEffect getEffect() {
		return this.effect;
	}

}
