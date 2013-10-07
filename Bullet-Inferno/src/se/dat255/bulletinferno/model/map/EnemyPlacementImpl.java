package se.dat255.bulletinferno.model.map;


import se.dat255.bulletinferno.model.entity.EnemyType;

import com.badlogic.gdx.math.Vector2;

public class EnemyPlacementImpl implements EnemyPlacement {
	private final EnemyType content;
	private final Vector2 position;
	
	/**
	 * Constructs a new enemy placement entry
	 * @param enemyType 
	 * @param x position
	 * @param y position
	 */
	public EnemyPlacementImpl(EnemyType enemyType, float x, float y) {
		this.content = enemyType;
		position = new Vector2(x,y);
	}
	
	@Override
	public Vector2 getPosition() {
		return position;
	}

	@Override
	public EnemyType getContent() {
		return content;
	}

}
