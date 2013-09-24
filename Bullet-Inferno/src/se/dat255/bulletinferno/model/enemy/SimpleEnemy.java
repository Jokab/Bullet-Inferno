package se.dat255.bulletinferno.model.enemy;

import se.dat255.bulletinferno.model.Collidable;
import se.dat255.bulletinferno.model.Destructible;
import se.dat255.bulletinferno.model.Enemy;
import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.PhysicsBody;
import se.dat255.bulletinferno.model.PhysicsBodyDefinition;
import se.dat255.bulletinferno.model.PhysicsBodyDefinitionImpl;
import se.dat255.bulletinferno.model.Projectile;
import se.dat255.bulletinferno.model.Weapon;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape;

abstract class SimpleEnemy implements Enemy, Collidable, Destructible {

	private int health;
	private final int initialHealth;
	private int score;
	private int credits;

	private static PhysicsBodyDefinition bodyDefinition = null;
	private PhysicsBody body = null;
	private Game game;
	
	private final Weapon weapon;

	public SimpleEnemy(Game game, Vector2 position, Vector2 velocity,
			int initialHealth, Weapon weapon) {
		this.game = game;
		this.initialHealth = initialHealth;
		this.health = initialHealth;
		this.weapon = weapon;

		if (bodyDefinition == null) {
			Shape shape = game.getPhysicsWorld().getShapeFactory().getRectangularShape(0.5f, 0.5f);
			bodyDefinition = new PhysicsBodyDefinitionImpl(shape);
		}
		body = game.getPhysicsWorld().createBody(bodyDefinition, this, position);
		body.setVelocity(velocity);
	}

	@Override
	public int getScore() {
		return score;
	}

	@Override
	public int getCredits() {
		return credits;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void preCollided(Collidable other) {
		if(other instanceof Projectile) {
			takeDamage(((Projectile)other).getDamage());
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
	public int getHealth() {
		return health;
	}

	@Override
	public void takeDamage(float damage) {
		health -= damage;
		// If enemy has died
		if(health <=0) {
			game.removeEnemy(this);
			dispose();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
		game.getPhysicsWorld().removeBody(body);
		body = null;
	}

	@Override
	public int getInitialHealth() {
		return initialHealth;
	}

	@Override
	public Vector2 getPosition() {
		return body.getPosition();
	}
	
	public void setVelocity(Vector2 velocity) {
		body.setVelocity(velocity);
	}
	
}
