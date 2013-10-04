package se.dat255.bulletinferno.model;

import se.dat255.bulletinferno.model.physics.AccelerationMovementPattern;
import se.dat255.bulletinferno.model.physics.DisorderedMovementPattern;
import se.dat255.bulletinferno.model.physics.PhysicsMovementPattern;
import se.dat255.bulletinferno.model.weapon.ProjectileImpl;

import com.badlogic.gdx.math.Vector2;

public enum ProjectileType implements ResourceIdentifier {
	
	/**
	 * damage, movementpattern
	 */
	RED_PROJECTILE(5f, null), // null = straight forward 
	GREEN_PROJECTILE(3f, new AccelerationMovementPattern(new Vector2(-5, 0))),
	MISSILE(3f, new AccelerationMovementPattern(new Vector2(10, 0))),
	PLASMA(1f, null);
	
	private float damage;
	private PhysicsMovementPattern pattern;
	
	ProjectileType(float damage, PhysicsMovementPattern pattern) {
		this.damage = damage;
		this.pattern = pattern;
	}

	public void releaseProjectile(Game game, Vector2 position, Vector2 offset,
			Vector2 projectileVector, Teamable source) {
		
		Projectile projectile = game.retrieveProjectile(ProjectileImpl.class);
		projectile.init(this, position.cpy().add(offset), projectileVector,
				damage, source);
		
	}
	
	public void setDamage(float damage) {
		this.damage = damage;
	}

	@Override
	public String getIdentifier() {
		return this.name();
	}

	public PhysicsMovementPattern getMovementPattern() {
		return pattern;
	}
	
	public float getDamage() {
		return this.damage;
	}
	
}
