package se.dat255.bulletinferno.view.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import se.dat255.bulletinferno.view.Renderable;

/**
 * Renders a single slice
 */
public class SliceView implements Renderable {
	
	/** TextureRegion sharing the same Texture for the whole Segment */
	private final TextureRegion textureRegion;
	/** Position of this view */
	private final float positionX;
	
	/**
	 * Sets the required values in order to render the SliceView
	 * @param textureRegion The region of the Texture to use
	 * @param positionX The position of the Slice
	 */
	public SliceView(TextureRegion textureRegion, float positionX) {
		this.textureRegion = textureRegion;
		this.positionX = positionX;
	}

	@Override
	public void render(SpriteBatch batch) {
		batch.draw(textureRegion, positionX, 0);
	}

	@Override
	public void dispose() {}

}
