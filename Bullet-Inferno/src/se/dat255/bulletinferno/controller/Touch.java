package se.dat255.bulletinferno.controller;

import se.dat255.bulletinferno.model.PlayerShipImpl;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;

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
	private Camera camera;

	/**
	 * Hard reference to the ship model. TODO: Probably shouldn't be directly
	 * accessed?
	 */
	private PlayerShipImpl ship;

	/**
	 * The finger index controlling the position of the ship.
	 */
	private int steeringFinger = -1;

	public Touch(final Camera camera, final PlayerShipImpl ship) {
		this.camera = camera;
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
		Vector3 touchVector = new Vector3(screenX, screenY, 0f);
		this.camera.unproject(touchVector);

		if (touchVector.x <= 0) {
			// Left half of the screen
			if (steeringFinger == -1) {
				steeringFinger = pointer;
			}
		} else {
			// Right half of the screen
		}

		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (pointer == steeringFinger) {
			steeringFinger = -1;
		}

		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// Unproject the touch location to the virtual screen.
		Vector3 touchVector = new Vector3(screenX, screenY, 0f);
		this.camera.unproject(touchVector);

		if (pointer == steeringFinger) {
			// Move ship
			this.ship.setY(touchVector.y);
		}

		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
