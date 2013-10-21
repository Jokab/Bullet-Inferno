package se.dat255.bulletinferno.view.menu;

import java.util.ArrayList;
import java.util.List;

import se.dat255.bulletinferno.model.weapon.WeaponDefinition;
import se.dat255.bulletinferno.model.weapon.WeaponDefinitionImpl;
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
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class WeaponButtonsView {

	private final ResourceManager resourceManager;
	private final List<WeaponButton> standardWeapons = new ArrayList<WeaponButton>();
	private final List<WeaponButton> heavyWeapons = new ArrayList<WeaponButton>();
	private WeaponButton standardSelectionButton;
	private WeaponButton heavySelectionButton;
	private final Skin skin;
	private final Table table;
	private final Image label;
	private final TextureRegionDrawable heavyWeaponLabel;
	private final TextureRegionDrawable standardWeaponLabel;
	
	public WeaponButtonsView(Stage stage, Skin skin, Table table, Image label,
			ResourceManager resourceManager) {
		this.skin = skin;
		this.table = table;
		this.label = label;
		this.heavyWeaponLabel = new TextureRegionDrawable(resourceManager.getTexture(
				TextureDefinitionImpl.LOADOUT_HEAVYWEAPON));
		this.standardWeaponLabel = new TextureRegionDrawable(resourceManager.getTexture(
				TextureDefinitionImpl.LOADOUT_STANDARD_WEAPON));
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
			WeaponDefinitionImpl[] arr = WeaponDefinitionImpl.values();
			WeaponDefinition curWeapon = null;
			String[] prefix = null;
			for (int i = 0; i < arr.length; i++) {
				prefix = arr[i].name().split("_");
				if (prefix[0].equals("HEAVY")) {
					curWeapon = arr[i];

					WeaponButton weaponButton = new WeaponButton(getTableButton(curWeapon),
							curWeapon,
							resourceManager);
					heavyWeapons.add(weaponButton);

					weaponButton.getButton().addListener(
							new TableElementClickedListener(heavySelectionButton, heavyWeapons));
				}
			}
		}

		if (heavySelectionButton.getData() == null) {
			setSelectionToClicked(heavyWeapons.get(0), heavySelectionButton);
		}
		// Set up the table to add these buttons to
		showTable(heavyWeapons, heavyWeaponLabel);
	}

	private void populateTableWithStandard() {
		WeaponDefinitionImpl[] arr = WeaponDefinitionImpl.values();
		WeaponDefinition curWeapon = null;
		String[] prefix = null;
		if (standardWeapons.size() == 0) {
			for (int i = 0; i < arr.length; i++) {
				prefix = arr[i].name().split("_");
				if (prefix[0].equals("STANDARD")) {
					curWeapon = arr[i];
					WeaponButton weaponButton = new WeaponButton(getTableButton(curWeapon),
							curWeapon,
							resourceManager);
					standardWeapons.add(weaponButton);

					weaponButton.getButton().addListener(
							new TableElementClickedListener(standardSelectionButton,
									standardWeapons));
				}
			}
		}

		if (standardSelectionButton.getData() == null) {
			setSelectionToClicked(standardWeapons.get(0), standardSelectionButton);
		}
		
		// Set up the table to add these buttons to
		showTable(standardWeapons, standardWeaponLabel);
	}

	private void showTable(List<WeaponButton> weaponList, Drawable labelSource) {
		table.clear();
		for (WeaponButton button : weaponList) {
			table.add(button.getButton()).padBottom(20).height(95).width(200).row();
		}
		label.setDrawable(labelSource);
	}

	private void setSelectionToClicked(WeaponButton wButton, WeaponButton selectionButton) {
		selectionButton.setData(wButton.getData());
		ButtonStyle style = new ButtonStyle(wButton.getButton().getStyle());

		style.up = new TextureRegionDrawable(resourceManager.getTexture(
				selectionButton.getData()));
		style.over = wButton.getButton().getStyle().up;

		selectionButton.getButton().setStyle(style);
	}

	private void deselectOtherButtons(WeaponButton selected, List<WeaponButton> weaponList) {
		for (WeaponButton wButton : weaponList) {
			if (wButton != selected && wButton.isSelected()) {
				wButton.toggleSelected(skin);
			}
		}
	}

	private Button getTableButton(ResourceIdentifier identifier) {
		TextureRegion texture = resourceManager.getTexture(identifier);
		ButtonStyle buttonStyle = new ButtonStyle();
		buttonStyle.up = new TextureRegionDrawable(texture);

		return new Button(buttonStyle);
	}

	public WeaponButton getStandardSelectionButton() {
		return standardSelectionButton;
	}

	public void setStandardSelectionButton(WeaponButton selectionWeaponButton) {
		standardSelectionButton = selectionWeaponButton;
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

	private class TableElementClickedListener extends ChangeListener {

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
				if (button == actor) {
					selected = wButton;
					if (!wButton.isSelected()) {
						wButton.toggleSelected(skin);
						setSelectionToClicked(wButton, selectionButton);
					} else {
						wButton.toggleSelected(skin);
					}
				}
				// TODO: add break here since we don't want to keep looping after we found the
				// matching weapon
			}

			deselectOtherButtons(selected, list);
		}
	}

	public class SelectionClickedListener extends ChangeListener {

		private final String type;

		public SelectionClickedListener(String type) {
			this.type = type;
		}

		@Override
		public void changed(ChangeEvent event, Actor actor) {
			populateTable(type);
		}

	}
}
