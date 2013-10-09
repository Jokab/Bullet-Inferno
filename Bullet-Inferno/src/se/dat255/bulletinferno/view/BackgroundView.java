package se.dat255.bulletinferno.view;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import se.dat255.bulletinferno.model.ModelEnvironment;
import se.dat255.bulletinferno.model.entity.PlayerShip;
import se.dat255.bulletinferno.model.map.Segment;
import se.dat255.bulletinferno.util.ManagedTexture;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.util.ResourceManagerImpl.TextureType;
import se.dat255.bulletinferno.view.map.SegmentView;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class BackgroundView implements Renderable {
	
	private PlayerShip ship;
	private ManagedTexture mTexture;
	private Texture texture;
	private List<SegmentView> segmentViews = Collections.emptyList();
	private ModelEnvironment models;
	
	private final ResourceManager resourceManager;
	
	/** The last Game.getRemovedSegmentCount() value, which reflects the current segmentViews. */
	private int lastRemovedSegmentCount = 0;

	public BackgroundView(ModelEnvironment models, ResourceManager resourceManager, PlayerShip ship) {
		this.ship = ship;		
		mTexture = resourceManager.getManagedTexture(TextureType.BLUE_BACKGROUND);
		texture = mTexture.getTexture();
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
		for(SegmentView segmentView : segmentViews) {
			if(segmentViewsActuallyRemoved >= removedSegmentsSinceLast) {
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
		if(segmentViewsActuallyRemoved > 0 || newSegmentCount > 0) {
			int newSegmentViewsSize = segmentViewsNotRemoved + newSegmentCount;
			List<SegmentView> newSegmentViews = new ArrayList<SegmentView>(newSegmentViewsSize);
			
			// Add old segment views.
			newSegmentViews.addAll(segmentViews.subList(segmentViewsActuallyRemoved,
					segmentViewsActuallyRemoved + segmentViewsNotRemoved));
			
			// Add new segment views, creating their actual views.
			for(Segment segment : segments.subList(segmentViewsNotRemoved, segments.size())) {
				newSegmentViews.add(new SegmentView(resourceManager, segment));
			}
			
			segmentViews = newSegmentViews;
			lastRemovedSegmentCount = removedSegmentCount;
		}
	}
	
	@Override
	public void render(SpriteBatch batch) {
		refreshSegmentViews();
		
		batch.disableBlending();
		batch.draw(texture, ship.getPosition().x-ship.getDimensions().x/2, 0, 16, 9, 0, 0, 32, 1024, false, false);
		batch.enableBlending();
		
		float shipLeftX = ship.getPosition().x;
		float shipRightX = ship.getPosition().x + 16;
		for(SegmentView segmentView : segmentViews){
			float startX = segmentView.segment.getPosition().x;
			float endX = startX + segmentView.segment.getWidth();
			if(shipLeftX <= startX || startX < shipRightX){
				segmentView.render(batch);
				//batch.draw(s.getEndTexture(), startX, 9, 0, 0, 2, Graphics.GAME_HEIGHT, 1, 1, 180);
				//batch.draw(s.getTexture(), startX, 0, 0, 0, (endX-startX-2), Graphics.GAME_HEIGHT, 1, 1, 0);
				//batch.draw(s.getEndTexture(), endX-2, 0, 0, 0, 2, Graphics.GAME_HEIGHT, 1, 1, 0);
			}
			
			//TODO: Thread-safe removal of segment that has been passed
				
			/*if(s.getEnd()<ship.getPosition().x){
				game.removeSegment(s);
			}*/
		}
		
	}

	@Override
	public void dispose() {
		mTexture.dispose(resourceManager);
	}
}
