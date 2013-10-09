package se.dat255.bulletinferno.controller;

import se.dat255.bulletinferno.model.ModelEnvironment;
import se.dat255.bulletinferno.model.loadout.SpecialAbility;
import se.dat255.bulletinferno.model.loadout.SpecialAbilityImpl;
import se.dat255.bulletinferno.model.loadout.SpecialDamageAll;
import se.dat255.bulletinferno.model.loadout.SpecialProjectileRain;

public enum SpecialAbilityType {

	DAMAGE_ALL_ENEMIES(new SpecialInitializer() {
		@Override
		public SpecialAbility initialize(ModelEnvironment game) {
			return new SpecialAbilityImpl(new SpecialDamageAll(game.getEntityEnvironment()));
		}
	}),
	PROJECTILE_RAIN(new SpecialInitializer() {
		@Override
		public SpecialAbility initialize(ModelEnvironment game) {
			return new SpecialAbilityImpl(new SpecialProjectileRain(game.getPhysicsEnvironment(), game.getWeaponEnvironment()));
		}
	});
	
	private ModelEnvironment game;
	private final SpecialAbility specialAbility;

	SpecialAbilityType(SpecialInitializer initializer) {
		this.specialAbility = initializer.initialize(game);
	}

	public SpecialAbility getSpecialAbility() {
		return specialAbility;
	}
	
	public interface SpecialInitializer {
		public SpecialAbility initialize(ModelEnvironment game);
	}
	
}
