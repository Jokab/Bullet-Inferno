package se.dat255.bulletinferno.model.entity;

import java.util.ArrayList;
import java.util.List;

import se.dat255.bulletinferno.model.entity.PlayerShipImpl.ShipType;
import se.dat255.bulletinferno.model.gui.Listener;
import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;
import se.dat255.bulletinferno.model.weapon.WeaponEnvironment;
import se.dat255.bulletinferno.model.weapon.WeaponLoadout;
import se.dat255.bulletinferno.util.GameActionEvent;

import com.badlogic.gdx.math.Vector2;

public class EntityEnvironmentImpl implements EntityEnvironment {
	
	private final List<Enemy> enemies = new ArrayList<Enemy>();
	private PlayerShip playerShip;
	private Listener<GameActionEvent> actionListener;
	
	public EntityEnvironmentImpl(PhysicsEnvironment physics, WeaponEnvironment weapons,
			WeaponLoadout weaponLoadout, Listener<Float> healthListener) {
		playerShip = new PlayerShipImpl(physics, this, new Vector2(0, 8), weaponLoadout, 
				ShipType.PLAYER_DEFAULT, healthListener);
	}
	
	public EntityEnvironmentImpl(PhysicsEnvironment physics, WeaponEnvironment weapons,
			WeaponLoadout weaponLoadout, Listener<Float> healthListener, 
			Listener<GameActionEvent> actionListener) {
		this(physics, weapons, weaponLoadout, healthListener);
		this.actionListener = actionListener;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addEnemy(Enemy enemy) {
		enemies.add(enemy);
		if(actionListener != null) {
			enemy.setActionListener(actionListener);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<? extends Enemy> getEnemies() {
		return enemies;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlayerShip getPlayerShip() {
		return playerShip;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeEnemy(Enemy enemy) {
		enemies.remove(enemy);
	}

}
