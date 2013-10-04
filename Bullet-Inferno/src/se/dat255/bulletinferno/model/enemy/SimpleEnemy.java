package se.dat255.bulletinferno.model.enemy;

import se.dat255.bulletinferno.model.Destructible;
import se.dat255.bulletinferno.model.Enemy;
import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.Projectile;
import se.dat255.bulletinferno.model.Teamable;
import se.dat255.bulletinferno.model.Weapon;
import se.dat255.bulletinferno.model.physics.Collidable;
import se.dat255.bulletinferno.model.physics.PhysicsBody;
import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinition;
import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinitionImpl;
import se.dat255.bulletinferno.model.physics.PhysicsMovementPattern;
import se.dat255.bulletinferno.model.physics.PhysicsViewportIntersectionListener;
import se.dat255.bulletinferno.util.PhysicsShapeFactory;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape;

public abstract class SimpleEnemy implements Enemy, Collidable, Destructible,
		PhysicsViewportIntersectionListener {

	private int health;
	private final int initialHealth;
	private final int score;
	private final int credits;
	private final EnemyType type;

	private static PhysicsBodyDefinition bodyDefinition = null;
	private PhysicsBody body = null;
	private final Game game;
	protected Vector2 velocity;
	protected Weapon[] weapons;

	/** A flag to make sure we don't remove ourself twice */
	private boolean flaggedForRemoval = false;
	// TODO : Fix this in box2d instead?
	private boolean isAwake = false;
	

	/**
	 * A task that when added to the Game's runLater will remove this projectile. Used to no modify
	 * the physics world during a simulation.
	 */
	private Runnable removeSelf = new Runnable() {
		@Override
		public void run() {
			game.removeEnemy(SimpleEnemy.this);
			dispose();
		}
	};


	public SimpleEnemy(Game game, EnemyType type, Vector2 position, Vector2 velocity,
			int initialHealth, Weapon[] weapons, int score, int credits) {
		this.game = game;
		this.type = type;
		this.initialHealth = initialHealth;
		health = initialHealth;
		this.weapons = weapons;
		this.score = score;
		this.credits = credits;
		this.velocity = velocity;

		if (bodyDefinition == null) {
			Shape shape = PhysicsShapeFactory.getRectangularShape(getDimensions().x, getDimensions().y);
			bodyDefinition = new PhysicsBodyDefinitionImpl(shape);
		}
		body = game.getPhysicsWorld().createBody(bodyDefinition, this, position);
	}
	
	public SimpleEnemy(Game game, EnemyType type, Vector2 position, Vector2 velocity,
			int initialHealth, Weapon[] weapons, int score, int credits, 
			PhysicsMovementPattern pattern) {
		this(game, type, position, velocity, initialHealth, weapons, score, credits);
		game.getPhysicsWorld().attachMovementPattern(pattern.copy(), body);

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
		if (isAwake && hitByOtherProjectile(other)) {
			takeDamage(((Projectile) other).getDamage());
		}
	}

	private boolean hitByOtherProjectile(Collidable other) {
		return other instanceof Projectile && !isInMyTeam(((Projectile) other).getSource());
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

			if (isDead()) {
				scheduleRemoveSelf();
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isDead() {
		return health <= 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
		game.getPhysicsWorld().removeBody(body);
		if(weapons!=null){
			for(int i = 0; i<(weapons.length); i++){
				if (weapons[i] != null) {
					weapons[i].getTimer().stop();
				}
		}

		body = null;
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

	@Override
	public EnemyType getType() {
		return this.type;
	}

	@Override
	public void viewportIntersectionBegin() {
		isAwake = true;
		body.setVelocity(velocity);
	}

	@Override
	public void viewportIntersectionEnd() {
		scheduleRemoveSelf();
	}

	/**
	 * Removes the ship from the world using game.runLater to not modify physics world while
	 * running.
	 */
	private void scheduleRemoveSelf() {
		if (!flaggedForRemoval) {
			game.runLater(removeSelf);
			flaggedForRemoval = true;
		}
	}
	
	@Override
	public Vector2 getDimensions() {
		return new Vector2(1,1);
	}
}
