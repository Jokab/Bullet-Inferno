package se.dat255.bulletinferno.model.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import se.dat255.bulletinferno.model.PhysicsBody;
import se.dat255.bulletinferno.model.PhysicsMovementPattern;

public class SineMovementPattern implements PhysicsMovementPattern {
	private float time = 0;
	private final float frequency;
	private final float maxForce;
	private static int i = 0;

	public SineMovementPattern(float frequency, float maxForce) {
		this.frequency = frequency;
		this.maxForce = maxForce;
	}

	@Override
	public void update(float timeDelta, PhysicsBody body) {
		Body box2dBody = body.getBox2DBody();
		time += timeDelta;
		// System.out.println(this);
		box2dBody.applyForceToCenter(new Vector2(0,
				(float) (-maxForce * Math.cos(frequency * time)))
				, true);
	}

	@Override
	public PhysicsMovementPattern copy() {

		return new SineMovementPattern(frequency, maxForce);

	}

}
