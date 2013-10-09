package se.dat255.bulletinferno.model.entity;

import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinition;
import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;
import se.dat255.bulletinferno.model.physics.PhysicsMovementPattern;
import se.dat255.bulletinferno.model.weapon.Weapon;
import se.dat255.bulletinferno.util.Timer;
import se.dat255.bulletinferno.util.Timerable;

import com.badlogic.gdx.math.Vector2;

public class DefaultEnemyShipImpl extends SimpleEnemy implements Ship, Timerable {
	
	private Weapon[] weapons;
	private Timer[] timers;
	
	public DefaultEnemyShipImpl(PhysicsEnvironment physics, EntityEnvironment entities, 
			EnemyDefinitionImpl type, Vector2 position, Vector2 velocity, int initialHealth, Weapon[] weapons,
			int score, int credits, PhysicsBodyDefinition bodyDefinition) {
		super(physics, entities, type, position, velocity, initialHealth, weapons, score, credits, 
				bodyDefinition);
		this.weapons = weapons;
		this.timers = new Timer[weapons.length];
		for(int i=0; i<weapons.length; i++){
			timers[i] = weapons[i].getTimer();
			timers[i].registerListener(this);
			timers[i].stop();
		}

	}
	
	public DefaultEnemyShipImpl(PhysicsEnvironment physics, EntityEnvironment entities,
			EnemyDefinitionImpl type, Vector2 position, Vector2 velocity, int initialHealth,
			Weapon[] weapons, int score, int credits, PhysicsBodyDefinition bodyDefinition,
			PhysicsMovementPattern pattern) {
		super(physics, entities, type, position, velocity, initialHealth, weapons, score, credits,
				bodyDefinition, pattern);
		this.weapons = weapons;
		this.timers = new Timer[weapons.length];
		for(int i=0; i< weapons.length; i++){
			timers[i] = weapons[i].getTimer();
			timers[i].registerListener(this);
			timers[i].stop();
		}
	}

	@Override
	public void onTimeout(Timer source, float timeSinceLast) {
		for(int i=0; i<weapons.length; i++){
			if(source == timers[i]){
				weapons[i].fire(new Vector2(getPosition().x, getPosition().y), velocity.cpy().nor(), this);
		
			}
		}
	}
	
	@Override
	public void viewportIntersectionBegin() {
		super.viewportIntersectionBegin();
		for(int i=0; i<weapons.length; i++){
			timers[i].start();
		}
	}
}
