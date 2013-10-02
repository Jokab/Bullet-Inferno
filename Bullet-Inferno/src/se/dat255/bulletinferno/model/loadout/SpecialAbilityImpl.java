package se.dat255.bulletinferno.model.loadout;

import se.dat255.bulletinferno.model.SpecialAbility;
import se.dat255.bulletinferno.model.SpecialEffect;

public class SpecialAbilityImpl implements SpecialAbility {
	
	private final SpecialEffect effect;

	public SpecialAbilityImpl(SpecialEffect effect) {
		this.effect = effect;
	}

	@Override
	public SpecialEffect getEffect() {
		return this.effect;
	}
}
