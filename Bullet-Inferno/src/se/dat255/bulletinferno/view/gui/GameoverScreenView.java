package se.dat255.bulletinferno.view.gui;

import se.dat255.bulletinferno.controller.GameController;
import se.dat255.bulletinferno.controller.MasterController;
import se.dat255.bulletinferno.model.ResourceManager;
import se.dat255.bulletinferno.view.RenderableGUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class GameoverScreenView implements RenderableGUI {

	private final Vector2 position, size;
	private final Sprite sprite;
	private final MasterController game;

	public GameoverScreenView(MasterController game, ResourceManager resourceManager) {
		Texture texture = resourceManager.getTexture("GAMEOVER_SCREEN");
		sprite = new Sprite(texture);
		size = new Vector2(16.0f, 9.0f);
		sprite.setSize(size.x, size.y);
		position = new Vector2(0.0f, 0.0f);
		sprite.setPosition(position.x - 8.0f, position.y - 4.5f);
		this.game = game;
	}

	@Override
	public void render(SpriteBatch batch) {
		sprite.draw(batch);
	}

	@Override
	public void dispose() {
		sprite.getTexture().dispose();
	}

	@Override
	public void pressed(float x, float y) {
		// Restart
		if(2.6f < x && x < 6.4f && 2.44f < y && y < 4f){
			game.startGame(null);
		}
		
		// Menu
		if(10.6f < x && x < 13f && 2.4f < y && y < 4.12){
			game.setScreen(game.getLoadoutScreen());
		}
	}

	@Override
	public Vector2 getPosition() {
		return position;
	}

	@Override
	public Vector2 getSize() {
		return size;
	}

}
