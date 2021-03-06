package se.dat255.bulletinferno.model.mock;

import se.dat255.bulletinferno.model.entity.PlayerShip;
import se.dat255.bulletinferno.model.physics.Collidable;
import se.dat255.bulletinferno.model.team.Teamable;
import se.dat255.bulletinferno.model.weapon.Weapon;
import se.dat255.bulletinferno.model.weapon.WeaponLoadout;

import com.badlogic.gdx.math.Vector2;

public class SimplePlayerShipMock implements PlayerShip {

	public Vector2 position;
	public Vector2 dimensions;
	
	@Override
	public void preCollided(Collidable other) {
	}

	@Override
	public void postCollided(Collidable other) {
	}

	@Override
	public void takeDamage(float damage) {
	}

	@Override
	public float getHealth() {
		return 0;
	}

	@Override
	public float getInitialHealth() {
		return 0;
	}

	@Override
	public boolean isInMyTeam(Teamable teamMember) {
		return false;
	}

	@Override
	public void dispose() {

	}

	@Override
	public Vector2 getPosition() {
		return position;
	}

	@Override
	public String getIdentifier() {
		return null;
	}

	@Override
	public void fireWeapon() {
	}

	@Override
	public Weapon getWeapon() {
		return null;
	}

	@Override
	public WeaponLoadout getLoadout() {
		return null;
	}

	@Override
	public void setTakeDamageModifier(float takeDamageModifier) {
		
	}

	@Override
	public boolean isDead() {
		return false;
	}
	
	@Override
	public void moveY(float dy) {
		
	}

	@Override
	public void moveY(float dy, float scale) {
		
	}

	@Override
	public Vector2 getDimensions() {
		return dimensions;
	}

	@Override
	public void restoreSpeed() {
		
	}

	@Override
	public void halt(float distance) {
		
	}

	@Override
	public float getXVelocity() {
		return 5;
	}

}
