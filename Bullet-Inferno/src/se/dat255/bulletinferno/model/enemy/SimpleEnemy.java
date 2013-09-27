package se.dat255.bulletinferno.model.enemy;

import se.dat255.bulletinferno.model.Collidable;
import se.dat255.bulletinferno.model.Destructible;
import se.dat255.bulletinferno.model.Enemy;
import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.PhysicsBody;
import se.dat255.bulletinferno.model.PhysicsBodyDefinition;
import se.dat255.bulletinferno.model.Projectile;
import se.dat255.bulletinferno.model.Weapon;
import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinitionImpl;
import se.dat255.bulletinferno.model.physics.SineMovementPattern;
import se.dat255.bulletinferno.model.Teamable;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape;

public abstract class SimpleEnemy implements Enemy, Collidable, Destructible {

	private int health;
	private final int initialHealth;
	private final int score;
	private final int credits;

	private static PhysicsBodyDefinition bodyDefinition = null;
	private PhysicsBody body = null;
	private final Game game;

	protected final Weapon weapon;

	public SimpleEnemy(Game game, Vector2 position, Vector2 velocity,
			int initialHealth, Weapon weapon, int score, int credits) {
		this.game = game;
		this.initialHealth = initialHealth;
		health = initialHealth;
		this.weapon = weapon;
		this.score = score;
		this.credits = credits;

		if (bodyDefinition == null) {
			Shape shape = game.getPhysicsWorld().getShapeFactory().getRectangularShape(0.08f, 0.1f);
			bodyDefinition = new PhysicsBodyDefinitionImpl(shape);
		}
		body = game.getPhysicsWorld().createBody(bodyDefinition, this, position);
		body.setVelocity(velocity);
		game.getPhysicsWorld().attachMovementPattern(new SineMovementPattern(4, 6f), body);

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
		if (other instanceof Projectile && !isInMyTeam(((Projectile) other).getSource())) {
			// If got hit by a projectile that wasn't fired from my team
			takeDamage(((Projectile) other).getDamage());
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
		// Take no damage if enemy isn't alive
		if (health > 0) {
			health -= damage;

			// If enemy has died
			if (health <= 0) {
				health = 0;
				game.removeEnemy(this);
				dispose();
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
		game.getPhysicsWorld().removeBody(body);
		body = null;
		if (weapon != null) {
			weapon.getTimer().stop();
		}
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

	@Override
	public boolean isInMyTeam(Teamable teamMember) {
		return teamMember instanceof Enemy;
	}
}
