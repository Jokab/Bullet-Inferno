package se.dat255.bulletinferno.model.enemy;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.ResourceIdentifier;
import se.dat255.bulletinferno.model.Weapon;
import se.dat255.bulletinferno.model.physics.DisorderedMovementPattern;
import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinition;
import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinitionImpl;
import se.dat255.bulletinferno.model.physics.PhysicsMovementPattern;
import se.dat255.bulletinferno.model.weapon.WeaponData;
import se.dat255.bulletinferno.util.PhysicsShapeFactory;

import com.badlogic.gdx.math.Vector2;

public enum EnemyType implements ResourceIdentifier {

	// (movement pattern = null) => no movement patter
	DEFAULT_ENEMY_SHIP(new Vector2(-3, 0), null, 5, new WeaponData[] { WeaponData.DISORDERER }, 10,
			10,
			new PhysicsBodyDefinitionImpl(PhysicsShapeFactory.getRectangularShape(1, 1))),
			
	SPECIAL_ENEMY_SHIP(new Vector2(-2, 0), new DisorderedMovementPattern(1, 1), 5,
			new WeaponData[] { WeaponData.FORCE_GUN }, 10, 10,
			new PhysicsBodyDefinitionImpl(PhysicsShapeFactory.getRectangularShape(1, 1))),

	BOSS_ENEMY_SHIP(new Vector2(0, 2), new DisorderedMovementPattern(1, 4), 15,
			new WeaponData[] {WeaponData.BOSS_LAUNCHER, WeaponData.BOSS_GUN}, 10, 10,
			new PhysicsBodyDefinitionImpl(PhysicsShapeFactory.getRectangularShape(1, 1)));

	private final Vector2 velocity;
	private final PhysicsMovementPattern pattern;
	private final int initialHealth;
	private WeaponData weaponData;
	private WeaponData[] weaponsData;
	private final int score;
	private final int credits;
	private final PhysicsBodyDefinition bodyDefinition;

	EnemyType(Vector2 velocity, PhysicsMovementPattern pattern, int initialHealth,
			WeaponData[] weaponsData, int score, int credits, PhysicsBodyDefinition bodyDefinition) {
		this.velocity = velocity.cpy();
		this.pattern = pattern;
		this.initialHealth = initialHealth;
		this.weaponsData = weaponsData;
		this.score = score;
		this.credits = credits;
		this.bodyDefinition = bodyDefinition;
	}

	public SimpleEnemy getEnemyShip(Game game, Vector2 position, boolean boss) {
		
		Weapon[] weapons = new Weapon[weaponsData.length];
		for (int i = 0; i < weapons.length; i++) {
			weapons[i] = weaponsData[i].getEnemyWeaponForGame(game);
		}

		if (pattern == null && !boss) {
			return new DefaultEnemyShipImpl(game, this, position, velocity, initialHealth,
					weapons, score, credits, bodyDefinition);
		} else if (!boss) {
			return new DefaultEnemyShipImpl(game, this, position, velocity, initialHealth,
					weapons, score, credits, bodyDefinition, pattern);
		} else {
			return new DefaultBossImpl(game, this, position, velocity, pattern, initialHealth,
					weapons, score, credits, bodyDefinition);
		}
		
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public int getInitialHealth() {
		return initialHealth;
	}

	public WeaponData getWeaponData() {
		return weaponData;
	}

	public int getScore() {
		return score;
	}

	public int getCredits() {
		return credits;
	}

	@Override
	public String getIdentifier() {
		return this.name();
	}
}
