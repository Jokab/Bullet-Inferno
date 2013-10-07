package se.dat255.bulletinferno.model.map;

import java.util.List;

import se.dat255.bulletinferno.model.entity.EntityEnvironment;
import se.dat255.bulletinferno.model.entity.EntityEnvironmentImpl;
import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;
import se.dat255.bulletinferno.model.weapon.WeaponData;

import com.badlogic.gdx.math.Vector2;

public class MapEnvironmentImpl implements MapEnvironment {
	private final SegmentManager segmentManager;
	
	/** The PhysicsEnvironment instance injected at construction. */
	private final PhysicsEnvironment physics;
	
	/** The EntityEnvironment instance injected at construction. */
	private final EntityEnvironment entities;
	
	public MapEnvironmentImpl(PhysicsEnvironment physics, WeaponData weaponType) {
		this.physics = physics;
		this.entities = new EntityEnvironmentImpl(physics, weaponType);
		segmentManager = new SegmentManagerImpl(physics, entities);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<? extends Segment> getSegments() {
		return segmentManager.getSegments();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getRemovedSegmentCount() {
		return segmentManager.getRemovedSegmentCount();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setViewport(Vector2 viewportPosition, Vector2 viewportDimensions) {
		segmentManager.setViewport(viewportPosition, viewportDimensions);
	}

	@Override
	public EntityEnvironment getEntityEnvironment() {
		return entities;
	}

}