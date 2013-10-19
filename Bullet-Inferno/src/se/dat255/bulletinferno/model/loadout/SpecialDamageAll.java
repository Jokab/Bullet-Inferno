package se.dat255.bulletinferno.model.loadout;

import se.dat255.bulletinferno.controller.Graphics;
import se.dat255.bulletinferno.model.entity.Destructible;
import se.dat255.bulletinferno.model.entity.Enemy;
import se.dat255.bulletinferno.model.entity.EntityEnvironment;
import se.dat255.bulletinferno.model.entity.PlayerShip;

/**
 * A special effect that slightly damage all enemies on the screen
 */
public class SpecialDamageAll implements SpecialEffect {

	private final EntityEnvironment entities;
	private static final float DAMAGE = 5;

	/**
	 * Constructs a SpecialEffet which will deal damage to all enemies
	 * on screen at the time of activation. The damage dealt is set in the
	 * field {@link #DAMAGE}.
	 * 
	 * @param entities
	 *        The EntityEnvironment for the game.
	 */
	public SpecialDamageAll(EntityEnvironment entities) {
		this.entities = entities;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void activate(PlayerShip playerShip) {
		float minX = playerShip.getPosition().x - playerShip.getDimensions().x;
		float maxX = playerShip.getPosition().x + playerShip.getDimensions().x
				+ Graphics.GAME_WIDTH;

		for (Enemy enemy : entities.getEnemies()) {
			if ((minX <= enemy.getPosition().x && enemy.getPosition().x <= maxX) &&
					enemy instanceof Destructible) {
				System.out.println("NUKE: " + enemy);
				((Destructible) enemy).takeDamage(DAMAGE);
			}
		}
	}
}
