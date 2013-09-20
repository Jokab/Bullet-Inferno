package se.dat255.bulletinferno.model;
import com.badlogic.gdx.math.Vector2;


public class PlayerShipImpl implements PlayerShip {
	private final Vector2 position = new Vector2();
	private Game world;
	private Weapon weapon;
	private final int initialHealth;
	private int health;

	public PlayerShipImpl(final Vector2 position, Game world, int initialHealth) {
		this.position.set(position);
		this.world = world;
		this.initialHealth = initialHealth;
		weapon = new WeaponImpl(0, world);
	}

	@Override
	public void collided(Collidable with) {
		// TODO Auto-generated method stub

	}

	@Override
	public void takeDamage(int damage) {
		this.health -= damage;
	}

	@Override
	public int getHealth() {
		return this.health;
	}

	@Override
	public int getInitialHealth() {
		return this.initialHealth;
	}
	

	@Override
	public Vector2 getPosition() {
		return position;
	}

	@Override
	public void setPosition(Vector2 position) {
		this.position.set(position);
	}

	@Override
	public void fireWeapon() {
		weapon.fire(position);
	}

}
