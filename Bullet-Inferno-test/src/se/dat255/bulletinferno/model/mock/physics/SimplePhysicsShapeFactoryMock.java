package se.dat255.bulletinferno.model.mock.physics;

import com.badlogic.gdx.physics.box2d.Shape;
import se.dat255.bulletinferno.model.PhysicsShapeFactory;

public class SimplePhysicsShapeFactoryMock implements PhysicsShapeFactory {
    @Override
    public Shape getRectangularShape(float width, float height) {
        return new SimplePhysicsShapeMock();
    }
}
