package se.dat255.bulletinferno.controller;

import se.dat255.bulletinferno.model.weapon.WeaponDefinition;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.util.ResourceManagerImpl.TextureType;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class WeaponButton {

	private final Button button;
	private WeaponDefinition weaponData;
	private boolean isSelected;
	private ResourceManager resourceManager;

	public WeaponButton(Button button, WeaponDefinition weaponData, ResourceManager resourceManager) {
		this.button = button;
		this.weaponData = weaponData;
		this.isSelected = false;
		this.resourceManager = resourceManager;
	}

	public Button getButton() {
		return button;
	}

	public WeaponDefinition getWeaponData() {
		return weaponData;
	}

	public boolean isSelected() {
		return this.isSelected;
	}

	public void toggleSelected(Skin skin) {
		this.isSelected = !isSelected;
		if (isSelected) {
			button.getStyle().up = skin.newDrawable(button.getStyle().up,
					Color.LIGHT_GRAY);
		} else {
			button.getStyle().up = new TextureRegionDrawable(new TextureRegion(
					resourceManager.getManagedTexture(TextureType.DISORDERER).getTexture()));
		}
	}

	public void setWeaponData(WeaponDefinition weaponData) {
		this.weaponData = weaponData;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((button == null) ? 0 : button.hashCode());
		result = prime * result + (isSelected ? 1231 : 1237);
		result = prime * result + ((weaponData == null) ? 0 : weaponData.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WeaponButton other = (WeaponButton) obj;
		if (button == null) {
			if (other.button != null)
				return false;
		} else if (!button.equals(other.button))
			return false;
		if (isSelected != other.isSelected)
			return false;
		if (weaponData != other.weaponData)
			return false;
		return true;
	}
	
	
}
