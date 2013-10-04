package se.dat255.bulletinferno.controller;

import com.badlogic.gdx.Gdx;

public abstract class SimpleController implements Controller {
	protected boolean isHidden = true;
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
