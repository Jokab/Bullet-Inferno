package se.dat255.bulletinferno.model.mock;

import java.util.List;

import se.dat255.bulletinferno.model.entity.Enemy;
import se.dat255.bulletinferno.model.entity.EntityEnvironment;
import se.dat255.bulletinferno.model.entity.PlayerShip;
import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;
import se.dat255.bulletinferno.model.weapon.WeaponEnvironment;

public class EntityMockEnvironment implements EntityEnvironment {
	private final PhysicsWorldImplSpy physics;
	private final WeaponMockEnvironment weapons;
	
	public EntityMockEnvironment() {
		this(new PhysicsWorldImplSpy() , new WeaponMockEnvironment());
	}
	
	public EntityMockEnvironment(PhysicsWorldImplSpy physics, WeaponMockEnvironment weapons) {
		this.physics = physics;
		this.weapons = weapons;
	}
	
	@Override
	public void addEnemy(Enemy emeny) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<? extends Enemy> getEnemies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayerShip getPlayerShip() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeEnemy(Enemy enemy) {
		// TODO Auto-generated method stub
		
	}

}
