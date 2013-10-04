package se.dat255.bulletinferno.model.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import se.dat255.bulletinferno.model.PhysicsBody;
import se.dat255.bulletinferno.model.PhysicsMovementPattern;

public class DisorderedProjectileMovementPattern implements PhysicsMovementPattern {
	private float time = 0;
	private final float frequency;
	private final float amplitude;
	private boolean forceSwitch = true;
	private float xSpeed;

	public DisorderedProjectileMovementPattern(float frequency, float amplitude) {
		this.frequency = frequency;
		this.amplitude = amplitude;
		this.xSpeed=10;
	}

	@Override
	public void update(float timeDelta, PhysicsBody body) {
		Body box2dBody = body.getBox2DBody();
		time += timeDelta;

		if (time > frequency) {

			time = -time;

			forceSwitch = !forceSwitch;
		}

		if (forceSwitch) {

			body.setVelocity(new Vector2(xSpeed, -amplitude));

		} else {

			body.setVelocity(new Vector2(xSpeed, amplitude));

		}
	}

	@Override
	public PhysicsMovementPattern copy() {

		return new DisorderedMovementPattern(frequency, amplitude);

	}

}
