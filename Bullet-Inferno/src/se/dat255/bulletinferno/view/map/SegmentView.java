package se.dat255.bulletinferno.view.map;

import javax.management.RuntimeErrorException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import se.dat255.bulletinferno.model.ResourceManagerImpl;
import se.dat255.bulletinferno.model.map.Segment;
import se.dat255.bulletinferno.model.map.Slice;
import se.dat255.bulletinferno.model.map.SliceType;
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
	public SegmentView(ResourceManagerImpl resourceManager, Segment segment) {
		this.segment = segment;
		
		// TODO: Not hardcode
		// Load segment image into texture
		texture = resourceManager.getTexture("MAP_MOUNTAIN");
		
		Slice[] slices = segment.getSlices();
		int length = slices.length;
		this.slices = new SliceView[length];
		for(int i = 0; i < length; i++){
			float positionX = segment.getPosition() + i*20;
			
			// TODO: Determine where on Texture the slice image is
			// TODO: Create TextureRegion of that part and pass to constructor below
			TextureRegion textureRegion = null;
			if(slices[i].getIdentifier().equals("MOUNTAIN_1")){
				textureRegion = new TextureRegion(texture, 0, 0, 512, 256);
			} else if(slices[i].getIdentifier().equals("MOUNTAIN_2")){
				textureRegion = new TextureRegion(texture, 512, 0, 512, 256);
			} else if(slices[i].getIdentifier().equals("MOUNTAIN_3")){
				textureRegion = new TextureRegion(texture, 0, 256, 512, 256);
			} else if(slices[i].getIdentifier().equals("MOUNTAIN_4")){
				textureRegion = new TextureRegion(texture, 512, 256, 512, 256);
			} else if(slices[i].getIdentifier().equals("MOUNTAIN_5")){
				textureRegion = new TextureRegion(texture, 0, 512, 512, 256);
			} else if(slices[i].getIdentifier().equals("MOUNTAIN_6")){
				textureRegion = new TextureRegion(texture, 512, 512, 512, 256);
			} else if(slices[i].getIdentifier().equals("MOUNTAIN_7")){
				textureRegion = new TextureRegion(texture, 0, 0, 768, 256);
			} else if(slices[i].getIdentifier().equals("MOUNTAIN_8")){
				textureRegion = new TextureRegion(texture, 512, 768, 512, 256);
			}
			if(texture == null || textureRegion == null) throw new RuntimeException((texture == null) + " or " + (textureRegion == null));
			SliceView sliceView = new SliceView(textureRegion, positionX);
			this.slices[i] = sliceView;
		}
	}

	@Override
	public void render(SpriteBatch batch) {
		// TODO: Check if on screen, if it should be rendered
		for(SliceView slice : slices){
			if(true /* Should be rendered*/){
				slice.render(batch);
			}
		}
	}

	@Override
	public void dispose() {
		texture.dispose();
	}
	
	@Override
	public String toString() {
		return "SegmentView["+segment.getPosition()+"]";
	}
	
	

}
