package se.dat255.bulletinferno.model;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

/**
 * Standard implementation of a physics simulation context and executor.
 */
public class PhysicsSimulationImpl implements PhysicsSimulation {

	/** A list of elements to run colision detection on. */
	private List<? extends Collidable> collidables = new ArrayList<Collidable>(0);
	
	/** The VelocityEntity objects that should be simulated. */
	private List<? extends VelocityEntity> velocityEntities = new ArrayList<VelocityEntity>(0);
	
	/** The AccelerationEntity objects that should be simulated. */
	private List<? extends AccelerationEntity> accelerationEntities =
			new ArrayList<AccelerationEntity>(0);
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(float deltaTime) {
		// Let accelerations affect velocities.
		for(AccelerationEntity entity : accelerationEntities) {
			Vector2 acceleration = entity.getAcceleration();
			Vector2 velocity = entity.getVelocity();
			velocity.add(acceleration.x * deltaTime, acceleration.y * deltaTime);
			entity.setVelocity(velocity);// (This is also set by .add() above.)
		}
		
		// Let velocities affect positions.
		for(VelocityEntity entity : velocityEntities) {
			Vector2 velocity = entity.getVelocity();
			Vector2 position = entity.getPosition();
			position.add(velocity.x * deltaTime, velocity.y * deltaTime);
			entity.setPosition(position);// (This is also set by .add() above.)
		}
		
		// Find collisions and report them.
		for(Collidable collidable : collidables) {
			if(collidable != null); // ...
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCollidables(List<? extends Collidable> collidables) {
		this.collidables = collidables;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setVelocityEntities(
			List<? extends VelocityEntity> velocityEntities) {
		this.velocityEntities = velocityEntities;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setAccelerationEntities(
			List<? extends AccelerationEntity> accelerationEntities) {
		this.accelerationEntities = accelerationEntities;
	}

}