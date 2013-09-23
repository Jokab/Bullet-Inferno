package se.dat255.bulletinferno.model.enemy;

import se.dat255.bulletinferno.model.Collidable;
import se.dat255.bulletinferno.model.Destructible;
import se.dat255.bulletinferno.model.Enemy;
import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.PhysicsBody;
import se.dat255.bulletinferno.model.PhysicsBodyDefinition;
import se.dat255.bulletinferno.model.PhysicsBodyDefinitionImpl;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape;

abstract class EnemyImpl implements Enemy, Collidable, Destructible {

	private int health;
	private final int initialHealth;
	private int score;
	private int credits;

	private static PhysicsBodyDefinition bodyDefinition = null;
	private PhysicsBody body = null;
	private Game game;

	public EnemyImpl(Game game, Vector2 position, Vector2 velocity,
			int initialHealth) {
		this.initialHealth = initialHealth;

		if (bodyDefinition == null) {
			Shape shape = game.getPhysicsWorld().getShapeFactory()
					.getRectangularShape(1f, 2f);
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
		// TODO Auto-generated method stub
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void postCollided(Collidable other) {
		// TODO Auto-generated method stub
	}

	@Override
	public int getHealth() {
		return health;
	}

	@Override
	public void takeDamage(int damage) {
		health -= damage;
	}

	@Override
	public int getInitialHealth() {
		return initialHealth;
	}

	@Override
	public Vector2 getPosition() {
		return body.getPosition();
	}
}
