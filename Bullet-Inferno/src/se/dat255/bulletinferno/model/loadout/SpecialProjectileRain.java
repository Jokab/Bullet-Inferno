package se.dat255.bulletinferno.model.loadout;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

import se.dat255.bulletinferno.Graphics;
import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.PlayerShip;
import se.dat255.bulletinferno.model.SpecialEffect;
import se.dat255.bulletinferno.model.weapon.ProjectileType;
import se.dat255.bulletinferno.util.Timer;
import se.dat255.bulletinferno.util.TimerImpl;
import se.dat255.bulletinferno.util.Timerable;

public class SpecialProjectileRain implements SpecialEffect, Timerable {

	private final Game game;
	private static final int AMOUNT_BULLETS = 20;
	private final Timer timer;
	private final List<Vector2> bulletPositions = new ArrayList<Vector2>(AMOUNT_BULLETS);
	private int counter = 0;
	private PlayerShip playerShip;

	public SpecialProjectileRain(Game game) {
		this.game = game;
		this.timer = game.getTimer();
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
			ProjectileType.RIGHT_ACCELERATING_PROJECTILE.releaseProjectile(game,
					bulletPositions.get(index), new Vector2(), new Vector2(3, 0), playerShip);
			counter++;
		}
	}
}
