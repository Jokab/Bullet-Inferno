package se.dat255.bulletinferno.model.loadout;

import se.dat255.bulletinferno.model.ModelEnvironment;

/**
 * Defines the different special abilities
 */
public enum SpecialAbilityDefinitionImpl implements SpecialAbilityDefinition {

	LOADOUT_SPECIAL_NUKE(new SpecialInitializer() {
		@Override
		public SpecialAbility initialize(ModelEnvironment game) {
			return new SpecialAbilityImpl(new SpecialDamageAll(game.getEntityEnvironment()));
		}
	}),
	LOADOUT_SPECIAL_PROJECTILE_RAIN(new SpecialInitializer() {
		@Override
		public SpecialAbility initialize(ModelEnvironment game) {
			return new SpecialAbilityImpl(new SpecialProjectileRain(game.getPhysicsEnvironment(),
					game.getWeaponEnvironment()));
		}
	});

	private final SpecialInitializer specialInitializer;

	SpecialAbilityDefinitionImpl(SpecialInitializer initializer) {
		this.specialInitializer = initializer;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SpecialAbility getSpecialAbility(ModelEnvironment game) {
		return specialInitializer.initialize(game);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getIdentifier() {
		return this.name();
	}

	/**
	 * Interface for specifying the callback listener for the enum instances above.
	 */
	public interface SpecialInitializer {
		public SpecialAbility initialize(ModelEnvironment game);
	}
}
