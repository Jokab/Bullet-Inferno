package se.dat255.bulletinferno.model.map;

import java.util.List;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.PhysicsBody;
import se.dat255.bulletinferno.model.PhysicsWorld;

public class SliceImpl implements Slice {
	private final int entryHeight;
	private final int exitHeight;
	private final Game game;
	private final List<PhysicsBody> bodies;
	
	public SliceImpl(Game game, int entryHeight, int exitHeight, List<PhysicsBody> bodies) {
		this.entryHeight = entryHeight;
		this.exitHeight = exitHeight;
		this.bodies = bodies;
		this.game = game;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
		PhysicsWorld world = game.getPhysicsWorld();
		// Remove all bodies from world
		for(PhysicsBody body : bodies) {
			world.removeBody(body);
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
	
}
