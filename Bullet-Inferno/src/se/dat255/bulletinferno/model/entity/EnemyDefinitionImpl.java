package se.dat255.bulletinferno.model.entity;

import se.dat255.bulletinferno.model.physics.DisorderedMovementPattern;
import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinition;
import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinitionImpl;
import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;
import se.dat255.bulletinferno.model.physics.PhysicsMovementPattern;
import se.dat255.bulletinferno.model.weapon.Weapon;
import se.dat255.bulletinferno.model.weapon.WeaponDefinitionImpl;
import se.dat255.bulletinferno.model.weapon.WeaponEnvironment;
import se.dat255.bulletinferno.model.weapon.WeaponPlacement;
import se.dat255.bulletinferno.model.weapon.WeaponPlacementImpl;
import se.dat255.bulletinferno.util.Listener;
import se.dat255.bulletinferno.util.PhysicsShapeFactory;

import com.badlogic.gdx.math.Vector2;

/**
 * Definitions of all the enemies and their stats
 */
public enum EnemyDefinitionImpl implements EnemyDefinition {

	KATZE(new Vector2(-1, 0), 0.5f,
			new WeaponPlacement[] { new WeaponPlacementImpl(WeaponDefinitionImpl.KATZE_GUN, -1f,
					0.25f) }, 10,
			10, new PhysicsBodyDefinitionImpl(PhysicsShapeFactory.getRectangularShape(1.7f, 0.9f))),

	SQUIB(new Vector2(-2, 0), 0.5f,
			new WeaponPlacement[] { new WeaponPlacementImpl(WeaponDefinitionImpl.LASER_GUN, -0.7f,
					-0.7f) }, 10, 10,
			new PhysicsBodyDefinitionImpl(PhysicsShapeFactory.getRectangularShape(1.7f, 1.7f)),
			new DisorderedMovementPattern(1, 1)),

	// BOSSES BELOW

	EHMO(new Vector2(0, 2), 0.10f,
			new WeaponPlacementImpl[] {
					new WeaponPlacementImpl(WeaponDefinitionImpl.BOSS_SPR, 0, 0),
					new WeaponPlacementImpl(WeaponDefinitionImpl.BOSS_AIM, 0, 0) }, 10, 10,

			new PhysicsBodyDefinitionImpl(PhysicsShapeFactory.getRectangularShape(3, 4.76f)),
			new DisorderedMovementPattern(1, 4)),

	DRIPPER(new Vector2(0, 2), 25,

			new WeaponPlacementImpl[] {
					new WeaponPlacementImpl(WeaponDefinitionImpl.BOSS_SPR, 0, 0.5f),
					new WeaponPlacementImpl(WeaponDefinitionImpl.BOSS_SPR, 0, 0.0f),
					new WeaponPlacementImpl(WeaponDefinitionImpl.BOSS_SPR, 0, -0.5f),
					new WeaponPlacementImpl(WeaponDefinitionImpl.BOSS_AIM, 0, 1 / 2f),
					new WeaponPlacementImpl(WeaponDefinitionImpl.BOSS_AIM, 0, 0f),
					new WeaponPlacementImpl(WeaponDefinitionImpl.BOSS_AIM, 0, -1 / 2f) }, 10, 10,

			new PhysicsBodyDefinitionImpl(PhysicsShapeFactory.getRectangularShape(3, 3.36f)),
			new DisorderedMovementPattern(0.5f, 3));

	private final Vector2 velocity;
	private final PhysicsMovementPattern pattern;

	private final float initialHealth;
	private WeaponPlacement[] weaponsData;

	private final int score;
	/** The credits the enemy awards the player on death */
	private final int credits;
	private final PhysicsBodyDefinition bodyDefinition;

	EnemyDefinitionImpl(Vector2 velocity, float initialHealth, WeaponPlacement[] weaponsData,
			int score,
			int credits,
			PhysicsBodyDefinition bodyDefinition, PhysicsMovementPattern pattern) {

		this.velocity = velocity.cpy();
		this.pattern = pattern;
		this.initialHealth = initialHealth;
		this.weaponsData = weaponsData;
		this.score = score;
		this.credits = credits;
		this.bodyDefinition = bodyDefinition;
	}

	EnemyDefinitionImpl(Vector2 velocity, float initialHealth, WeaponPlacement[] weaponsData,
			int score,
			int credits, PhysicsBodyDefinition bodyDefinition) {
		this(velocity, initialHealth, weaponsData, score, credits, bodyDefinition, null);
	}

	@Override
	public Enemy createEnemy(PhysicsEnvironment physics, EntityEnvironment entityEnvironment,
			WeaponEnvironment weaponEnvironment, Vector2 position, Listener<Integer> scoreListener) {
		if (this == EHMO || this == DRIPPER) {
			return getBoss(physics, entityEnvironment, weaponEnvironment, position, scoreListener);
		} else {
			return getEnemyShip(physics, entityEnvironment, weaponEnvironment, position,
					scoreListener);
		}

	}

	private DefaultEnemyShipImpl getEnemyShip(PhysicsEnvironment physics,
			EntityEnvironment entities,

			WeaponEnvironment weaponEnvironment, Vector2 position, Listener<Integer> scoreListener) {

		Weapon[] weapons = new Weapon[weaponsData.length];
		for (int i = 0; i < weapons.length; i++) {

			weapons[i] = weaponsData[i].getContent().createWeapon(physics, weaponEnvironment,
					weaponsData[i].getOffset());
		}

		if (pattern == null) {
			return new DefaultEnemyShipImpl(physics, entities, this, position, velocity,
					initialHealth,

					weapons, score, credits, bodyDefinition, scoreListener);
		} else {
			return new DefaultEnemyShipImpl(physics, entities, this, position, velocity,
					initialHealth,
					weapons, score, credits, bodyDefinition, pattern, scoreListener);

		}
	}

	private DefaultBossImpl getBoss(PhysicsEnvironment physics, EntityEnvironment entities,
			WeaponEnvironment weaponEnvironment, Vector2 position, Listener<Integer> scoreListener) {
		Weapon[] weapons = new Weapon[weaponsData.length];
		for (int i = 0; i < weapons.length; i++) {
			weapons[i] = weaponsData[i].getContent().createWeapon(physics, weaponEnvironment,
					weaponsData[i].getOffset());
		}
		return new DefaultBossImpl(physics, entities, this, position, velocity, pattern,
				initialHealth,

				weapons, score, credits, bodyDefinition, scoreListener);

	}

}
