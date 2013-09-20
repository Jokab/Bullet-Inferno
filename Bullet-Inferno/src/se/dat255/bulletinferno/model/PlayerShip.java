package se.dat255.bulletinferno.model;

import com.badlogic.gdx.math.Vector2;

public interface PlayerShip extends Ship {

	public void fireWeapon();

	void setPosition(Vector2 position);
}
