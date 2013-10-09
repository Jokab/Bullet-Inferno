package se.dat255.bulletinferno.menu;

import se.dat255.bulletinferno.model.loadout.SpecialAbilityDefinition;
import se.dat255.bulletinferno.util.ResourceManager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class SpecialButton extends CustomizedButton {

	private SpecialAbilityDefinition ability;
	private final ResourceManager resourceManager;
	
	public SpecialButton(Button button, SpecialAbilityDefinition ability, ResourceManager resourceManager) {
		super(button);
		this.ability = ability;
		this.resourceManager = resourceManager;
	}

	@Override
	public void toggleSelected(Skin skin) {
		this.isSelected = !isSelected;
		if (isSelected) {
			button.getStyle().up = skin.newDrawable(button.getStyle().up,
					Color.LIGHT_GRAY);
		} else {
			button.getStyle().up = new TextureRegionDrawable(new TextureRegion(
					resourceManager.getManagedTexture(this.ability).getTexture()));
		}
	}

	public SpecialAbilityDefinition getData() {
		return this.ability;
	}

	public void setData(SpecialAbilityDefinition data) {
		this.ability = data;
	}

}
