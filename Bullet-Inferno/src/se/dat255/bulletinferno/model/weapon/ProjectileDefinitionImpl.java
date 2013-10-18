package se.dat255.bulletinferno.model.weapon;

import se.dat255.bulletinferno.model.physics.AccelerationMovementPattern;
import se.dat255.bulletinferno.model.physics.DisorderedMovementPattern;
import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinition;
import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinitionImpl;
import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;
import se.dat255.bulletinferno.model.physics.PhysicsMovementPattern;
import se.dat255.bulletinferno.model.physics.SpreadMovementPattern;
import se.dat255.bulletinferno.model.team.Teamable;
import se.dat255.bulletinferno.util.PhysicsShapeFactory;
import se.dat255.bulletinferno.util.ResourceIdentifier;

import com.badlogic.gdx.math.Vector2;

/**
 * An enum for holding different types of projectiles. Are fired by using {@link #releaseProjectile()}
 */
public enum ProjectileDefinitionImpl implements ResourceIdentifier, ProjectileDefinition {

	/**
	 * damage, movementpattern, hitbox
	 * 
	 * null movement pattern implies straight travel
	 */

	VELOCITY_BULLET(0.2f, new SpreadMovementPattern(0.2f), new PhysicsBodyDefinitionImpl(
			PhysicsShapeFactory.getRectangularShape(
					0.2f, 0.1f))),
	ROUND_BULLET(0.1f, new SpreadMovementPattern(0.7f), new PhysicsBodyDefinitionImpl(
			PhysicsShapeFactory.getRectangularShape(
					0.3f, 0.1f))),
	PLASMA(0.3f, new DisorderedMovementPattern(0.05f, 2f), new PhysicsBodyDefinitionImpl(
			PhysicsShapeFactory.getRectangularShape(
					0.4f, 0.2f))),
	LASER(1f, null, new PhysicsBodyDefinitionImpl(PhysicsShapeFactory.getRectangularShape(
			0.6f, 0.05f))),
	EGG(1f, new AccelerationMovementPattern(new Vector2(30, 0)), new PhysicsBodyDefinitionImpl(
			PhysicsShapeFactory.getRectangularShape(
					0.2f, 0.15f))),

	RED_PROJECTILE(0.1f, null, new PhysicsBodyDefinitionImpl(
			PhysicsShapeFactory.getRectangularShape(
					0.25f, 0.25f))),
	GREEN_PROJECTILE(0.15f, new AccelerationMovementPattern(new Vector2(-10, 0)),
			new PhysicsBodyDefinitionImpl(PhysicsShapeFactory.getRectangularShape(0.25f, 0.25f))),
	MISSILE(0.5f, new AccelerationMovementPattern(new Vector2(10, 0)),
			new PhysicsBodyDefinitionImpl(
					PhysicsShapeFactory.getRectangularShape(0.4f, 0.3f))),
	SPECIAL_ABILITY_MISSILE(0.45f, new AccelerationMovementPattern(new Vector2(10, 0)),
			new PhysicsBodyDefinitionImpl(
					PhysicsShapeFactory.getRectangularShape(0.4f, 0.3f))),
	HIGH_VELOCITY_PROJECTILE(0.2f, new AccelerationMovementPattern(new Vector2(20, 0)),
			new PhysicsBodyDefinitionImpl(PhysicsShapeFactory.getRectangularShape(0.8f, 0.5f)));

	private float damage;
	private final PhysicsMovementPattern pattern;
	private final PhysicsBodyDefinition bodyDefinition;

	ProjectileDefinitionImpl(float damage, PhysicsMovementPattern pattern,
			PhysicsBodyDefinition bodyDefinition) {
		this.damage = damage;
		this.pattern = pattern;
		this.bodyDefinition = bodyDefinition;
	}

	@Override
	public Projectile releaseProjectile(PhysicsEnvironment physics,
			WeaponEnvironment weapons,
			Vector2 position,
			Vector2 projectileVector, Teamable source) {

		Projectile projectile = weapons
				.retrieveProjectile(ProjectileImpl.class);
		projectile.init(this, position.cpy(), projectileVector,
				damage, source, bodyDefinition);
		return projectile;
	}

	@Override
	public void setDamage(float damage) {
		this.damage = damage;
	}

	@Override
	public String getIdentifier() {
		return this.name();
	}

	@Override
	public PhysicsMovementPattern getMovementPattern() {
		return pattern;
	}

	@Override
	public float getDamage() {
		return damage;
	}
}
