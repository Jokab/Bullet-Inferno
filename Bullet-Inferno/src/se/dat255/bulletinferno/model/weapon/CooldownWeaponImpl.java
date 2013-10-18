package se.dat255.bulletinferno.model.weapon;

import com.badlogic.gdx.math.Vector2;

import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;
import se.dat255.bulletinferno.model.team.Teamable;
import se.dat255.bulletinferno.util.Timer;
import se.dat255.bulletinferno.util.Timerable;

public class CooldownWeaponImpl extends WeaponImpl implements Timerable {

	private final ProjectileDefinition projectileType;
	private final Timer firingRateTimer; // very high firing rate, but needs to be limited
	private final PhysicsEnvironment physics;
	private final WeaponEnvironment weapons;
	private final float projectileSpeed;
	private final int fullAmmo;
	private int usedAmmo = 0;

	public CooldownWeaponImpl(PhysicsEnvironment physics, WeaponEnvironment weapons,
			WeaponDefinition weaponData, float reloadingTime, ProjectileDefinition projectileType,
			float projectileSpeed, Vector2 offset) {
		super(physics, weapons, weaponData, reloadingTime, projectileType, projectileSpeed, offset);

		this.projectileType = projectileType;
		this.physics = physics;
		this.weapons = weapons;
		this.projectileSpeed = projectileSpeed;

		// Magic number... ?
		fullAmmo = (int) (10 / reloadingTime); // reloading time determines amount of projectiles

		Timer cooldownTimer = getTimer();
		cooldownTimer.setTime(reloadingTime);
		cooldownTimer.setContinuous(true);
		cooldownTimer.registerListener(this);
		cooldownTimer.start();

		firingRateTimer = physics.getTimer();
		firingRateTimer.setTime(0.1f); // very high firing rate, but needs to be limited
		firingRateTimer.stop(); // Set it to finished

	}

	@Override
	public void fire(Vector2 position, Vector2 direction, Teamable source) {
		if (firingRateTimer.isFinished()) {
			if (usedAmmo < fullAmmo) {
				usedAmmo = usedAmmo + 1;
				projectileType.releaseProjectile(physics, weapons,
						position.add(getOffset().cpy().add(new Vector2(getDimensions().x, 0))),
						direction.scl(projectileSpeed), source);
			}
			// System.out.println("Projectiles left: " + (fullAmmo - usedAmmo));
			firingRateTimer.restart();
		}
	}

	@Override
	public void onTimeout(Timer source, float timeSinceLast) {
		if (usedAmmo > 0) {
			usedAmmo = usedAmmo - 1;
		}
	}
}