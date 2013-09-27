package se.dat255.bulletinferno.model.mock.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import se.dat255.bulletinferno.model.PhysicsBody;

public class SimplePhysicsBodyMock implements PhysicsBody
{
    private Vector2 position = new Vector2();
    private Vector2 velocity = new Vector2();

    public SimplePhysicsBodyMock(Vector2 position) {
        this.position.set(position);
    }

    @Override
    public Vector2 getPosition() {
        return this.position;
    }

    @Override
    public void setVelocity(Vector2 velocity) {
        this.velocity.set(velocity);
    }

    @Override
    public Vector2 getVelocity() {
        return velocity;
    }

    @Override
    public Body getBox2DBody() {
        return null;
    }
}
