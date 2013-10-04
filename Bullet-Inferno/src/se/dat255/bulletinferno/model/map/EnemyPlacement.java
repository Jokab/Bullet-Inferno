package se.dat255.bulletinferno.model.map;

import se.dat255.bulletinferno.model.enemy.EnemyType;

import com.badlogic.gdx.math.Vector2;


/**
 * A link entry between an {@link EnemyType} and a position for which it should be placed
 * in a {@link Slice}
 *
 */
public interface EnemyPlacement {
	/**
	 * Returns the Enemy type held by this entry
	 * @return enemy type
	 */
	public EnemyType getContent();
	
	/**
	 * Returns the position held by the entry
	 * @return position
	 */
	public Vector2 getPosition();
	
}
