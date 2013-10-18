package se.dat255.bulletinferno.model.physics;

import com.badlogic.gdx.math.Vector2;


/**
 * A special movement pattern for bosses and other sighted entities. 
 * The user of this pattern will be evading the given entity
 */
public class EvadingMovementPattern implements PhysicsMovementPattern {
	
	private PositionEntity entity;
	
	/**
	 * Constructs a new evading movement pattern, where the user of this
	 * movement pattern will try to evade the specified entity.
	 * @param entity to evade
	 */
	public EvadingMovementPattern(PositionEntity entity) {
		this.entity = entity;
	}

	//TODO: Make more fluent and nice
	@Override
	public void update(float timeDelta, PhysicsBody body) {
		if( entity.getPosition().y < body.getPosition().y +0.1f && body.getPosition().y < 7.5f){
			body.setVelocity(new Vector2(body.getVelocity().x, 2));
		} else if( entity.getPosition().y > body.getPosition().y -0.1f && body.getPosition().y > 0.5f){
			body.setVelocity(new Vector2(body.getVelocity().x, -2));
		} else if (body.getPosition().y >= 7.5f){
			body.setVelocity(new Vector2(body.getVelocity().x, -2));
		} else if (body.getPosition().y < 1.5f){
			body.setVelocity(new Vector2(body.getVelocity().x, 2));
		}
	}

	@Override
	public PhysicsMovementPattern copy() {
		return new EvadingMovementPattern(entity);
	}
}
