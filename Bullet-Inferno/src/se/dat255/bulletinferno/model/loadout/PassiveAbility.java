package se.dat255.bulletinferno.model.loadout;

/**
 * A PassiveAbility holds a {@link PassiveEffect}.
 *
 */
public interface PassiveAbility {

	/**
	 * Returns the PassiveEffect that this PassiveAbility can apply to the player.
	 * 
	 * @return The PassiveEffect.
	 */
	public PassiveEffect getEffect();

}
