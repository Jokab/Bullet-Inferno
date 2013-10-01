package se.dat255.bulletinferno.model;

public interface Loadout {
	
	public Weapon getPrimaryWeapon();
	
	public Weapon getHeavyWeapon();
	
	public SpecialAbility getSpecialAbility();

	public PassiveAbility getPassiveAbility();
}
