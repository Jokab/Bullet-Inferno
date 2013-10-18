package se.dat255.bulletinferno.model.entity;

import com.badlogic.gdx.math.Vector2;

import se.dat255.bulletinferno.model.physics.DisorderedMovementPattern;
import se.dat255.bulletinferno.model.physics.EvadingMovementPattern;
import se.dat255.bulletinferno.model.physics.FollowingMovementPattern;
import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinition;
import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;
import se.dat255.bulletinferno.model.physics.PhysicsMovementPattern;
import se.dat255.bulletinferno.model.weapon.Weapon;
import se.dat255.bulletinferno.util.Listener;
import se.dat255.bulletinferno.util.Timer;
import se.dat255.bulletinferno.util.Timerable;

/**
 * Abstract superclass for different implementation of bosses.
 * Implements some methods to be user by subclass to differentiate
 * boss behavior
 */
public abstract class SimpleBoss extends SimpleEnemy implements Timerable, Ship {

	private final PlayerShip player;
	private final PhysicsEnvironment physics;
	private final EntityEnvironment entities;
	private Timer[] timers;
	private String weaponId;
	private DisorderedMovementPattern dmp = new DisorderedMovementPattern(0.5f, 3);
	private FollowingMovementPattern fmp;
	private EvadingMovementPattern emp;
	private String currentPattern;
	private final Listener<Integer> scoreListener;

	/** Flag indicating whether we have told player to move us on screen or not */
	private boolean isOnScreen = false;

	public SimpleBoss(PhysicsEnvironment physics, EntityEnvironment entities,
			EnemyDefinitionImpl type, Vector2 position, Vector2 velocity, float initialHealth,
			Weapon[] weapons, int score, int credits,
			PhysicsBodyDefinition bodyDefinition, Listener<Integer> scoreListener) {
		super(physics, entities, type, position, velocity, initialHealth, weapons,
				score, credits, bodyDefinition, scoreListener);

		this.timers = new Timer[getWeapons().length];
		this.entities = entities;
		this.physics = physics;
		this.player = entities.getPlayerShip();
		for (int i = 0; i < getWeapons().length; i++) {
			timers[i] = getWeapons()[i].getTimer();
			timers[i].registerListener(this);
			timers[i].stop();
		}

		this.fmp = new FollowingMovementPattern(player);
		float halfHeight = getBody().getDimensions().y / 2;
		this.emp = new EvadingMovementPattern(player, 2.5f, 0.7f + halfHeight, 9f - halfHeight);
		currentPattern = "none";

		this.scoreListener = scoreListener;
	}

	public SimpleBoss(PhysicsEnvironment physics, EntityEnvironment entities,
			EnemyDefinitionImpl type, Vector2 position, Vector2 velocity, float initialHealth,
			Weapon[] weapons,
			int score, int credits, PhysicsBodyDefinition bodyDefinition,
			PhysicsMovementPattern pattern, Listener<Integer> scoreListener) {
		this(physics, entities, type, position, velocity, initialHealth, weapons, score, credits,
				bodyDefinition, scoreListener);

		if (pattern instanceof DisorderedMovementPattern) {
			currentPattern = "dmp";
			this.dmp = (DisorderedMovementPattern) pattern;
			this.fmp = new FollowingMovementPattern(player);
			float halfHeight = getBody().getDimensions().y / 2;
			this.emp = new EvadingMovementPattern(player, 2.5f, 0.7f + halfHeight, 9f - halfHeight);
		} else if (pattern instanceof FollowingMovementPattern) {
			currentPattern = "fmp";
			this.fmp = (FollowingMovementPattern) pattern;
			float halfHeight = getBody().getDimensions().y / 2;
			this.emp = new EvadingMovementPattern(player, 2.5f, 0.7f + halfHeight, 9f - halfHeight);
		} else if (pattern instanceof EvadingMovementPattern) {
			this.emp = (EvadingMovementPattern) pattern;
			this.fmp = new FollowingMovementPattern(player);
		}
		physics.attachMovementPattern(pattern, getBody());

	}

	@Override
	public void viewportIntersectionBegin() {
		super.viewportIntersectionBegin();

		for (int i = 0; i < getWeapons().length; i++) {
			timers[i].start();
		}
		if (!isOnScreen) {
			player.halt(super.getDimensions().x);
			isOnScreen = true;
		}
	}

	@Override
	public void takeDamage(float damage) {
		super.takeDamage(damage);

		if (isDead()) {
			scoreListener.call(getScore());
			entities.getPlayerShip().restoreSpeed();
		}
	}

	// Different firing methods determine how many weapons to fire and in what direction

	/**
	 * Fires projectiles in a straight line. Only fires the BOSS_SPR* weapons. Cannot be used
	 * simultaneously with fireSpread() or fireSpreadAim()
	 */
	public void fireWide(Timer source) {
		Weapon[] weapons = getWeapons();
		Vector2 position = getPosition();
		for (int i = 0; i < weapons.length; i++) {
			weaponId = weapons[i].getType().getIdentifier();
			if (source == timers[i] && weaponId.substring(0, 8).equals("BOSS_SPR")) {
				weapons[i].fire(position,
						new Vector2(-1, weapons[i].getOffset().y).nor(),
						this);
			}
		}

	}

	/**
	 * Fires projectiles in a wide, spreading pattern. Only fires the BOSS_SPR* weapons. Cannot be
	 * used simultaneously with fireWide() or fireSpreadAim()
	 */
	public void fireSpread(Timer source) {
		Weapon[] weapons = getWeapons();
		Vector2 position = getPosition();
		for (int i = 0; i < weapons.length; i++) {
			weaponId = weapons[i].getType().getIdentifier();
			if (source == timers[i] && weaponId.substring(0, 8).equals("BOSS_SPR")) {
				weapons[i].fire(position, new Vector2(-1, 0), this);
				weapons[i].fire(new Vector2(position.x, position.y + weapons[i].getOffset().y),
						new Vector2(-1, 0), this);
			}
		}

	}

	/**
	 * Fires projectiles in a wide, spreading pattern aimed towards the player. Only fires the
	 * BOSS_AIM* weapons. Cannot be used simultaneously with fireAim()
	 */
	public void fireSpreadAim(Timer source) {
		Weapon[] weapons = getWeapons();
		Vector2 position = getPosition();
		for (int i = 0; i < weapons.length; i++) {
			weaponId = weapons[i].getType().getIdentifier();
			if (source == timers[i] && weaponId.substring(0, 8).equals("BOSS_AIM")) {
				weapons[i].fire(position, new Vector2(player.getPosition().x
						- position.x, player.getPosition().y - position.y
						+ weapons[i].getOffset().y).nor(),
						this);
			}
		}

	}

	/** Fires projectiles aimed towards the player. Only fires the BOSS_AIM* weapons. */
	public void fireAim(Timer source) {
		Weapon[] weapons = getWeapons();
		Vector2 position = getPosition();
		for (int i = 0; i < weapons.length; i++) {
			weaponId = weapons[i].getType().getIdentifier();
			if (source == timers[i] && weaponId.substring(0, 8).equals("BOSS_AIM")) {
				getWeapons()[i].fire(position, new Vector2(player.getPosition().x
						- position.x, player.getPosition().y - position.y
						- weapons[i].getOffset().y).nor(),
						this);
			}
		}

	}

	// Methods to change the movement of the boss

	public void prepareMovementChange() {
		// Set y-velocity to 0
		getBody().setVelocity(new Vector2(getBody().getVelocity().x, 0));
		physics.detachMovementPattern(getBody());
	}

	/** Gives the boss a basic up-down movement */
	public void changeToDisorderedMovement() {
		prepareMovementChange();
		if (!currentPattern.equals("dmp")) {
			physics.attachMovementPattern(dmp.copy(), getBody());
			currentPattern = "dmp";
		}
	}

	/** Makes the boss try to match the players y-position */
	public void changeToFollowingMovement() {
		prepareMovementChange();
		if (!currentPattern.equals("fmp")) {
			physics.attachMovementPattern(fmp.copy(), getBody());
			currentPattern = "fmp";
		}
	}

	/** Makes the boss avoid the player */
	public void changeToEvadingMovement() {
		prepareMovementChange();
		if (!currentPattern.equals("emp")) {
			physics.attachMovementPattern(emp.copy(), getBody());
			currentPattern = "emp";
		}
	}

	/**
	 * Returns the weapons' timers for this boss.
	 * 
	 * @return The timers.
	 */
	public Timer[] getWeaponTimers() {
		return timers;
	}

	/**
	 * Returns the movement pattern for this boss.
	 * 
	 * @return The movement pattern.
	 */
	public PhysicsMovementPattern getMovementPattern() {
		return physics.getMovementPattern(getBody());
	}

}
