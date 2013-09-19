package se.dat255.bulletinferno.model;

import com.badlogic.gdx.math.Vector2;

public class PlayerShipImpl implements PlayerShip {
	private final Vector2 position = new Vector2();
	private Game world;
	private Weapon weapon;

	public PlayerShipImpl(final Vector2 position, Game world) {
		this.position.set(position);
		this.world = world;
		weapon = new WeaponImpl(5, world);
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
	
	public Weapon getWeapon() {
		return weapon;
	}

	@Override
	public Vector2 getPosition() {
		return position;
	}

	@Override
	public void setPosition(Vector2 position) {
		this.position.set(position);
	}

}
