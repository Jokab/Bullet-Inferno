package se.dat255.bulletinferno.view.map;

import java.util.List;

import se.dat255.bulletinferno.model.map.Segment;
import se.dat255.bulletinferno.model.map.Slice;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.view.Renderable;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Handles the rendering of a Segment and the
 * Slices it contains
 */
public class SegmentView implements Renderable {
	/** The segment this view handles */
	public final Segment segment;
	/** A reference to the slices needed for this view to render */
	public final SliceView[] slices;

	/** Creates a new view for the given segment */
	public SegmentView(ResourceManager resourceManager, Segment segment) {
		this.segment = segment;
		List<? extends Slice> slices = segment.getSlices();

		int length = slices.size();
		this.slices = new SliceView[length];
		for (int i = 0; i < slices.size(); i++) {
			TextureRegion textureRegion = resourceManager.getTexture(slices.get(i));

			SliceView sliceView = new SliceView(slices.get(i), textureRegion);
			this.slices[i] = sliceView;
		}
	}

	@Override
	public void render(SpriteBatch batch, Camera viewport) {
		for (SliceView slice : slices) {
			slice.render(batch, viewport);
		}
	}

	@Override
	public void dispose() {
	}

	@Override
	public String toString() {
		return "SegmentView[" + segment.getPosition() + "]";
	}

}
