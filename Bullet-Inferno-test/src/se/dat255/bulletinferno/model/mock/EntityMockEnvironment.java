package se.dat255.bulletinferno.model.mock;

import java.util.ArrayList;
import java.util.List;

import se.dat255.bulletinferno.model.entity.Enemy;
import se.dat255.bulletinferno.model.entity.EntityEnvironment;
import se.dat255.bulletinferno.model.entity.PlayerShip;

public class EntityMockEnvironment implements EntityEnvironment {
	public final PhysicsWorldImplSpy physics;
	public final WeaponMockEnvironment weapons;
	public List<Enemy> enemies = new ArrayList<Enemy>();
	
	public EntityMockEnvironment() {
		this(new PhysicsWorldImplSpy() , new WeaponMockEnvironment());
	}
	
	public EntityMockEnvironment(PhysicsWorldImplSpy physics, WeaponMockEnvironment weapons) {
		this.physics = physics;
		this.weapons = weapons;
	}
	
	@Override
	public void addEnemy(Enemy enemy) {
		enemies.add(enemy);
	}

	@Override
	public List<? extends Enemy> getEnemies() {
		return this.enemies;
	}

	@Override
	public PlayerShip getPlayerShip() {
		return null;
	}

	@Override
	public void removeEnemy(Enemy enemy) {
		// TODO Auto-generated method stub
		
	}

}
