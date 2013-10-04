package se.dat255.bulletinferno.model.enemy;

import java.awt.Dimension;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.PhysicsMovementPattern;
import se.dat255.bulletinferno.model.Ship;
import se.dat255.bulletinferno.model.Weapon;
import se.dat255.bulletinferno.util.Timer;
import se.dat255.bulletinferno.util.Timerable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class DefaultEnemyShipImpl extends SimpleEnemy implements Ship, Timerable {
	
	private Weapon[] weapons;
	private Timer[] timers;
	
	public DefaultEnemyShipImpl(Game game, EnemyType type, Vector2 position, Vector2 velocity,
			int initialHealth, Weapon[] weapons, int score, int credits) {
		super(game, type, position, velocity, initialHealth, weapons, score, credits);
		this.weapons = weapons;
		this.timers = new Timer[weapons.length];
		for(int i=0; i<weapons.length; i++){
			timers[i] = weapons[i].getTimer();
			timers[i].registerListener(this);
			timers[i].stop();
		}

	}
	
	public DefaultEnemyShipImpl(Game game, EnemyType type, Vector2 position, Vector2 velocity,
			int initialHealth, Weapon[] weapons, int score, int credits, 
			PhysicsMovementPattern pattern) {
		super(game, type, position, velocity, initialHealth, weapons, score, credits, pattern);
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

	@Override
	public Vector2 getDimensions() {
		return new Vector2(1,1);
	}

}
