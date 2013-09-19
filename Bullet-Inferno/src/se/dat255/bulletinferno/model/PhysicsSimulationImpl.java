package se.dat255.bulletinferno.model;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Standard implementation of a physics simulation context and executor.
 */
public class PhysicsSimulationImpl implements PhysicsSimulation {
	
    private World world;
    
	public PhysicsSimulationImpl() {
	    
	}
	
	/** A list of elements to run colision detection on. */
	private List<? extends Collidable> collidables = new ArrayList<Collidable>(0);
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(float deltaTime) {
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCollidables(List<? extends Collidable> collidables) {
		this.collidables = collidables;
	}

}