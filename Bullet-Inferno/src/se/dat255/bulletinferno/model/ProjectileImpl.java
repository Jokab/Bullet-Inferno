package se.dat255.bulletinferno.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape;

public class ProjectileImpl implements Projectile {
    
    private static PhysicsBodyDefinition bodyDefinition = null;
    
    private PhysicsBody body = null;
    
	private int damage;
	private final Game game;
	
	/**
	 * Constructs a new projectile
	 * @param world
	 */
	public ProjectileImpl(Game game) {
		this.game = game;
		
		if(bodyDefinition == null) {
		    Shape shape = game.getPhysicsWorld().getShapeFactory().getRectangularShape(1f, 2f);
		    bodyDefinition = new PhysicsBodyDefinitionImpl(shape, true);
		}
	}
	
	/**
	 * Initializes the projectile
	 * @param origin position
	 * @param velocity
	 * @param damage
	 */
	public void init(Vector2 origin, Vector2 velocity, int damage) {
		this.damage = damage;
		
		this.setPosition(origin);
		this.setVelocity(velocity);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getDamage() {
		return damage;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void collided(Collidable entity) {
		// Code for special behavior here
		
		game.disposeProjectile(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void reset() {
		// TODO Reset projectile
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setVelocity(Vector2 velocity) {
		body.setVelocity(velocity);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Vector2 getPosition() {
		return body.getPosition();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setPosition(Vector2 position) {
	    // @todo fix init method for this.
	    if(body != null) {
	        throw new RuntimeException("Operation not supported yet.");
	    }
	    body = game.getPhysicsWorld().createBody(bodyDefinition, position);
	}
}
