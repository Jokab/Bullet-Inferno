package se.dat255.bulletinferno.model.entity;

import java.util.ArrayList;

import se.dat255.bulletinferno.model.loadout.Loadout;
import se.dat255.bulletinferno.model.loadout.PassiveAbility;
import se.dat255.bulletinferno.model.physics.Collidable;
import se.dat255.bulletinferno.model.physics.PhysicsBody;
import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinition;
import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinitionImpl;
import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;
import se.dat255.bulletinferno.model.team.Teamable;
import se.dat255.bulletinferno.model.weapon.Projectile;
import se.dat255.bulletinferno.model.weapon.Weapon;
import se.dat255.bulletinferno.util.PhysicsShapeFactory;
import se.dat255.bulletinferno.util.Timer;
import se.dat255.bulletinferno.util.Timerable;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Shape;

public class PlayerShipImpl implements PlayerShip {

	public enum ShipType implements Teamable {
		PLAYER_DEFAULT;

		@Override
		public boolean isInMyTeam(Teamable teamMember) {
			return true;
		}
	}

	private final PhysicsEnvironment physics;
	private final int initialHealth;
	private float takeDamageModifier = 1; // default
	private int health;
	private final ShipType shipType;
	private final Loadout loadout;
	private PhysicsBody body = null;
	private Vector2 forwardSpeed = new Vector2(2, 0); // TODO: Not hardcode?
	
	/** A timer used to every update check our location relative to a specified halt distance */
	private Timer haltTimer;
	/** The x-coordinate at which the ship should come to a stop. */
	private float haltAtPosition;
	
	private Timerable haltShipTimerable = new Timerable() {
		@Override
		public void onTimeout(Timer source, float timeSinceLast) {
			PlayerShipImpl ship = PlayerShipImpl.this;
			
			float diffX = ship.body.getPosition().x - ship.haltAtPosition;
			if (diffX >= 0) {
				ship.body.setVelocity(new Vector2(0, 0));
				//ship.body.getBox2DBody().setTransform(-diffX, 0, 0);
				source.unregisterListener(this);
			}
		}
	};
	
	public PlayerShipImpl(PhysicsEnvironment physics, EntityEnvironment entities, 
			final Vector2 position, int initialHealth, Loadout loadout, ShipType shipType) {
		this.physics = physics;
		this.initialHealth = initialHealth;
		this.health = initialHealth;
		this.loadout = loadout;
		this.shipType = shipType;
		
		// Set up the halt timer used to stop the ship at a specified location
		this.haltTimer = physics.getTimer();
		haltTimer.setTime(0);
		haltTimer.setContinuous(true);
		

		// TODO: should probably not apply this here
		if (loadout.getPassiveAbility() != null) {
			loadout.getPassiveAbility().getEffect().applyEffect(this);
		}

		Shape shape = PhysicsShapeFactory.getRectangularShape(1, 1);
		PhysicsBodyDefinition bodyDefinition = new PhysicsBodyDefinitionImpl(shape);

		body = physics.createBody(bodyDefinition, this, position);
		body.setVelocity(forwardSpeed);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void preCollided(Collidable other) {
		if (hitByOtherProjectile(other)) {
			takeDamage(((Projectile) other).getDamage());
		} else if (collidedWithSomethingElse(other)) {
			System.out.println("You crashed!!!");
		}
	}

	private boolean collidedWithSomethingElse(Collidable other) {
		return other instanceof Teamable && !isInMyTeam((Teamable) other);
	}

	private boolean hitByOtherProjectile(Collidable other) {
		return other instanceof Projectile && !isInMyTeam(((Projectile) other).getSource());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void postCollided(Collidable other) {
		// NOP
	}

	@Override
	public void takeDamage(float damage) {
		this.health -= damage * takeDamageModifier;

		if (isDead()) {
			dispose();
		}
	}

	@Override
	public void setTakeDamageModifier(float takeDamageModifier) {
		this.takeDamageModifier = takeDamageModifier;
	}

	@Override
	public int getHealth() {
		return this.health;
	}

	@Override
	public int getInitialHealth() {
		return this.initialHealth;
	}

	@Override
	public Vector2 getPosition() {
		return body.getPosition();
	}

	@Override
	public void moveY(float dy) {
		moveY(dy, 1);
	}

	@Override
	public void moveY(float dy, float scale) {
		if (!isDead()) {
			body.getBox2DBody().setTransform(getPosition().add(0, scale * dy), 0);
		}
	}

	@Override
	public void fireWeapon() {
		loadout.getPrimaryWeapon().fire(getPosition(), new Vector2(1, 0), this);
	}

	@Override
	public Weapon getWeapon() {
		return this.loadout.getPrimaryWeapon();
	}

	@Override
	public boolean isInMyTeam(Teamable teamMember) {
		return teamMember instanceof PlayerShip;
	}

	@Override
	public String getIdentifier() {
		return this.shipType.name();
	}

	@Override
	public void attachPassive(PassiveAbility passiveAbility) {
		passiveAbility.getEffect().applyEffect(this);
	}

	@Override
	public void dispose() {
		body.setVelocity(new Vector2()); // we need to stop moving
	}

	@Override
	public Loadout getLoadout() {
		return this.loadout;
	}

	@Override
	public boolean isDead() {
		return this.health <= 0;
	}

	@Override
	public Vector2 getDimensions() {
		ArrayList<Fixture> fixtures = body.getBox2DBody().getFixtureList();
		BoundingBox boundingBox = new BoundingBox();
		for (Fixture fixture : fixtures) {
			// TODO
		}
		// TODO: Temporary solution, remove when above is working. 
		return new Vector2(1, 1);
	}
	
	@Override
	public void halt(float distance) {
		haltTimer.registerListener(haltShipTimerable);
		haltAtPosition = body.getPosition().x + distance;
		haltTimer.start();
	}

	@Override
	public void restoreSpeed(){
		body.setVelocity(forwardSpeed);
	}
	
	
}
