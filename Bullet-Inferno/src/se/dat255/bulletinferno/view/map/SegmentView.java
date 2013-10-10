package se.dat255.bulletinferno.view.map;

import java.util.List;

import se.dat255.bulletinferno.model.map.Segment;
import se.dat255.bulletinferno.model.map.Slice;
import se.dat255.bulletinferno.util.ManagedTexture;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.util.ResourceManagerImpl.TextureType;
import se.dat255.bulletinferno.view.Renderable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Handles the rendering of a Segment and the
 * Silces it contains
 */
public class SegmentView implements Renderable {
	private final ResourceManager resourceManager;

	/** The segment this view handles */
	public final Segment segment;
	/** A reference to the slices needed for this view to render */
	public final SliceView[] slices;
	/** The texture that is used for this Segment and its Slices */
	public final Texture texture;

	private final ManagedTexture mTexture;

	/** Creates a new view for the given segment */
	public SegmentView(ResourceManager resourceManager, Segment segment) {
		this.segment = segment;
		this.resourceManager = resourceManager;

		// TODO: Not hardcode
		// Load segment image into texture
		mTexture = resourceManager.getManagedTexture(TextureType.MAP_MOUNTAIN);
		texture = mTexture.getTexture();

		List<? extends Slice> slices = segment.getSlices();

		int length = slices.size();
		this.slices = new SliceView[length];
		int i = 0; // TODO : Fix iteration
		for (Slice slice : slices) {
			// TODO: Determine where on Texture the slice image is
			TextureRegion textureRegion = null;
			if (slice.getIdentifier().equals("MOUNTAIN_1")) {
				textureRegion = new TextureRegion(texture, 0, 0, 448, 252);
			} else if (slice.getIdentifier().equals("MOUNTAIN_2")) {
				textureRegion = new TextureRegion(texture, 0, 252, 448, 252);
			} else if (slice.getIdentifier().equals("MOUNTAIN_3")) {
				textureRegion = new TextureRegion(texture, 0, 504, 448, 252);
			} else if (slice.getIdentifier().equals("MOUNTAIN_4")) {
				textureRegion = new TextureRegion(texture, 0, 756, 448, 252);
			} else if (slice.getIdentifier().equals("MOUNTAIN_5")) {
				textureRegion = new TextureRegion(texture, 448, 0, 448, 252);
			} else if (slice.getIdentifier().equals("MOUNTAIN_6")) {
				textureRegion = new TextureRegion(texture, 448, 252, 448, 252);
			} else if (slice.getIdentifier().equals("MOUNTAIN_7")) {
				textureRegion = new TextureRegion(texture, 448, 504, 448, 252);
			} else if (slice.getIdentifier().equals("MOUNTAIN_8")) {
				textureRegion = new TextureRegion(texture, 448, 756, 448, 252);
			}
			if (texture == null || textureRegion == null) {
				throw new RuntimeException((texture == null) + " or " + (textureRegion == null));
			}

			SliceView sliceView = new SliceView(slice, textureRegion);
			this.slices[i] = sliceView;
			i++;
		}
	}

	@Override
	public void render(SpriteBatch batch) {
		// TODO: Check if on screen, if it should be rendered
		for (SliceView slice : slices) {
			if (true /* Should be rendered */) {
				slice.render(batch);
			}
		}
	}

	@Override
	public void dispose() {
		mTexture.dispose(resourceManager);
	}

	@Override
	public String toString() {
		return "SegmentView[" + segment.getPosition() + "]";
	}

}
