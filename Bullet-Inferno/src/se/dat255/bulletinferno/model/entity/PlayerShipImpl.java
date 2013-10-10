package se.dat255.bulletinferno.model.entity;

import java.util.ArrayList;
import java.util.List;

import se.dat255.bulletinferno.model.physics.Collidable;
import se.dat255.bulletinferno.model.physics.PhysicsBody;
import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinition;
import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinitionImpl;
import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinitionImpl.BodyType;
import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;
import se.dat255.bulletinferno.model.team.Teamable;
import se.dat255.bulletinferno.model.weapon.Projectile;
import se.dat255.bulletinferno.model.weapon.Weapon;
import se.dat255.bulletinferno.model.weapon.WeaponLoadout;
import se.dat255.bulletinferno.util.PhysicsShapeFactory;
import se.dat255.bulletinferno.util.Timer;
import se.dat255.bulletinferno.util.Timerable;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape;

public class PlayerShipImpl implements PlayerShip, Timerable {

	public enum ShipType implements Teamable {
		PLAYER_DEFAULT(new Vector2[] { new Vector2(1 / 2f, 2 / 3f), new Vector2(1 / 2f, -2 / 5f) });

		private final Vector2[] weaponPositionModifier;

		ShipType(Vector2[] weaponPositionModifier) {
			this.weaponPositionModifier = weaponPositionModifier;
		}

		public Vector2[] getWeaponPosisitionModifier() {
			return weaponPositionModifier;
		}

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
	private final WeaponLoadout weaponLoadout;
	private PhysicsBody body = null;
	private Vector2 forwardSpeed = new Vector2(2, 0); // TODO: Not hardcode?
	private final Vector2[] weaponPositionModifier;

	/**
	 * A timer used to fire the standard weapon
	 */
	private Timer weaponTimer;

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
				ship.body.setVelocity(Vector2.Zero);
				// ship.body.getBox2DBody().setTransform(-diffX, 0, 0);
				source.unregisterListener(this);
			}
		}
	};

	public PlayerShipImpl(PhysicsEnvironment physics, EntityEnvironment entities,
			final Vector2 position, int initialHealth, WeaponLoadout loadout, ShipType shipType) {
		this.physics = physics;
		this.initialHealth = initialHealth;
		this.health = initialHealth;
		this.weaponLoadout = loadout;
		this.shipType = shipType;
		this.weaponPositionModifier = shipType.getWeaponPosisitionModifier();

		// Set up the halt timer used to stop the ship at a specified location
		this.haltTimer = physics.getTimer();
		haltTimer.setTime(0);
		haltTimer.setContinuous(true);

		this.weaponTimer = loadout.getStandardWeapon().getTimer();
		weaponTimer.setContinuous(true);
		weaponTimer.registerListener(this);
		weaponTimer.start();

		List<Shape> shapes = new ArrayList<Shape>(2);
		shapes.add(PhysicsShapeFactory.getRectangularShape(2.4f, 0.3f));
		shapes.add(PhysicsShapeFactory.getRectangularShape(0.5f, 1.5f, new Vector2(
				(2.4f / 2 - 0.5f / 2), 0)));
		PhysicsBodyDefinition bodyDefinition = new PhysicsBodyDefinitionImpl(shapes,
				BodyType.DYNAMIC);

		this.body = physics.createBody(bodyDefinition, this, position);
		this.body.setVelocity(forwardSpeed);

		// Sets correct offsets based on ship type
		// Needs to be done after the body creation, i.e. getDimensions()
		weaponLoadout.getStandardWeapon().setOffset(
				getDimensions().cpy().scl(weaponPositionModifier[0]));
		weaponLoadout.getHeavyWeapon().setOffset(
				getDimensions().cpy().scl(weaponPositionModifier[1]));

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void preCollided(Collidable other) {
		if (hitByOtherProjectile(other)) {
			takeDamage(((Projectile) other).getDamage());
		} else if (collidedWithSomethingElse(other)) {
			// TODO: Should be some constant or dynamic value.
			takeDamage(20);
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
		weaponLoadout.getHeavyWeapon().fire(getPosition(), new Vector2(1, 0), this);
	}

	@Override
	public Weapon getWeapon() {
		return this.weaponLoadout.getStandardWeapon();
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
	public void dispose() {
		body.setVelocity(new Vector2()); // we need to stop moving
	}

	@Override
	public WeaponLoadout getLoadout() {
		return this.weaponLoadout;
	}

	@Override
	public boolean isDead() {
		return this.health <= 0;
	}

	@Override
	public void halt(float distance) {
		haltTimer.registerListener(haltShipTimerable);
		haltAtPosition = body.getPosition().x + distance;
		haltTimer.start();
	}

	@Override
	public void restoreSpeed() {
		haltTimer.stop();
		haltTimer.unregisterListener(haltShipTimerable);
		body.setVelocity(forwardSpeed);
	}

	@Override
	public void onTimeout(Timer source, float timeSinceLast) {

		Weapon standardWeapon = weaponLoadout.getStandardWeapon();

		if (source == weaponTimer) {
			standardWeapon.fire(getPosition(), new Vector2(1, 0),
					this);
		}
	}

	@Override
	public Vector2 getDimensions() {
		return body.getDimensions();
	}

	// TODO: DEBUG
	/*
	 * public Body getBody() {
	 * return body.getBox2DBody();
	 * }
	 */

}
