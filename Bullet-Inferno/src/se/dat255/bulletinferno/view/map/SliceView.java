package se.dat255.bulletinferno.view.map;

import se.dat255.bulletinferno.model.map.Slice;
import se.dat255.bulletinferno.view.Renderable;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

/**
 * Renders a single slice
 */
public class SliceView implements Renderable {
	
	/** TextureRegion sharing the same Texture for the whole Segment */
	private final TextureRegion textureRegion;

	/** */
	private Slice slice;
	
	private Vector3 minPoint = new Vector3();
	private Vector3 maxPoint = new Vector3();
	
	/**
	 * Sets the required values in order to render the SliceView
	 * @param slice the slice to be rendered by this view
	 * @param textureRegion The region of the Texture to use
	 */
	public SliceView(Slice slice, TextureRegion textureRegion) {
		this.slice = slice;
		this.textureRegion = textureRegion;
	}

	@Override
	public void render(SpriteBatch batch, Camera viewport) {
		minPoint.x = slice.getPosition().x;
		maxPoint.x = slice.getPosition().x + slice.getWidth();
		
		if(viewport.frustum.pointInFrustum(minPoint) || viewport.frustum.pointInFrustum(maxPoint)){
			batch.draw(textureRegion, slice.getPosition().x, slice.getPosition().y, 
					slice.getWidth(), 9);
		}
	}

	@Override
	public void dispose() {}

}
