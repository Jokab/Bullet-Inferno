package se.dat255.bulletinferno.menu;

import java.util.ArrayList;
import java.util.List;

import se.dat255.bulletinferno.model.loadout.SpecialAbilityDefinitionImpl;
import se.dat255.bulletinferno.util.ResourceIdentifier;
import se.dat255.bulletinferno.util.ResourceManager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

public class SpecialButtonsView {

	private final ResourceManager resourceManager;
	private final List<SpecialButton> specialButtons = new ArrayList<SpecialButton>();
	private SpecialButton selectionSpecialButton;
	private final Stage stage;
	private final Skin skin;
	private final Table table;

	public SpecialButtonsView(Stage stage, Skin skin, Table table, ResourceManager resourceManager) {
		this.stage = stage;
		this.skin = skin;
		this.table = table;
		this.resourceManager = resourceManager;
	}

	private Button getTableButton(ResourceIdentifier identifier) {
		Texture texture = resourceManager.getManagedTexture(identifier).getTexture();
		TextureRegion region = new TextureRegion(texture);
		ButtonStyle buttonStyle = new ButtonStyle();
		buttonStyle.up = new TextureRegionDrawable(region);

		return new Button(buttonStyle);
	}

	public void populateTable() {
		if (specialButtons.size() == 0) {
			table.clear();
			for (int i = 0; i < 5; i++) {
				// TODO: the line below needs changing to take into account all weapons
				SpecialAbilityDefinitionImpl ability = SpecialAbilityDefinitionImpl.PROJECTILE_RAIN;
				SpecialButton specialButton = new SpecialButton(getTableButton(ability), ability,
						resourceManager);
				specialButtons.add(specialButton);
				specialButton.getButton().addListener(new ClickedListener());
			}

		}
		// Set up the table to add these buttons to
		showTable();
	}

	private void showTable() {
		table.clearChildren();
		for (SpecialButton button : specialButtons) {
			this.table.add(button.getButton()).padBottom(20).height(50).width(100).row();
		}
	}

	private void setSelectionToNothing(ButtonStyle style) {
		ButtonStyle newStyle = new ButtonStyle(style);
		newStyle.up = new TextureRegionDrawable(new TextureRegion(
				new Texture("data/frame.png")));
		newStyle.over = newStyle.up;
		selectionSpecialButton.getButton().setStyle(newStyle);
		selectionSpecialButton.setData(null);
	}

	private void deselectOtherButtons(SpecialButton selected) {
		for (SpecialButton sButton : specialButtons) {
			if (sButton != selected && sButton.isSelected()) {
				sButton.toggleSelected(skin);
			}
		}
	}

	private void setSelectionToSelected(SpecialButton sButton) {
		ButtonStyle style = sButton.getButton().getStyle();
		selectionSpecialButton.setData(sButton.getData());

		Texture texture = resourceManager.getManagedTexture(
				selectionSpecialButton.getData()).getTexture();
		style.up = new TextureRegionDrawable(new TextureRegion(texture));
		style.over = style.up;

		selectionSpecialButton.getButton().setStyle(style);
	}

	public void setSelectionButton(SpecialButton selectionSpecialButton) {
		this.selectionSpecialButton = selectionSpecialButton;
	}

	private class ClickedListener extends ChangeListener {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			SpecialButton selected = null;
			for (SpecialButton sButton : specialButtons) {
				Button button = sButton.getButton();
				if (button == ((Button) actor)) {
					selected = sButton;
					if (!sButton.isSelected()) {
						sButton.toggleSelected(skin);
						setSelectionToSelected(sButton);
					} else {
						sButton.toggleSelected(skin);
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
			if (selectionSpecialButton.getData() == null) {
				populateTable();
			} else {
				Button button = selectionSpecialButton.getButton();
				if (button == ((Button) actor)) {
					setSelectionToNothing(button.getStyle());
					deselectOtherButtons(new SpecialButton(null, null, null));
				}
			}
		}

	}
}
