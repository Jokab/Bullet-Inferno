package se.dat255.bulletinferno.model;

import com.badlogic.gdx.physics.box2d.Shape;

/**
 * Factory that creates various complex Box2D shapes with simple method calls.
 */
public interface PhysicsShapeFactory {

	/**
	 * Creates a rectangle that is of the desired width/height. (For Box2D
	 * hackers: this method uses normal width/height, rather than
	 * half-width/heights as Box2D usually uses.)
	 * 
	 * <p>
	 * Please note that shapes must be properly disposed of (but the physics
	 * system will handle that for you if you have used the shape with the
	 * system, e.g. in a PhysicsBodyDefinition).
	 * 
	 * @param width
	 *            the width of the shape.
	 * @param height
	 *            the height of the shape.
	 * @return a rectangular Box2D shape.
	 */
	public Shape getRectangularShape(float width, float height);

}