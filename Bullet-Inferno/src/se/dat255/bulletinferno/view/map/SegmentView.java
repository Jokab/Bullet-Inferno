package se.dat255.bulletinferno.view.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import se.dat255.bulletinferno.model.map.Segment;
import se.dat255.bulletinferno.model.map.Slice;
import se.dat255.bulletinferno.view.Renderable;

/**
 * Handles the rendering of a Segment and the
 *  Silces it contains
 */
public class SegmentView implements Renderable {
	/** The segment this view handles */
	public final Segment segment;
	/** A reference to the slices needed for this view to render */
	public final SliceView[] slices;
	/** The texture that is used for this Segment and it's Slices */
	public final Texture texture;
	
	/** Creates a new view for the given segment */
	public SegmentView(Segment segment) {
		this.segment = segment;
		float positionX = segment.getPosition();
		
		// TODO: Get type of segment
		// TODO: Load segment image into texture below
		texture = null;
		
		Slice[] slices = segment.getSlices();
		int length = slices.length;
		this.slices = new SliceView[length];
		for(int i = 0; i < length; i++){
			// TODO: Get type of slice
			// TODO: Determine where on Texture the slice image is
			// TODO: Create TextureRegion of that part and pass to constructor below
			TextureRegion textureRegion = null;
			SliceView sliceView = new SliceView(textureRegion, positionX);
			this.slices[i] = sliceView;
		}
	}

	@Override
	public void render(SpriteBatch batch) {
		// TODO: Check if on screen, if it should be rendered
		for(SliceView slice : slices){
			if(true /* Should be rendered*/)
				slice.render(batch);
		}
	}

	@Override
	public void dispose() {
		texture.dispose();
	}
	
	

}
