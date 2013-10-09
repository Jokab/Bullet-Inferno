package se.dat255.bulletinferno.model.loadout;

import se.dat255.bulletinferno.model.ModelEnvironment;
import se.dat255.bulletinferno.util.ResourceIdentifier;

public interface SpecialAbilityDefinition extends ResourceIdentifier {

	SpecialAbility getSpecialAbility(ModelEnvironment game);

}
