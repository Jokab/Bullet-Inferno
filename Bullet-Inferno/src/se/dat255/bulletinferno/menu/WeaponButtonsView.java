package se.dat255.bulletinferno.menu;

import java.util.ArrayList;
import java.util.List;

import se.dat255.bulletinferno.model.weapon.WeaponDefinition;
import se.dat255.bulletinferno.model.weapon.WeaponDefinitionImpl;
import se.dat255.bulletinferno.util.ResourceIdentifier;
import se.dat255.bulletinferno.util.ResourceManager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

public class WeaponButtonsView {

	private final ResourceManager resourceManager;
	private final List<WeaponButton> standardWeapons = new ArrayList<WeaponButton>();
	private WeaponButton selectionButton;
	private final Stage stage;
	private final Skin skin;
	private final Table table;
	private final Label label;

	public WeaponButtonsView(Stage stage, Skin skin, Table table, Label label, ResourceManager resourceManager) {
		this.stage = stage;
		this.skin = skin;
		this.table = table;
		this.label = label;
		this.resourceManager = resourceManager;
	}

	public void populateTable() {
		if (standardWeapons.size() == 0) {
			for (int i = 0; i < 5; i++) {
				// TODO: the line below needs changing to take into account all weapons
				WeaponDefinition weaponData = WeaponDefinitionImpl.DISORDERER;
				WeaponButton weaponButton = new WeaponButton(getTableButton(weaponData),
						weaponData,
						resourceManager);
				standardWeapons.add(weaponButton);
	
				weaponButton.getButton().addListener(new ClickedListener());
			}
		}
	
		// Set up the table to add these buttons to
		showTable();
	}

	private void showTable() {
		table.clear();
		for (WeaponButton button : standardWeapons) {
			this.table.add(button.getButton()).padBottom(20).height(50).width(100).row();
		}
		label.setText("Primary weapons");
	}

	public void setSelectionToClicked(WeaponButton wButton) {
		selectionButton.setData(wButton.getData());
		ButtonStyle style = new ButtonStyle(wButton.getButton().getStyle());

		Texture texture = resourceManager.getManagedTexture(
				selectionButton.getData()).getTexture();
		style.up = new TextureRegionDrawable(new TextureRegion(texture));
		style.over = wButton.getButton().getStyle().up;

		selectionButton.getButton().setStyle(style);
	}

	public void setSelectionToNothing(ButtonStyle style) {
		ButtonStyle newStyle = new ButtonStyle(style);
		newStyle.up = new TextureRegionDrawable(new TextureRegion(
				new Texture("data/frame.png")));
		newStyle.over = newStyle.up;
		selectionButton.getButton().setStyle(newStyle);
		selectionButton.setData(null);
	}

	private void deselectOtherButtons(WeaponButton selected) {
		for (WeaponButton wButton : standardWeapons) {
			if (wButton != selected && wButton.isSelected()) {
				wButton.toggleSelected(skin);
			}
		}
	}

	private Button getTableButton(ResourceIdentifier identifier) {
		Texture texture = resourceManager.getManagedTexture(identifier).getTexture();
		TextureRegion region = new TextureRegion(texture);
		ButtonStyle buttonStyle = new ButtonStyle();
		buttonStyle.up = new TextureRegionDrawable(region);

		return new Button(buttonStyle);
	}

	public WeaponButton getSelectionButton() {
		return selectionButton;
	}

	public void setSelectionButton(WeaponButton selectionWeaponButton) {
		this.selectionButton = selectionWeaponButton;
	}

	public List<WeaponButton> getStandardWeapons() {
		return standardWeapons;
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
					deselectOtherButtons(new WeaponButton(null, null, null));
				}
			}
		}

	}

	public class ClickedListener extends ChangeListener {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			WeaponButton selected = null;
			for (WeaponButton wButton : standardWeapons) {
				Button button = wButton.getButton();
				if (button == ((Button) actor)) {
					selected = wButton;
					if (!wButton.isSelected()) {
						wButton.toggleSelected(skin);
						setSelectionToClicked(wButton);
					} else {
						wButton.toggleSelected(skin);
						setSelectionToNothing(button.getStyle());
					}
				}
				// TODO: add break here since we don't want to keep looping after we found the
				// matching weapon
			}

			deselectOtherButtons(selected);
		}
	}
}
