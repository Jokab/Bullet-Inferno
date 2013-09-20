package se.dat255.bulletinferno.model;

import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;

/**
 * Factory class that creates various complex Box2D shapes with simple method
 * calls.
 */
public class PhysicsShapeFactoryImpl implements PhysicsShapeFactory {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Shape getRectangularShape(float width, float height) {
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width * 2f, height * 2f);
		return shape;
	}

}