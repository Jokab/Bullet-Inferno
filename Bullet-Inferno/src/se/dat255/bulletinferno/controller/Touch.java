package se.dat255.bulletinferno.controller;

import se.dat255.bulletinferno.Graphics;
import se.dat255.bulletinferno.model.PlayerShip;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

/**
 * The main touch controller More info:
 * https://code.google.com/p/libgdx/wiki/InputEvent
 * 
 * @author Marc Jamot
 * @version 1.0
 * @since 13-09-12
 */
public class Touch implements InputProcessor {

	/**
	 * The game camera. This is needed to unproject x/y values to the virtual
	 * screen size.
	 */
	private final Graphics graphics;

	/**
	 * Hard reference to the ship model. TODO: Probably shouldn't be directly
	 * accessed?
	 */
	private final PlayerShip ship;

	/**
	 * The finger index controlling the position of the ship.
	 */
	private int steeringFinger = -1;

	public Touch(final Graphics graphics, final PlayerShip ship) {
		this.graphics = graphics;
		this.ship = ship;
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		
		// Unproject the touch location to the virtual screen.
		Vector2 touchVector = new Vector2(screenX, screenY);
		Graphics.screenToWorld(touchVector);

		Gdx.app.log("Touch", "Down id = " + pointer);

		if (touchVector.x <= Graphics.GAME_WIDTH / 2) {
			// Left half of the screen
			if (steeringFinger == -1) {
				Gdx.app.log("Touch", "Steering set to " + pointer);
				steeringFinger = pointer;
			}
		} else {
			// Right half of the screen
			ship.fireWeapon();
		}
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (pointer == steeringFinger) {
			Gdx.app.log("Touch", "Steering finger unset " + pointer);
			steeringFinger = -1;
		}

		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// Unproject the touch location to the virtual screen.
		Vector2 touchVector = new Vector2(screenX, screenY);
		graphics.screenToWorld(touchVector);

		if (pointer == steeringFinger) {
			// Move ship
			ship.setPosition(new Vector2(0, touchVector.y));
		}

		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// Unproject the touch location to the virtual screen.
		Vector2 touchVector = new Vector2(screenX, screenY);
		graphics.screenToWorld(touchVector);
		// Move ship
		ship.setPosition(new Vector2(0, touchVector.y));
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
