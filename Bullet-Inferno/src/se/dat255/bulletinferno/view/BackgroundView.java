package se.dat255.bulletinferno.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import se.dat255.bulletinferno.model.ModelEnvironment;
import se.dat255.bulletinferno.model.entity.PlayerShip;
import se.dat255.bulletinferno.model.map.Segment;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.util.TextureDefinitionImpl;
import se.dat255.bulletinferno.view.map.SegmentView;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BackgroundView implements Renderable {

	private final PlayerShip ship;
	private final TextureRegion texture;
	private List<SegmentView> segmentViews = Collections.emptyList();
	private final ModelEnvironment models;

	private final ResourceManager resourceManager;

	/** The last Game.getRemovedSegmentCount() value, which reflects the current segmentViews. */
	private int lastRemovedSegmentCount = 0;

	public BackgroundView(ModelEnvironment models, ResourceManager resourceManager, PlayerShip ship) {
		this.ship = ship;
		texture = resourceManager.getTexture(TextureDefinitionImpl.BLUE_BACKGROUND);
		this.models = models;
		this.resourceManager = resourceManager;
	}

	/**
	 * Refreshes the segment views list to reflect the current game.
	 */
	private void refreshSegmentViews() {
		int removedSegmentCount = models.getRemovedSegmentCount();
		int removedSegmentsSinceLast = removedSegmentCount - lastRemovedSegmentCount;

		// Note that is is possible that segmentViews.size() < removedSegmentsSinceLast!
		int segmentViewsActuallyRemoved = 0;
		for (SegmentView segmentView : segmentViews) {
			if (segmentViewsActuallyRemoved >= removedSegmentsSinceLast) {
				break;
			}

			segmentView.dispose();
			segmentViewsActuallyRemoved++;
		}

		// This is a safe measurement even if removedSegmentsSinceLast exceeds segmentViews.size().
		int segmentViewsNotRemoved = segmentViews.size() - segmentViewsActuallyRemoved;

		List<? extends Segment> segments = models.getSegments();
		int newSegmentCount = segments.size() - segmentViewsNotRemoved;

		// Only create a new list if something actually changed (removals, additions).
		if (segmentViewsActuallyRemoved > 0 || newSegmentCount > 0) {
			int newSegmentViewsSize = segmentViewsNotRemoved + newSegmentCount;
			List<SegmentView> newSegmentViews = new ArrayList<SegmentView>(newSegmentViewsSize);

			// Add old segment views.
			newSegmentViews.addAll(segmentViews.subList(segmentViewsActuallyRemoved,
					segmentViewsActuallyRemoved + segmentViewsNotRemoved));

			// Add new segment views, creating their actual views.
			for (Segment segment : segments.subList(segmentViewsNotRemoved, segments.size())) {
				newSegmentViews.add(new SegmentView(resourceManager, segment));
			}

			segmentViews = newSegmentViews;
			lastRemovedSegmentCount = removedSegmentCount;
		}
	}

	@Override
	public void render(SpriteBatch batch, Camera viewport) {
		refreshSegmentViews();

		batch.disableBlending();
		batch.draw(texture, viewport.position.x - viewport.viewportWidth / 2, 0,
				viewport.viewportWidth, viewport.viewportHeight);
		batch.enableBlending();

		float shipLeftX = ship.getPosition().x;
		float shipRightX = ship.getPosition().x + 16;
		for (SegmentView segmentView : segmentViews) {
			float startX = segmentView.segment.getPosition().x;
			if (shipLeftX <= startX || startX < shipRightX) {
				segmentView.render(batch, viewport);
			}
		}
	}

	@Override
	public void dispose() {
	}
}
