package se.dat255.bulletinferno.model.loadout;

import se.dat255.bulletinferno.model.ModelEnvironment;

/**
 * Defines the different special abilities
 */
public enum SpecialAbilityDefinitionImpl implements SpecialAbilityDefinition {

	DAMAGE_ALL_ENEMIES(new SpecialInitializer() {
		@Override
		public SpecialAbility initialize(ModelEnvironment game) {
			return new SpecialAbilityImpl(new SpecialDamageAll(game.getEntityEnvironment()));
		}
	}),
	PROJECTILE_RAIN(new SpecialInitializer() {
		@Override
		public SpecialAbility initialize(ModelEnvironment game) {
			return new SpecialAbilityImpl(new SpecialProjectileRain(game.getPhysicsEnvironment(),
					game.getWeaponEnvironment()));
		}
	});

	private ModelEnvironment game;
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
