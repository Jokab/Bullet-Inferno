package se.dat255.bulletinferno.model.map;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import se.dat255.bulletinferno.model.entity.EnemyDefinitionImpl;
import se.dat255.bulletinferno.model.entity.EntityEnvironment;
import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;
import se.dat255.bulletinferno.model.weapon.WeaponEnvironment;

import com.badlogic.gdx.math.Vector2;

/**
 * Configurations of simple SliceDefinitions.
 * 
 * @see SliceDefinition
 */
public enum SliceDefinitionImpl implements SliceDefinition {
	WATER(0, 0, 20f, Collections.<ObstaclePlacement>emptyList()),
	
	MOUNTAIN_1(0, 1.75f, 16f, Arrays.asList(
			placeObstacle(ObstacleDefinitionImpl.MOUNTAIN_1_GROUND, 0, 0),
			placeObstacle(ObstacleDefinitionImpl.DEFAULT_TREE, 11.37f, 1.7f)), 
		Arrays.asList(
			placeEnemy(EnemyDefinitionImpl.DEFAULT_ENEMY_SHIP, 12, 8.5f))
		),
	
	MOUNTAIN_2(1.75f, 3.8f, 16f, Arrays.asList(
			placeObstacle(ObstacleDefinitionImpl.MOUNTAIN_2_GROUND, 0, 0))
		),
	MOUNTAIN_3(1.75f, 3.8f, 16f, Arrays.asList(placeObstacle(ObstacleDefinitionImpl.MOUNTAIN_3_GROUND, 0, 0)),
			Arrays.asList(
				placeEnemy(EnemyDefinitionImpl.DEFAULT_ENEMY_SHIP, 6.15f, 4.3f),
				placeEnemy(EnemyDefinitionImpl.DEFAULT_ENEMY_SHIP, 6.73f, 4.07f))
		),
	
	MOUNTAIN_4(3.8f, 1.75f, 16f, Arrays.asList(
				placeObstacle(ObstacleDefinitionImpl.MOUNTAIN_4_GROUND, 0, 0),
				placeObstacle(ObstacleDefinitionImpl.FLOATING_ROCK, 10f, 2.85f),
				placeObstacle(ObstacleDefinitionImpl.SMALL_TREE, 2.38f, 4.81f)),
			Arrays.asList(
				placeEnemy(EnemyDefinitionImpl.DEFAULT_ENEMY_SHIP, 8.25f, 8.5f),
				placeEnemy(EnemyDefinitionImpl.DEFAULT_ENEMY_SHIP, 11.35f, 7.73f))
		),
	MOUNTAIN_5(1.75f, 1.75f, 16f, Arrays.asList(
				placeObstacle(ObstacleDefinitionImpl.FLAT_GROUND, 0, 1.75f)),
			Arrays.asList(	
				placeEnemy(EnemyDefinitionImpl.DEFAULT_ENEMY_SHIP, 4f, 8f),
				placeEnemy(EnemyDefinitionImpl.DEFAULT_ENEMY_SHIP, 4f, 6f),
				placeEnemy(EnemyDefinitionImpl.DEFAULT_ENEMY_SHIP, 4f, 4f),
				placeEnemy(EnemyDefinitionImpl.DEFAULT_ENEMY_SHIP, 14f, 6f),
				placeEnemy(EnemyDefinitionImpl.DEFAULT_ENEMY_SHIP, 14f, 4f))
		),
	
	MOUNTAIN_6(1.75f, 1.75f, 16f, Arrays.asList(
			placeObstacle(ObstacleDefinitionImpl.MOUNTAIN_6_GROUND, 0, 0)),
			Arrays.asList(
					placeEnemy(EnemyDefinitionImpl.DEFAULT_ENEMY_SHIP, 8f, 2.37f),
					placeEnemy(EnemyDefinitionImpl.DEFAULT_ENEMY_SHIP, 5.72f, 3.92f),
					placeEnemy(EnemyDefinitionImpl.DEFAULT_ENEMY_SHIP, 10.58f, 7.78f))
		),
	
	MOUNTAIN_7(3.8f, 1.75f, 16f, Arrays.asList(
			placeObstacle(ObstacleDefinitionImpl.MOUNTAIN_7_GROUND, 0, 0)),
			Arrays.asList(placeEnemy(EnemyDefinitionImpl.DEFAULT_ENEMY_SHIP, 10, 3))
		),
	
	MOUNTAIN_8(1.75f, 0, 16f, Arrays.asList(
			placeObstacle(ObstacleDefinitionImpl.MOUNTAIN_8_GROUND, 0, 0),
			placeObstacle(ObstacleDefinitionImpl.DEFAULT_TREE, 4.63f, 1.7f)),
			Arrays.asList(
				placeEnemy(EnemyDefinitionImpl.HARD_BOSS_SHIP, 8.35f, 5.55f))
		),
	SIMPLE_GROUND(2f, 2f, 16f, Collections.<ObstaclePlacement>emptyList())
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
	private static EnemyPlacement placeEnemy(EnemyDefinitionImpl enemyType, float x, float y) {
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
	public Slice createSlice(PhysicsEnvironment physics, EntityEnvironment entities,
			WeaponEnvironment weapons, Vector2 position, ScoreController scoreController) {
		return new SliceImpl(physics, entities, weapons, this, entryHeight, exitHeight, position,
				width, obstaclePlacements, enemyPlacements, scoreController);
	}
}