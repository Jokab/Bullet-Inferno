package se.dat255.bulletinferno.model.loadout;

import se.dat255.bulletinferno.util.ResourceIdentifier;

public interface PassiveAbilityDefinition extends ResourceIdentifier {

	/**
	 * Returns the PassiveAbility that was chosen.
	 * 
	 * @return The PassiveAbility.
	 */
	PassiveAbility getPassiveAbility();
	
}
