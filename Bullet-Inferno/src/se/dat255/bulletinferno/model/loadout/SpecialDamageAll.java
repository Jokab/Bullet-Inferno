package se.dat255.bulletinferno.model.loadout;

import se.dat255.bulletinferno.model.Destructible;
import se.dat255.bulletinferno.model.Enemy;
import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.PlayerShip;
import se.dat255.bulletinferno.model.SpecialEffect;

public class SpecialDamageAll implements SpecialEffect {

	private final Game game;
	private static final float DAMAGE = 5;

	public SpecialDamageAll(Game game) {
		this.game = game;
	}

	@Override
	public void activate(PlayerShip playerShip) {
		for(Enemy enemy : game.getEnemies()) {
			if(enemy instanceof Destructible) {
				((Destructible) enemy).takeDamage(DAMAGE);
			}
		}
	}
}
