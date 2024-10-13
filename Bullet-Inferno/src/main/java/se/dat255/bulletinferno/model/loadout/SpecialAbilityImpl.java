package se.dat255.bulletinferno.model.loadout;

public class SpecialAbilityImpl implements SpecialAbility {

	private final SpecialEffect effect;

	/**
	 * Constructs a SpecialAbility with the passed effect.
	 * 
	 * @param effect
	 */
	public SpecialAbilityImpl(SpecialEffect effect) {
		this.effect = effect;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SpecialEffect getEffect() {
		return effect;
	}
}
