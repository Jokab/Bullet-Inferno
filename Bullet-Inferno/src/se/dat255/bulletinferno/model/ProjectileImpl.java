package se.dat255.bulletinferno.model;

import com.badlogic.gdx.math.Vector2;

public class ProjectileImpl implements Projectile {
	private final Vector2 position;

	public ProjectileImpl(Vector2 origin) {
		position = origin;
	}

	@Override
	public int damage() {
		return 10;
	}

	@Override
	public void onImpact(Object target) {

	}

	@Override
	public boolean intersects(Vector2 point) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void collided(Collidable with) {
		// TODO Auto-generated method stub

	}

	@Override
	public Vector2 getVelocity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setVelocity(Vector2 velocity) {
		// TODO Auto-generated method stub

	}

	@Override
	public Vector2 getPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPosition(Vector2 point) {
		// TODO Auto-generated method stub

	}
}
