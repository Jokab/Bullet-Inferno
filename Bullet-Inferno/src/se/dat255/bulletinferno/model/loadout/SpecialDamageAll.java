package se.dat255.bulletinferno.model.loadout;

import se.dat255.bulletinferno.controller.Graphics;
import se.dat255.bulletinferno.model.entity.Destructible;
import se.dat255.bulletinferno.model.entity.Enemy;
import se.dat255.bulletinferno.model.entity.EntityEnvironment;
import se.dat255.bulletinferno.model.entity.PlayerShip;
import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;

/**
 * A special effect that slightly damages all enemies on the screen.
 */
public class SpecialDamageAll implements SpecialEffect {

	private final SpecialEffectTimerHelper timerHelper;
	private final EntityEnvironment entities;
	private static final float DAMAGE = 0.5f;

	/**
	 * Constructs a SpecialEffet which will deal damage to all enemies
	 * on screen at the time of activation. The damage dealt is set in the
	 * field {@link #DAMAGE}.
	 * 
	 * @param entities
	 *        The EntityEnvironment for the game.
	 */
	public SpecialDamageAll(EntityEnvironment entities, PhysicsEnvironment physics, float reloadTime) {
		this.entities = entities;
		this.timerHelper = new SpecialEffectTimerHelperImpl(physics, reloadTime);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void activate(PlayerShip playerShip) {
		if (timerHelper.isReady()) {
			float minX = playerShip.getPosition().x - playerShip.getDimensions().x;
			float maxX = playerShip.getPosition().x + playerShip.getDimensions().x
					+ Graphics.GAME_WIDTH;

			for (Enemy enemy : entities.getEnemies()) {
				if ((minX <= enemy.getPosition().x && enemy.getPosition().x <= maxX) &&
						enemy instanceof Destructible) {
					((Destructible) enemy).takeDamage(DAMAGE);
				}
			}
			
			timerHelper.useCharge();
			timerHelper.startReloadTimer();
		}
	}

	@Override
	public boolean isReady() {
		return timerHelper.isReady();
	}
	
	@Override
	public float getReadyPercentage() {
		return timerHelper.getReadyPercentage();
	}

	@Override
	public int getCharges() {
		return timerHelper.getCharges();
	}
}
