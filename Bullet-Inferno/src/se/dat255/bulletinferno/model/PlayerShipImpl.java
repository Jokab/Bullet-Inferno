package se.dat255.bulletinferno.model;

import se.dat255.bulletinferno.Graphics;
import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinitionImpl;
import se.dat255.bulletinferno.util.PhysicsShapeFactory;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape;

public class PlayerShipImpl implements PlayerShip {

	public enum ShipType implements Teamable {
		PLAYER_DEFAULT;

		@Override
		public boolean isInMyTeam(Teamable teamMember) {
			return true;
		}
	}

	private final Game game;
	private final int initialHealth;
	private float takeDamageModifier = 1; // default
	private int health;
	private final ShipType shipType;
	private final Loadout loadout;
	private PhysicsBody body = null;
	private Vector2 forwardSpeed = new Vector2(2, 0); // TODO: Not hardcode?

	public PlayerShipImpl(Game game, final Vector2 position, int initialHealth, Loadout loadout,
			ShipType shipType) {
		this.game = game;
		this.initialHealth = initialHealth;
		this.health = initialHealth;
		this.loadout = loadout;
		this.shipType = shipType;

		// TODO: should probably not apply this here
		if (loadout.getPassiveAbility() != null) {
			loadout.getPassiveAbility().getEffect().applyEffect(this);
		}

		Shape shape = PhysicsShapeFactory.getRectangularShape(0.08f, 0.1f);
		PhysicsBodyDefinition bodyDefinition = new PhysicsBodyDefinitionImpl(shape);

		body = game.getPhysicsWorld().createBody(bodyDefinition, this, position);
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
}
