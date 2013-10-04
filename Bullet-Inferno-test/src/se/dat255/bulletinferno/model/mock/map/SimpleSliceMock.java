package se.dat255.bulletinferno.model.mock.map;

import java.util.ArrayList;
import java.util.List;

import se.dat255.bulletinferno.model.Obstacle;
import se.dat255.bulletinferno.model.map.Slice;

public class SimpleSliceMock implements Slice {
	public float entryHeight = 1;
	public float exitHeight = 1;
	public float width = 1;
	public String identifier = "Mock_Slice";
	public List<? extends Obstacle> obstacles = new ArrayList<Obstacle>();

	/**
	 * 
	 * @param entryHeight
	 * @param exitHeight
	 * @param width
	 */
	public SimpleSliceMock(float entryHeight, float exitHeight, float width) {
		this(entryHeight, exitHeight, width, "Mock_Slice", new ArrayList<Obstacle>());
	}

	/**
	 * 
	 * @param entryHeight
	 * @param exitHeight
	 * @param width
	 * @param identifier
	 * @param obstacles
	 */
	public SimpleSliceMock(float entryHeight, float exitHeight, float width, String identifier,
			List<? extends Obstacle> obstacles) {
		this.entryHeight = entryHeight;
		this.exitHeight = exitHeight;
		this.width = width;
		this.identifier = identifier;
		this.obstacles = obstacles;
	}

	@Override
	public void dispose() {
	}

	@Override
	public String getIdentifier() {
		return identifier;
	}

	@Override
	public float getEntryHeight() {
		return entryHeight;
	}

	@Override
	public float getExitHeight() {
		return exitHeight;
	}

	@Override
	public List<? extends Obstacle> getObstacles() {
		return obstacles;
	}

	@Override
	public float getWidth() {
		return width;
	}

}
