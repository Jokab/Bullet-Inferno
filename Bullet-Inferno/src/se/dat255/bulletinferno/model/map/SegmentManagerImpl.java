package se.dat255.bulletinferno.model.map;

import java.util.ArrayList;
import java.util.List;

import se.dat255.bulletinferno.controller.ScoreController;
import se.dat255.bulletinferno.model.entity.EntityEnvironment;
import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;
import se.dat255.bulletinferno.model.weapon.WeaponEnvironment;

import com.badlogic.gdx.math.Vector2;

/**
 * Implements a simple SegmentManager.
 * 
 * @see SegmentManager
 */
public class SegmentManagerImpl implements SegmentManager {

	/** The minimum number of slices per segment when randomizing slices in segments. */
	private static final int SLICES_PER_SEGMENT_MIN = 10;

	/** The maximum number of slices per segment when randomizing slices in segments. */
	private static final int SLICES_PER_SEGMENT_MAX = 20;

	/** The PhysicsEnvironment instance injected at construction. */
	private final PhysicsEnvironment physics;
	
	/** The EntityEnvironment instance injected at construction. */
	private final EntityEnvironment entities;
	
	/** The WeaponEnvironment instance injected at construction. */
	private final WeaponEnvironment weapons;
	
	/**
	 * Currently active segments on the map. Removes from this list increments removedSegmentsCount
	 * by one one for each removed segment. Removes are only allowed from the head (beginning) and
	 * adds only allowed to the tail (end), similar to a queue but not exactly.
	 */
	private List<Segment> segments = new ArrayList<Segment>(0);
	
	/** The number of segments that have been removed from the (beginning of) segments so far. */
	private int removedSegmentCount = 0;
	
	private final ScoreController scoreController;
	
	/** Segment factory instance. */
	private SegmentFactory segmentFactory = new SegmentFactory();

	/**
	 * Construct a new segment manager. Segments will be available after calling set
	 */
	public SegmentManagerImpl(PhysicsEnvironment physics, EntityEnvironment entities,
			WeaponEnvironment weapons, ScoreController scoreController) {
		this.physics = physics;
		this.entities = entities;
		this.weapons = weapons;
		this.scoreController = scoreController;
	}

	/**
	 * Adds a segment to the end of the active segment list (i.e. to the right of the last).
	 * 
	 * @param segment
	 *        the segment to add.
	 */
	private void addSegment(Segment segment) {
		segments.add(segment);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<? extends Segment> getSegments() {
		return segments;
	}

	/**
	 * Removes segments from the beginning of the active segment list (i.e. the leftmost ones).
	 * 
	 * @param segment
	 *        the number of segments to remove.
	 */
	private void removeSegments(int numberOfSegments) {
		if (segments.size() < numberOfSegments) {
			throw new IllegalArgumentException("numberOfSegments exceeds segment list length");
		}
		
		if(numberOfSegments < 1) {
			return;
		}

		// The idea here is to use the old number of segments as an initial capacity, as there will
		// probably be inserted exactly numberOfSegments new segments very soon!
		List<Segment> newSegments = new ArrayList<Segment>(segments.size());

		int leftToRemove = numberOfSegments;
		for (Segment segment : segments) {
			if (leftToRemove > 0) {
				segment.dispose();
				leftToRemove--;
			} else {
				newSegments.add(segment);
			}
		}

		segments = newSegments;
		removedSegmentCount += numberOfSegments;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getRemovedSegmentCount() {
		return removedSegmentCount;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setViewport(Vector2 viewportPosition, Vector2 viewportDimensions) {
		float halfWidth = viewportDimensions.x/2;
		float xMin = viewportPosition.x - halfWidth;
		float xMax = viewportPosition.x + halfWidth;
		
		// Remove any segments that is not in the viewport.
		int segmentsToRemove = 0;
		for(Segment segment : segments) {
			if(segment.getPosition().x + segment.getWidth() < xMin) {
				segmentsToRemove++;
			}
		}
		removeSegments(segmentsToRemove);
		
		// Get the position with highest X-value where a new segment could possibly be added, if the
		// position is inside the viewport of course.
		float rightmostLeftBounds = xMin;
		int lastSegmentIndex = segments.size() - 1;
		if(lastSegmentIndex > -1) {
			Segment lastSegment = segments.get(lastSegmentIndex);
			rightmostLeftBounds = lastSegment.getPosition().x + lastSegment.getWidth();
		}
		
		// Add new segments until the viewport is full of them (possibly adding none).
		while(rightmostLeftBounds < xMax) {
			Segment segment = segmentFactory.generateRandomSegment(physics, entities, weapons,
					new Vector2(rightmostLeftBounds, 0),
					SLICES_PER_SEGMENT_MIN, SLICES_PER_SEGMENT_MAX, scoreController);
			
			float width = segment.getWidth();
			if(width <= 0) {
				throw new IllegalStateException("the generated segment had non-positive width");
			}
			rightmostLeftBounds += width;
			
			addSegment(segment);
		}
	}

}