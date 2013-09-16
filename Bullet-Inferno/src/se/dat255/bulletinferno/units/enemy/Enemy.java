package se.dat255.bulletinferno.units.enemy;

import com.badlogic.gdx.math.Vector2;

public interface Enemy {
	
	Vector2 getVelocity();
	
	void setVelocity(Vector2 velocity);
	
	Vector2 getPosition();
	
	void setPosition(Vector2 position);
	
	int getHitPoints();
	
	void setHitPoints(int hitpoints);

	void update(float deltaTime);
}
