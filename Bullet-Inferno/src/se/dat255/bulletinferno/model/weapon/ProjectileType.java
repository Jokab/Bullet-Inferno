package se.dat255.bulletinferno.model.weapon;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.PhysicsMovementPattern;
import se.dat255.bulletinferno.model.Projectile;
import se.dat255.bulletinferno.model.ProjectileImpl;
import se.dat255.bulletinferno.model.ResourceIdentifier;
import se.dat255.bulletinferno.model.Teamable;
import se.dat255.bulletinferno.model.physics.AccelerationMovementPattern;
import se.dat255.bulletinferno.model.physics.SineMovementPattern;

import com.badlogic.gdx.math.Vector2;

public enum ProjectileType implements ResourceIdentifier {
	
	DEFAULT_PROJECTILE(5f, null), // null = straight forward 
	LEFT_ACCELERATING_PROJECTILE(3f, new AccelerationMovementPattern(new Vector2(-10, 0))),
	RIGHT_ACCELERATING_PROJECTILE(3f, new AccelerationMovementPattern(new Vector2(3, 0))),
	SINE_PROJECTILE(10f, new SineMovementPattern(10f, 10f));
	
	private float damage;
	private PhysicsMovementPattern pmp;
	
	ProjectileType(float damage, PhysicsMovementPattern pmp) {
		this.damage = damage;
		this.pmp = pmp;
	}

	public void releasePorjectile(Game game, Vector2 position, Vector2 offset,
			Vector2 projectileVelocity, Teamable source) {
		
		Projectile projectile = game.retrieveProjectile(ProjectileImpl.class);
		projectile.init(position.cpy().add(offset), projectileVelocity,
				damage, source, pmp);

	}

	@Override
	public String getIdentifier() {
		return this.name();
	}

}
