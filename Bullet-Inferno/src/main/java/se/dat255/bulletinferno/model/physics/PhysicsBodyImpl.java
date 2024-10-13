package se.dat255.bulletinferno.model.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;

/**
 * Standard implementation of a simulated PhysicsBody.
 */
public class PhysicsBodyImpl implements PhysicsBody {

	/** The Box2D body wrapped by this object. */
	private final Body body;

	/** Cached dimensions for this body */
	private Vector2 dimensions;

	/**
	 * Constructs a new PhysicsBodyImpl from the Box2D body supplied.
	 * 
	 * @param body
	 *        a PhysicsBodyDefiniton to inherit properties from.
	 */
	public PhysicsBodyImpl(Body body) {
		this.body = body;
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
	public void setVelocity(Vector2 velocity) {
		body.setLinearVelocity(velocity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Vector2 getVelocity() {
		return body.getLinearVelocity();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Body getBox2DBody() {
		return body;
	}

	@Override
	public Vector2 getDimensions() {
		if (dimensions == null) {
			calculateDimensions();
		}
		return dimensions;
	}

	/**
	 * This method calculates only the <strong>dimensions of the body</strong>, so if the body
	 * consist of two {@link Fixture}, each having a {@link Shape} that do not intercept
	 * each with other, this method will calculate the box surrounding them both.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void calculateDimensions() {
		Vector2 lowerPoint = new Vector2();
		Vector2 upperPoint = new Vector2();
		Vector2 currentPoint = new Vector2();
		boolean firstPoint = true;

		// Each fixture has a shape, go through them and find the shape's surrounding box
		for (Fixture f : body.getFixtureList()) {
			if (f.getShape().getType() == Shape.Type.Polygon) {
				PolygonShape poly = (PolygonShape) f.getShape();

				if (firstPoint && poly.getVertexCount() > 0) {
					// If the current point is the first, set it has a point of reference
					poly.getVertex(0, currentPoint);
					upperPoint.set(currentPoint);
					lowerPoint.set(currentPoint);
					firstPoint = false;
				}

				// Check the current point's position and to see if it is less or greater
				// then the upper and lower.
				for (int i = 0; i < poly.getVertexCount(); i++) {
					poly.getVertex(i, currentPoint);
					if (currentPoint.x > upperPoint.x) {
						upperPoint.x = currentPoint.x;
					}
					if (currentPoint.y > upperPoint.y) {
						upperPoint.y = currentPoint.y;
					}
					if (currentPoint.x < lowerPoint.x) {
						lowerPoint.x = currentPoint.x;
					}
					if (currentPoint.y < lowerPoint.y) {
						lowerPoint.y = currentPoint.y;
					}
				}
			} else if (f.getShape().getType() == Shape.Type.Circle) {
				// Get position and take +- radius in all directions to emulate
				// a box around the circle, to see if the points are less or greater
				// then the upper and lower.
				currentPoint = ((CircleShape) f.getShape()).getPosition();
				Float radius = f.getShape().getRadius();

				if (firstPoint) {
					upperPoint.set(currentPoint);
					lowerPoint.set(currentPoint);
					firstPoint = false;
				}

				if (currentPoint.x + radius > upperPoint.x) {
					upperPoint.x = currentPoint.x + radius;
				}
				if (currentPoint.y + radius > upperPoint.y) {
					upperPoint.y = currentPoint.y + radius;
				}
				if (currentPoint.x - radius < lowerPoint.x) {
					lowerPoint.x = currentPoint.x - radius;
				}
				if (currentPoint.y - radius < lowerPoint.y) {
					lowerPoint.y = currentPoint.y - radius;
				}
			}

		}

		dimensions = upperPoint.add(lowerPoint.scl(-1f));
	}
}