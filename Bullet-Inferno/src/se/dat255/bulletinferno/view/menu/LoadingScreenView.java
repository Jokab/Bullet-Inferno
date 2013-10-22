package se.dat255.bulletinferno.view.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.Disposable;

/**
 * Class for rendering of the loading screen.
 */
public class LoadingScreenView extends WidgetGroup implements Disposable {

	private static final int LOAD_BAR_MAX_WIDTH = 501;
	private static final int LOAD_BAR_BOTTOM_MARGIN = 30;

	/** Text format to be displayed while the loading is in progress */
	private final String progressLabelText = "Loading... %d%%";
	/** Text to be displayed when the loading is finished */
	private final String onFinishedLabelText = "Touch to Start!";

	private float percent;

	// Should be disposed GUI elements
	private final Texture screenBgTexture;
	private final Texture loadBarTexture;
	private final Texture loadBarBgTexture;
	private final BitmapFont progressLabelFont;

	// GUI elements
	private final Image screenBg;
	private final Label progressLabel;
	private final Image loadBarBg;
	private final Image loadBar;

	/**
	 * Creates a new LoadingScreenView.
	 * 
	 * <p>
	 * <b>Note:</b> This will load textures directly, and it is therefore important to remember to
	 * dispose this view!
	 * </p>
	 */
	public LoadingScreenView() {
		// Background
		screenBgTexture = new Texture(Gdx.files.internal("data/loadingScreenBg.png"));
		screenBg = new Image(screenBgTexture);
		screenBg.setFillParent(true);
		addActor(screenBg);

		// The loading bar background
		loadBarBgTexture = new Texture(Gdx.files.internal("data/loadingBarBg.png"));
		loadBarBgTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		loadBarBg = new Image(loadBarBgTexture);
		loadBarBg.setY(LOAD_BAR_BOTTOM_MARGIN);
		loadBarBg.setWidth(LOAD_BAR_MAX_WIDTH);
		loadBarBg.setX(1280 / 2 - LOAD_BAR_MAX_WIDTH / 2);
		addActor(loadBarBg);

		// The loading bar
		loadBarTexture = new Texture(Gdx.files.internal("data/loadingBar.png"));
		loadBarTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		loadBar = new Image(loadBarTexture);
		loadBar.setY(LOAD_BAR_BOTTOM_MARGIN);
		loadBar.setX(loadBarBg.getX());
		loadBar.setWidth(LOAD_BAR_MAX_WIDTH);
		addActor(loadBar);

		// Progress label
		progressLabelFont = new BitmapFont(Gdx.files.internal("data/fonts/arial_bold_32.fnt"),
				false);
		progressLabelFont.getRegion().getTexture()
				.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		LabelStyle ls = new LabelStyle(progressLabelFont, Color.BLACK);
		progressLabel = new Label("", ls);
		progressLabel.setY(65);
		addActor(progressLabel);
	}

	/**
	 * Sets the displayed loadProgress.
	 * 
	 * @param loadProgress
	 *        the current load progress, in percentage from 0 to 1.
	 */
	public void setLoadProgress(float loadProgress) {
		percent = Interpolation.linear.apply(percent, loadProgress, 0.1f);

		progressLabel.setText(String.format(progressLabelText, (int) Math.floor(percent * 100)));
		centerProgressLabel();

		// The width scaling is applied from the middle, resulting in non-transparent parts of the
		// loading bar overlapping the loading bar background. The below is an attempt to counter
		// that.
		float xPercent = 1 - Math.min((float) (percent / 0.5), 1);
		loadBar.setX(loadBarBg.getX() + 4 * xPercent);

		loadBar.setScaleX(percent);
		loadBar.invalidate();

	}

	/**
	 * Sets the loading to finished and displays the finished loading message.
	 */
	public void loadingFinished() {
		if (!progressLabel.getText().equals(onFinishedLabelText)) {
			progressLabel.setText(onFinishedLabelText);
			centerProgressLabel();

			loadBar.setScaleX(1);
			loadBar.invalidate();
		}
	}

	/**
	 * Helper method for centering the progress label when it changes text.
	 */
	private void centerProgressLabel() {
		progressLabel.setX(getWidth() / 2 - progressLabel.getPrefWidth() / 2);
	}

	@Override
	public void dispose() {
		remove();
		screenBgTexture.dispose();
		loadBarTexture.dispose();
		loadBarBgTexture.dispose();
		progressLabelFont.dispose();
	}

}
