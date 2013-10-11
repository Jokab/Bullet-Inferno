package se.dat255.bulletinferno.model.map;

import se.dat255.bulletinferno.model.entity.EnemyDefinition;
import se.dat255.bulletinferno.model.entity.EnemyDefinitionImpl;

import com.badlogic.gdx.math.Vector2;


/**
 * A link entry between an {@link EnemyDefinitionImpl} and a position for which it should be placed
 * in a {@link Slice}
 *
 */
public interface EnemyPlacement {
	/**
	 * Returns the Enemy definition held by this entry
	 * @return enemy type
	 */
	public EnemyDefinition getContent();
	
	/**
	 * Returns the position held by the entry
	 * @return position
	 */
	public Vector2 getPosition();
	
}
