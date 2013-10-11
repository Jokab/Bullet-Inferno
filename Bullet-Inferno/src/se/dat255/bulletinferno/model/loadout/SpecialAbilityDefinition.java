package se.dat255.bulletinferno.model.loadout;

import se.dat255.bulletinferno.model.ModelEnvironment;
import se.dat255.bulletinferno.util.ResourceIdentifier;

public interface SpecialAbilityDefinition extends ResourceIdentifier {

	/**
	 * Returns the SpecialAbility selected.
	 * 
	 * @param game The ModelEnvironment for the game.
	 * @return The SpecialAbility.
	 */
	SpecialAbility getSpecialAbility(ModelEnvironment game);

}
