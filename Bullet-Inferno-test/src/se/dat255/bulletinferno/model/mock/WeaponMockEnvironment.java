package se.dat255.bulletinferno.model.mock;

import java.util.ArrayList;
import java.util.List;

import se.dat255.bulletinferno.model.weapon.Projectile;
import se.dat255.bulletinferno.model.weapon.WeaponEnvironment;

public class WeaponMockEnvironment implements WeaponEnvironment {
	public List<Projectile> retrievedProjectiles = new ArrayList<Projectile>();
	public List<Projectile> destroyedProjectiles = new ArrayList<Projectile>();
	public List<Projectile> activeProjectiles = new ArrayList<Projectile>();
	
	@Override
	public List<? extends Projectile> getProjectiles() {
		return activeProjectiles;
	}

	@Override
	public Projectile retrieveProjectile(Class<? extends Projectile> type) {
		Projectile p = new SimpleMockProjectile();
		retrievedProjectiles.add(p);
		activeProjectiles.add(p);
		return p;
	}
	
	@Override
	public void disposeProjectile(Projectile projectile) {
		destroyedProjectiles.add(projectile);
		activeProjectiles.remove(projectile);
		projectile.reset();
		
	}

}
