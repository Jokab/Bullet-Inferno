package se.dat255.bulletinferno.model.enemy;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.ResourceIdentifier;
import se.dat255.bulletinferno.model.weapon.WeaponData;

import com.badlogic.gdx.math.Vector2;

public enum EnemyType implements ResourceIdentifier {

	// ORDER:
	// velocity, health, weapon, score, credits
	DEFAULT_SHIP(new Vector2(-3, 0), 5, WeaponData.FASTENEMY, 10, 10),
	FAST_SHIP(new Vector2(-6, 0), 5, WeaponData.FASTENEMY, 10, 10),
	SLOW_SHIP(new Vector2(-1.5f, 0), 5, WeaponData.FASTENEMY, 10, 10);

	private final Vector2 velocity;
	private final int initialHealth;
	private final WeaponData weaponData;
	private final int score;
	private final int credits;

	EnemyType(Vector2 velocity, int initialHealth, WeaponData weaponData, int score, int credits) {
		this.velocity = velocity;
		this.initialHealth = initialHealth;
		this.weaponData = weaponData;
		this.score = score;
		this.credits = credits;
	}

	public SimpleEnemy getEnemyShip(Game game, Vector2 position) {
		return new DefaultEnemyShipImpl(game, this, position, velocity, initialHealth,
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
