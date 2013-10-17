package se.dat255.bulletinferno.model.mock;

import java.util.ArrayList;
import java.util.List;

import se.dat255.bulletinferno.model.weapon.ProjectileDefinition;
import se.dat255.bulletinferno.model.weapon.WeaponEnvironment;

public class WeaponMockEnvironment implements WeaponEnvironment {
	public List<ProjectileDefinition> retrievedProjectiles = new ArrayList<ProjectileDefinition>();
	public List<ProjectileDefinition> destroyedProjectiles = new ArrayList<ProjectileDefinition>();
	public List<ProjectileDefinition> activeProjectiles = new ArrayList<ProjectileDefinition>();
	
	@Override
	public List<? extends ProjectileDefinition> getProjectiles() {
		return activeProjectiles;
	}

	@Override
	public ProjectileDefinition retrieveProjectile(Class<? extends ProjectileDefinition> type) {
		ProjectileDefinition p = new SimpleMockProjectile();
		retrievedProjectiles.add(p);
		activeProjectiles.add(p);
		return p;
	}
	
	@Override
	public void disposeProjectile(ProjectileDefinition projectile) {
		destroyedProjectiles.add(projectile);
		activeProjectiles.remove(projectile);
		projectile.reset();
		
	}

}
