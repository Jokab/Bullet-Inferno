package se.dat255.bulletinferno.view.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import se.dat255.bulletinferno.util.ManagedTexture;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.util.ResourceManagerImpl.TextureType;
import se.dat255.bulletinferno.view.Renderable;

/**
 * Displays the score of the game
 */
public class ScoreView implements Renderable {
	
	/** Reference to the resourcemanager */
	private final ResourceManager resourceManager;
	
	/** The texture that holds the numbers */
	private final ManagedTexture numberImage;
	/** The regions for the different numbers */
	private final TextureRegion[] numberRegions;
	/** The backing array of the score */
	private final int[] scoreArray = new int[10];
	
	/**
	 * Loads the initial images of the score view
	 * @param resourceManager The manager that holds the assets
	 */
	public ScoreView(ResourceManager resourceManager) {
		this.resourceManager = resourceManager;
		numberImage = resourceManager.getManagedTexture(TextureType.HUD_NUMBERS);
		Texture t = numberImage.getTexture();
		numberRegions = new TextureRegion[]{
			new TextureRegion(t, 19, 20, 31, 68),
			new TextureRegion(t, 64, 20, 17, 68),
			new TextureRegion(t, 97, 20, 31, 68),
			new TextureRegion(t, 144, 20, 33, 68),
			new TextureRegion(t, 190, 20, 33, 68),
			new TextureRegion(t, 19, 111, 31, 68),
			new TextureRegion(t, 64, 111, 33, 69),
			new TextureRegion(t, 112, 111, 33, 69),
			new TextureRegion(t, 157, 111, 32, 69),
			new TextureRegion(t, 207, 111, 31, 69),
		};
	}
	
	/** Sets the backing array of the graphic score */
	public void setScore(int score){
		for(int i = 9; i >= 0; i--){
			this.scoreArray[i] = score % 10;
			score /= 10;
		}
	}

	@Override
	public void render(SpriteBatch batch) {
		// First run i until not 0, unless last number, then 0 is ok
		for(int i = 0; i < 10; i++){
			batch.draw(numberRegions[scoreArray[i]], i*0.4f-8, 4f, 0.5f, 0.5f);
		}
	}

	@Override
	public void dispose() {
		numberImage.dispose(resourceManager);
	}
}
