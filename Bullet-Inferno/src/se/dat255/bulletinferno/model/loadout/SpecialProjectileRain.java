package se.dat255.bulletinferno.model.loadout;

import java.util.ArrayList;
import java.util.List;

import se.dat255.bulletinferno.controller.Graphics;
import se.dat255.bulletinferno.model.entity.PlayerShip;
import se.dat255.bulletinferno.model.weapon.WeaponEnvironment;
import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;
import se.dat255.bulletinferno.model.weapon.ProjectileType;
import se.dat255.bulletinferno.util.Timer;
import se.dat255.bulletinferno.util.Timerable;

import com.badlogic.gdx.math.Vector2;

public class SpecialProjectileRain implements SpecialEffect, Timerable {

	private final PhysicsEnvironment physics;
	private final WeaponEnvironment weapons;
	private static final int AMOUNT_BULLETS = 20;
	private final Timer timer;
	private final List<Vector2> bulletPositions = new ArrayList<Vector2>(AMOUNT_BULLETS);
	private int counter = 0;
	private PlayerShip playerShip;

	public SpecialProjectileRain(PhysicsEnvironment physics, WeaponEnvironment weapons) {
		this.physics = physics;
		this.weapons = weapons;
		this.timer = physics.getTimer();
		timer.registerListener(this);
	}

	@Override
	public void activate(PlayerShip playerShip) {
		timer.setTime(0.1f);
		timer.setContinuous(true);
		timer.start();
		this.playerShip = playerShip;
		for (int i = 1; i <= AMOUNT_BULLETS; i++) {
			float xPos = playerShip.getWeapon().getOffset().x;
			float yPos = (((Graphics.GAME_HEIGHT - 2) / AMOUNT_BULLETS) * i + 1);
			bulletPositions.add(new Vector2(xPos, yPos));
		}
	}

	@Override
	public void onTimeout(Timer source, float timeSinceLast) {
		int index = (int) Math.ceil(Math.random() * AMOUNT_BULLETS -1);
		if (counter < AMOUNT_BULLETS) {
			ProjectileType.MISSILE.releaseProjectile(physics, weapons,
					bulletPositions.get(index), new Vector2(), new Vector2(3, 0), playerShip);
			counter++;
		}
	}
}
