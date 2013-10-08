package se.dat255.bulletinferno.model.mock;

import java.util.List;

import se.dat255.bulletinferno.model.weapon.Projectile;
import se.dat255.bulletinferno.model.weapon.WeaponEnvironment;

public class WeaponMockEnvironment implements WeaponEnvironment {

	@Override
	public List<? extends Projectile> getProjectiles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Projectile retrieveProjectile(Class<? extends Projectile> type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void disposeProjectile(Projectile projectile) {
		projectile.reset();
		
	}

}
