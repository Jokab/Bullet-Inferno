package se.dat255.bulletinferno;

import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;


public class LoadoutScreen extends AbstractScreen {
	private final MyGame myGame;

	public LoadoutScreen(MyGame myGame) {
		this.myGame = myGame;
	}

	@Override
	public void show() {
		Timer.schedule(new Task() {
			@Override
			public void run() {
				myGame.setScreen(myGame.gameScreen);
			}
		}, 2);
		
	}

	@Override
	public void resize(int width, int height) {
	}
}
