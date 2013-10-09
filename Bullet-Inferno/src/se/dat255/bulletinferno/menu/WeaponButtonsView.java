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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

public class WeaponButtonsView {

	private final ResourceManager resourceManager;
	private final List<WeaponButton> primaryWeapons = new ArrayList<WeaponButton>();
	private WeaponButton selectionWeaponButton;
	private final Stage stage;
	private final Skin skin;
	private final Table table;

	public WeaponButtonsView(Stage stage, Skin skin, Table table, ResourceManager resourceManager) {
		this.stage = stage;
		this.skin = skin;
		this.table = table;
		this.resourceManager = resourceManager;
	}

	public void setSelectionToClicked(WeaponButton wButton) {
		selectionWeaponButton.setData(wButton.getData());
		ButtonStyle style = new ButtonStyle(wButton.getButton().getStyle());

		Texture texture = resourceManager.getManagedTexture(
				selectionWeaponButton.getData()).getTexture();
		style.up = new TextureRegionDrawable(new TextureRegion(texture));
		style.over = wButton.getButton().getStyle().up;

		selectionWeaponButton.getButton().setStyle(style);
	}

	public void setSelectionToNothing(ButtonStyle style) {
		ButtonStyle newStyle = new ButtonStyle(style);
		newStyle.up = new TextureRegionDrawable(new TextureRegion(
				new Texture("data/frame.png")));
		newStyle.over = newStyle.up;
		selectionWeaponButton.getButton().setStyle(newStyle);
		selectionWeaponButton.setData(null);
	}
	
	private void deselectOtherButtons(WeaponButton selected) {
		for (WeaponButton wButton : primaryWeapons) {
			if (wButton != selected && wButton.isSelected()) {
				wButton.toggleSelected(skin);
			}
		}
	}

	public void setupPrimaryWeaponButtons() {
		for (int i = 0; i < 5; i++) {
			// TODO: the line below needs changing to take into account all weapons
			WeaponDefinition weaponData = WeaponDefinitionImpl.DISORDERER;
			WeaponButton weaponButton = new WeaponButton(getTableButton(weaponData), weaponData,
					resourceManager);
			primaryWeapons.add(weaponButton);
			weaponButton.getButton().addListener(new ClickedListener());
		}

		// Set up the table to add these buttons to
		setupPrimaryWeaponsTable();
	}
	
	public void setupPrimaryWeaponsTable() {
		table.clear();
		for (WeaponButton button : primaryWeapons) {
			this.table.add(button.getButton()).padBottom(20).height(50).width(100).row();
		}
	}
	
	private Button getTableButton(ResourceIdentifier identifier) {
		Texture texture = resourceManager.getManagedTexture(identifier).getTexture();
		TextureRegion region = new TextureRegion(texture);
		ButtonStyle buttonStyle = new ButtonStyle();
		buttonStyle.up = new TextureRegionDrawable(region);

		return new Button(buttonStyle);
	}

	public WeaponButton getSelectionWeaponButton() {
		return selectionWeaponButton;
	}

	public void setSelectionButton(WeaponButton selectionWeaponButton) {
		this.selectionWeaponButton = selectionWeaponButton;
	}
	
	public List<WeaponButton> getPrimaryWeapons() {
		return primaryWeapons;
	}

	public class SelectionClickedListener extends ChangeListener {
	
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			Button button = selectionWeaponButton.getButton();
			if (button == ((Button) actor)) {
				setSelectionToNothing(button.getStyle());
				deselectOtherButtons(new WeaponButton(null, null, null));
			}
		}
	
	}

	public class ClickedListener extends ChangeListener {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			WeaponButton selected = null;
			for (WeaponButton wButton : primaryWeapons) {
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
