package se.dat255.bulletinferno.model.weapon;

/**
 * @see WeaponLoadout
 */
public class WeaponLoadoutImpl implements WeaponLoadout {

	private final Weapon primaryWeapon;
	private final Weapon heavyWeapon;

	public WeaponLoadoutImpl(Weapon primaryWeapon, Weapon heavyWeapon) {
		this.primaryWeapon = primaryWeapon;
		this.heavyWeapon = heavyWeapon;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Weapon getStandardWeapon() {
		return primaryWeapon;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Weapon getHeavyWeapon() {
		return heavyWeapon;
	}

}