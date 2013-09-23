package se.dat255.bulletinferno.model;
import java.util.EnumSet;

import com.badlogic.gdx.math.Vector2;

public interface PlayerShip extends Ship {
	

	
	public void fireWeapon();
	
	public void update(float deltaTime);
	
	void setPosition(Vector2 position);

	public void moveTo(float yPos);
	public void stopMovement();
	

}
