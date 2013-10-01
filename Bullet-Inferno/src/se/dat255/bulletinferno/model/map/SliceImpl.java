package se.dat255.bulletinferno.model.map;

import java.util.LinkedList;
import java.util.List;

import se.dat255.bulletinferno.model.Collidable;
import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.PhysicsBody;
import se.dat255.bulletinferno.model.PhysicsBodyDefinition;
import se.dat255.bulletinferno.model.PhysicsWorld;

public class SliceImpl implements Slice, Collidable {
	private final int entryHeight;
	private final int exitHeight;
	private final Game game;
	private final List<PhysicsBody> bodies = new LinkedList<PhysicsBody>();
	
	public SliceImpl(Game game, int entryHeight, int exitHeight, int x, 
			List<PhysicsBodyDefinition> bodyDefinitions) {
		this.entryHeight = entryHeight;
		this.exitHeight = exitHeight;
		this.game = game;
		
		// Define bodies
		for(PhysicsBodyDefinition def : bodyDefinitions) {
			// Create a new body from the definition,
			// add the slice's world coordinates to the definitions
			// position relative to the slice, to get world coordinate
			game.getPhysicsWorld().createBody(def, this, 
					def.getBox2DBodyDefinition().position.add(x, 0));
		}
		
	}
	
	public SliceImpl(Game game, int entryHeight, int exitHeight, int x) {
		this.entryHeight = entryHeight;
		this.exitHeight = exitHeight;
		this.game = game;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
		PhysicsWorld world = game.getPhysicsWorld();
		// Remove all bodies from world, if there is any
		if(bodies != null) {
			for(PhysicsBody body : bodies) {
				world.removeBody(body);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getEntryHeight() {
		return entryHeight;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getExitHeight() {
		return exitHeight;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<PhysicsBody> getPhysicsBodyies() {
		return bodies;
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
	
}
