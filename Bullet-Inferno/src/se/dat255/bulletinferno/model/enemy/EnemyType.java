package se.dat255.bulletinferno.model.enemy;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.ResourceIdentifier;
import se.dat255.bulletinferno.model.Weapon;
import se.dat255.bulletinferno.model.physics.DisorderedMovementPattern;
import se.dat255.bulletinferno.model.physics.PhysicsMovementPattern;
import se.dat255.bulletinferno.model.weapon.WeaponData;

import com.badlogic.gdx.math.Vector2;

public enum EnemyType implements ResourceIdentifier {

	// (movement pattern = null) => no movement patter
	DEFAULT_ENEMY_SHIP(new Vector2(-3, 0), null, 5, new WeaponData[]{WeaponData.DISORDERER}, 10,
			10),
	SPECIAL_ENEMY_SHIP(new Vector2(-2, 0), new DisorderedMovementPattern(1, 1) , 5,
			new WeaponData[]{WeaponData.FORCE_GUN}, 10, 10),
	
			
	BOSS_ENEMY_SHIP(new Vector2(0, 2), new DisorderedMovementPattern(1, 4) , 15,
			new WeaponData[]{WeaponData.BOSS_GUN, WeaponData.BOSS_GUN2, WeaponData.BOSS_GUN3, WeaponData.BOSS_GUN4, WeaponData.BOSS_GUN5,WeaponData.BOSS_LAUNCHER, WeaponData.BOSS_LAUNCHER2, WeaponData.BOSS_LAUNCHER3, WeaponData.BOSS_LAUNCHER4, WeaponData.BOSS_LAUNCHER5}, 10, 10);
 
	private final Vector2 velocity;
	private final PhysicsMovementPattern pattern;
	private final int initialHealth;
	private WeaponData weaponData;
	private WeaponData[] weaponsData; 
	private final int score;
	private final int credits;
	private Vector2[] offsets; 

	EnemyType(Vector2 velocity, PhysicsMovementPattern pattern, int initialHealth,
			WeaponData[] weaponsData, int score, int credits) {
		this.velocity = velocity.cpy();
		this.pattern = pattern;
		this.initialHealth = initialHealth;
		this.weaponsData = weaponsData;
		this.score = score;
		this.credits = credits;
	}

	public SimpleEnemy getEnemyShip(Game game, Vector2 position, boolean boss) {
		if (pattern == null && !boss) {
			Weapon[] wData = new Weapon[weaponsData.length];
			for(int i = 0; i<wData.length; i++){
				wData[i]=weaponsData[i].getEnemyWeaponForGame(game);
			}
			return new DefaultEnemyShipImpl(game, this, position, velocity, initialHealth,
					wData, score, credits);
		} else if(!boss) {
			
			Weapon[] wData = new Weapon[weaponsData.length];
			for(int i = 0; i<wData.length; i++){
				wData[i]=weaponsData[i].getEnemyWeaponForGame(game);
			}
			return new DefaultEnemyShipImpl(game, this, position, velocity, initialHealth,
					wData, score, credits, pattern);
		} else {
			Weapon[] wData = new Weapon[weaponsData.length];
			offsets = new Vector2[weaponsData.length];
			for(int i = 0; i<wData.length; i++){
				wData[i]=weaponsData[i].getEnemyWeaponForGame(game);
				offsets[i]=weaponsData[i].getOffset();
			}
			return new AngryBoss(game, this, position, velocity, pattern, initialHealth,
					wData, score, credits, offsets);
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
