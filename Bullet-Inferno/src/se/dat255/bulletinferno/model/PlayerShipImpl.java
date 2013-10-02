package se.dat255.bulletinferno.model;

import se.dat255.bulletinferno.Graphics;

import com.badlogic.gdx.math.Vector2;

public class PlayerShipImpl implements PlayerShip, ResourceIdentifier {
	
	public enum ShipType {
		PLAYER_DEFAULT;
	}
	
	private final Vector2 position;
	private final Game game;
	private final int initialHealth;
	private float takeDamageModifier = 1; // default
	private int health;
	private float moveToPos; 
	private float moveSpeed = 6.0f;
	private float velocity = 1f;
	private final ShipType shipType;
	private final Loadout loadout;

	public PlayerShipImpl(Game game, final Vector2 position, int initialHealth, Loadout loadout, ShipType shipType) {
		this.position = position.cpy();
		this.game = game;
		this.initialHealth = initialHealth;
		this.health = initialHealth;
		this.loadout = loadout;
		this.shipType = shipType;
		
		//TODO: should probably not apply this here
		if(loadout.getPassiveAbility() != null) {
			loadout.getPassiveAbility().getEffect().applyEffect(this);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void preCollided(Collidable other) {
		if(hitByOtherProjectile(other)) {
			takeDamage(((Projectile) other).getDamage());
		} else if (collidedWithSomethingElse(other)) {
			// TODO game over / die
		}
	}

	private boolean collidedWithSomethingElse(Collidable other) {
		return other instanceof Teamable && !isInMyTeam((Teamable)other);
	}

	private boolean hitByOtherProjectile(Collidable other) {
		return other instanceof Projectile  && !isInMyTeam(((Projectile)other).getSource());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void postCollided(Collidable other) {
		// NOP
	}

	@Override
	public void takeDamage(float damage) {
		this.health -= damage * takeDamageModifier;
	}
	
	@Override
	public void setTakeDamageModifier(float takeDamageModifier) {
		this.takeDamageModifier = takeDamageModifier;
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
		this.position.add(velocity *deltaTime,0);
		Graphics.setNewCameraPos((this.getPosition().x+Graphics.GAME_WIDTH/2),(Graphics.GAME_HEIGHT/2));
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
		loadout.getPrimaryWeapon().fire(position, new Vector2(1,0), this);
	}
	
	@Override
	public Weapon getWeapon() {
		return this.loadout.getPrimaryWeapon();
	}

	@Override
	public boolean isInMyTeam(Teamable teamMember) {
		return teamMember instanceof PlayerShip;
	}
	
	@Override
	public String getIdentifier() {
		return this.shipType.name();
	}
	
	@Override
	public void attachPassive(PassiveAbility passiveAbility) {
		passiveAbility.getEffect().applyEffect(this);
	}
	
	@Override
	public void dispose() {
		// TODO: do stuff here
	}

	@Override
	public Loadout getLoadout() {
		return this.loadout;
	}
}
