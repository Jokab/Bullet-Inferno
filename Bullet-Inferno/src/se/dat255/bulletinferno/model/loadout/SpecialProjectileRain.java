package se.dat255.bulletinferno.model.loadout;

import com.badlogic.gdx.math.Vector2;

import se.dat255.bulletinferno.Graphics;
import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.PlayerShip;
import se.dat255.bulletinferno.model.SpecialEffect;
import se.dat255.bulletinferno.model.weapon.ProjectileType;

public class SpecialProjectileRain implements SpecialEffect {
	
	private final Game game;
	private static final int AMOUNT_BULLETS = 10;

	public SpecialProjectileRain(Game game) {
		this.game = game;
	}
	
	@Override
	public void activate(PlayerShip playerShip) {
		for(int i = 1; i <= AMOUNT_BULLETS; i++) {
			float xPos = playerShip.getWeapon().getOffset().x;
			float yPos = (((Graphics.GAME_HEIGHT -2) / AMOUNT_BULLETS) * i + 1);
			Vector2 position = new Vector2(xPos, yPos);
			ProjectileType.DEFAULT_PROJECTILE.releaseProjectile(game, position, new Vector2(), new Vector2(3,0), playerShip);
		}
	}
}
