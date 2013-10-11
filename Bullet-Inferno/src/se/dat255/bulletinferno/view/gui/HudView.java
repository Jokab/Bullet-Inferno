package se.dat255.bulletinferno.view.gui;

import java.util.HashSet;
import java.util.Set;

import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.util.ResourceManagerImpl.TextureType;
import se.dat255.bulletinferno.view.Renderable;
import se.dat255.bulletinferno.view.RenderableGUI;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Displays the score of the game
 */
public class HudView implements Renderable {
	
	/** Reference to the resourcemanager */
	private final ResourceManager resourceManager;
	
	/** The different regions for life statuses */
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
	/** The amount of heat regions to display; 0.0f -> 1.0f */
	private float heatValue = 0.5f;
	
	private final RenderableGUI pauseScreen, pauseButton;
	
	/**
	 * Loads the initial image and sets the regions
	 * @param resourceManager The manager that holds the assets
	 */
	public HudView(ResourceManager resourceManager) {
		this.resourceManager = resourceManager;
		Texture t = resourceManager.getTexture(TextureType.HUD_TEXTURE);
		lifeRegion = new TextureRegion(t, 9, 10, 1, 20); // 9 -> 158
		heatRegions = new TextureRegion[]{
				new TextureRegion(t, 167, 29, 20, 38), // No heat
				new TextureRegion(t, 190, 30, 20, 38), // Overheat
				new TextureRegion(t, 242, 3, 270, 86), // Heat background
		};
		numberRegions = new TextureRegion[]{
				new TextureRegion(t, 13, 90, 30, 65), // 0
				new TextureRegion(t, 60, 90, 20, 65), // 1
				new TextureRegion(t, 92, 90, 30, 65), // 2
				new TextureRegion(t, 139, 90, 30, 65), // 3
				new TextureRegion(t, 184, 90, 30, 65), // 4
				new TextureRegion(t, 234, 90, 30, 65), // 5
				new TextureRegion(t, 280, 90, 30, 65), // 6
				new TextureRegion(t, 324, 90, 30, 65), // 7
				new TextureRegion(t, 372, 90, 30, 65), // 8
				new TextureRegion(t, 420, 90, 30, 65), // 9
		};
		pauseScreen = new PauseScreenView(resourceManager);
		pauseButton = new PauseIconView(new TextureRegion(t, 11, 162, 66, 64));
		hudRegions.add(pauseButton);
	}
	
	/** Sets the backing array of the graphic score */
	public void setScore(int score){
		for(int i = 9; i >= 0; i--){
			this.scoreArray[i] = score % 10;
			score /= 10;
		}
		for(activeScoreNumbers = 0; activeScoreNumbers < 10 && scoreArray[activeScoreNumbers] == 0; 
				activeScoreNumbers++)
			;
		if(activeScoreNumbers == 0)
			activeScoreNumbers++;
		else
			activeScoreNumbers = 10 - activeScoreNumbers;
	}
	
	/** Sets the value of life between 0 and 1 */
	public void setLife(float life){
		lifeRegion.setRegionX((int)(9 + 149f * life));
		lifeRegion.setRegionWidth(1);
		lifeWidth = 3f * life;
	}
	
	/** Displays pause screen */
	public void pause(){
		hudRegions.remove(pauseButton);
		hudRegions.add(pauseScreen);
	}
	
	/** Removes pause screen */
	public void unpause(){
		hudRegions.remove(pauseScreen);
		hudRegions.add(pauseButton);
	}
	
	public void gameOver(){
		RenderableGUI gameOver = new GameoverScreenView(resourceManager);
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
		batch.draw(lifeRegion, -1.5f, 4f, lifeWidth, 0.5f);
		for(int i = 10 - activeScoreNumbers, j = 0; i < 10; i++, j++){
			batch.draw(numberRegions[scoreArray[i]], j*0.4f-8, 4f, 0.5f, 0.5f);
		}
		
		for(RenderableGUI gui : hudRegions){
			gui.render(batch);
		}
	}

	@Override
	public void dispose() {
		pauseScreen.dispose(resourceManager);
	}
}
