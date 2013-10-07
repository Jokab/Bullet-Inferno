package se.dat255.bulletinferno.model.map;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.enemy.EnemyType;

import com.badlogic.gdx.math.Vector2;

/**
 * Configurations of simple SliceDefinitions.
 * 
 * @see SliceDefinition
 */
public enum SliceDefinitionImpl implements SliceDefinition {
	WATER(0, 0, 20f, Collections.<ObstaclePlacement>emptyList()),
	MOUNTAIN_1(0, 1, 20f, Collections.<ObstaclePlacement>emptyList(), 
			Arrays.asList(placeEnemy(EnemyType.DEFAULT_ENEMY_SHIP, 10, 3),
			placeEnemy(EnemyType.DEFAULT_ENEMY_SHIP, 10, 5),
			placeEnemy(EnemyType.BOSS_ENEMY_SHIP, 10, 5))),
	MOUNTAIN_2(2, 3, 20f, Collections.<ObstaclePlacement>emptyList(), 
			Arrays.asList(placeEnemy(EnemyType.DEFAULT_ENEMY_SHIP, 10, 3),
					placeEnemy(EnemyType.BOSS_ENEMY_SHIP, 10, 5))),
	MOUNTAIN_3(2, 2, 20f, Collections.<ObstaclePlacement>emptyList(),
			Arrays.asList(placeEnemy(EnemyType.DEFAULT_ENEMY_SHIP, 10, 3),
					placeEnemy(EnemyType.BOSS_ENEMY_SHIP, 10, 5))),
	MOUNTAIN_4(1, 3, 20f, Collections.<ObstaclePlacement>emptyList(),
			Arrays.asList(placeEnemy(EnemyType.DEFAULT_ENEMY_SHIP, 10, 3),
					placeEnemy(EnemyType.BOSS_ENEMY_SHIP, 10, 5))),
	MOUNTAIN_5(1, 1, 20f, Collections.<ObstaclePlacement>emptyList(),
			Arrays.asList(placeEnemy(EnemyType.DEFAULT_ENEMY_SHIP, 10, 3),
					placeEnemy(EnemyType.BOSS_ENEMY_SHIP, 10, 5))),
	MOUNTAIN_6(3, 2, 20f, Collections.<ObstaclePlacement>emptyList(),
			Arrays.asList(placeEnemy(EnemyType.DEFAULT_ENEMY_SHIP, 10, 3),
					placeEnemy(EnemyType.DEFAULT_ENEMY_SHIP, 10, 5))),
	MOUNTAIN_7(3, 2, 20f, Collections.<ObstaclePlacement>emptyList(),
			Arrays.asList(placeEnemy(EnemyType.DEFAULT_ENEMY_SHIP, 10, 3),
					placeEnemy(EnemyType.DEFAULT_ENEMY_SHIP, 10, 5))),
	MOUNTAIN_8(2, 0, 20f, Collections.<ObstaclePlacement>emptyList(),
			Arrays.asList(placeEnemy(EnemyType.DEFAULT_ENEMY_SHIP, 10, 3),
					placeEnemy(EnemyType.DEFAULT_ENEMY_SHIP, 10, 5))),
	SIMPLE_GROUND(2f, 2f, 20f, Collections.<ObstaclePlacement>emptyList())
	;
	
	/** The entry height of the slice */
	private final float entryHeight;
	
	/** The exit height of the slice */
	private final float exitHeight;
	
	/** The width of the slice */
	private final float width;
	
	/** A list of ObstaclePlacement, used to place Obstacles in the Slice */
	private final List<? extends ObstaclePlacement> obstaclePlacements;
	
	private final List<? extends EnemyPlacement> enemyPlacements;
	
	SliceDefinitionImpl(float entryHeight, float exitHeight, float width, 
			List<? extends ObstaclePlacement> obstaclePlacements){
		this(entryHeight, exitHeight, width, obstaclePlacements, 
				Collections.<EnemyPlacement>emptyList());
	}
	
	SliceDefinitionImpl(float entryHeight, float exitHeight, float width,
			List<? extends ObstaclePlacement> obstaclePlacements, 
			List<? extends EnemyPlacement> enemyPlacements) {
		this.entryHeight = entryHeight;
		this.exitHeight = exitHeight;
		this.width = width;
		this.obstaclePlacements = obstaclePlacements;
		this.enemyPlacements = enemyPlacements;
	}
	
	/**
	 * Internal helper to create an {@link ObstaclePlacement} quickly for enum definitions.
	 */
	private static ObstaclePlacement placeObstacle(ObstacleDefinition definition,
			float x, float y) {
		return new ObstaclePlacementImpl(definition, x, y);
	}
	
	/**
	 * Internal helper to create an {@link EnemyPlacement} quickly for enum definitions.
	 */
	private static EnemyPlacement placeEnemy(EnemyType enemyType, float x, float y) {
		return new EnemyPlacementImpl(enemyType, x, y); 
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
				obstaclePlacements, enemyPlacements);
	}
}