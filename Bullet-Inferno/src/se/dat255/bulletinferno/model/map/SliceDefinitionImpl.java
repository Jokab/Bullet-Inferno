package se.dat255.bulletinferno.model.map;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import se.dat255.bulletinferno.model.Game;

import com.badlogic.gdx.math.Vector2;

/**
 * Configurations of simple SliceDefinitions.
 * 
 * @see SliceDefinition
 */
public enum SliceDefinitionImpl implements SliceDefinition {
	WATER(0, 0, 20f, Collections.<ObstaclePlacement>emptyList()),
	MOUNTAIN_1(0, 1, 20f, Collections.<ObstaclePlacement>emptyList()),
	MOUNTAIN_2(2, 3, 20f, Collections.<ObstaclePlacement>emptyList()),
	MOUNTAIN_3(2, 2, 20f, Collections.<ObstaclePlacement>emptyList()),
	MOUNTAIN_4(1, 3, 20f, Collections.<ObstaclePlacement>emptyList()),
	MOUNTAIN_5(1, 1, 20f, Collections.<ObstaclePlacement>emptyList()),
	MOUNTAIN_6(3, 2, 20f, Collections.<ObstaclePlacement>emptyList()),
	MOUNTAIN_7(3, 2, 20f, Collections.<ObstaclePlacement>emptyList()),
	MOUNTAIN_8(2, 0, 20f, Collections.<ObstaclePlacement>emptyList()),
	SIMPLE_GROUND(2f, 2f, 20f, Arrays.asList(place(ObstacleDefinitionImpl.FLAT_GROUND, 0, 0)))
	;
	
	/** The entry height of the slice */
	private final float entryHeight;
	
	/** The exit height of the slice */
	private final float exitHeight;
	
	/** The width of the slice */
	private final float width;
	
	/** A list of ObstaclePlacement, used to place Obstacles in the Slice */
	private final List<? extends ObstaclePlacement> obstaclePlacements;
	
	SliceDefinitionImpl(float entryHeight, float exitHeight, float width, 
			List<? extends ObstaclePlacement> obstaclePlacements){
		this.entryHeight = entryHeight;
		this.exitHeight = exitHeight;
		this.width = width;
		this.obstaclePlacements = obstaclePlacements;
	}
	
	/**
	 * Internal helper to create an {@link ObstaclePlacement} quickly for enum definitions.
	 */
	private static ObstaclePlacement place(ObstacleDefinition definition, float x, float y) {
		return new ObstaclePlacementImpl(definition, new Vector2(x, y));
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
	public Slice createSlice(Game game, Vector2 position) {
		return new SliceImpl(game, this, entryHeight, exitHeight, position, width,
				obstaclePlacements);
	}
	
}