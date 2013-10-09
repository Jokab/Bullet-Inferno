package se.dat255.bulletinferno.model.loadout;

import se.dat255.bulletinferno.model.entity.Destructible;
import se.dat255.bulletinferno.model.entity.Enemy;
import se.dat255.bulletinferno.model.entity.EntityEnvironment;
import se.dat255.bulletinferno.model.entity.PlayerShip;

public class SpecialDamageAll implements SpecialEffect {

	private final EntityEnvironment entities;
	private static final float DAMAGE = 5;

	/**
	 * Constructs a SpecialEffet which will deal damage to all enemies
	 * on screen at the time of activation. The damage dealt is set in the
	 * field {@link #DAMAGE}.
	 * 
	 * @param entities The EntityEnvironment for the game.
	 */
	public SpecialDamageAll(EntityEnvironment entities) {
		this.entities = entities;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void activate(PlayerShip playerShip) {
		for(Enemy enemy : entities.getEnemies()) {
			if(enemy instanceof Destructible) {
				((Destructible) enemy).takeDamage(DAMAGE);
			}
		}
	}
}
