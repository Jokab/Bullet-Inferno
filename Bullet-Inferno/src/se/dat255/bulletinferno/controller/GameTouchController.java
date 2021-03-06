package se.dat255.bulletinferno.controller;

import java.util.LinkedList;
import java.util.List;

import se.dat255.bulletinferno.model.entity.PlayerShip;
import se.dat255.bulletinferno.view.gui.GuiEvent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

/**
 * The main touch controller<br>
 * Information on development of LibGDX input handling:<br>
 * https://code.google.com/p/libgdx/wiki/InputEvent
 */
public class GameTouchController implements InputProcessor {

	/**
	 * Listener for special effect requests from the user.
	 */
	public interface SpecialAbilityListener {
		/**
		 * A special effect was requested by the user (needs to be validated).
		 */
		public void specialAbilityRequested();
	}

	/** The keyboard key to shot the heavy weapon */
	private final int SHOT_KEY = Input.Keys.SPACE;
	/** The keyboard key to use the special ability */
	private final int SPECIAL_ABILITY_KEY = Input.Keys.G;

	/** Describes the sense of the point device */
	private static final float SENSE_SCALE = 1f;

	/** A list of special ability listeners */
	private final List<SpecialAbilityListener> specialAbilityListeners = new LinkedList<GameTouchController.SpecialAbilityListener>();

	/**
	 * The game camera. This is needed to un-project x/y values to the virtual
	 * screen size.
	 */
	private final Graphics graphics;

	/**
	 * Hard reference to the ship model.
	 */
	private final PlayerShip ship;

	private final GameController gameController;
	private final MasterController masterController;

	/** The finger index controlling the position of the ship. */
	private int steeringFinger = -1;
	/** The origin of touch down finger controlling the ship */
	private final Vector2 touchOrigin = new Vector2();

	/** Flag indicating that keyboard presses should be ignored */
	private boolean suppressKeyboard;

	public GameTouchController(final Graphics graphics, final PlayerShip ship,
			GameController gameController, MasterController masterController) {
		this.graphics = graphics;
		this.ship = ship;
		this.gameController = gameController;
		this.masterController = masterController;
	}

	@Override
	public boolean keyDown(int keycode) {
		if (suppressKeyboard) {
			return true;
		}
		if (keycode == SHOT_KEY) {
			ship.fireWeapon();
		}
		if (keycode == SPECIAL_ABILITY_KEY) {
			for (SpecialAbilityListener listener : specialAbilityListeners) {
				listener.specialAbilityRequested();
			}
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return suppressKeyboard;
	}

	@Override
	public boolean keyTyped(char character) {
		return suppressKeyboard;
	}

	/** Pre-calculated values to increase speed */
	private static float INVERTER_WIDTH = 1.0f / 16.0f,
			INVERTER_HEIGHT = 1.0f / 9.0f;

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// Check if GUI input was to be handled
		float guiX = screenX / (Gdx.graphics.getWidth() * INVERTER_WIDTH);
		float guiY = screenY / (Gdx.graphics.getHeight() * INVERTER_HEIGHT);
		guiX -= 8.0f;
		guiY = 4.5f - guiY;
		GuiEvent event = graphics.getHudView().guiInput(guiX, guiY);
		if (event != null) {
			switch (event) {
			case PAUSE:
				gameController.pauseGame();
				break;
			case UNPAUSE:
				gameController.unpauseGame();
				break;
			case GAMEOVER:
				gameController.gameOver();
				break;
			case RESTARTGAME:
				masterController.startGame(null,
						masterController.getGameScreen().getWeaponData(),
						masterController.getGameScreen().getSpecial(),
						masterController.getGameScreen().getPassive(),
						false
						);
				break;
			case STOPGAME:
				masterController.setScreen(masterController.getLoadoutScreen());
				break;
			case SPECIAL_ABILITY:
				for (SpecialAbilityListener listener : specialAbilityListeners) {
					listener.specialAbilityRequested();
				}
				break;
			}
			return true;
		}

		// Otherwise it's world input
		// Unproject the touch location to the virtual screen.
		Vector2 touchVector = new Vector2(screenX, screenY);
		graphics.screenToWorld(touchVector);

		if (touchVector.x <= ship.getPosition().x + 8f) {
			// Left half of the screen
			// Set the touchOrigin vector to know where the touch originated from
			touchOrigin.set(touchVector);
			steeringFinger = pointer;
		} else {
			// Right half of the screen
			ship.fireWeapon();
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (pointer == steeringFinger) {
			touchOrigin.set(new Vector2());
			steeringFinger = -1;
		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if (pointer == steeringFinger) {
			// Un-project the touch location to the virtual screen.
			Vector2 touchVector = new Vector2(screenX, screenY);
			graphics.screenToWorld(touchVector);
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
		// Un-project the touch location to the virtual screen.
		Vector2 touchVector = new Vector2(screenX, screenY);
		graphics.screenToWorld(touchVector);

		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	/**
	 * Adds a SpecialAbilityListener to the list of listeners.
	 * 
	 * @param specialAbilityListener
	 *        the listener that should be added.
	 */
	public void addSpecialAbilityListener(SpecialAbilityListener specialAbilityListener) {
		specialAbilityListeners.add(specialAbilityListener);
	}

	/**
	 * Removes the first occurrence of the specified SpecialAbilityListener from the list of
	 * listeners.
	 * 
	 * @param specialAbilityListener
	 *        the listener that should be removed.
	 */
	public void removeSpecialAbilityListener(SpecialAbilityListener specialAbilityListener) {
		specialAbilityListeners.remove(specialAbilityListener);
	}

	public void setSuppressKeyboard(boolean suppressKeyboard) {
		this.suppressKeyboard = suppressKeyboard;
	}

}