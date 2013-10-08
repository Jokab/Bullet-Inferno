package se.dat255.bulletinferno.model.entity;

import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;
import se.dat255.bulletinferno.model.weapon.WeaponEnvironment;

import com.badlogic.gdx.math.Vector2;

public interface EnemyDefinition {
	public Enemy createEnemy(PhysicsEnvironment physics, EntityEnvironment entities,
			WeaponEnvironment weaponEnvironment, Vector2 position);
}
