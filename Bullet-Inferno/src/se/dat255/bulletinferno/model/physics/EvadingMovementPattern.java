package se.dat255.bulletinferno.model.physics;

import com.badlogic.gdx.math.Vector2;
import se.dat255.bulletinferno.model.entity.PlayerShip;


/**
 * A special movement pattern for bosses and other sighted entities. 
 * The using entity tries to evade the player ship by dodging in y
 * 
 * @author Simon Österberg 
 */
public class EvadingMovementPattern implements PhysicsMovementPattern {

	private PlayerShip player;
	
	public EvadingMovementPattern(PlayerShip ship) {
		this.player = ship;
	}

	//TODO: Make more fluent and nice
	@Override
	public void update(float timeDelta, PhysicsBody body) {
		if( player.getPosition().y < body.getPosition().y +0.1f && body.getPosition().y < 7.5f){
			body.setVelocity(new Vector2(body.getVelocity().x, 2));
		} else if( player.getPosition().y > body.getPosition().y -0.1f && body.getPosition().y > 0.5f){
			body.setVelocity(new Vector2(body.getVelocity().x, -2));
		} else if (body.getPosition().y >= 7.5f){
			body.setVelocity(new Vector2(body.getVelocity().x, -2));
		} else if (body.getPosition().y < 1.5f){
			body.setVelocity(new Vector2(body.getVelocity().x, 2));
		}
	}

	@Override
	public PhysicsMovementPattern copy() {

		return new EvadingMovementPattern(player);

	}

}



