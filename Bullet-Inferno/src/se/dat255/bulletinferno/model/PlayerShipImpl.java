package se.dat255.bulletinferno.model;

import com.badlogic.gdx.math.Vector2;

import se.dat255.bulletinferno.MyGame;

public class PlayerShipImpl implements PlayerShip {

	private float y;
	private float x;

	public PlayerShipImpl(final float x, final float y) {
		this.x = x;
		this.y = y;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		if (y > -(MyGame.VIRTUAL_HEIGHT / 2) && y < MyGame.VIRTUAL_HEIGHT / 2) {
			this.y = y;
		}
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
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
	public void takeDamage(int damage) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getHealth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getInitialHealth() {
		// TODO Auto-generated method stub
		return 0;
	}

}
