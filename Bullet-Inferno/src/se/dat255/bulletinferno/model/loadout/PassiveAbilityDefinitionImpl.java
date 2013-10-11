package se.dat255.bulletinferno.model.loadout;

public enum PassiveAbilityDefinitionImpl implements PassiveAbilityDefinition {

	RELOADING_TIME(new PassiveAbilityImpl(new PassiveReloadingTime(0.75f))),
	TAKE_DAMAGE_MODIFIER(new PassiveAbilityImpl(new PassiveTakeDamageModifier(0.5f))),
	DAMAGE_MODIFIER(new PassiveAbilityImpl(new PassiveDamageModifier(1.10f)));
	
	private final PassiveAbility ability;
	
	PassiveAbilityDefinitionImpl(PassiveAbility ability) {
		this.ability = ability;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PassiveAbility getPassiveAbility() {
		return this.ability;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getIdentifier() {
		return this.name();
	}
}
