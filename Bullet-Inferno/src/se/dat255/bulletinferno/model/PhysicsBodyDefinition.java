package se.dat255.bulletinferno.model;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public interface PhysicsBodyDefinition {

	/**
	 * @return the internal Box2D body definition object abstracted away by this
	 *         object.
	 */
	public BodyDef getBox2DBodyDefinition();

	/**
	 * @return the internal Box2D fixture definition object (currently only one
	 *         supported).
	 */
	public FixtureDef getBox2DFixtureDefinition();

}