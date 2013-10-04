package se.dat255.bulletinferno.model.physics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import se.dat255.bulletinferno.model.PhysicsBodyDefinition;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Disposable;

/**
 * Class representing a PhysicsBody type. Body definitions can be re-used for many body creations.
 * 
 * <p>
 * For Box2D hackers: the PhysicsBodyDefinition contains a BodyDef and one or more FixtureDef(s).
 */
public class PhysicsBodyDefinitionImpl implements PhysicsBodyDefinition,
		Disposable {

	/** The body type to use for the body definition. */
	public enum BodyType {
		/** Standard. The most "living" type, collides with other dynamic and static bodies. */
		DYNAMIC(BodyDef.BodyType.DynamicBody),

		/** Used for moving ground. Does only collide with dynamic bodies, not anything else! */
		KINEMATIC(BodyDef.BodyType.KinematicBody),

		/** Used for ground and other static elements. Does only collide with dynamic bodies! */
		STATIC(BodyDef.BodyType.DynamicBody);

		private final BodyDef.BodyType bodyType;

		private BodyType(BodyDef.BodyType bodyType) {
			this.bodyType = bodyType;
		}
	}

	/** The internal Box2D definition we're wrapping. */
	private final BodyDef definition = new BodyDef();

	/**
	 * The internal shapes used for the fixtures used by the body definition.
	 */
	private final List<? extends Shape> shapes;

	/**
	 * The internal fixture definitions used for the body. Should be linked to this.shapes.
	 */
	private final List<? extends FixtureDef> fixtureDefinitions;

	/**
	 * Construct a new body definition from a simple shape (dynamic body).
	 * 
	 * @param shape
	 *        the shape of the physics body. Will be managed and disposed of
	 *        by the physics engine from now on.
	 */
	public PhysicsBodyDefinitionImpl(Shape shape) {
		this(Arrays.asList(shape), BodyType.DYNAMIC);
	}

	/**
	 * Construct a new body definition.
	 * 
	 * <p>
	 * For Box2D hackers: all shapes will generate a fixture each <strong>that is a sensor</strong>.
	 * 
	 * @param shapes
	 *        the list of shapes of the physics body. Will be managed and disposed of by the physics
	 *        engine from now on.
	 * @param bodyType
	 *        the body type to use. Use dynamic if unsure.
	 */
	public PhysicsBodyDefinitionImpl(List<? extends Shape> shapes, BodyType bodyType) {
		definition.type = bodyType.bodyType;
		definition.gravityScale = 0; // No gravity.

		this.shapes = shapes;
		
		List<FixtureDef> fixtureDefinitions = new ArrayList<FixtureDef>(shapes.size());
		for(Shape shape : shapes) {
			FixtureDef fixtureDefinition = new FixtureDef();
			fixtureDefinition.shape = shape;
			fixtureDefinition.density = 0f;
			fixtureDefinition.friction = 0f;
			fixtureDefinition.isSensor = true; // Sensors do not have collision response.
			fixtureDefinitions.add(fixtureDefinition);
		}
		this.fixtureDefinitions = fixtureDefinitions;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BodyDef getBox2DBodyDefinition() {
		return definition;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
		for(Shape shape : shapes) {
			shape.dispose();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<? extends FixtureDef> getBox2DFixtureDefinition() {
		return fixtureDefinitions;
	}

}