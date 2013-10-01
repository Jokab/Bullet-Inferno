package se.dat255.bulletinferno.model.mock;

import se.dat255.bulletinferno.model.Collidable;
import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.PhysicsMovementPattern;
import se.dat255.bulletinferno.model.Projectile;
import se.dat255.bulletinferno.model.Teamable;

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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postCollided(Collidable other) {
		// TODO Auto-generated method stub
	}

	@Override
	public void init(Vector2 position, Vector2 velocity, float damage, Teamable source) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Teamable getSource() {
		return source;
	}
	
	public void setSource(Teamable source) {
		this.source = source;
	}

	@Override
	public void init(Vector2 position, Vector2 velocity, float damage, Teamable source,
			PhysicsMovementPattern pmp) {
		// TODO Auto-generated method stub
		
	}

}
