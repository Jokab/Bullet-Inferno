package se.dat255.bulletinferno.model.entity;

import se.dat255.bulletinferno.model.physics.DisorderedMovementPattern;
import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinition;
import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinitionImpl;
import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;
import se.dat255.bulletinferno.model.physics.PhysicsMovementPattern;
import se.dat255.bulletinferno.model.weapon.Weapon;
import se.dat255.bulletinferno.model.weapon.WeaponDefinitionImpl;
import se.dat255.bulletinferno.model.weapon.WeaponEnvironment;
import se.dat255.bulletinferno.util.PhysicsShapeFactory;
import se.dat255.bulletinferno.util.ResourceIdentifier;

import com.badlogic.gdx.math.Vector2;

public enum EnemyDefinitionImpl implements EnemyDefinition, ResourceIdentifier {
	DEFAULT_ENEMY_SHIP(new Vector2(-3, 0), 5, new WeaponDefinitionImpl[] { WeaponDefinitionImpl.DISORDERER }, 10,
			10, new PhysicsBodyDefinitionImpl(PhysicsShapeFactory.getRectangularShape(1, 1))),

	SPECIAL_ENEMY_SHIP(new Vector2(-2, 0), 5, new WeaponDefinitionImpl[] { WeaponDefinitionImpl.FORCE_GUN }, 10, 10,
			new PhysicsBodyDefinitionImpl(PhysicsShapeFactory.getRectangularShape(1, 1)),
			new DisorderedMovementPattern(1, 1)),

	BOSS_ENEMY_SHIP(new Vector2(0, 2), 25,
			new WeaponDefinitionImpl[] { WeaponDefinitionImpl.BOSS_LAUNCHER, WeaponDefinitionImpl.BOSS_GUN }, 10, 10,
			new PhysicsBodyDefinitionImpl(PhysicsShapeFactory.getRectangularShape(2, 2)),
			new DisorderedMovementPattern(1, 4));

	private final Vector2 velocity;
	private final PhysicsMovementPattern pattern;
	private final int initialHealth;
	private WeaponDefinitionImpl[] weaponsData;
	private final int score;
	private final int credits;
	private final PhysicsBodyDefinition bodyDefinition;

	EnemyDefinitionImpl(Vector2 velocity, int initialHealth, WeaponDefinitionImpl[] weaponsData, int score,
			int credits, PhysicsBodyDefinition bodyDefinition) {
		this(velocity, initialHealth, weaponsData, score, credits, bodyDefinition, null);
	}

	EnemyDefinitionImpl(Vector2 velocity, int initialHealth, WeaponDefinitionImpl[] weaponsData, int score,
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

	private DefaultEnemyShipImpl getEnemyShip(PhysicsEnvironment physics,
			EntityEnvironment entities, WeaponEnvironment weaponEnvironment, Vector2 position) {
		Weapon[] weapons = new Weapon[weaponsData.length];
		for (int i = 0; i < weapons.length; i++) {
			weapons[i] = weaponsData[i].createWeapon(physics, weaponEnvironment);
		}

		if (pattern == null) {
			return new DefaultEnemyShipImpl(physics, entities, this, position, velocity,
					initialHealth, weapons, score, credits, bodyDefinition);
		} else {
			return new DefaultEnemyShipImpl(physics, entities, this, position, velocity,
					initialHealth, weapons, score, credits, bodyDefinition, pattern);
		}
	}

	private DefaultBossImpl getBoss(PhysicsEnvironment physics, EntityEnvironment entities,
			WeaponEnvironment weaponEnvironment, Vector2 position) {
		Weapon[] weapons = new Weapon[weaponsData.length];
		for (int i = 0; i < weapons.length; i++) {
			weapons[i] = weaponsData[i].createWeapon(physics, weaponEnvironment);
		}

		return new DefaultBossImpl(physics, entities, this, position, velocity, pattern,
				initialHealth, weapons, score, credits, bodyDefinition);
	}

	@Override
	public String getIdentifier() {
		return this.name();
	}

	@Override
	public Enemy createEnemy(PhysicsEnvironment physics, EntityEnvironment entities,
			WeaponEnvironment weaponEnvironment, Vector2 position) {
		if (this == BOSS_ENEMY_SHIP) {
			return getBoss(physics, entities, weaponEnvironment, position);
		} else {
			return getEnemyShip(physics, entities, weaponEnvironment, position);
		}
	}
}
