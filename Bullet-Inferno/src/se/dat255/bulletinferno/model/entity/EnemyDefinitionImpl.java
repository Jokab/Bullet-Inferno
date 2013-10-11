package se.dat255.bulletinferno.model.entity;

import se.dat255.bulletinferno.controller.ScoreController;
import se.dat255.bulletinferno.model.physics.DisorderedBossMovementPattern;
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
import se.dat255.bulletinferno.util.PhysicsShapeFactory;
import se.dat255.bulletinferno.util.ResourceIdentifier;

import com.badlogic.gdx.math.Vector2;

public enum EnemyDefinitionImpl implements EnemyDefinition, ResourceIdentifier {

	DEFAULT_ENEMY_SHIP(new Vector2(-1, 0), 5,
			new WeaponPlacement[] {new WeaponPlacementImpl(WeaponDefinitionImpl.DISORDERER, 0, 0)}, 10,
			10, new PhysicsBodyDefinitionImpl(PhysicsShapeFactory.getRectangularShape(1, 1))),

	SPECIAL_ENEMY_SHIP(new Vector2(-2, 0), 5,
			new WeaponPlacement[] {new WeaponPlacementImpl(WeaponDefinitionImpl.FORCE_GUN, 0, 0) }, 10, 10,
			new PhysicsBodyDefinitionImpl(PhysicsShapeFactory.getRectangularShape(1, 1)), new DisorderedMovementPattern(1, 1)),

	EASY_BOSS_SHIP(new Vector2(0, 2), 10,
			new WeaponPlacementImpl[] { new WeaponPlacementImpl(WeaponDefinitionImpl.BOSS_SPR, 0 , 0),
					new WeaponPlacementImpl (WeaponDefinitionImpl.BOSS_AIM, 0, 0) }, 10, 10,
			new PhysicsBodyDefinitionImpl(PhysicsShapeFactory.getRectangularShape(2, 2)),
			new DisorderedMovementPattern(1, 4)),

	HARD_BOSS_SHIP(new Vector2(0, 2), 25,

			new WeaponPlacementImpl[] {
					new WeaponPlacementImpl(WeaponDefinitionImpl.BOSS_SPR, 0, 0),
					new WeaponPlacementImpl(WeaponDefinitionImpl.BOSS_AIM, 0, 1/2f),
					new WeaponPlacementImpl(WeaponDefinitionImpl.BOSS_SPR, 0, 0),
					new WeaponPlacementImpl(WeaponDefinitionImpl.BOSS_AIM, 0, -1/2f) }, 10, 10,
			new PhysicsBodyDefinitionImpl(PhysicsShapeFactory.getRectangularShape(2, 2)),
			new DisorderedMovementPattern(1, 4));

	private final Vector2 velocity;
	private final PhysicsMovementPattern pattern;
	private final int initialHealth;
	private WeaponPlacement[] weaponsData;
	private final int score;
	private final int credits;
	private final PhysicsBodyDefinition bodyDefinition;

	EnemyDefinitionImpl(Vector2 velocity, int initialHealth, WeaponPlacement[] weaponsData, int score,
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
	
	EnemyDefinitionImpl(Vector2 velocity, int initialHealth, WeaponPlacement[] weaponsData, int score,
			int credits, PhysicsBodyDefinition bodyDefinition) {
		this(velocity, initialHealth, weaponsData, score, credits, bodyDefinition,  null);
	}

	@Override
	public Enemy createEnemy(PhysicsEnvironment physics, EntityEnvironment entities,
			WeaponEnvironment weaponEnvironment, Vector2 position, ScoreController scoreController) {
		if (this == EASY_BOSS_SHIP || this == HARD_BOSS_SHIP) {
			return getBoss(physics, entities, weaponEnvironment, position, scoreController);
		} else {
			return getEnemyShip(physics, entities, weaponEnvironment, position, scoreController);
		}
	}

	private DefaultEnemyShipImpl getEnemyShip(PhysicsEnvironment physics, EntityEnvironment entities, 
			WeaponEnvironment weaponEnvironment, Vector2 position, ScoreController scoreController) {
	
		Weapon[] weapons = new Weapon[weaponsData.length];
		for (int i = 0; i < weapons.length; i++) {
			
			weapons[i] = weaponsData[i].getContent().createWeapon(physics, weaponEnvironment);
			weapons[i].setOffset(weaponsData[i].getOffset());
		}

		if (pattern == null) {
			return new DefaultEnemyShipImpl(physics, entities, this, position, velocity, initialHealth, 
					weapons, score, credits, bodyDefinition, scoreController);
		} else {
			return new DefaultEnemyShipImpl(physics, entities, this, position, velocity, initialHealth, 
					weapons, score, credits, bodyDefinition, pattern, scoreController);
		}
	}

	private DefaultBossImpl getBoss(PhysicsEnvironment physics, EntityEnvironment entities,
			WeaponEnvironment weaponEnvironment, Vector2 position, ScoreController scoreController) {
		Weapon[] weapons = new Weapon[weaponsData.length];
		for (int i = 0; i < weapons.length; i++) {
			weapons[i] = weaponsData[i].getContent().createWeapon(physics, weaponEnvironment);
			weapons[i].setOffset(new Vector2(weaponsData[i].getOffset()));
		}
		return new DefaultBossImpl(physics, entities, this, position, velocity, pattern, initialHealth, 
				weapons, score, credits, bodyDefinition, scoreController);
	}

	@Override
	public String getIdentifier() {
		return this.name();
	}
}
