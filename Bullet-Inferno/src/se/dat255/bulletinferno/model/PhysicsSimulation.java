package se.dat255.bulletinferno.model;

import java.util.List;

public interface PhysicsSimulation {
	public void update(float delta);

	public void setCollidables(List<? extends Collidable> collidables);

	public void setVelocityEntities(
			List<? extends VelocityEntity> velocityEntities);

	public void setAccelerationEntities(
			List<? extends AccelerationEntity> accelerationEntities);
}
