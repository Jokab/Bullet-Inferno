package se.dat255.bulletinferno.model.entity;

import com.badlogic.gdx.math.Vector2;
import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinition;
import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;
import se.dat255.bulletinferno.model.physics.PhysicsMovementPattern;
import se.dat255.bulletinferno.model.weapon.Weapon;
import se.dat255.bulletinferno.util.Timer;
import se.dat255.bulletinferno.util.Timerable;

public abstract class SimpleBoss extends SimpleEnemy implements Timerable {

	
	private final PlayerShip player;
	
	private final PhysicsEnvironment physics;
	private Timer[] timers;

	/** Flag indicating wheter we have told player to move us on screen or not */
	private boolean isOnScreen = false;
	
	public SimpleBoss(PhysicsEnvironment physics, EntityEnvironment entities,
			EnemyType type, Vector2 position, Vector2 velocity, int initialHealth, Weapon[] weapons,
			int score, int credits, PhysicsBodyDefinition bodyDefinition, 
			PhysicsMovementPattern pattern) {
		super(physics, entities, type, position, velocity, initialHealth, weapons, score, credits,
				bodyDefinition, pattern);
		
		this.timers = new Timer[weapons.length];
		for (int i = 0; i < weapons.length; i++) {
			timers[i] = weapons[i].getTimer();
			timers[i].registerListener(this);
			timers[i].stop();
		}
		this.physics = physics;
		this.player = entities.getPlayerShip();
		
		
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
	public void fireSpread(Timer source) {
		for (int i = 0; i < weapons.length / 2; i++) {

			if (source == timers[i]) {
				weapons[i].fire(this.getPosition(), new Vector2(-1, 0), this);
			}
		}

	}

	public void fireAim(Timer source) {
		for (int i = weapons.length / 2; i < weapons.length; i++) {

			if (source == timers[i]) {
				weapons[i].fire(this.getPosition(), new Vector2(player.getPosition().x
						- getPosition().x, player.getPosition().y - getPosition().y).nor(),
						this);
			}
		}

	}
	
	
	public void fireAimSpread(Timer source) {
		
		fireSpread(source);

		fireAim(source);
	}
	
	
	public Timer[] getWeaponTimers(){
		return timers;
	}

}
