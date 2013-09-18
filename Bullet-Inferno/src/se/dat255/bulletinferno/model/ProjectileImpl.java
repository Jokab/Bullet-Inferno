package se.dat255.bulletinferno.model;

import com.badlogic.gdx.math.Vector2;

public class ProjectileImpl extends SimpleVelocity implements Projectile {

	public ProjectileImpl(Vector2 origin, Vector2 velocity) {
		super(origin, velocity);
	}

	@Override
	public int getDamage() {
		return 10;
	}

	@Override
	public boolean intersects(Vector2 point) {
		return false;
	}

	@Override
	public void collided(Collidable entity) {
		if(entity instanceof Destructible) {
			// We know it can be hit
			((Destructible) entity).takeDamage(this.getDamage());
		}
	}
}
