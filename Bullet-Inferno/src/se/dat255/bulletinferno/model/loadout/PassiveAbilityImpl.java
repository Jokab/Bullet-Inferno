package se.dat255.bulletinferno.model.loadout;

import se.dat255.bulletinferno.model.PassiveAbility;
import se.dat255.bulletinferno.model.PassiveEffect;


public class PassiveAbilityImpl implements PassiveAbility {

	private final PassiveEffect effect;

	public PassiveAbilityImpl(PassiveEffect effect) {
		this.effect = effect;
	}
	
	@Override
	public PassiveEffect getEffect() {
		return this.effect;
	}

}
