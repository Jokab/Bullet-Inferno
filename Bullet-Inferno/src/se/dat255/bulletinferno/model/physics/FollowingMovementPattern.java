package se.dat255.bulletinferno.model.physics;

import com.badlogic.gdx.math.Vector2;

/**
 * A special movement pattern for bosses and other sighted entities.
 * The user of this pattern will be following the given entity
 * 
 */
public class FollowingMovementPattern implements PhysicsMovementPattern {

	private final PositionEntity entity;

	/**
	 * Constructs a new following movement pattern, where the user of this
	 * movement pattern will follow the specified entity.
	 * 
	 * @param entity
	 *        to evade
	 */
	public FollowingMovementPattern(PositionEntity entity) {
		this.entity = entity;
	}

	@Override
	public void update(float timeDelta, PhysicsBody body) {
		if (entity.getPosition().y > body.getPosition().y + 0.1f) {
			body.setVelocity(new Vector2(body.getVelocity().x, 2));
		} else if (entity.getPosition().y < body.getPosition().y - 0.1f) {
			body.setVelocity(new Vector2(body.getVelocity().x, -2));
		} else {
			body.setVelocity(new Vector2(body.getVelocity().x, 0));
		}
	}

	@Override
	public PhysicsMovementPattern copy() {
		return new FollowingMovementPattern(entity);
	}
}
