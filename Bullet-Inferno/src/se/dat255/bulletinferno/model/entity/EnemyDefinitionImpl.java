package se.dat255.bulletinferno.model.entity;

import se.dat255.bulletinferno.model.gui.Listener;
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

/**
 * Definitions of all the enemies and their stats
 */
public enum EnemyDefinitionImpl implements EnemyDefinition {

	DEFAULT_ENEMY_SHIP(new Vector2(-1, 0), 0.2f,
			new WeaponDefinitionImpl[] { WeaponDefinitionImpl.DISORDERER }, 10,
			10, new PhysicsBodyDefinitionImpl(PhysicsShapeFactory.getRectangularShape(1, 1)),
			new Vector2[] { new Vector2(0, 0) }),

	SPECIAL_ENEMY_SHIP(new Vector2(-2, 0), 1f,
			new WeaponDefinitionImpl[] { WeaponDefinitionImpl.FORCE_GUN }, 10, 10,
			new PhysicsBodyDefinitionImpl(PhysicsShapeFactory.getRectangularShape(1, 1)),
			new Vector2[] { new Vector2(0, 0) },
			new DisorderedMovementPattern(1, 1)),

	EASY_BOSS_SHIP(new Vector2(0, 2), 4f,
			new WeaponDefinitionImpl[] { WeaponDefinitionImpl.BOSS_SPR,
					WeaponDefinitionImpl.BOSS_AIM }, 10, 10,
			new PhysicsBodyDefinitionImpl(PhysicsShapeFactory.getRectangularShape(2, 2)),
			new Vector2[] { new Vector2(0, 0), new Vector2(0, 0) },
			new DisorderedMovementPattern(1, 4)),

	HARD_BOSS_SHIP(new Vector2(0, 2), 5f,
			new WeaponDefinitionImpl[] { WeaponDefinitionImpl.BOSS_SPR,
					WeaponDefinitionImpl.BOSS_SPR, WeaponDefinitionImpl.BOSS_SPR,
					WeaponDefinitionImpl.BOSS_AIM, WeaponDefinitionImpl.BOSS_AIM,
					WeaponDefinitionImpl.BOSS_AIM }, 10, 10,
			new PhysicsBodyDefinitionImpl(PhysicsShapeFactory.getRectangularShape(2, 2)),
			new Vector2[] { new Vector2(0, 0), new Vector2(0, 1/2f), new Vector2(0, -1/2f),
					new Vector2(0, 0), new Vector2(0, 1/2f), new Vector2(0, -1/2f) },
			new DisorderedMovementPattern(1, 4));

	private final Vector2 velocity;
	private final PhysicsMovementPattern pattern;
	private final float initialHealth;
	private WeaponDefinitionImpl[] weaponsData;
	/** The score the enemy awards the player on death */
	private final int score;
	/** The credits the enemy awards the player on death */
	private final int credits;
	private final PhysicsBodyDefinition bodyDefinition;
	private final Vector2[] weaponPositionModifier;

	EnemyDefinitionImpl(Vector2 velocity, float initialHealth, WeaponDefinitionImpl[] weaponsData, 
			int score, int credits, PhysicsBodyDefinition bodyDefinition, 
			Vector2[] weaponPositionModifier, PhysicsMovementPattern pattern) {
		this.velocity = velocity.cpy();
		this.pattern = pattern;
		this.initialHealth = initialHealth;
		this.weaponsData = weaponsData;
		this.weaponPositionModifier = weaponPositionModifier;
		this.score = score;
		this.credits = credits;
		this.bodyDefinition = bodyDefinition;
	}
	
	EnemyDefinitionImpl(Vector2 velocity, float initialHealth, WeaponDefinitionImpl[] weaponsData, 
			int score, int credits, PhysicsBodyDefinition bodyDefinition, Vector2[] weaponPositionModifier) {
		this(velocity, initialHealth, weaponsData, score, credits, bodyDefinition, weaponPositionModifier,  null);
	}

	private DefaultEnemyShipImpl getEnemyShip(PhysicsEnvironment physics, EntityEnvironment entities, 
			WeaponEnvironment weaponEnvironment, Vector2 position, Listener<Integer> scoreListener) {
		Weapon[] weapons = new Weapon[weaponsData.length];
		for (int i = 0; i < weapons.length; i++) {
			weapons[i] = weaponsData[i].createWeapon(physics, weaponEnvironment);
		}

		if (pattern == null) {
			return new DefaultEnemyShipImpl(physics, entities, this, position, velocity, initialHealth, 
					weapons, weaponPositionModifier, score, credits, bodyDefinition, scoreListener);
		} else {
			return new DefaultEnemyShipImpl(physics, entities, this, position, velocity, initialHealth, 
					weapons, weaponPositionModifier, score, credits, bodyDefinition, pattern, scoreListener);
		}
	}

	private DefaultBossImpl getBoss(PhysicsEnvironment physics, EntityEnvironment entities,
			WeaponEnvironment weaponEnvironment, Vector2 position, Listener<Integer> scoreListener) {
		Weapon[] weapons = new Weapon[weaponsData.length];
		for (int i = 0; i < weapons.length; i++) {
			weapons[i] = weaponsData[i].createWeapon(physics, weaponEnvironment);
		}
		return new DefaultBossImpl(physics, entities, this, position, velocity, pattern, initialHealth, 
				weapons, weaponPositionModifier, score, credits, bodyDefinition, scoreListener);
	}
	
	@Override
	public Enemy createEnemy(PhysicsEnvironment physics, EntityEnvironment entities,
			WeaponEnvironment weaponEnvironment, Vector2 position, Listener<Integer> scoreListener) {
		if (this == EASY_BOSS_SHIP || this == HARD_BOSS_SHIP) {
			return getBoss(physics, entities, weaponEnvironment, position, scoreListener);
		} else {
			return getEnemyShip(physics, entities, weaponEnvironment, position, scoreListener);
		}
	}
}
