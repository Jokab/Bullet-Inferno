package se.dat255.bulletinferno.view.menu;

import se.dat255.bulletinferno.model.weapon.WeaponDefinition;
import se.dat255.bulletinferno.util.ResourceManager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Button for selecting weapons
 */
public class WeaponButton extends CustomizedButton {

	private WeaponDefinition weaponData;
	private final ResourceManager resourceManager;

	public WeaponButton(Button button, WeaponDefinition weaponData, ResourceManager resourceManager) {
		super(button);
		this.weaponData = weaponData;
		this.resourceManager = resourceManager;
	}

	public WeaponDefinition getData() {
		return weaponData;
	}

	@Override
	public void toggleSelected(Skin skin) {
		isSelected = !isSelected;
		if (isSelected) {
			button.getStyle().up = skin.newDrawable(button.getStyle().up,
					Color.LIGHT_GRAY);
		} else {
			button.getStyle().up = new TextureRegionDrawable(new TextureRegion(
					resourceManager.getTexture(weaponData)));
		}
	}

	public void setData(WeaponDefinition weaponData) {
		this.weaponData = weaponData;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (button == null ? 0 : button.hashCode());
		result = prime * result + (isSelected ? 1231 : 1237);
		result = prime * result + (weaponData == null ? 0 : weaponData.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		WeaponButton other = (WeaponButton) obj;
		if (button == null) {
			if (other.button != null) {
				return false;
			}
		} else if (!button.equals(other.button)) {
			return false;
		}
		if (isSelected != other.isSelected) {
			return false;
		}
		return weaponData == other.weaponData;
	}

}
