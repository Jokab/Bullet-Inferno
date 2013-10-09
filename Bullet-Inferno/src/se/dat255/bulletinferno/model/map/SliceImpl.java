package se.dat255.bulletinferno.model.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import se.dat255.bulletinferno.model.entity.EnemyDefinitionImpl;
import se.dat255.bulletinferno.model.entity.EntityEnvironment;
import se.dat255.bulletinferno.model.physics.Collidable;
import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;
import se.dat255.bulletinferno.model.weapon.WeaponEnvironment;

import com.badlogic.gdx.math.Vector2;

public class SliceImpl implements Slice, Collidable {

	/** The entry height of this slice */
	private final float entryHeight;

	/** The exit height of this slice */
	private final float exitHeight;

	/** The width of this Slice */
	private final float width;

	/** The PhysicsEnvironment instance injected at construction. */
	private final PhysicsEnvironment physics;
	
	/** The EntityEnvironment instance injected at construction. */
	private final EntityEnvironment entities;
	
	/** The WeaponEnvironment instance injected at construction. */
	private final WeaponEnvironment weapons;

	/** The "type of" slice, used for providing an identifier to the ResourceIdentifier. */
	private final SliceDefinitionImpl id;

	/** The obstacles present in this slice. */
	private final List<? extends Obstacle> obstacles;

	/**
	 * Creates a new Slice in the Game instance provided.
	 * 
	 * @param physics
	 *        the {@link PhysicsEnvironment} to run against.
	 * @param entities
	 *        the {@link EntityEnvironment} to run against.
	 * @param id
	 *        The identifier to be used when identifying this Slice.
	 * @param entryHeight
	 *        The entry (i.e. the leftmost) height of the Slice.
	 * @param exitHeight
	 *        The exit (i.e. the rightmost) height of the Slice.
	 * @param position
	 *        The world-coordinates the Slice will be placed at in the physics world.
	 * @param width
	 *        The width of the Slice.
	 * @param obstaclePlacements
	 *        A list of mappings to where Obstacles should be placed in the Slice.
	 * @param enemyPlacement
	 * 		  A list of mappings to where enemies should be place in the Slice
	 */
	public SliceImpl(PhysicsEnvironment physics, EntityEnvironment entities,
			WeaponEnvironment weapons, SliceDefinitionImpl id, float entryHeight, float exitHeight,
			Vector2 position, float width, List<? extends ObstaclePlacement> obstaclePlacements,
			List<? extends EnemyPlacement> enemyPlacements) {
		this.entryHeight = entryHeight;
		this.exitHeight = exitHeight;
		this.physics = physics;
		this.entities = entities;
		this.weapons = weapons;
		this.id = id;
		this.width = width;

		// Create obstacles from the provided definitions.
		List<Obstacle> obstacles = new ArrayList<Obstacle>(obstaclePlacements.size());
		for (ObstaclePlacement obstaclePlacement : obstaclePlacements) {
			Vector2 obstaclePosition = obstaclePlacement.getPosition().cpy().add(position);

			// Calculate the relative position to this slice. createObstacle() wants world-coords.
			Obstacle obstacle = obstaclePlacement.getObstacleDefinition()
					.createObstacle(physics, obstaclePosition);
			obstacles.add(obstacle);
		}
		
		this.obstacles = obstacles;
		
		// Create enemies from the provided definitions
		for (EnemyPlacement enemyPlacement : enemyPlacements) {
			Vector2 enemyPosition = enemyPlacement.getPosition().cpy().add(position);
			entities.addEnemy(enemyPlacement.getContent().createEnemy(physics, entities, weapons, 
					enemyPosition));
		}
	}
	
	/**
	 * Creates a new Slice in the Game instance provided.
	 * 
	 * @param physics
	 *        the {@link PhysicsEnvironment} to run against.
	 * @param entities
	 *        the {@link EntityEnvironment} to run against.
	 * @param id
	 *        The identifier to be used when identifying this Slice.
	 * @param entryHeight
	 *        The entry (i.e. the leftmost) height of the Slice.
	 * @param exitHeight
	 *        The exit (i.e. the rightmost) height of the Slice.
	 * @param position
	 *        The world-coordinates the Slice will be placed at in the physics world.
	 * @param width
	 *        The width of the Slice.
	 * @param obstaclePlacements
	 *        A list of mappings to where Obstacles should be placed in the Slice.
	 */
	public SliceImpl(PhysicsEnvironment physics, EntityEnvironment entities,
			WeaponEnvironment weapons, SliceDefinitionImpl id, float entryHeight, float exitHeight,
			Vector2 position, float width, List<? extends ObstaclePlacement> obstaclePlacements) {
		this(physics, entities, weapons, id, entryHeight, exitHeight, position, width,
				obstaclePlacements, Collections.<EnemyPlacement>emptyList());
	}

	
	public SliceImpl(PhysicsEnvironment physics, EntityEnvironment entities,
			WeaponEnvironment weapons, SliceDefinitionImpl id, float entryHeight, float exitHeight,
			Vector2 position, float width) {
		this(physics, entities, weapons, id, entryHeight, exitHeight, position, width,
				Collections.<ObstaclePlacement> emptyList());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
		for (Obstacle obstacle : obstacles) {
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
