package se.dat255.bulletinferno.model.entity;

import com.badlogic.gdx.math.Vector2;

import se.dat255.bulletinferno.model.physics.DisorderedBossMovementPattern;
import se.dat255.bulletinferno.model.physics.EvadingMovementPattern;
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
 * boss behavior
 * 
 * @author Simon Österberg
 * 
 */

public abstract class SimpleBoss extends SimpleEnemy implements Timerable {

	
	private final PlayerShip player;
	
	private final PhysicsEnvironment physics;
	private final EntityEnvironment entities;
	private Timer[] timers;
	private String weaponId;
	private DisorderedBossMovementPattern dmp;
	private FollowingMovementPattern fmp;
	private EvadingMovementPattern emp;
	private String currentPattern;
	

	/** Flag indicating whether we have told player to move us on screen or not */
	private boolean isOnScreen = false;
	
	public SimpleBoss(PhysicsEnvironment physics, EntityEnvironment entities,
			EnemyDefinitionImpl type, Vector2 position, Vector2 velocity, int initialHealth, Weapon[] weapons,
			Vector2[] weaponPositionModifier,
			int score, int credits, PhysicsBodyDefinition bodyDefinition) {
		super(physics, entities, type, position, velocity, initialHealth, weapons, weaponPositionModifier, score, credits,
				bodyDefinition);
		
		this.timers = new Timer[weapons.length];
		this.entities = entities;		
		this.physics = physics;
		this.player = entities.getPlayerShip();
		for (int i = 0; i < weapons.length; i++) {
			timers[i] = weapons[i].getTimer();
			timers[i].registerListener(this);
			timers[i].stop();
		}
	
		this.fmp = new FollowingMovementPattern(player);
		this.dmp = new DisorderedBossMovementPattern(3f,3);
		this.emp = new EvadingMovementPattern(player);
		currentPattern = "none";
			
	}
		
	public SimpleBoss(PhysicsEnvironment physics, EntityEnvironment entities,
			EnemyDefinitionImpl type, Vector2 position, Vector2 velocity, int initialHealth, Weapon[] weapons, 
			Vector2[] weaponPositionModifier,
			int score, int credits, PhysicsBodyDefinition bodyDefinition, 
			PhysicsMovementPattern pattern) {
		this(physics, entities, type, position, velocity, initialHealth, weapons, weaponPositionModifier, score, credits,
				bodyDefinition);

		if(pattern instanceof DisorderedBossMovementPattern){
			currentPattern = "dmp";
			this.dmp = (DisorderedBossMovementPattern) pattern;
			this.fmp = new FollowingMovementPattern(player);
			this.emp = new EvadingMovementPattern(player);
		} else if (pattern instanceof FollowingMovementPattern){
			currentPattern = "fmp";
			this.fmp = (FollowingMovementPattern) pattern;
			this.dmp = new DisorderedBossMovementPattern(3f,3);
			this.emp = new EvadingMovementPattern(player);
		} else if (pattern instanceof EvadingMovementPattern){
			this.emp = (EvadingMovementPattern) pattern;
			this.fmp = new FollowingMovementPattern(player);
			this.dmp = new DisorderedBossMovementPattern(3f,3);
		}
		physics.attachMovementPattern(pattern, body);


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
			entities.getPlayerShip().restoreSpeed();
		}
	}
	
	// Different firing methods determine how many weapon to fire and in what direction
	
	// Fires projectiles in a straight line. Only fires the BOSS_SPR* weapons.
	// Cannot be used simultaneously with fireSpread() or fireSpreadAim()
	public void fireWide(Timer source) {
		for (int i = 0; i < weapons.length; i++) {
			weaponId = weapons[i].getType().getIdentifier();
			if (source == timers[i] && weaponId.substring(0,8).equals("BOSS_SPR")) {
				weapons[i].fire(this.getPosition(), new Vector2(-1,weapons[i].getOffset().y).nor(),
						this);
			}
		}

	}
	
	// Fires projectiles in a wide, spreading pattern. Only fires the BOSS_SPR* weapons.
	// Cannot be used simultaneously with fireWide() or fireSpreadAim()
	public void fireSpread(Timer source) {
		for (int i = 0; i < weapons.length; i++) {
			weaponId = weapons[i].getType().getIdentifier();
			if (source == timers[i] && weaponId.substring(0,8).equals("BOSS_SPR")) {
				weapons[i].fire(this.getPosition(), new Vector2(-1, 0), this);
			}
		}

	}
	
	// Fires projectiles in a wide, spreading pattern aimed towards the player. Only fires the BOSS_AIM* 
	//		weapons.
	// Cannot be used simultaneously with fireAim()
	public void fireSpreadAim(Timer source) {
		for (int i = 0; i < weapons.length; i++) {
			weaponId = weapons[i].getType().getIdentifier();
			if (source == timers[i] && weaponId.substring(0,8).equals("BOSS_AIM")) {
				weapons[i].fire(this.getPosition(), new Vector2(player.getPosition().x
						- getPosition().x, player.getPosition().y - getPosition().y + weapons[i].getOffset().y).nor(),
						this);
			}
		}

	}

	// Fires projectiles aimed towards the player. Only fires the BOSS_AIM* weapons.
	public void fireAim(Timer source) {
		for (int i = 0; i < weapons.length; i++) {
			weaponId = weapons[i].getType().getIdentifier();
			if (source == timers[i] && weaponId.substring(0,8).equals("BOSS_AIM")) {
				weapons[i].fire(this.getPosition(), new Vector2(player.getPosition().x
						- getPosition().x, player.getPosition().y - getPosition().y -  weapons[i].getOffset().y).nor(),
						this);
			}
		}

	}
	
	// Methods to change the movement of the boss
	
	public void prepareMovementChange(){
		body.setVelocity(new Vector2(body.getVelocity().x,0));
		physics.detachMovementPattern(body);
	}
	
	// Gives the boss a basic up-down movement
	public void changeToDisorderedMovement(){
		prepareMovementChange();
		if(!currentPattern.equals("dmp")){
			physics.attachMovementPattern(dmp.copy(), body);
			currentPattern = "dmp";
		}
	}
	
	// Makes the boss try to match the players y-position
	public void changeToFollowingMovement(){
		prepareMovementChange();
		if(!currentPattern.equals("fmp")){
			physics.attachMovementPattern(fmp.copy(), body);
			currentPattern = "fmp";
		}
	}
	
	// Makes the boss avoid the player
	public void changeToEvadingMovement(){
		prepareMovementChange();
		if(!currentPattern.equals("emp")){
			physics.attachMovementPattern(emp.copy(), body);
			currentPattern = "emp";
		}
	}
	
	public Timer[] getWeaponTimers(){
		return timers;
	}
	
	public PhysicsMovementPattern getMovementPattern(){
		return physics.getMovementPattern(body);
	}


}
