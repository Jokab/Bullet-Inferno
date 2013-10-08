package se.dat255.bulletinferno.model.entity;

import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;
import se.dat255.bulletinferno.model.weapon.WeaponEnvironment;

import com.badlogic.gdx.math.Vector2;

public interface EnemyDefinition {
	/**
	 * Constructs a enemy instance according to this definition.
	 * @param physics
	 * @param entities
	 * @param weaponEnvironment
	 * @param position
	 * @return enemy
	 */
	public Enemy createEnemy(PhysicsEnvironment physics, EntityEnvironment entities,
			WeaponEnvironment weaponEnvironment, Vector2 position);
}
