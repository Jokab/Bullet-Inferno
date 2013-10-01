package se.dat255.bulletinferno.util;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;

/**
 * Static factory class that creates various complex Box2D shapes with simple method calls.
 */
public class PhysicsShapeFactory {
	private PhysicsShapeFactory() {
		// No construction.
	}

	/**
	 * Creates a rectangle that is of the desired width/height. (For Box2D hackers: this method uses
	 * normal width/height, rather than half-width/heights as Box2D usually uses.)
	 * 
	 * <p>
	 * Please note that shapes must be properly disposed of (but the physics system will handle that
	 * for you if you have used the shape with the system, e.g. in a PhysicsBodyDefinition).
	 * 
	 * @param width
	 *        the width of the shape.
	 * @param height
	 *        the height of the shape.
	 * @return a rectangular Box2D shape.
	 */
	public static Shape getRectangularShape(float width, float height) {
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width * 2f, height * 2f);
		return shape;
	}
	
	/**
	 * Like {@link PhysicsShapeFactory#getRectangularShape(float, float)} but has position. Yep,
	 * Box2D shapes have (relative) position, which is usually interpreted as how much off-center
	 * they are on a physics body (which in turn depends on other shapes/fixtures on the body).
	 * 
	 * @param position the position relative to center (usually on a physics body).
	 * @see PhysicsShapeFactory#getRectangularShape(float, float)
	 */
	public static Shape getRectangularShape(float width, float height, Vector2 position) {
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width * 2f, height * 2f, position, 0);
		return shape;
	}

}