package se.dat255.bulletinferno.model;
import java.util.EnumSet;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;


public class PlayerShipImpl implements PlayerShip {
	private final Vector2 position = new Vector2();
	private Game world;
	private Weapon weapon;
	private final int initialHealth;
	private int health;
	private enum MoveDirection { UP, NONE, DOWN };
	public EnumSet<MoveDirection> DIRECTION = EnumSet.noneOf(MoveDirection.class);

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
		if(DIRECTION.contains(MoveDirection.UP)){
			//setPosition(new Vector2(0, position.y+(float)0.1));
			this.position.add(0, 0.1f);
		}
		if(DIRECTION.contains(MoveDirection.DOWN)){
			//setPosition(new Vector2(0, position.y-(float)0.1));
			this.position.add(0, -0.1f);
		}
	}
	
	@Override
	public void moveUp(){
		DIRECTION.add(MoveDirection.UP);
	}
	
	public void moveDown(){
		DIRECTION.add(MoveDirection.DOWN);
	}
	
	@Override
	public void stopMoveUp(){
		DIRECTION.remove(MoveDirection.UP);
	}
	
	@Override
	public void stopMoveDown(){
		DIRECTION.remove(MoveDirection.DOWN);
	}
	
	@Override
	public void stopMovement(){
		DIRECTION.clear();
	}
	
	@Override
	public void fireWeapon() {
		weapon.fire(position);
	}
	

}
