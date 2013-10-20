package se.dat255.bulletinferno.view.gui;

import java.util.HashSet;
import java.util.Set;

import se.dat255.bulletinferno.controller.GameController;
import se.dat255.bulletinferno.model.loadout.SpecialEffect;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.util.TextureDefinitionImpl;
import se.dat255.bulletinferno.view.Renderable;
import se.dat255.bulletinferno.view.RenderableGUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Main view of all the HUD elements in the game
 */
public class HudView implements Renderable {

	/** The rate of alpha value that the the life bar should fade out at when life is full */
	private static final float LIFE_ALPHA_FADEOUT_PER_SECOND = 1.5f;

	/** The minimum value of alpha the health bar should have */
	private static final float MIN_LIFE_BAR_ALPHA = 0.4f;

	/** Reference to the resourcemanager */
	private final ResourceManager resourceManager;

	/** The region for life background */
	private final TextureRegion lifeBackground;
	/** The region for life statuses */
	private final TextureRegion lifeRegion;
	/** The different regions for weapon heat statuses */
	private final TextureRegion[] heatRegions;
	/** The different regions for displaying numbers in game */
	private final TextureRegion[] numberRegions;
	/** The regions for the different HUD elements */
	private final Set<RenderableGUI> hudRegions = new HashSet<RenderableGUI>();

	/** The backing array of the score */
	private final int[] scoreArray = new int[10];
	/** Number of active score numbers to draw */
	private int activeScoreNumbers = 1;
	/** The width of the health bar */
	private float lifeWidth;
	/** The current life points of the ship (between 0 and 1) */
	private float life;
	/** The interpolation coefficient value between 0 and 1 for fading alpha of the life bar */
	private float lifeFadeoutTime;
	/** The amount of heat regions to display; 0.0f -> 1.0f */
	private final float heatValue = 0.5f;

	/** Reference to the paus view for easily showing/hiding them */
	private final RenderableGUI pauseScreen, pauseButton;

	/** Button for activating the special ability */
	private final SpecialIconView specialButton;

	/**
	 * Loads the initial image and sets the regions
	 * 
	 * @param resourceManager
	 *        The manager that holds the assets
	 * @param gameController 
	 */
	public HudView(ResourceManager resourceManager, GameController gameController) {
		this.resourceManager = resourceManager;
		Texture hudTexture = resourceManager.getTexture(TextureDefinitionImpl.HUD_TEXTURE)
				.getTexture();

		lifeBackground = new TextureRegion(hudTexture, 2, 35, 162, 33);
		lifeRegion = new TextureRegion(hudTexture, 9, 10, 1, 20); // 9 -> 158
		heatRegions = new TextureRegion[] {
				new TextureRegion(hudTexture, 167, 29, 20, 38), // No heat
				new TextureRegion(hudTexture, 190, 30, 20, 38), // Overheat
				new TextureRegion(hudTexture, 242, 3, 270, 86), // Heat background
		};
		numberRegions = new TextureRegion[] {
				new TextureRegion(hudTexture, 13, 90, 30, 65), // 0
				new TextureRegion(hudTexture, 60, 90, 20, 65), // 1
				new TextureRegion(hudTexture, 92, 90, 30, 65), // 2
				new TextureRegion(hudTexture, 139, 90, 30, 65), // 3
				new TextureRegion(hudTexture, 184, 90, 30, 65), // 4
				new TextureRegion(hudTexture, 234, 90, 30, 65), // 5
				new TextureRegion(hudTexture, 280, 90, 30, 65), // 6
				new TextureRegion(hudTexture, 324, 90, 30, 65), // 7
				new TextureRegion(hudTexture, 372, 90, 30, 65), // 8
				new TextureRegion(hudTexture, 420, 90, 30, 65), // 9
		};
		pauseScreen = new PauseScreenView(resourceManager);
		pauseButton = new PauseIconView(new TextureRegion(hudTexture, 6, 158, 74, 85));
		hudRegions.add(pauseButton);
		specialButton = new SpecialIconView(new TextureRegion(hudTexture, 105, 162, 89, 89));
		hudRegions.add(specialButton);
	}

	/** Sets the backing array of the graphic score */
	public void setScore(int score) {
		for (int i = 9; i >= 0; i--) {
			scoreArray[i] = score % 10;
			score /= 10;
		}
		for (activeScoreNumbers = 0; activeScoreNumbers < 10 && scoreArray[activeScoreNumbers] == 0; activeScoreNumbers++) {
			;
		}
		if (activeScoreNumbers == 0) {
			activeScoreNumbers++;
		} else {
			activeScoreNumbers = 10 - activeScoreNumbers;
		}
	}

	/** Sets the value of life between 0 and 1 */
	public void setLife(float life) {
		this.life = life;
		lifeRegion.setRegionX((int) (9 + 149f * life));
		lifeRegion.setRegionWidth(1);
		lifeWidth = 3f * life;
	}

	/** Displays pause screen */
	public void pause() {
		hudRegions.remove(pauseButton);
		hudRegions.remove(specialButton);
		hudRegions.add(pauseScreen);
	}

	/** Removes pause screen */
	public void unpause() {
		hudRegions.remove(pauseScreen);
		hudRegions.add(pauseButton);
		hudRegions.add(specialButton);
	}

	/** Shows the game over screen **/
	public void gameOver(int score) {
		RenderableGUI gameOver = new GameoverScreenView(resourceManager, score);
		hudRegions.clear();
		hudRegions.add(gameOver);
	}

	/**
	 * Checks if a GUI element was activated, also calling that
	 * element.
	 * 
	 * @param x
	 *        The X position of the GUI
	 * @param y
	 *        The Y position of the GUI
	 * @return If a GUI element was activated
	 */
	public GuiEvent guiInput(float x, float y) {
		for (RenderableGUI gui : hudRegions) {
			Vector2 position = gui.getPosition();
			Vector2 size = gui.getSize();
			if (x > position.x && y > position.y && x < position.x + size.x
					&& y < position.y + size.y) {
				return gui.pressed(x, y);
			}
		}
		return null;
	}

	@Override
	public void render(SpriteBatch batch, Camera viewport) {
		if (life >= 1) {
			lifeFadeoutTime += LIFE_ALPHA_FADEOUT_PER_SECOND * Gdx.graphics.getDeltaTime();
			lifeFadeoutTime = Math.min(1, lifeFadeoutTime);
			Color prevColor = batch.getColor();
			batch.setColor(Color.WHITE.cpy().lerp(
					1f, 1f, 1f, MIN_LIFE_BAR_ALPHA, lifeFadeoutTime));
			batch.draw(lifeBackground, -1.6f, 3.9f, 3.2f, 0.7f);
			batch.draw(lifeRegion, -1.5f, 4f, lifeWidth, 0.5f);
			batch.setColor(prevColor);
		} else {
			lifeFadeoutTime = 0;
			batch.draw(lifeBackground, -1.6f, 3.9f, 3.2f, 0.7f);
			batch.draw(lifeRegion, -1.5f, 4f, lifeWidth, 0.5f);
		}

		for (int i = 10 - activeScoreNumbers, j = 0; i < 10; i++, j++) {
			batch.draw(numberRegions[scoreArray[i]], j * 0.4f - 8, 4f, 0.5f, 0.5f);
		}

		for (RenderableGUI gui : hudRegions) {
			gui.render(batch);
		}
		
		specialButton.render(batch);
	}

	@Override
	public void dispose() {
		pauseScreen.dispose(resourceManager);
	}

	public void onSpecialEffect(SpecialEffect effect) {
		specialButton.setSpecialEffect(effect);
	}
}
