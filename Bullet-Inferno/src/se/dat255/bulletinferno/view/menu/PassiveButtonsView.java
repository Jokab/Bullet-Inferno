package se.dat255.bulletinferno.view.menu;

import java.util.ArrayList;
import java.util.List;

import se.dat255.bulletinferno.model.loadout.PassiveAbilityDefinition;
import se.dat255.bulletinferno.model.loadout.PassiveAbilityDefinitionImpl;
import se.dat255.bulletinferno.util.ResourceIdentifier;
import se.dat255.bulletinferno.util.ResourceManager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class PassiveButtonsView {

	private final ResourceManager resourceManager;
	private final List<PassiveButton> passiveButtons = new ArrayList<PassiveButton>();
	private PassiveButton selectionButton;
	private final Skin skin;
	private final Table table;
	private final Label label;

	public PassiveButtonsView(Stage stage, Skin skin, Table table, Label label, ResourceManager resourceManager) {
		this.skin = skin;
		this.table = table;
		this.label = label;
		this.resourceManager = resourceManager;
	}

	public void populateTable() {
		if (passiveButtons.size() == 0) {
			table.clear();
			for (int i = 0; i < 5; i++) {
				// TODO: the line below needs changing to take into account all weapons
				PassiveAbilityDefinition ability = PassiveAbilityDefinitionImpl.TAKE_DAMAGE_MODIFIER;
				PassiveButton passiveButton = new PassiveButton(getTableButton(ability), ability,
						resourceManager);
				passiveButtons.add(passiveButton);
				passiveButton.getButton().addListener(new ClickedListener());
			}
	
		}
		
		setSelectionToSelected(passiveButtons.get(0));
		// Set up the table to add these buttons to
		showTable();
	}

	private void showTable() {
		table.clear();
		for (PassiveButton button : passiveButtons) {
			this.table.add(button.getButton()).padBottom(20).height(50).width(100).row();
		}
		label.setText("Passive abilities");
	}

	private Button getTableButton(ResourceIdentifier identifier) {
		Texture texture = resourceManager.getTexture(identifier);
		TextureRegion region = new TextureRegion(texture);
		ButtonStyle buttonStyle = new ButtonStyle();
		buttonStyle.up = new TextureRegionDrawable(region);

		return new Button(buttonStyle);
	}

	private void setSelectionToNothing(ButtonStyle style) {
		ButtonStyle newStyle = new ButtonStyle(style);
		newStyle.up = new TextureRegionDrawable(new TextureRegion(
				new Texture("data/frame.png")));
		newStyle.over = newStyle.up;
		selectionButton.getButton().setStyle(newStyle);
		selectionButton.setData(null);
	}

	private void deselectOtherButtons(PassiveButton selected) {
		for (PassiveButton pButton : passiveButtons) {
			if (pButton != selected && pButton.isSelected()) {
				pButton.toggleSelected(skin);
			}
		}
	}

	private void setSelectionToSelected(PassiveButton pButton) {
		ButtonStyle style = pButton.getButton().getStyle();
		selectionButton.setData(pButton.getData());

		Texture texture = resourceManager.getTexture(
				selectionButton.getData());
		style.up = new TextureRegionDrawable(new TextureRegion(texture));
		style.over = style.up;

		selectionButton.getButton().setStyle(style);
	}


	public PassiveButton getSelectionButton() {
		return this.selectionButton;
	}

	public void setSelectionButton(PassiveButton selectionSpecialButton) {
		this.selectionButton = selectionSpecialButton;
	}


	private class ClickedListener extends ChangeListener {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			PassiveButton selected = null;
			for (PassiveButton pButton : passiveButtons) {
				Button button = pButton.getButton();
				if (button == ((Button) actor)) {
					selected = pButton;
					if (!pButton.isSelected()) {
						pButton.toggleSelected(skin);
						setSelectionToSelected(pButton);
					} else {
						pButton.toggleSelected(skin);
						setSelectionToNothing(button.getStyle());
					}
				}
				// TODO: add break here since we don't want to keep looping after we found the
				// matching weapon
			}
	
			deselectOtherButtons(selected);
		}
	}

	public class SelectionClickedListener extends ChangeListener {
	
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			if (selectionButton.getData() == null) {
				populateTable();
			} else {
				Button button = selectionButton.getButton();
				if (button == ((Button) actor)) {
					setSelectionToNothing(button.getStyle());
					deselectOtherButtons(new PassiveButton(null, null, null));
				}
			}
		}
	
	}
}
