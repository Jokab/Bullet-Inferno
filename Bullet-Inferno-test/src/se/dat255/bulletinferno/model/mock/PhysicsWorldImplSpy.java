package se.dat255.bulletinferno.model.mock;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

import se.dat255.bulletinferno.model.Collidable;
import se.dat255.bulletinferno.model.PhysicsBody;
import se.dat255.bulletinferno.model.PhysicsBodyDefinition;
import se.dat255.bulletinferno.model.PhysicsWorldImpl;

public class PhysicsWorldImplSpy extends PhysicsWorldImpl {

	public List<CreateBodyCall> createBodyCalls = new ArrayList<CreateBodyCall>();
	public List<RemoveBodyCall> removeBodyCalls = new ArrayList<RemoveBodyCall>();
	
	public class CreateBodyCall {
		public CreateBodyCall(PhysicsBodyDefinition definition,
				Collidable collidable, Vector2 position, PhysicsBody returnValue) {
			this.definition = definition;
			this.collidable = collidable;
			this.position = position;
			
			this.returnValue = returnValue;
		}
		
		public PhysicsBodyDefinition definition;
		public Collidable collidable;
		public Vector2 position;
		
		public PhysicsBody returnValue;
	}
	
	public class RemoveBodyCall {
		public RemoveBodyCall(PhysicsBody body) {
			this.body = body;
		}
		
		public PhysicsBody body;
	}

	@Override
	public PhysicsBody createBody(PhysicsBodyDefinition definition,
			Collidable collidable, Vector2 position) {
		PhysicsBody returnValue = super.createBody(definition, collidable, position);
		createBodyCalls.add(new CreateBodyCall(definition, collidable, position, returnValue));
		return returnValue;
	}

	@Override
	public void removeBody(PhysicsBody body) {
		removeBodyCalls.add(new RemoveBodyCall(body));
		super.removeBody(body);
	}

	/*@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
	}*/

	/*@Override
	public PhysicsShapeFactory getShapeFactory() {
		return super.getShapeFactory();
	}*/

}