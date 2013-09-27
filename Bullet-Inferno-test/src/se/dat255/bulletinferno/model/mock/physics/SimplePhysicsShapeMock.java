package se.dat255.bulletinferno.model.mock.physics;

import com.badlogic.gdx.physics.box2d.Shape;

public class SimplePhysicsShapeMock extends Shape {
    @Override
    public Type getType() {
        return Type.Polygon;
    }
}
