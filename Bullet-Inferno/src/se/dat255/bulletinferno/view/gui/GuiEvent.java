package se.dat255.bulletinferno.view.gui;

/**
 * An event that is passed from a view to the controller that handles the game.
 * The controller can then handle the event accordingly
 */
public enum GuiEvent {
	PAUSE, UNPAUSE, GAMEOVER, RESTARTGAME, STOPGAME
}
