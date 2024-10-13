package se.dat255.bulletinferno.view.menu;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * A general button implementation to be used with the menu
 */
public abstract class CustomizedButton {
	/** The actual GUI button displayed */
	protected final Button button;
	/** If the button is toggled or not */
	protected boolean isSelected = false;

	/** Sets the reference to the GUI button */
	public CustomizedButton(Button button) {
		this.button = button;
	}

	/** Getter for the GUI button */
	public Button getButton() {
		return button;
	}

	/** Returns if the button is toggled */
	public boolean isSelected() {
		return isSelected;
	}

	/** What happens when the button gets toggled */
	public abstract void toggleSelected(Skin skin);
}
