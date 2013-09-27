package se.dat255.bulletinferno.model.physics;

import org.omg.CORBA.FREE_MEM;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import se.dat255.bulletinferno.model.PhysicsBody;
import se.dat255.bulletinferno.model.PhysicsMovementPattern;

public class SineMovementPattern implements PhysicsMovementPattern {
	private float time = 0; 
	private final float frequency;
	private final float maxForce;
	
	public SineMovementPattern(float frequency, float maxForce) {
		this.frequency = frequency;
		this.maxForce = maxForce;
	}
	
	@Override
	public void update(float timeDelta, PhysicsBody body) {
		Body box2dBody = body.getBox2DBody();
		time += timeDelta;
		
		box2dBody.applyForceToCenter(new Vector2(0, (float) (-maxForce*Math.cos(frequency*time)))
					, false);
	}

}
