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
import se.dat255.bulletinferno.util.Listener;
import se.dat255.bulletinferno.util.PhysicsShapeFactory;
import se.dat255.bulletinferno.util.Timer;
import se.dat255.bulletinferno.util.Timerable;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape;

public class PlayerShipImpl implements PlayerShip, Timerable {

	public enum ShipType implements Teamable {
		PLAYER_DEFAULT(new Vector2[] { new Vector2(0.225f, 35 / 100f),
				new Vector2(0.25f, -58 / 100f) });

		private final Vector2[] weaponPositionModifier;

		ShipType(Vector2[] weaponPositionModifier) {
			this.weaponPositionModifier = weaponPositionModifier;
		}

		public Vector2[] getWeaponPositionModifier() {
			return weaponPositionModifier;
		}

		@Override
		public boolean isInMyTeam(Teamable teamMember) {
			return true;
		}
	}

	private float takeDamageModifier = 1;
	private float health = 1.0f;
	private final ShipType shipType;
	private final WeaponLoadout weaponLoadout;
	private PhysicsBody body = null;
	private final Vector2 forwardSpeed = new Vector2(5, 0);
	private final Vector2[] weaponPositionModifier;
	private final Listener<Float> healthListener;

	/**
	 * A timer used to fire the standard weapon
	 */
	private final Timer weaponTimer;

	/** A timer used to every update check our location relative to a specified halt distance */
	private final Timer haltTimer;
	/** The x-coordinate at which the ship should come to a stop. */
	private float haltAtPosition;

	private final Timerable haltShipTimerable = new Timerable() {
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
			final Vector2 position, WeaponLoadout loadout, ShipType shipType,
			Listener<Float> healthListener) {
		weaponLoadout = loadout;
		this.shipType = shipType;
		weaponPositionModifier = shipType.getWeaponPositionModifier();
		this.healthListener = healthListener;
		healthListener.call(health);

		// Add health increment
		Timer healthIncrement = physics.getTimer();
		healthIncrement.setContinuous(true);
		healthIncrement.setTime(0.01f);
		healthIncrement.registerListener(new Timerable() {
			@Override
			public void onTimeout(Timer source, float timeSinceLast) {
				giveHealth(0.002f);
			}
		});
		healthIncrement.start();

		// Set up the halt timer used to stop the ship at a specified location
		haltTimer = physics.getTimer();
		haltTimer.setTime(0);
		haltTimer.setContinuous(true);

		weaponTimer = loadout.getStandardWeapon().getTimer();
		weaponTimer.setContinuous(true);
		weaponTimer.registerListener(this);
		weaponTimer.start();

		List<Shape> shapes = new ArrayList<Shape>(2);
		// Body
		shapes.add(PhysicsShapeFactory.getRectangularShape(1f, 0.6f));
		shapes.add(PhysicsShapeFactory.getRectangularShape(1.8f, 0.1f, new Vector2(
				0f, 0.15f)));
		// Box
		shapes.add(PhysicsShapeFactory.getRectangularShape(0.4f, 0.51f, new Vector2(
				0.45f, 0.25f)));
		// Propeller
		shapes.add(PhysicsShapeFactory.getRectangularShape(0.03f, 0.5f, new Vector2(
				0.83f, -0.1f)));
		PhysicsBodyDefinition bodyDefinition = new PhysicsBodyDefinitionImpl(shapes,
				BodyType.DYNAMIC);

		body = physics.createBody(bodyDefinition, this, position);
		body.setVelocity(forwardSpeed);

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
		if (other instanceof Projectile) {
			if (!isInMyTeam(((Projectile) other).getSource())) {
				takeDamage(((Projectile) other).getDamage());
			}
		} else if (collidedWithNonTeamMember(other)) {
			if (other instanceof Enemy) {
				takeDamage(0.6f, true);
			}
		} else {
			die();
		}
	}

	private boolean collidedWithNonTeamMember(Collidable other) {
		return other instanceof Teamable && !isInMyTeam((Teamable) other);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void postCollided(Collidable other) {
		// NOP
	}

	/**
	 * Gives health to the player and visually update
	 * 
	 * @param health
	 *        The health to give
	 */
	public void giveHealth(float health) {
		this.health += health;
		if (this.health > 1.0f) {
			this.health = 1.0f;
		}
		healthListener.call(this.health);
	}

	@Override
	public void takeDamage(float damage) {
		health -= damage * takeDamageModifier;
		healthListener.call(health);

		if (isDead()) {
			dispose();
		}
	}

	/**
	 * Helper method for taking damage without using the takeDamageModifier.
	 * 
	 * @see PlayerShip#takeDamage(float)
	 */
	private void takeDamage(float damage, boolean ignoreDamageModifier) {
		if (ignoreDamageModifier) {
			takeDamage(damage / takeDamageModifier);
		} else {
			takeDamage(damage);
		}
	}

	@Override
	public void setTakeDamageModifier(float takeDamageModifier) {
		this.takeDamageModifier = takeDamageModifier;
	}

	@Override
	public float getHealth() {
		return health;
	}

	@Override
	public float getInitialHealth() {
		return 1.0f;
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
			float positionY = body.getPosition().y;
			float resultY = positionY + dy;
			if (resultY < 0.5f) {
				dy = 0.5f - positionY;
			}
			if (resultY > 8.5f) {
				dy = 8.5f - positionY;
			}
			body.getBox2DBody().setTransform(getPosition().add(0, scale * dy), 0);
		}
	}

	@Override
	public void fireWeapon() {
		if (!isDead()) {
			weaponLoadout.getHeavyWeapon().fire(getPosition(), new Vector2(1, 0), this);
		}
	}

	@Override
	public Weapon getWeapon() {
		return weaponLoadout.getStandardWeapon();
	}

	@Override
	public boolean isInMyTeam(Teamable teamMember) {
		return teamMember instanceof PlayerShip;
	}

	@Override
	public String getIdentifier() {
		return shipType.name();
	}

	@Override
	public void dispose() {
		body.setVelocity(new Vector2()); // we need to stop moving
	}

	@Override
	public WeaponLoadout getLoadout() {
		return weaponLoadout;
	}

	@Override
	public boolean isDead() {
		return health <= 0;
	}

	/** Causes the player to instantly die */
	private void die() {
		takeDamage(health, true);
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getXVelocity() {
		return body.getVelocity().x;
	}

}