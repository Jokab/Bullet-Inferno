package se.dat255.bulletinferno.model;

public class LoadoutImpl implements Loadout {

	private final Weapon primaryWeapon;
	private final Weapon heavyWeapon;
	private final SpecialAbility specialAbility;
	private final PassiveAbility passiveAbility;

	public LoadoutImpl(Weapon primaryWeapon, Weapon heavyWeapon, SpecialAbility specialAbility, PassiveAbility passiveAbility) {
		this.primaryWeapon = primaryWeapon;
		this.heavyWeapon = heavyWeapon;
		this.specialAbility = specialAbility;
		this.passiveAbility = passiveAbility;
	}
	
	@Override
	public Weapon getPrimaryWeapon() {
		return this.primaryWeapon;
	}

	@Override
	public Weapon getHeavyWeapon() {
		return this.heavyWeapon;
	}

	@Override
	public SpecialAbility getSpecialAbility() {
		return this.specialAbility;
	}
	
	@Override
	public PassiveAbility getPassiveAbility() {
		return this.passiveAbility;
	}

}
