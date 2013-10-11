package se.dat255.bulletinferno.model.loadout;


/**
 * A SpecialAbility is set at the loadout screen and is then set to the player
 * for that game. If you press the designated button, the SpecialAbility
 * will activate its SpecialEffect, which will do something in the world.
 * SpecialAbilities will have a recharge timer of some sort.
 *
 */
public interface SpecialAbility {

	/**
	 * Returns the effect carried by this SpecialAbility.
	 * 
	 * @return The SpecialEffect.
	 */
	public SpecialEffect getEffect();
	
}
