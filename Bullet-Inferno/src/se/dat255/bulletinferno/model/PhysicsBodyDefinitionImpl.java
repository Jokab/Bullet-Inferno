package se.dat255.bulletinferno.model;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Disposable;

/**
 * Class representing a PhysicsBody type. Body definitions can be re-used for many body creations.
 * 
 * <p>For Box2D hackers: the PhysicsBodyDefinition contains a BodyDef and one or more Fixture
 */
class PhysicsBodyDefinitionImpl implements PhysicsBodyDefinition, Disposable {
    
    /** The internal Box2D definition we're wrapping. */
    private final BodyDef definition = new BodyDef();
    
    /** The internal shape used for the one fixture used by the body definition (right now). */
    private final Shape shape;
    
    /** The internal fixture definition used for the body. Should be linked to this.shape. */
    private final FixtureDef fixtureDefinition = new FixtureDef();
    
    /**
     * Construct a new body definition (non-bullet).
     * 
     * @param shape the shape of the physics body. Will be managed and disposed of by the physics
     *              engine from now on.
     */
    public PhysicsBodyDefinitionImpl(Shape shape) {
        this(shape, false);
    }
    
    /**
     * Construct a new body definition, optionally defined as a bullet, meaning it moves very fast
     * (true means higher precision at the expense of worse performance).
     * 
     * @param shape the shape of the physics body. Will be managed and disposed of by the physics
     *              engine from now on.
     * @param isBullet whether to apply high precision to the bodies of this type.
     */
    public PhysicsBodyDefinitionImpl(Shape shape, boolean isBullet) {
        definition.type = BodyDef.BodyType.DynamicBody;
        definition.gravityScale = 0; // No gravity.
        definition.bullet = isBullet;
        
        this.shape = shape;
        
        fixtureDefinition.shape = this.shape;
        fixtureDefinition.density = 0f;
        fixtureDefinition.friction = 0f;
        fixtureDefinition.isSensor = true; // Sensors does not have collision response.
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
        shape.dispose();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FixtureDef getBox2DFixtureDefinition() {
        return fixtureDefinition;
    }
    
}