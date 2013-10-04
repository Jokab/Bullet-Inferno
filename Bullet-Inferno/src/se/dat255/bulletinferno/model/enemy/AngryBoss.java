package se.dat255.bulletinferno.model.enemy;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.Ship;
import se.dat255.bulletinferno.model.Weapon;
import se.dat255.bulletinferno.model.physics.PhysicsMovementPattern;
import se.dat255.bulletinferno.util.Timer;
import se.dat255.bulletinferno.util.Timerable;

import com.badlogic.gdx.math.Vector2;

public class AngryBoss extends BossAI implements Ship, Timerable{
	

		
	public AngryBoss(Game game, EnemyType type, Vector2 position, Vector2 velocity, PhysicsMovementPattern pattern,
			int initialHealth, Weapon[] weapons, int score, int credits, Vector2[] offsets) {
		super(game, type, position, velocity, initialHealth, weapons, score, credits, offsets, pattern);
	}
	
	
	/*
	 * This should be used instead of onTimeout, but for some reason we get a NullPointerException
	 * 
	 */
	@Override
	public void fire(){
		if(isShooting()){
			if(getHealth() > 0.75*getInitialHealth()){
				fireSpread();		
			}else if(getHealth() <= 0.5*getInitialHealth() && getHealth() > 0.25*getInitialHealth()){
				fireAimed();
			}else{
				fireSpread();
				fireAimed();
			}
		}
	}
	
	@Override
	public void onTimeout(Timer source, float timeSinceLast) {
		if(isShooting()){
			if(getHealth() > 0.75*getInitialHealth()){
				fireSpread();		
			}else if(getHealth() <= 0.5*getInitialHealth() && getHealth() > 0.25*getInitialHealth()){
				fireAimed();
			}else{
				fireSpread();
				fireAimed();
			}
		}
	}


	@Override
	public Vector2 getDimensions() {
		return new Vector2(2,2);
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

