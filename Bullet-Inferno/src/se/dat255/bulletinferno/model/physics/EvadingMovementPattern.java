package se.dat255.bulletinferno.model.physics;

import com.badlogic.gdx.math.Vector2;

/**
 * A special movement pattern for bosses and other sighted entities.
 * The user of this pattern will be evading the given entity
 */
public class EvadingMovementPattern implements PhysicsMovementPattern {

	private final PositionEntity avoidedEntity;

	/** The maximum distance the movement pattern will act on. */
	private final float evadeLimitY;

	/** The velocity the body will be given in either direction (Y, -Y) to fulfill its purpose. */
	private static final float EVADE_VELOCITY = 2f;

	/** The velocity the body will have when fleeing. */
	private static final float FLEE_VELOCITY = 4f;

	/**
	 * The strategy to follow.
	 */
	private enum Strategy {
		/** Normal evading up to the limit. */
		EVADE,

		/** Flee to the upper corner and stop there. */
		FLEE_UP,

		/** Flee to the lower corner and stop there. */
		FLEE_DOWN
	}

	/** Strategy currently being followed. */
	private Strategy strategy = Strategy.EVADE;

	/** The lowest Y position (world coordinates) allowed to move to when evading. */
	private final float evadeYMin;

	/** The highest Y position (world coordinates) allowed to move to when evading. */
	private final float evadeYMax;

	/**
	 * Constructs a new evading movement pattern, where the user of this
	 * movement pattern will try to evade the specified entity.
	 * 
	 * @param avoidedEntity
	 *        the entity to evade, up to the given limit.
	 * @param evadeLimitY
	 *        the limiting distance for which the body will be evading. When moved out of this
	 *        distance, the movement pattern will be disabled until it get inside again.
	 * @param evadeYMin
	 *        the lowest y world position to goto, otherwise turn another way.
	 * @param evadeYMax
	 *        the highest y world position to goto, otherwise turn another way.
	 */
	public EvadingMovementPattern(PositionEntity avoidedEntity, float evadeLimitY, float evadeYMin,
			float evadeYMax) {
		this.avoidedEntity = avoidedEntity;
		this.evadeLimitY = evadeLimitY;
		this.evadeYMin = evadeYMin;
		this.evadeYMax = evadeYMax;
	}

	@Override
	public void update(float timeDelta, PhysicsBody body) {
		float bodyY = body.getPosition().y;
		float avoidY = avoidedEntity.getPosition().y;
		boolean withinLimit = Math.abs(bodyY - avoidY) < evadeLimitY;

		float velocityY = 0f;

		if (strategy == Strategy.FLEE_UP) {
			if (withinLimit && bodyY < evadeYMax) {
				velocityY = FLEE_VELOCITY;
			} else {
				strategy = Strategy.EVADE;
			}
		} else if (strategy == Strategy.FLEE_DOWN) {
			if (withinLimit && bodyY > evadeYMin) {
				velocityY = -FLEE_VELOCITY;
			} else {
				strategy = Strategy.EVADE;
			}
		} else if (strategy == Strategy.EVADE) {
			if (withinLimit) {
				if (bodyY > evadeYMax) {
					strategy = Strategy.FLEE_DOWN;
				} else if (bodyY < evadeYMin) {
					strategy = Strategy.FLEE_UP;
				} else {
					velocityY = bodyY > avoidY ? EVADE_VELOCITY : -EVADE_VELOCITY;
				}
			}
		}

		body.setVelocity(new Vector2(body.getVelocity().x, velocityY));
	}

	@Override
	public PhysicsMovementPattern copy() {
		return new EvadingMovementPattern(avoidedEntity, evadeLimitY, evadeYMin, evadeYMax);
	}

}