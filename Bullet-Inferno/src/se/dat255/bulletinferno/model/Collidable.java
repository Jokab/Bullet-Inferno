package se.dat255.bulletinferno.model;

import com.badlogic.gdx.math.Vector2;

public interface Collidable {
	public boolean intersects(Vector2 point);
	
	public void collided(Collidable with);
	
}
