package se.dat255.bulletinferno.model;

import java.util.List;

import se.dat255.bulletinferno.model.entity.Enemy;
import se.dat255.bulletinferno.model.entity.PlayerShip;
import se.dat255.bulletinferno.model.map.Segment;
import se.dat255.bulletinferno.model.weapon.Projectile;
import se.dat255.bulletinferno.util.Disposable;

import com.badlogic.gdx.math.Vector2;

public interface ModelEnvironment extends Disposable {

	/**
	 * Updates the models (the simulation is time-step based). This should be called once every
	 * frame.
	 * 
	 * @param deltaTime
	 *        the time in seconds since last call.
	 */
	public void update(float deltaTime);

	/**
	 * Sets the viewport the Game happens in. Used as an optimization to not run the game in areas
	 * that is not within (wholly or partially), or near, the viewport.
	 * 
	 * @param viewportPosition
	 *        the center-position i world coordinates for the viewport.
	 * @param viewportDimensions
	 *        the dimensions of the viewport in world coordinates (width, height).
	 */
	public void setViewport(Vector2 viewportPosition, Vector2 viewportDimensions);
	
	public PlayerShip getPlayerShip();
	
	/**
	 * @return a list of all the active segments in the world.
	 */
	public List<? extends Segment> getSegments();
	
	/**
	 * @return the number of segments that has been removed from the segment list since the game
	 * started. Removes only happen from the beginning of the segment list.
	 */
	public int getRemovedSegmentCount();
	
	/**
	 * Returns a list of all enemies in the game
	 *
	 * @return enemies
	 */
	public List<? extends Enemy> getEnemies();
	
	/**
	 * Returns a list of all projectiles in the game
	 *
	 * @return a list of projectiles currently in the game.
	 */
	public List<? extends Projectile> getProjectiles();

}