package se.dat255.bulletinferno.model.map;

import java.util.List;

import se.dat255.bulletinferno.controller.ScoreController;
import se.dat255.bulletinferno.model.entity.EntityEnvironment;
import se.dat255.bulletinferno.model.entity.EntityEnvironmentImpl;
import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;
import se.dat255.bulletinferno.model.weapon.Projectile;
import se.dat255.bulletinferno.model.weapon.WeaponDefinition;
import se.dat255.bulletinferno.model.weapon.WeaponEnvironment;
import se.dat255.bulletinferno.model.weapon.WeaponEnvironmentImpl;
import se.dat255.bulletinferno.model.weapon.WeaponLoadout;
import se.dat255.bulletinferno.model.weapon.WeaponLoadoutImpl;

import com.badlogic.gdx.math.Vector2;

public class MapEnvironmentImpl implements MapEnvironment {
	private final SegmentManager segmentManager;
	
	/** The PhysicsEnvironment instance injected at construction. */
	private final PhysicsEnvironment physics;
	
	/** The EntityEnvironment instance injected at construction. */
	private final EntityEnvironment entities;
	
	/** The WeaponEnvironment instance injected at construction. */
	private final WeaponEnvironment weapons;
	
	public MapEnvironmentImpl(PhysicsEnvironment physics, WeaponDefinition[] weaponData, ScoreController scoreController) {
		this.physics = physics;
		this.weapons = new WeaponEnvironmentImpl(physics);
		
		// TODO: Replace null with heavy weapon and move upwards in call hierarchy somehow.
		WeaponLoadout weaponLoadout = new WeaponLoadoutImpl(
				weaponData[0].createWeapon(physics, weapons),
				weaponData[1].createWeapon(physics, weapons));
		
		this.entities = new EntityEnvironmentImpl(physics, weapons, weaponLoadout);
		
		segmentManager = new SegmentManagerImpl(physics, entities, weapons, scoreController);
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

	@Override
	public List<? extends Projectile> getProjectiles() {
		return weapons.getProjectiles();
	}

	@Override
	public WeaponEnvironment getWeaponEnvironment() {
		return weapons;
	}

}