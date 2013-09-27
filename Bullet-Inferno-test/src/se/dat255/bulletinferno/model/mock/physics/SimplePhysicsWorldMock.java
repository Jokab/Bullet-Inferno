package se.dat255.bulletinferno.model.mock.physics;

import com.badlogic.gdx.math.Vector2;
import se.dat255.bulletinferno.model.*;

public class SimplePhysicsWorldMock implements PhysicsWorld {

    public PhysicsShapeFactory physicsShapeFactory = new SimplePhysicsShapeFactoryMock();

    @Override
    public PhysicsBody createBody(PhysicsBodyDefinition definition, Collidable collidable, Vector2 position) {
        return new SimplePhysicsBodyMock(position);
    }

    @Override
    public void removeBody(PhysicsBody body) {
    }

    @Override
    public void update(float deltaTime) {
    }

    @Override
    public PhysicsShapeFactory getShapeFactory() {
        return physicsShapeFactory;
    }

    @Override
    public void setViewport(Vector2 viewportPosition, Vector2 viewportDimension) {
    }

    @Override
    public void attachMovementPattern(PhysicsMovementPattern pattern, PhysicsBody body) {
    }

    @Override
    public void detachMovementPattern(PhysicsBody body) {
    }

    @Override
    public void dispose() {
    }
}
