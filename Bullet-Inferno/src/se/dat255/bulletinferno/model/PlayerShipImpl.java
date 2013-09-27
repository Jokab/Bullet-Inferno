package se.dat255.bulletinferno.model;

import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinitionImpl;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Shape;

public class PlayerShipImpl implements PlayerShip {

	private final Vector2 position = new Vector2();
	private final Game game;
	private Weapon weapon;
	private final int initialHealth;
	private int health;
	private float moveToPos;
	private float moveSpeed = 6.0f;

	private static PhysicsBodyDefinition bodyDefinition = null;
	private PhysicsBody body = null;

	public PlayerShipImpl(Game game, final Vector2 position, int initialHealth, Weapon weapon) {
		this.position.set(position);
		this.game = game;
		this.initialHealth = initialHealth;
		this.health = initialHealth;
		this.weapon = weapon;

		if (bodyDefinition == null) {
			Shape shape = game.getPhysicsWorld().getShapeFactory().getRectangularShape(0.5f, 0.5f);
			bodyDefinition = new PhysicsBodyDefinitionImpl(shape);
		}
		body = game.getPhysicsWorld().createBody(bodyDefinition, this, position);

		game.setPlayerShip(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void preCollided(Collidable other) {
		if (other instanceof Projectile && !isInMyTeam(((Projectile) other).getSource())) {
			// If got hit by a projectile not fired by me
			takeDamage(((Projectile) other).getDamage());
		} else if (other instanceof Teamable && !isInMyTeam((Teamable) other)) {
			// TODO game over / die
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void postCollided(Collidable other) {
		// NOP
	}

	@Override
	public void takeDamage(float damage) {
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
	public void update(float deltaTime) {
		if (position.y > moveToPos + 0.1f) {
			this.position.add(0, -moveSpeed * deltaTime);
		} else if (position.y < moveToPos - 0.1f) {
			this.position.add(0, moveSpeed * deltaTime);
		}
		System.out.println(this.health);

		if (this.health <= 0) {
			dispose();
		}
	}

	@Override
	public void moveTo(float yPos) {
		moveToPos = yPos;
	}

	@Override
	public float getMovePos() {
		return moveToPos;
	}

	@Override
	public void stopMovement() {
		moveToPos = position.y;
	}

	@Override
	public void fireWeapon() {
		weapon.fire(position, new Vector2(1, 0), this);
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	@Override
	public boolean isInMyTeam(Teamable teamMember) {
		return teamMember instanceof PlayerShip;
	}

	@Override
	public void dispose() {
		game.getPhysicsWorld().removeBody(body);
		body = null;
//		if (weapon != null) {
//			weapon.getTimer().stop();
//		}
	}
}
