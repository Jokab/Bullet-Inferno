package se.dat255.bulletinferno.model.enemy;

import com.badlogic.gdx.math.Vector2;
import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.PhysicsMovementPattern;
import se.dat255.bulletinferno.model.PlayerShip;
import se.dat255.bulletinferno.model.Weapon;
import se.dat255.bulletinferno.util.Timer;
import se.dat255.bulletinferno.util.Timerable;

public abstract class BossAI extends SimpleEnemy implements Timerable {
	
	private Weapon[] weapons;
	private Timer[] timers;
	private Vector2[] offsets;
	private PlayerShip player;
	private Vector2[] aim;
	private Game game;
	private boolean speedSet = false;
	private boolean shooting = false;
	private float spreadSpeed = 0.8f;
	private float aimSpeed = 0.1f;
	

	public BossAI(Game game, EnemyType type, Vector2 position, Vector2 velocity,
			PhysicsMovementPattern pattern, int initialHealth, Weapon[] weapons, int score,
			int credits, Vector2[] offsets) {
		super(game, type, position, velocity, pattern, initialHealth, weapons, score, credits);
		
		this.game = game;
		this.weapons = weapons;
		this.timers = new Timer[weapons.length];
		this.aim = new Vector2[weapons.length];
		this.offsets = offsets;
		this.player = game.getPlayerShip();
		for(int i=0; i< weapons.length; i++){
			timers[i] = weapons[i].getTimer();
			timers[i].registerListener(this);
		}
	}
	
	public abstract void fire();
	
	public abstract void onTimeout(Timer source, float timeSinceLast);


	private void calculateAimVector(){
		Vector2 position = super.getPosition();

		for(int i = 0; i<(int)weapons.length/2; i++){
			aim[i] = new Vector2(player.getPosition().sub(position.x, (position.y+offsets[i].y)).nor());
		}
	}


	public void fireSpread(){
		calculateAimVector();
		for(int i = (int)weapons.length/2; i<weapons.length; i++){
			if(!speedSet){
				weapons[i].setReloadingTime(spreadSpeed);
			}
			weapons[i].fire(this.getPosition(), velocity.cpy().nor().scl(new Vector2(1,0)), this);
		}
		speedSet=true;
	}


	public void fireAimed(){
		calculateAimVector();
		for(int i = 0; i<(int)weapons.length/2; i++){
			if(!speedSet){
				weapons[i].setReloadingTime(aimSpeed);
			}
			weapons[i].fire(this.getPosition(), aim[i].scl(velocity.cpy()), this);

		}
		speedSet=true;
	}


	@Override
	public void viewportIntersectionBegin() {
		shooting=true;
		player.setXSpeed(0f);

	}
	
	public boolean isShooting(){
		return shooting;
	}
	
	@Override
	public void takeDamage(float damage) {
		// Take no damage if enemy isn't alive
		if(damage >= getHealth()) {
			game.bossDead();
			shooting = false;
			super.takeDamage(damage);
		}else{
			super.takeDamage(damage);	
		}
	}

}

