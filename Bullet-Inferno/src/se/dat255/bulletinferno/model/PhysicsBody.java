package se.dat255.bulletinferno.model;

import com.badlogic.gdx.math.Vector2;

/**
 * A PhysicsBody represents an instance of a simulated physics entity.
 */
public interface PhysicsBody {
    
    /**
     * @return the body position right now (in world coordinates).
     */
    public Vector2 getPosition();
    
    /**
     * Sets the (linear) velocity of the body.
     * @param velocity the velocity to set.
     */
    public void setVelocity(Vector2 velocity);
    
    /**
     * @return the velocity of the body.
     */
    public Vector2 getVelocity();
    
}