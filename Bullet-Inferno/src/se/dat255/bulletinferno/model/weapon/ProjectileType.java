package se.dat255.bulletinferno.model.weapon;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.PhysicsMovementPattern;
import se.dat255.bulletinferno.model.Projectile;
import se.dat255.bulletinferno.model.ProjectileImpl;
import se.dat255.bulletinferno.model.ResourceIdentifier;
import se.dat255.bulletinferno.model.Teamable;
import se.dat255.bulletinferno.model.physics.AccelerationMovementPattern;
import se.dat255.bulletinferno.model.physics.DisorderedMovementPattern;

import com.badlogic.gdx.math.Vector2;

public enum ProjectileType implements ResourceIdentifier {
	
	/**
	 * damage, movementpattern
	 */
	DEFAULT_PROJECTILE(5f, null), // null = straight forward 
	LEFT_ACCELERATING_PROJECTILE(3f, new AccelerationMovementPattern(new Vector2(-10, 0))),
	RIGHT_ACCELERATING_PROJECTILE(3f, new AccelerationMovementPattern(new Vector2(10, 0))),
	SINE_PROJECTILE(1f, new DisorderedMovementPattern(0.1f,2f));
	
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
