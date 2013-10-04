package se.dat255.bulletinferno.controller;

import com.badlogic.gdx.Gdx;

/**
 * Base implementation of a controller.<br>
 * Handles simple show and hide logics.
 */
public abstract class SimpleController implements Controller {
	/** If the screen this controller handles is hidden */
	protected boolean isHidden = true;
	/** If the screen this controller handles is paused */
	protected boolean isPaused = true;

	@Override
	public void show() {
		isHidden = false;
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
		isHidden = true;
	}

	@Override
	public void pause() {
		isPaused = true;
	}

	@Override
	public void resume() {
		isPaused = false;
	}

}
