package se.dat255.bulletinferno.model.enemy;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.PhysicsMovementPattern;
import se.dat255.bulletinferno.model.PlayerShip;
import se.dat255.bulletinferno.model.Ship;
import se.dat255.bulletinferno.model.Weapon;
import se.dat255.bulletinferno.util.Timer;
import se.dat255.bulletinferno.util.Timerable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class AngryBoss extends SimpleEnemy implements Ship, Timerable {
	
	private Weapon[] weapons;
	private Timer[] timers;
	private int noOfWeapons;
	private Vector2[] offsets;
	private PlayerShip player;
	private Vector2[] aim;
		
	public AngryBoss(Game game, EnemyType type, Vector2 position, Vector2 velocity, PhysicsMovementPattern pattern,
			int initialHealth, Weapon[] weapons, Vector2[] offsets, int score, int credits) {
		super(game, type, position, velocity, pattern, initialHealth, weapons, score, credits);
		this.offsets = offsets;
		this.weapons = weapons;
		this.timers = new Timer[weapons.length];
		this.aim = new Vector2[weapons.length];
		this.offsets = new Vector2[offsets.length];
		this.offsets = offsets;
		this.player = game.getPlayerShip();
		for(int i=0; i< weapons.length; i++){
			timers[i] = weapons[i].getTimer();
			timers[i].registerListener(this);
		}

	}

	private void calculateAimVector(){
		Vector2 position = super.getPosition();
		
		for(int i = 0; i<weapons.length; i++){
			aim[i] = new Vector2(player.getPosition().sub(position.x, (position.y+offsets[i].y)).nor());
		}
	}
	
	private void fireAimed(){
		calculateAimVector();
		for(int i = 0; i<weapons.length; i++){
			weapons[i].fire(this.getPosition(), aim[i].scl(velocity.cpy()), this);
			
		}
	}
	
	
	@Override
	public void onTimeout(Timer source, float timeSinceLast) {
		for(int i=0; i<weapons.length; i++){
			fireAimed();
			
			//if(source == timers[i]){
				//weapons[i].fire(new Vector2(getPosition().x, getPosition().y), velocity.cpy().nor(), this);
		
			//}
		}
	}
}

	
	
	
	
	
	
	/*
	If agentpower > playerpower and health is high:
	    mood = 'attack'
	If agentpower > playerpower and health is low:
	    mood = 'findpowerup'
	Else:
	    mood = 'evade'
	    
	    
	    if mood == 'attack':
	        if player is in range:
	            attack
	        else:
	            move closer
	    if mood == 'findpowerup':
	        if player is in range:
	            move away
	        if player is in range of power up:
	            if really need power up:
	                attack
	            if not really need power up:
	                move away
	        else:
	            move closer to power up
	    if mood == 'evade':
	        if player is in range:
	            move away
	        else:
	            find powerup	          
*/

