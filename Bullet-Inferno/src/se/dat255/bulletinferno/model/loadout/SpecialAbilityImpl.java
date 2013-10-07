package se.dat255.bulletinferno.model.loadout;


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
