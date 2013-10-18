package se.dat255.bulletinferno.view.menu;

import se.dat255.bulletinferno.model.loadout.SpecialAbilityDefinition;
import se.dat255.bulletinferno.util.ResourceManager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Button for selecting special abilities
 */
public class SpecialButton extends CustomizedButton {

	private SpecialAbilityDefinition ability;
	private final ResourceManager resourceManager;

	public SpecialButton(Button button, SpecialAbilityDefinition ability,
			ResourceManager resourceManager) {
		super(button);
		this.ability = ability;
		this.resourceManager = resourceManager;
	}

	@Override
	public void toggleSelected(Skin skin) {
		isSelected = !isSelected;
		if (isSelected) {
			button.getStyle().up = skin.newDrawable(button.getStyle().up,
					Color.LIGHT_GRAY);
		} else {
			button.getStyle().up = new TextureRegionDrawable(new TextureRegion(
					resourceManager.getTexture(ability)));
		}
	}

	public SpecialAbilityDefinition getData() {
		return ability;
	}

	public void setData(SpecialAbilityDefinition data) {
		ability = data;
	}

}
