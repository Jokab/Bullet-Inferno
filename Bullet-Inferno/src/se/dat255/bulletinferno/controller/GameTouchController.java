package se.dat255.bulletinferno.controller;

import se.dat255.bulletinferno.model.PlayerShip;
import se.dat255.bulletinferno.model.SpecialEffect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

/**
 * The main touch controller<br>
 * Information on development of LibGDX input handling:<br>
 * https://code.google.com/p/libgdx/wiki/InputEvent
 */
public class GameTouchController implements InputProcessor {

	private final int UPKEY = 51;
	private final int DOWNKEY = 47;
	private final int FIREKEY = 62;

	/** Describes the sense of the point device */
	private static final float SENSE_SCALE = 1f;
	
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

	/** The finger index controlling the position of the ship. */
	private int steeringFinger = -1;
	/** The origin of touch down finger controlling the ship*/
	private Vector2 touchOrigin = new Vector2();

	public GameTouchController(final Graphics graphics, final PlayerShip ship) {
		this.graphics = graphics;
		this.ship = ship;
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == UPKEY) {
			// ship.moveTo(Graphics.GAME_HEIGHT);
		}
		if (keycode == DOWNKEY) {
			// ship.moveTo(0f);
		}
		if (keycode == FIREKEY) {
			ship.fireWeapon();
		}
		if (keycode == Keys.G) {
			SpecialEffect effect = ship.getLoadout().getSpecialAbility().getEffect();
			if (effect != null) {
				effect.activate(ship);
			}
		}
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.X) {
			ship.takeDamage(10);
			System.out.println("Player health: " + ship.getHealth());
		}

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
		// Check if GUI input was to be handled TODO: The second division can be made in prehand
		float guiX = (float) screenX / (Gdx.graphics.getWidth() / 16);
		float guiY = (float) screenY / (Gdx.graphics.getHeight() / 9);
		guiY = 9 - guiY;
		if (graphics.guiInput(guiX, guiY)) {
			return true;
		}
		
		// Otherwise it's world input
		// Unproject the touch location to the virtual screen.
		Vector2 touchVector = new Vector2(screenX, screenY);
		Graphics.screenToWorld(touchVector);

		Gdx.app.log("Touch", "Down id = " + pointer);

		if (touchVector.x <= ship.getPosition().x + 8f) {
			// Left half of the screen
			// Set the touchOrigin vector to know where the touch originated from
			touchOrigin.set(touchVector);
			steeringFinger = pointer;
			//touchDragged(screenX, screenY, pointer);
		} else {
			// Right half of the screen
			ship.fireWeapon();
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(pointer == steeringFinger) {
			touchOrigin.set(new Vector2());
			steeringFinger = -1;
		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if(pointer == steeringFinger) {
			// Unproject the touch location to the virtual screen.
			Vector2 touchVector = new Vector2(screenX, screenY);
			Graphics.screenToWorld(touchVector);
			if (touchVector.x <= ship.getPosition().x + 8f) {
				ship.moveY(touchVector.y - touchOrigin.y, SENSE_SCALE);
				touchOrigin.set(touchVector);
			}
		}
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// Same as touchDragged but for desktop
		// Unproject the touch location to the virtual screen.
		Vector2 touchVector = new Vector2(screenX, screenY);
		Graphics.screenToWorld(touchVector);

		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
