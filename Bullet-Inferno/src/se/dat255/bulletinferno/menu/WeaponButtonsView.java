package se.dat255.bulletinferno.menu;

import java.util.ArrayList;
import java.util.List;

import se.dat255.bulletinferno.model.weapon.WeaponDefinition;
import se.dat255.bulletinferno.model.weapon.WeaponDefinitionImpl;
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

public class WeaponButtonsView {

	private final ResourceManager resourceManager;
	private final List<WeaponButton> standardWeapons = new ArrayList<WeaponButton>();
	private final List<WeaponButton> heavyWeapons = new ArrayList<WeaponButton>();
	private WeaponButton standardSelectionButton;
	private WeaponButton heavySelectionButton;
	private final Stage stage;
	private final Skin skin;
	private final Table table;
	private final Label label;

	public WeaponButtonsView(Stage stage, Skin skin, Table table, Label label,
			ResourceManager resourceManager) {
		this.stage = stage;
		this.skin = skin;
		this.table = table;
		this.label = label;
		this.resourceManager = resourceManager;
	}

	public void populateTable(String type) {
		if (type.equals("standard")) {
			populateTableWithStandard();
		} else if (type.equals("heavy")) {
			populateTableWithHeavy();
		}
	}

	private void populateTableWithHeavy() {
		if (heavyWeapons.size() == 0) {
			for (int i = 0; i < 5; i++) {
				// TODO: the line below needs changing to take into account all weapons
				WeaponDefinition weaponData = WeaponDefinitionImpl.MISSILE_LAUNCHER;
				WeaponButton weaponButton = new WeaponButton(getTableButton(weaponData),
						weaponData,
						resourceManager);
				heavyWeapons.add(weaponButton);

				weaponButton.getButton().addListener(
						new TableElementClickedListener(heavySelectionButton, heavyWeapons));
			}
		}

		// Set up the table to add these buttons to
		setSelectionToClicked(heavyWeapons.get(0), heavySelectionButton);
		showTable(heavyWeapons, "Heavy Weapons");
	}

	private void populateTableWithStandard() {
		if (standardWeapons.size() == 0) {
			for (int i = 0; i < 5; i++) {
				// TODO: the line below needs changing to take into account all weapons
				WeaponDefinition weaponData = WeaponDefinitionImpl.DISORDERER;
				WeaponButton weaponButton = new WeaponButton(getTableButton(weaponData),
						weaponData,
						resourceManager);
				standardWeapons.add(weaponButton);

				weaponButton.getButton().addListener(
						new TableElementClickedListener(standardSelectionButton, standardWeapons));
			}
		}

		setSelectionToClicked(standardWeapons.get(0), standardSelectionButton);
		// Set up the table to add these buttons to
		showTable(standardWeapons, "Standard Weapons");
	}

	private void showTable(List<WeaponButton> weaponList, String labelText) {
		table.clear();
		for (WeaponButton button : weaponList) {
			this.table.add(button.getButton()).padBottom(20).height(50).width(100).row();
		}
		label.setText(labelText);
	}

	public void setSelectionToClicked(WeaponButton wButton, WeaponButton selectionButton) {
		selectionButton.setData(wButton.getData());
		ButtonStyle style = new ButtonStyle(wButton.getButton().getStyle());

		Texture texture = resourceManager.getTexture(
				selectionButton.getData());
		style.up = new TextureRegionDrawable(new TextureRegion(texture));
		style.over = wButton.getButton().getStyle().up;

		selectionButton.getButton().setStyle(style);
	}

	public void setSelectionToNothing(ButtonStyle style, WeaponButton selectionButton) {
		ButtonStyle newStyle = new ButtonStyle(style);
		newStyle.up = new TextureRegionDrawable(new TextureRegion(
				new Texture("data/frame.png")));
		newStyle.over = newStyle.up;
		selectionButton.getButton().setStyle(newStyle);
		selectionButton.setData(null);
	}

	private void deselectOtherButtons(WeaponButton selected, List<WeaponButton> weaponList) {
		for (WeaponButton wButton : weaponList) {
			if (wButton != selected && wButton.isSelected()) {
				wButton.toggleSelected(skin);
			}
		}
	}

	private Button getTableButton(ResourceIdentifier identifier) {
		Texture texture = resourceManager.getTexture(identifier);
		TextureRegion region = new TextureRegion(texture);
		ButtonStyle buttonStyle = new ButtonStyle();
		buttonStyle.up = new TextureRegionDrawable(region);

		return new Button(buttonStyle);
	}

	public WeaponButton getStandardSelectionButton() {
		return standardSelectionButton;
	}

	public void setStandardSelectionButton(WeaponButton selectionWeaponButton) {
		this.standardSelectionButton = selectionWeaponButton;
	}

	public List<WeaponButton> getStandardWeapons() {
		return standardWeapons;
	}

	public WeaponButton getHeavySelectionButton() {
		return heavySelectionButton;
	}

	public void setHeavySelectionButton(WeaponButton heavySelectionButton) {
		this.heavySelectionButton = heavySelectionButton;
	}

	public List<WeaponButton> getHeavyWeapons() {
		return heavyWeapons;
	}

	public class TableElementClickedListener extends ChangeListener {

		private final WeaponButton selectionButton;
		private final List<WeaponButton> list;

		public TableElementClickedListener(WeaponButton selectionButton, List<WeaponButton> list) {
			this.selectionButton = selectionButton;
			this.list = list;
		}

		@Override
		public void changed(ChangeEvent event, Actor actor) {
			WeaponButton selected = null;
			for (WeaponButton wButton : list) {
				Button button = wButton.getButton();
				if (button == ((Button) actor)) {
					selected = wButton;
					if (!wButton.isSelected()) {
						wButton.toggleSelected(skin);
						setSelectionToClicked(wButton, selectionButton);
					} else {
						wButton.toggleSelected(skin);
						setSelectionToNothing(button.getStyle(), selectionButton);
					}
				}
				// TODO: add break here since we don't want to keep looping after we found the
				// matching weapon
			}

			deselectOtherButtons(selected, list);
		}
	}

	public class SelectionClickedListener extends ChangeListener {

		private final WeaponButton selectionButton;
		private final List<WeaponButton> list;
		private final String type;

		public SelectionClickedListener(WeaponButton selectionButton, List<WeaponButton> list,
				String type) {
			this.selectionButton = selectionButton;
			this.list = list;
			this.type = type;
		}

		@Override
		public void changed(ChangeEvent event, Actor actor) {
			if (selectionButton.getData() == null) {
				populateTable(type);
			} else {
				Button button = selectionButton.getButton();
				if (button == ((Button) actor)) {
					setSelectionToNothing(button.getStyle(), selectionButton);
					deselectOtherButtons(new WeaponButton(null, null, null), list);
				}
			}
		}

	}
}
