package se.dat255.bulletinferno.model.mock;

import com.badlogic.gdx.math.Vector2;

import se.dat255.bulletinferno.model.entity.PlayerShip;
import se.dat255.bulletinferno.model.loadout.PassiveAbility;
import se.dat255.bulletinferno.model.physics.Collidable;
import se.dat255.bulletinferno.model.team.Teamable;
import se.dat255.bulletinferno.model.weapon.Weapon;
import se.dat255.bulletinferno.model.weapon.WeaponLoadout;

public class SimplePlayerShipMock implements PlayerShip {

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
	public int getHealth() {
		return 0;
	}

	@Override
	public int getInitialHealth() {
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
		return null;
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
	public void attachPassive(PassiveAbility passiveAbility) {
		
	}

	@Override
	public void setTakeDamageModifier(float takeDamageModifier) {
		
	}

	@Override
	public boolean isDead() {
		return false;
	}
	
	public void moveY(float dy) {
		
	}

	@Override
	public void moveY(float dy, float scale) {
		
	}

	@Override
	public Vector2 getDimensions() {
		return null;
	}

	@Override
	public void restoreSpeed() {
		
	}

	@Override
	public void halt(float distance) {
		
	}

}
