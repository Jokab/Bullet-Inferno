package se.dat255.bulletinferno.model.map;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinitionImpl;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape;

public enum SliceType {
	WATER(0, 0, 0, new PhysicsBodyPositionDefinition[] {});
	
	private final float entryHeight;
	private final float exitHeight;
	private final float width;
	private final PhysicsBodyPositionDefinition[] bodyDefinitions;
	
	private class PhysicsBodyPositionDefinition extends PhysicsBodyDefinitionImpl {
		public PhysicsBodyPositionDefinition(Shape shape, Vector2 position) {
			super(shape);
			getBox2DBodyDefinition().position.set(position);
		}
	}
	
	SliceType(float entryHeight, float exitHeight, float width, 
			PhysicsBodyPositionDefinition[] bodyDefinitions){
		this.entryHeight = entryHeight;
		this.exitHeight = exitHeight;
		this.width = width;
		this.bodyDefinitions = bodyDefinitions;
	}
	
	public Slice getSlice(Game game, Vector2 position) {
		return new SliceImpl(game, this, entryHeight, exitHeight, position, width, bodyDefinitions);
	}
	
	
	
}
