package se.dat255.bulletinferno.model.map;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

import se.dat255.bulletinferno.model.Collidable;
import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.PhysicsBody;
import se.dat255.bulletinferno.model.PhysicsBodyDefinition;
import se.dat255.bulletinferno.model.PhysicsWorld;

public class SliceImpl implements Slice, Collidable {
	private final float entryHeight;
	private final float exitHeight;
	private final Game game;
	private final SliceType id;
	private final float width;
	private final List<PhysicsBody> bodies = new LinkedList<PhysicsBody>();
	
	public SliceImpl(Game game, SliceType id, float entryHeight, float exitHeight, 
			Vector2 position, float width, PhysicsBodyDefinition[] bodyDefinitions) {
		this(game, id, entryHeight, exitHeight, position, width);
		
		// Define bodies
		for(PhysicsBodyDefinition def : bodyDefinitions) {
			// Create a new body from the definition,
			// add the slice's world coordinates to the definitions
			// position relative to the slice, to get world coordinate
			game.getPhysicsWorld().createBody(def, this, 
					def.getBox2DBodyDefinition().position.add(position));
		}
		
	}
	
	public SliceImpl(Game game, SliceType id, float entryHeight, float exitHeight, Vector2 position,
			float width) {
		this.entryHeight = entryHeight;
		this.exitHeight = exitHeight;
		this.game = game;
		this.id = id;
		this.width = width;
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
