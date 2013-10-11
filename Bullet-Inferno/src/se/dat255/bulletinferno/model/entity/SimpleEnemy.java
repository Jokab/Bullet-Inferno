package se.dat255.bulletinferno.model.entity;

import se.dat255.bulletinferno.controller.ScoreController;
import se.dat255.bulletinferno.model.physics.Collidable;
import se.dat255.bulletinferno.model.physics.PhysicsBody;
import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinition;
import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;
import se.dat255.bulletinferno.model.physics.PhysicsMovementPattern;
import se.dat255.bulletinferno.model.physics.PhysicsViewportIntersectionListener;
import se.dat255.bulletinferno.model.team.Teamable;
import se.dat255.bulletinferno.model.weapon.Projectile;
import se.dat255.bulletinferno.model.weapon.Weapon;

import com.badlogic.gdx.math.Vector2;

public abstract class SimpleEnemy implements Enemy, Collidable, Destructible,
		PhysicsViewportIntersectionListener {

	private int health;
	private final int initialHealth;
	private final int score;
	private final int credits;
	private final EnemyDefinitionImpl type;

	protected PhysicsBody body = null;
	private final PhysicsEnvironment physics;
	private final EntityEnvironment entities;
	protected Vector2 velocity;
	protected Weapon[] weapons;
	
	private final ScoreController scoreController;

	/** A flag to make sure we don't remove ourself twice */
	private boolean flaggedForRemoval = false;
	// TODO : Fix this in box2d instead?
	protected boolean isAwake = false;

	/**
	 * A task that when added to the Game's runLater will remove this projectile. Used to no modify
	 * the physics world during a simulation.
	 */
	private Runnable removeSelf = new Runnable() {
		@Override
		public void run() {
			entities.removeEnemy(SimpleEnemy.this);
			dispose();
		}
	};

	public SimpleEnemy(PhysicsEnvironment physics, EntityEnvironment entities,
			EnemyDefinitionImpl type,
			Vector2 position, Vector2 velocity, int initialHealth, Weapon[] weapons, int score,
			int credits, PhysicsBodyDefinition bodyDefinition, ScoreController scoreController) {
		
		this.physics = physics;
		this.type = type;
		this.initialHealth = initialHealth;
		health = initialHealth;
		this.weapons = weapons;
		this.score = score;
		this.credits = credits;
		this.velocity = velocity;
		this.entities = entities;
		this.scoreController = scoreController;
		
		body = this.physics.createBody(bodyDefinition, this, position);
	}

	public SimpleEnemy(PhysicsEnvironment physics, EntityEnvironment entities, EnemyDefinitionImpl type, 
			Vector2 position, Vector2 velocity, int initialHealth, Weapon[] weapons, int score, 
			int credits, PhysicsBodyDefinition bodyDefinition, PhysicsMovementPattern pattern,
			ScoreController scoreController) {
		this(physics, entities, type, position, velocity, initialHealth, weapons,score, credits,
				bodyDefinition, scoreController);
		if(pattern != null){
			physics.attachMovementPattern(pattern.copy(), body);
		}

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
				scoreController.addScore(getScore());
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
		physics.removeBody(body);
		if (weapons != null) {
			for (int i = 0; i < (weapons.length); i++) {
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
	public EnemyDefinitionImpl getType() {
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
			physics.runLater(removeSelf);
			flaggedForRemoval = true;
		}
	}
	
	

	@Override
	public Vector2 getDimensions() {
		return body.getDimensions();
	}
}
