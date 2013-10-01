package se.dat255.bulletinferno.controller;

import se.dat255.bulletinferno.Graphics;
import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.PlayerShip;
import se.dat255.bulletinferno.model.weapon.WeaponData;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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

	private final int UPKEY = 51;
	private final int DOWNKEY = 47;
	private final int FIREKEY = 62;

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
	private final int steeringFinger = -1;

	private final Game game;

	public Touch(final Game game, final Graphics graphics, final PlayerShip ship) {
		this.game = game;
		this.graphics = graphics;
		this.ship = ship;
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == UPKEY) {
			ship.moveTo(Graphics.GAME_HEIGHT);
		}
		if (keycode == DOWNKEY) {
			ship.moveTo(0f);
		}
		if (keycode == FIREKEY) {
			ship.fireWeapon();
		}
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == UPKEY && ship.getMovePos() > ship.getPosition().y) {
			ship.stopMovement();
		}
		if (keycode == DOWNKEY && ship.getMovePos() < ship.getPosition().y) {
			ship.stopMovement();
		}
		if (keycode == Keys.NUM_1) {
			ship.setWeapon(WeaponData.FAST.getPlayerWeaponForGame(game));
			System.out
					.println("Switched to fast weapon. Delay: " + WeaponData.FAST.getReloadTime());
		}
		if (keycode == Keys.NUM_2) {
			ship.setWeapon(WeaponData.SLOW.getPlayerWeaponForGame(game));
			System.out
					.println("Switched to fast weapon. Delay: " + WeaponData.SLOW.getReloadTime());
		}
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
			// Move ship by giving the touch coordinate to the moveTo-method
			touchDragged(screenX, screenY, pointer);
			// }
		} else {
			// Right half of the screen
			ship.fireWeapon();
		}

		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		ship.stopMovement();
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// Unproject the touch location to the virtual screen.
		Vector2 touchVector = new Vector2(screenX, screenY);
		Graphics.screenToWorld(touchVector);

		if (touchVector.x <= ship.getPosition().x + 8f) {
			// Left half of the screen
			// Move ship by giving the touch coordinate to the moveTo-method
			ship.stopMovement();
			if (touchVector.y > ship.getPosition().y + 0.1f) {
				ship.moveTo(touchVector.y);
			} else if (touchVector.y < ship.getPosition().y - 0.1f) {
				ship.moveTo(touchVector.y);
			} else {
				ship.stopMovement();
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

		// Move ship by giving the touch coordinate to the moveTo-method
		ship.stopMovement();
		if (touchVector.y > ship.getPosition().y + 0.1f) {
			ship.moveTo(touchVector.y);
		} else if (touchVector.y < ship.getPosition().y - 0.1f) {
			ship.moveTo(touchVector.y);
		} else {
			ship.stopMovement();
		}

		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
