package se.dat255.bulletinferno.model.loadout;

import se.dat255.bulletinferno.model.entity.Destructible;
import se.dat255.bulletinferno.model.entity.Enemy;
import se.dat255.bulletinferno.model.entity.EntityEnvironment;
import se.dat255.bulletinferno.model.entity.PlayerShip;

public class SpecialDamageAll implements SpecialEffect {

	private final EntityEnvironment entities;
	private static final float DAMAGE = 5;

	public SpecialDamageAll(EntityEnvironment entities) {
		this.entities = entities;
	}

	@Override
	public void activate(PlayerShip playerShip) {
		for(Enemy enemy : entities.getEnemies()) {
			if(enemy instanceof Destructible) {
				((Destructible) enemy).takeDamage(DAMAGE);
			}
		}
	}
}
