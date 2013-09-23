package se.dat255.bulletinferno.model;
import se.dat255.bulletinferno.model.weapon.WeaponImpl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;


public class PlayerShipImpl implements PlayerShip {
	private final Vector2 position = new Vector2();
	private Game world;
	private Weapon weapon;
	private final int initialHealth;
	private int health;
	private float moveToPos; 
	private float moveSpeed = 0.1f;

	public PlayerShipImpl(final Vector2 position, Game world, int initialHealth) {
		this.position.set(position);
		this.world = world;
		this.initialHealth = initialHealth;
		weapon = new WeaponImpl(0, world);
		world.setPlayerShip(this);
	}

	@Override
	public void collided(Collidable with) {
		// TODO Auto-generated method stub

	}

	@Override
	public void takeDamage(int damage) {
		this.health -= damage;
	}

	@Override
	public int getHealth() {
		return this.health;
	}

	@Override
	public int getInitialHealth() {
		return this.initialHealth;
	}
	

	@Override
	public Vector2 getPosition() {
		return position;
	}

	@Override
	public void setPosition(Vector2 position) {
		this.position.set(position);
	}
	
	@Override
	public void update(float deltaTime){
				
		if(position.y > moveToPos + 0.1f){
			this.position.add(0, -moveSpeed);
		}else if(position.y < moveToPos - 0.1f){
			this.position.add(0, moveSpeed);
		}
		

	}
	
	@Override
	public void moveTo(float yPos){
		moveToPos = yPos;
	}
		
	@Override
	public void stopMovement(){
		moveToPos = position.y;
	}
	
	@Override
	public void fireWeapon() {
		weapon.fire(position);
	}

}
