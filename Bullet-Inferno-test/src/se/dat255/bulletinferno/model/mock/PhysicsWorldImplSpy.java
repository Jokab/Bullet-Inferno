package se.dat255.bulletinferno.model.mock;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

import se.dat255.bulletinferno.model.physics.Collidable;
import se.dat255.bulletinferno.model.physics.PhysicsBody;
import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinition;
import se.dat255.bulletinferno.model.physics.PhysicsEnvironmentImpl;
import se.dat255.bulletinferno.util.Timer;

public class PhysicsWorldImplSpy extends PhysicsEnvironmentImpl {

	public List<CreateBodyCall> createBodyCalls = new ArrayList<CreateBodyCall>();
	public List<RemoveBodyCall> removeBodyCalls = new ArrayList<RemoveBodyCall>();
	
	public Timer timer;
	
	public PhysicsWorldImplSpy() {
		
	}
	
	public PhysicsWorldImplSpy(Timer timer) {
		this.timer = timer;
	}
	
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

	@Override
	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
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