package se.dat255.bulletinferno.model.physics;

import com.badlogic.gdx.math.Vector2;

/**
 * A special disordered movement pattern for bosses, making sure the boss
 * moves around the center of the screen as opposed to the position when the movement is
 * started. Makes sure the boss stays on screen.
 * 
 * @author Simon Österberg
 * 
 */
public class DisorderedBossMovementPattern implements PhysicsMovementPattern {
	private final float frequency;
	private final float amplitude;

	public DisorderedBossMovementPattern(float frequency, float amplitude) {
		this.frequency = frequency;
		this.amplitude = amplitude;
	}

	@Override
	public void update(float timeDelta, PhysicsBody body) {
		float GAME_HEIGHT_DIV = 4.5f;
		if (body.getPosition().y > GAME_HEIGHT_DIV + amplitude / 2) {
			body.setVelocity(new Vector2(body.getVelocity().x, -frequency));
		} else if (body.getPosition().y < GAME_HEIGHT_DIV - amplitude / 2) {
			body.setVelocity(new Vector2(body.getVelocity().x, frequency));
		} else if (body.getVelocity().y == 0) {
			body.setVelocity(new Vector2(body.getVelocity().x, frequency));
		}
	}

	@Override
	public PhysicsMovementPattern copy() {
		return new DisorderedBossMovementPattern(frequency, amplitude);
	}
}
