package se.dat255.bulletinferno.controller;

import se.dat255.bulletinferno.model.ModelEnvironment;
import se.dat255.bulletinferno.model.entity.PlayerShip;
import se.dat255.bulletinferno.model.loadout.SpecialAbility;
import se.dat255.bulletinferno.model.loadout.SpecialAbilityImpl;
import se.dat255.bulletinferno.model.loadout.SpecialDamageAll;
import se.dat255.bulletinferno.model.loadout.SpecialProjectileRain;

public enum SpecialAbilityType {

	DAMAGE_ALL_ENEMIES(new SpecialInitializer() {
		@Override
		public SpecialAbility initialize(ModelEnvironment game) {
//			SpecialAbilityType.this.game = game;
			return new SpecialAbilityImpl(new SpecialDamageAll(game));
		}
	}),
	PROJECTILE_RAIN(new SpecialInitializer() {
		@Override
		public SpecialAbility initialize(ModelEnvironment game) {
//			SpecialAbilityType.this.game = game;
			return new SpecialAbilityImpl(new SpecialProjectileRain(game.getPhysicsEnvironment(), game.getPlayerShip()));
		}
	});
	
	private ModelEnvironment game;
	private final PlayerShip playerShip;
	private final SpecialAbility specialAbility;

	SpecialAbilityType(SpecialInitializer initializer) {
		this.game = game;
		this.specialAbility = initializer.initialize(game);
		this.playerShip = game.getPlayerShip();
	}

	public SpecialAbility getSpecialAbility() {
		return specialAbility;
	}
	
	public interface SpecialInitializer {
		public SpecialAbility initialize(ModelEnvironment game);
	}
	
}
