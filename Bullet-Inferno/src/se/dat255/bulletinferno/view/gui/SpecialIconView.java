package se.dat255.bulletinferno.view.gui;

import se.dat255.bulletinferno.model.loadout.SpecialEffect;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.view.RenderableGUI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Displays the pause icon in-game that allows the player to pause the game.
 */
public class SpecialIconView implements RenderableGUI {

	private final TextureRegion textureRegion;
	private final TextureRegion textureRegionLoading;
	private final Vector2 position = new Vector2(6.4f, -4.4f);
	private final Vector2 size = new Vector2(1.5f, 1.5f);
	private SpecialEffect specialEffect;

	
	public SpecialIconView(TextureRegion textureRegion) {
		this.textureRegion = textureRegion;
		this.textureRegionLoading = new TextureRegion(textureRegion);
	}

	@Override
	public void render(SpriteBatch batch) {
		
		if (specialEffect != null && !specialEffect.isReady()) {
			float readyPercent = Math.round(specialEffect.getReadyPercentage() * 1000)/1000f;
			
			// Draw a faded background
			Color lastColor = batch.getColor();
			batch.setColor(new Color(0.5f, 0.5f, 0.5f, 0.5f));
			batch.draw(textureRegion, position.x, position.y, size.x, size.y);
			batch.setColor(lastColor);
			
			// Draw a cropped "active" texture above
			int textureWidth = textureRegion.getRegionWidth();
			textureRegionLoading.setRegionWidth((int) (textureWidth * readyPercent));
			batch.draw(textureRegionLoading, position.x, position.y, size.x * readyPercent, size.y);
		} else {
			batch.draw(textureRegion, position.x, position.y, size.x, size.y);
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
		return position;
	}

	@Override
	public Vector2 getSize() {
		return size;
	}

	public void setSpecialEffect(SpecialEffect effect) {
		this.specialEffect = effect;
	}
}
