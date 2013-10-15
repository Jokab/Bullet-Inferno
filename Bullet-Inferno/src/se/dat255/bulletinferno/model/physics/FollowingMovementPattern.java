package se.dat255.bulletinferno.model.physics;

import se.dat255.bulletinferno.model.entity.PlayerShip;

import com.badlogic.gdx.math.Vector2;


/**
 * A special movement pattern for bosses and other sighted entities. 
 * The using entity tries to match its y-position with the players
 * 
 * @author Simon Österberg 
 */
public class FollowingMovementPattern implements PhysicsMovementPattern {

	private PlayerShip player;
	
	public FollowingMovementPattern(PlayerShip ship) {
		this.player = ship;
	}

	
	@Override
	public void update(float timeDelta, PhysicsBody body) {
		if( player.getPosition().y > body.getPosition().y +0.1f){
			body.setVelocity(new Vector2(body.getVelocity().x, 2));
		} else if( player.getPosition().y < body.getPosition().y -0.1f){
			body.setVelocity(new Vector2(body.getVelocity().x, -2));
		} else {
			body.setVelocity(new Vector2(body.getVelocity().x, 0));
		}
	}

	@Override
	public PhysicsMovementPattern copy() {

		return new FollowingMovementPattern(player);

	}

}



