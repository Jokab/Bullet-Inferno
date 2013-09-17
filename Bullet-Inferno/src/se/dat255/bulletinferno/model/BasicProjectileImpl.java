package se.dat255.bulletinferno.model;

import com.badlogic.gdx.math.Vector2;

public class BasicProjectileImpl implements Projectile {
	private final Vector2 position;
	
	public BasicProjectileImpl(Vector2 origin) {
		position = origin;
	}
	
	@Override
	public int damage() {
		return 10;
	}

	@Override
	public void onImpact(Object target) {
		
	}
}
