package se.dat255.bulletinferno.model.mock;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.entity.Teamable;
import se.dat255.bulletinferno.model.physics.Collidable;
import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinition;
import se.dat255.bulletinferno.model.physics.PhysicsMovementPattern;
import se.dat255.bulletinferno.model.weapon.Projectile;
import se.dat255.bulletinferno.model.weapon.ProjectileType;

import com.badlogic.gdx.math.Vector2;

public class SimpleMockProjectile implements Projectile {
	public Game game;
	private Teamable source;
	
	public SimpleMockProjectile() {
		this(null);
	}

	public SimpleMockProjectile(Game game) {
		this.game = game;
	}

	@Override
	public void reset() {
	}

	@Override
	public float getDamage() {
		return 0;
	}

	@Override
	public void setVelocity(Vector2 velocity) {
	}

	@Override
	public Vector2 getPosition() {
		return null;
	}

	@Override
	public void preCollided(Collidable other) {
		
	}

	@Override
	public void postCollided(Collidable other) {
	}

	@Override
	public void init(ProjectileType type, Vector2 position, Vector2 velocity, float damage, Teamable source,
			PhysicsBodyDefinition bodyDefinition) {
		
	}

	@Override
	public Teamable getSource() {
		return source;
	}
	
	public void setSource(Teamable source) {
		this.source = source;
	}

	@Override
	public ProjectileType getType() {
		return null;
	}

	@Override
	public Vector2 getDimensions() {
		return null;
	}

}
