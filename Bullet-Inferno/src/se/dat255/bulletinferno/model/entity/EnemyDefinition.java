package se.dat255.bulletinferno.model.entity;

import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;
import se.dat255.bulletinferno.model.weapon.WeaponEnvironment;
import se.dat255.bulletinferno.util.Listener;

import com.badlogic.gdx.math.Vector2;

public interface EnemyDefinition {
	/**
	 * Constructs a enemy instance according to this definition.
	 * 
	 * @param physicsEnvironment
	 * @param entityEnvironment
	 * @param weaponEnvironment
	 * @param position
	 * @return enemy
	 */
	public Enemy createEnemy(PhysicsEnvironment physicsEnvironment,
			EntityEnvironment entityEnvironment,
			WeaponEnvironment weaponEnvironment, Vector2 position, Listener<Integer> scoreListener);
}
