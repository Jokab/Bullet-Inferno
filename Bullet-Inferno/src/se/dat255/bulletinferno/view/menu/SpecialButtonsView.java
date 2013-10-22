package se.dat255.bulletinferno.view.menu;

import java.util.ArrayList;
import java.util.List;

import se.dat255.bulletinferno.model.loadout.SpecialAbilityDefinitionImpl;
import se.dat255.bulletinferno.util.ResourceIdentifier;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.util.TextureDefinitionImpl;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class SpecialButtonsView {

	private final ResourceManager resourceManager;
	private final List<SpecialButton> specialButtons = new ArrayList<SpecialButton>();
	private SpecialButton selectionButton;
	private final Skin skin;
	private final Table table;
	private final Image label;
	private final TextureRegionDrawable labelSource;

	public SpecialButtonsView(Stage stage, Skin skin, Table table, Image label,
			ResourceManager resourceManager) {
		this.skin = skin;
		this.table = table;
		this.label = label;
		labelSource = new TextureRegionDrawable(resourceManager.getTexture(
				TextureDefinitionImpl.LOADOUT_SPECIAL_ABILITIES));
		this.resourceManager = resourceManager;
	}

	public void populateTable() {
		SpecialAbilityDefinitionImpl[] arr = SpecialAbilityDefinitionImpl.values();
		if (specialButtons.size() == 0) {
			table.clear();
			for (SpecialAbilityDefinitionImpl ability : arr) {
				// TODO: the line below needs changing to take into account all weapons
				SpecialButton specialButton = new SpecialButton(getTableButton(ability), ability,
						resourceManager);
				specialButtons.add(specialButton);
				specialButton.getButton().addListener(new TableElementClickedListener());
			}

		}

		if (selectionButton.getData() == null) {
			setSelectionToSelected(specialButtons.get(0));
		}
		// Set up the table to add these buttons to
		showTable();
	}

	private void showTable() {
		table.clear();
		for (SpecialButton button : specialButtons) {
			table.add(button.getButton()).padBottom(20).height(95).width(175).row();
		}
		label.setDrawable(labelSource);
	}

	private Button getTableButton(ResourceIdentifier identifier) {
		TextureRegion texture = resourceManager.getTexture(identifier);
		ButtonStyle buttonStyle = new ButtonStyle();
		buttonStyle.up = new TextureRegionDrawable(texture);

		return new Button(buttonStyle);
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
		selectionButton.setData(sButton.getData());

		style.up = new TextureRegionDrawable(resourceManager.getTexture(
				selectionButton.getData()));
		style.over = style.up;

		selectionButton.getButton().setStyle(style);
	}

	public SpecialButton getSelectionButton() {
		return selectionButton;
	}

	public void setSelectionButton(SpecialButton selectionSpecialButton) {
		selectionButton = selectionSpecialButton;
	}

	private class TableElementClickedListener extends ChangeListener {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			SpecialButton selected = null;
			for (SpecialButton sButton : specialButtons) {
				Button button = sButton.getButton();
				if (button == actor) {
					selected = sButton;
					if (!sButton.isSelected()) {
						sButton.toggleSelected(skin);
						setSelectionToSelected(sButton);
					} else {
						sButton.toggleSelected(skin);
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
			populateTable();
		}

	}
}
