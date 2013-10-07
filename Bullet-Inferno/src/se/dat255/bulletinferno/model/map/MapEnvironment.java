package se.dat255.bulletinferno.model.map;

import java.util.List;

import se.dat255.bulletinferno.model.entity.EntityEnvironment;

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
	
}