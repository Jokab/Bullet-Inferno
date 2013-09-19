package se.dat255.bulletinferno.model;

import com.badlogic.gdx.math.Vector2;

public interface PlayerShip extends Ship {
	
	Weapon getWeapon();
	
	void setPosition(Vector2 position);

}
