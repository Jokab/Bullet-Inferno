package se.dat255.bulletinferno.model.map;


import se.dat255.bulletinferno.model.entity.EnemyDefinition;

import com.badlogic.gdx.math.Vector2;

public class EnemyPlacementImpl implements EnemyPlacement {
	private final EnemyDefinition content;
	private final Vector2 position;
	
	/**
	 * Constructs a new enemy placement entry
	 * @param definition enemy 
	 * @param x position
	 * @param y position
	 */
	public EnemyPlacementImpl(EnemyDefinition definition, float x, float y) {
		this.content = definition;
		this.position = new Vector2(x,y);
	}
	
	@Override
	public Vector2 getPosition() {
		return position;
	}

	@Override
	public EnemyDefinition getContent() {
		return content;
	}

}
