package se.dat255.bulletinferno.controller;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public abstract class CustomizedButton {

	protected final Button button;
	protected boolean isSelected = false;

	public CustomizedButton(Button button) {
		this.button = button;
	}
	
	public Button getButton() {
		return this.button;
	}

	public boolean isSelected() {
		return this.isSelected;
	}

	public abstract void toggleSelected(Skin skin);
}
