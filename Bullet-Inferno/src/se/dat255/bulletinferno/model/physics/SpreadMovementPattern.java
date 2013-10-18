package se.dat255.bulletinferno.model.physics;

import com.badlogic.gdx.math.Vector2;

public class SpreadMovementPattern implements PhysicsMovementPattern {

	private final float maxSpread;
	private boolean hasDisordered = false;

	public SpreadMovementPattern(float maxSpread) {
		this.maxSpread = maxSpread;
	}

	@Override
	public void update(float timeDelta, PhysicsBody body) {

		if (!hasDisordered) {

			body.getBox2DBody().applyLinearImpulse(
					new Vector2(0, (float) (Math.random() - Math.random()) * maxSpread),
					body.getDimensions(), true);

			hasDisordered = true;
		}

	}

	@Override
	public PhysicsMovementPattern copy() {

		return new SpreadMovementPattern(maxSpread);

	}

}
