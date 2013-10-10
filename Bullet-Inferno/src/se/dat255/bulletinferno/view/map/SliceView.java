package se.dat255.bulletinferno.view.map;

import se.dat255.bulletinferno.model.map.Slice;
import se.dat255.bulletinferno.view.Renderable;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Renders a single slice
 */
public class SliceView implements Renderable {
	
	/** TextureRegion sharing the same Texture for the whole Segment */
	private final TextureRegion textureRegion;

	/** */
	private Slice slice;
	
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
	public void render(SpriteBatch batch) {
		batch.draw(textureRegion, slice.getPosition().x, slice.getPosition().y, 
				slice.getWidth(), 9);
	}

	@Override
	public void dispose() {}

}
