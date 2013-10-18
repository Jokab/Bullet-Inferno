package se.dat255.bulletinferno.model.physics;

import com.badlogic.gdx.math.Vector2;

public class DisorderedMovementPattern implements PhysicsMovementPattern {
	private float time = 0;
	private final float frequency;
	private final float amplitude;
	private boolean forceSwitch = true;

	public DisorderedMovementPattern(float frequency, float amplitude) {
		this.frequency = frequency;
		this.amplitude = amplitude;
	}

	@Override
	public void update(float timeDelta, PhysicsBody body) {
		time += timeDelta;
		if (time > frequency) {
			time = -time;
			forceSwitch = !forceSwitch;
		}

		if (forceSwitch) {
			body.setVelocity(new Vector2(body.getVelocity().x, -amplitude));
		} else {
			body.setVelocity(new Vector2(body.getVelocity().x, amplitude));
		}
	}

	@Override
	public PhysicsMovementPattern copy() {
		return new DisorderedMovementPattern(frequency, amplitude);
	}
}
