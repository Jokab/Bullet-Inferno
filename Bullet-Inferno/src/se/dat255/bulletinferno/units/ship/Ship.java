package se.dat255.bulletinferno.units.ship;

import se.dat255.bulletinferno.MyGame;
import se.dat255.bulletinferno.units.BaseUnit;

/**
 * Basic empty class for git (can't upload empty folders)
 */
public class Ship extends BaseUnit {

	private float y;
	private float x;

	public Ship(final float x, final float y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public void update(float delta) {
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		if (y > -(MyGame.VIRTUAL_HEIGHT / 2) && y < (MyGame.VIRTUAL_HEIGHT / 2)) {
			this.y = y;
		}
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

}
