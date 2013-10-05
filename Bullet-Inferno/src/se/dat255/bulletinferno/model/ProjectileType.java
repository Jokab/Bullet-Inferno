package se.dat255.bulletinferno.model;

import se.dat255.bulletinferno.model.physics.AccelerationMovementPattern;
import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinition;
import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinitionImpl;
import se.dat255.bulletinferno.model.physics.PhysicsMovementPattern;
import se.dat255.bulletinferno.model.weapon.ProjectileImpl;
import se.dat255.bulletinferno.util.PhysicsShapeFactory;

import com.badlogic.gdx.math.Vector2;

public enum ProjectileType implements ResourceIdentifier {
	
	/**
	 * damage, movementpattern
	 */
	RED_PROJECTILE(5f, null, new PhysicsBodyDefinitionImpl(PhysicsShapeFactory.getRectangularShape(
			0.25f, 0.25f))), // null = straight forward
	GREEN_PROJECTILE(3f, new AccelerationMovementPattern(new Vector2(-5, 0)),
			new PhysicsBodyDefinitionImpl(PhysicsShapeFactory.getRectangularShape(0.25f,0.25f))),
	MISSILE(1000f, new AccelerationMovementPattern(new Vector2(10, 0)), new PhysicsBodyDefinitionImpl(
			PhysicsShapeFactory.getRectangularShape(0.25f,0.25f))),
	PLASMA(1f, null, new PhysicsBodyDefinitionImpl(PhysicsShapeFactory.getRectangularShape(0.25f,0.25f)));

	private float damage;
	private final PhysicsMovementPattern pattern;
	private final PhysicsBodyDefinition bodyDefinition;
	
	ProjectileType(float damage, PhysicsMovementPattern pattern, PhysicsBodyDefinition bodyDefinition) {
		this.damage = damage;
		this.pattern = pattern;
		this.bodyDefinition = bodyDefinition;
	}

	public void releaseProjectile(Game game, Vector2 position, Vector2 offset,
			Vector2 projectileVector, Teamable source) {
		
		Projectile projectile = game.retrieveProjectile(ProjectileImpl.class);
		projectile.init(this, position.cpy().add(offset), projectileVector,
				damage, source, bodyDefinition);
		
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
