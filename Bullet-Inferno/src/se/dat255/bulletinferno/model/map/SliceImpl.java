package se.dat255.bulletinferno.model.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

import se.dat255.bulletinferno.model.Collidable;
import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.Obstacle;
import se.dat255.bulletinferno.model.PhysicsBody;
import se.dat255.bulletinferno.model.PhysicsBodyDefinition;
import se.dat255.bulletinferno.model.PhysicsWorld;

public class SliceImpl implements Slice, Collidable {
	private final float entryHeight;
	private final float exitHeight;
	private final Game game;
	private final SliceType id;
	private final float width;
	
	/** The obstacles present in this slice. */
	private final List<? extends Obstacle> obstacles;
	
	public SliceImpl(Game game, SliceType id, float entryHeight, float exitHeight,
			Vector2 position, float width, List<? extends ObstacleDefinition> obstacleDefinitions) {
		this.entryHeight = entryHeight;
		this.exitHeight = exitHeight;
		this.game = game;
		this.id = id;
		this.width = width;

		// Create obstacles from definitions.
		List<Obstacle> obstacles = new ArrayList<Obstacle>(obstacleDefinitions.size());
		for (ObstacleDefinition obstacleDefinition : obstacleDefinitions) {
			Vector2 obstaclePosition = position.cpy(); // @todo Add obstacle-in-slice position here.
			obstacles.add(obstacleDefinition.createObstacle(game, obstaclePosition));
		}
		this.obstacles = obstacles;
	}

	public SliceImpl(Game game, SliceType id, float entryHeight, float exitHeight,
			Vector2 position, float width) {
		this(game, id, entryHeight, exitHeight, position, width,
				Collections.<ObstacleDefinition> emptyList());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
		for(Obstacle obstacle : obstacles) {
			obstacle.dispose();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getEntryHeight() {
		return entryHeight;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getExitHeight() {
		return exitHeight;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<? extends Obstacle> getObstacles() {
		return obstacles;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void preCollided(Collidable other) {
		// NOP
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void postCollided(Collidable other) {
		// NOP
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getIdentifier() {
		return id.name();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getWidth() {
		return width;
	}
	
}
