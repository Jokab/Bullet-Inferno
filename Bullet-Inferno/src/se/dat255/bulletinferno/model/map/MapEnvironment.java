package se.dat255.bulletinferno.model.map;

import java.util.List;

import se.dat255.bulletinferno.model.entity.EntityEnvironment;
import se.dat255.bulletinferno.model.weapon.ProjectileDefinition;
import se.dat255.bulletinferno.model.weapon.WeaponEnvironment;

import com.badlogic.gdx.math.Vector2;

public interface MapEnvironment {

	/**
	 * <strong>Note:</strong> setViewport must be called before this method is called.
	 * 
	 * @return a list of all active segments (to some extent in, or at least near, the viewport).
	 */
	public List<? extends Segment> getSegments();

	/**
	 * @return the number of segments that have been removed from the active segment list since the
	 *         SegmentManager started. Removals only happen from the beginning of the segment list.
	 */
	public int getRemovedSegmentCount();

	/**
	 * Sets the viewport. Segments will be inside (completely or to some extent, or at least near).
	 * 
	 * @param viewportPosition
	 *        the center-position in world coordinates for the viewport.
	 * @param viewportDimensions
	 *        the dimensions of the viewport in world coordinates (width, height).
	 */
	public void setViewport(Vector2 viewportPosition, Vector2 viewportDimensions);

	public EntityEnvironment getEntityEnvironment();

	/**
	 * Returns a list of all projectiles in the game
	 * 
	 * @return a list of projectiles currently in the game.
	 */
	public List<? extends ProjectileDefinition> getProjectiles();

	/**
	 * @return the games weapon environment. Only for use by controllers!
	 */
	public WeaponEnvironment getWeaponEnvironment();

}