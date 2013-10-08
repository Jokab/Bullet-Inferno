package se.dat255.bulletinferno.model.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import se.dat255.bulletinferno.model.physics.DisorderedMovementPattern;
import se.dat255.bulletinferno.model.physics.FollowingMovementPattern;
import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinition;
import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;
import se.dat255.bulletinferno.model.physics.PhysicsMovementPattern;
import se.dat255.bulletinferno.model.weapon.Weapon;
import se.dat255.bulletinferno.util.Timer;
import se.dat255.bulletinferno.util.Timerable;

/**
 * Abstract superclass for different implementation of bosses. 
 * Implements some methods to be user by subclass to differentiate
 * boss behaviour
 * 
 * @author Simon Österberg
 * 
 */

public abstract class SimpleBoss extends SimpleEnemy implements Timerable {

	
	private final PlayerShip player;
	
	private final PhysicsEnvironment physics;
	private Timer[] timers;
	private Vector2[] offsets;
	private String weaponId;
	DisorderedMovementPattern dmp;
	FollowingMovementPattern fmp;

	/** Flag indicating whether we have told player to move us on screen or not */
	private boolean isOnScreen = false;
	
	public SimpleBoss(PhysicsEnvironment physics, EntityEnvironment entities,
			EnemyType type, Vector2 position, Vector2 velocity, int initialHealth, Weapon[] weapons,
			int score, int credits, PhysicsBodyDefinition bodyDefinition, 
			PhysicsMovementPattern pattern) {
		super(physics, entities, type, position, velocity, initialHealth, weapons, score, credits,
				bodyDefinition, pattern);

		this.timers = new Timer[weapons.length];
		this.offsets = new Vector2[weapons.length];
		for (int i = 0; i < weapons.length; i++) {
			timers[i] = weapons[i].getTimer();
			timers[i].registerListener(this);
			timers[i].stop();
			offsets[i] = weapons[i].getOffset();
		}
		this.physics = physics;
		this.player = entities.getPlayerShip();
	}
	
	public SimpleBoss(PhysicsEnvironment physics, EntityEnvironment entities,
			EnemyType type, Vector2 position, Vector2 velocity, int initialHealth, Weapon[] weapons,
			int score, int credits, PhysicsBodyDefinition bodyDefinition) {
		this(physics, entities, type, position, velocity, initialHealth, weapons, score, credits,
				bodyDefinition, null);
		fmp = new FollowingMovementPattern(player);
		physics.attachMovementPattern(fmp.copy(), body);
		this.dmp = new DisorderedMovementPattern(1,4);
	}

	public abstract void onTimeout(Timer source, float timeSinceLast);

	

	@Override
	public void viewportIntersectionBegin() {
		for (int i = 0; i < weapons.length; i++) {
			timers[i].start();
		}
		super.viewportIntersectionBegin();
		if(!isOnScreen) {
			player.halt(super.getDimensions().x);
			isOnScreen = true;
		}
	}


	@Override
	public void takeDamage(float damage) {
		
		super.takeDamage(damage);

		if (isDead()) {
			// TODO Restore player ship speed
		}
	}
	
	// Different firing methods determine how many weapon to fire and in what direction
	public void fireWide(Timer source) {
		for (int i = 0; i < weapons.length; i++) {
			weaponId = weapons[i].getType().getIdentifier();
			if (source == timers[i] && weaponId.substring(0,8).equals("BOSS_SPR")) {
				weapons[i].fire(this.getPosition(), new Vector2(-1,offsets[i].y).nor(),
						this);
			}
		}

	}
	
	public void fireSpread(Timer source) {
		for (int i = 0; i < weapons.length; i++) {
			weaponId = weapons[i].getType().getIdentifier();
			if (source == timers[i] && weaponId.substring(0,8).equals("BOSS_SPR")) {
				weapons[i].fire(this.getPosition(), new Vector2(-1, 0), this);
			}
		}

	}

	public void fireAim(Timer source) {
		for (int i = 0; i < weapons.length; i++) {
			weaponId = weapons[i].getType().getIdentifier();
			if (source == timers[i] && weaponId.substring(0,8).equals("BOSS_AIM")) {
				weapons[i].fire(this.getPosition(), new Vector2(player.getPosition().x
						- getPosition().x, player.getPosition().y - getPosition().y - offsets[i].y).nor(),
						this);
			}
		}

	}
	
	
	public void fireAimSpread(Timer source) {
		
		fireAim(source);
		
		fireSpread(source);


	}
	
	public void changeToDisorderedMovement(){
		physics.attachMovementPattern(dmp.copy(), body);
	}
	
	public void changeToFollowingMovement(){
		physics.attachMovementPattern(fmp.copy(), body);
	}
	
	
	public Timer[] getWeaponTimers(){
		return timers;
	}

}
