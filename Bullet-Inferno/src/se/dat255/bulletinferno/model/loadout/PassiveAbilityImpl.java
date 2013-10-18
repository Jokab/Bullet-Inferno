package se.dat255.bulletinferno.model.loadout;

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
		return effect;
	}

}
