package se.dat255.bulletinferno.view.gui;

import se.dat255.bulletinferno.model.loadout.SpecialEffect;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.view.RenderableGUI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Displays the special effect icon in-game that allows the player to use the special effect.
 */
public class SpecialIconView implements RenderableGUI {

	/** The screen coordinates where the icon should be drawn */
	private final static Vector2 POSITION = new Vector2(6.4f, -4.4f);
	/** The size of the icon */
	private final static Vector2 SIZE = new Vector2(1.5f, 1.5f);
	/** The color that the should be used to tint the icon when effect on cooldown. */
	private static final Color INACTIVE_COLOR = new Color(0.5f, 0.5f, 0.5f, 0.5f);

	/** The normal texture region for the special icon */
	private final TextureRegion textureRegion;

	/** A region used for cropping the special icon when loading */
	private final TextureRegion textureRegionLoading;

	/** The special effect instance that this view is showing, used for tracking cooldown */
	private SpecialEffect specialEffect;

	public SpecialIconView(TextureRegion textureRegion) {
		this.textureRegion = textureRegion;
		textureRegionLoading = new TextureRegion(textureRegion);
	}

	@Override
	public void render(SpriteBatch batch) {
		if (specialEffect != null && !specialEffect.isReady()) {
			float readyPercent = specialEffect.getReadyPercentage();

			// Draw a faded background
			Color lastColor = batch.getColor();
			batch.setColor(INACTIVE_COLOR);
			batch.draw(textureRegion, POSITION.x, POSITION.y, SIZE.x, SIZE.y);

			// Draw a cropped "slightly inactive" texture above
			Color slightlyInactive = lastColor.cpy();
			slightlyInactive.a = 0.7f;
			batch.setColor(slightlyInactive);
			int textureWidth = textureRegion.getRegionWidth();
			textureRegionLoading.setRegionWidth((int) (textureWidth * readyPercent));
			batch.draw(textureRegionLoading, POSITION.x, POSITION.y, SIZE.x * readyPercent, SIZE.y);

			// Reset the color
			batch.setColor(lastColor);
		} else {
			batch.draw(textureRegion, POSITION.x, POSITION.y, SIZE.x, SIZE.y);
		}
	}

	/** Disposed by HudView */
	@Override
	@Deprecated
	public void dispose(ResourceManager resourceManager) {
	}

	@Override
	public GuiEvent pressed(float x, float y) {
		return GuiEvent.SPECIAL_ABILITY;
	}

	@Override
	public Vector2 getPosition() {
		return POSITION.cpy();
	}

	@Override
	public Vector2 getSize() {
		return SIZE.cpy();
	}

	/**
	 * Sets the instance that this view is showing.
	 * 
	 * @param effect
	 *        the special effect instance to show.
	 */
	public void setSpecialEffect(SpecialEffect effect) {
		specialEffect = effect;
	}
}
