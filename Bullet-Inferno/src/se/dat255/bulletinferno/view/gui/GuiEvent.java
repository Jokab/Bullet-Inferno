package se.dat255.bulletinferno.view.gui;

/**
 * An event that is passed from a view to the controller that handles the game.
 * The controller can then handle the event accordingly
 */
public enum GuiEvent {
	/** Handled pausing and unpausing of the game */
	PAUSE, UNPAUSE,
	/** Handles the state of the game */
	GAMEOVER, RESTARTGAME, STOPGAME,
	/** Fires the ships special ability */
	SPECIAL_ABILITY
}
