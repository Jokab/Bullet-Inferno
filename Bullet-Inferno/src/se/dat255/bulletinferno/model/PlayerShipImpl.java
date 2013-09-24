package se.dat255.bulletinferno.model;

import com.badlogic.gdx.math.Vector2;

public class PlayerShipImpl implements PlayerShip {
	
	private final Vector2 position = new Vector2();
	private final Game game;
	private Weapon weapon;
	private final int initialHealth;
	private int health;
	private float moveToPos; 
	private float moveSpeed = 6.0f;

	public PlayerShipImpl(Game game, final Vector2 position, int initialHealth, Weapon weapon) {
		this.position.set(position);
		this.game = game;
		this.initialHealth = initialHealth;
		this.health = initialHealth;
		this.weapon = weapon;
		game.setPlayerShip(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void preCollided(Collidable other) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void postCollided(Collidable other) {
		// TODO Auto-generated method stub
	}

	@Override
	public void takeDamage(float damage) {
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
			this.position.add(0, -moveSpeed *deltaTime);
		} else if(position.y < moveToPos - 0.1f){
			this.position.add(0, moveSpeed *deltaTime);
		}
	}
	
	@Override
	public void moveTo(float yPos){
		moveToPos = yPos;
	}
	
	@Override
	public float getMovePos(){
		return moveToPos;
	}
		
	@Override
	public void stopMovement(){
		moveToPos = position.y;
	}
	
	@Override
	public void fireWeapon() {
		weapon.fire(position);
	}
	
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
}
