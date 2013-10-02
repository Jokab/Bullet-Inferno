package se.dat255.bulletinferno.model.enemy;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.PhysicsMovementPattern;
import se.dat255.bulletinferno.model.ResourceIdentifier;
import se.dat255.bulletinferno.model.physics.AccelerationMovementPattern;
import se.dat255.bulletinferno.model.physics.SineMovementPattern;
import se.dat255.bulletinferno.model.weapon.WeaponData;

import com.badlogic.gdx.math.Vector2;

public enum EnemyType implements ResourceIdentifier {

	// ORDER:
	// velocity, health, weapon, score, credits
	DEFAULT_ENEMY_SHIP(new Vector2(-3, 0),  new SineMovementPattern(4, 6f), 5, WeaponData.SLOW,  10, 10),
	SPECIAL_ENEMY_SHIP(new Vector2(-6, 0), new AccelerationMovementPattern(new Vector2(-10,0)), 5, WeaponData.SLOW, 10, 10);

	private final Vector2 velocity;
	private final PhysicsMovementPattern pattern;
	private final int initialHealth;
	private final WeaponData weaponData;
	private final int score;
	private final int credits;

	EnemyType(Vector2 velocity, PhysicsMovementPattern pattern, int initialHealth, WeaponData weaponData, int score, int credits) {
		this.velocity = velocity.cpy();
		this.pattern = pattern;
		this.initialHealth = initialHealth;
		this.weaponData = weaponData;
		this.score = score;
		this.credits = credits;
	}

	public SimpleEnemy getEnemyShip(Game game, Vector2 position) {
		return new DefaultEnemyShipImpl(game, this, position, velocity, pattern, initialHealth,
				weaponData.getEnemyWeaponForGame(game), score, credits);
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
