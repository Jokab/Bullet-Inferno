package se.dat255.bulletinferno.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.Disposable;

/**
 * Class for rendering of the loading screen.
 */
public class LoadingScreenView extends WidgetGroup implements Disposable {

	/** Text format to be displayed while the loading is in progress */
	private final String progressLabelText = "Loading... %d%%";
	/** Text to be displayed when the loading is finished */
	private final String onFinishedLabelText = "Touch to Start!";

	// Should be disposed GUI elements
	private Texture screenBgTexture;
	private BitmapFont progressLabelFont;

	// GUI elements
	private Image screenBg;
	private Label progressLabel;

	/**
	 * Creates a new LoadingScreenView.
	 * 
	 * <p>
	 * <b>Note:</b> This will load textures directly, and it is therefore important to remember to
	 * dispose this view!
	 * </p>
	 */
	public LoadingScreenView() {
		screenBgTexture = new Texture("data/loadingScreenBg.png");
		screenBg = new Image(screenBgTexture);
		screenBg.setFillParent(true);
		addActor(screenBg);

		progressLabelFont = new BitmapFont();
		progressLabelFont.scale(3);
		LabelStyle ls = new LabelStyle(progressLabelFont, Color.BLACK);

		progressLabel = new Label("", ls);
		addActor(progressLabel);
	}

	/**
	 * Sets the displayed loadProgress.
	 * 
	 * @param loadProgress
	 *        the current load progress, in percentage from 0 to 1.
	 */
	public void setLoadProgress(float loadProgress) {
		int percLoaded = (int) Math.floor(loadProgress * 100);
		progressLabel.setText(String.format(progressLabelText, percLoaded));
		centerProgressLabel();
	}

	/**
	 * Sets the loading to finished and displays the finished loading message.
	 */
	public void loadingFinished() {
		if (!progressLabel.getText().equals(onFinishedLabelText)) {
			progressLabel.setText(onFinishedLabelText);
			centerProgressLabel();
		}
	}

	/**
	 * Helper method for centering the progress label when it changes text.
	 */
	private void centerProgressLabel() {
		progressLabel.setPosition((getWidth() / 2) - (progressLabel.getPrefWidth() / 2),
				progressLabel.getPrefHeight() + 10);
	}

	@Override
	public void dispose() {
		remove();
		screenBgTexture.dispose();
		progressLabelFont.dispose();
	}

}
