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
	private final Slice slice;

	private final Vector3 minBounds = new Vector3(0, 0, 0);
	private final Vector3 maxBounds = new Vector3(0, 0, 0);
	private final BoundingBox bounds = new BoundingBox(minBounds, maxBounds);

	/**
	 * Sets the required values in order to render the SliceView
	 * 
	 * @param slice
	 *        the slice to be rendered by this view
	 * @param textureRegion
	 *        The region of the Texture to use
	 */
	public SliceView(Slice slice, TextureRegion textureRegion) {
		this.slice = slice;
		this.textureRegion = textureRegion;
	}

	@Override
	public void render(SpriteBatch batch, Camera viewport) {
		minBounds.x = slice.getPosition().x;
		minBounds.y = slice.getPosition().y;
		maxBounds.x = minBounds.x + slice.getWidth();
		maxBounds.y = minBounds.y;
		bounds.set(minBounds, maxBounds);

		if (viewport.frustum.boundsInFrustum(bounds)) {
			batch.draw(textureRegion, slice.getPosition().x, slice.getPosition().y,
					slice.getWidth(), 9);
		}
	}

	@Override
	public void dispose() {
	}

}
