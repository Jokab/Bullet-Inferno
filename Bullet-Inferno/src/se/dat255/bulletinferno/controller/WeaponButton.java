package se.dat255.bulletinferno.controller;

import se.dat255.bulletinferno.model.weapon.WeaponData;

import com.badlogic.gdx.scenes.scene2d.ui.Button;

public class WeaponButton {

	private final Button button;
	private WeaponData weaponData;
	private boolean isSelected;
	
	public WeaponButton(Button button, WeaponData weaponData) {
		this.button = button;
		this.weaponData = weaponData;
		this.isSelected = false;
	}

	public Button getButton() {
		return button;
	}

	public WeaponData getWeaponData() {
		return weaponData;
	}
	
	public boolean isSelected() {
		return this.isSelected;
	}

	public void toggleSelected() {
		this.isSelected = !isSelected;
	}

	public void setWeaponData(WeaponData weaponData) {
		this.weaponData = weaponData;
	}
}
