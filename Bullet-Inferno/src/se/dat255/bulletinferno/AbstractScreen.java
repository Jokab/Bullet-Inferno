package se.dat255.bulletinferno;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;

public abstract class AbstractScreen implements Screen {
	protected boolean isHidden = true;
	protected boolean isPaused = true;
	
	
	@Override
	public void render(float delta) {
		// Clear the screen every frame
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	}

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
