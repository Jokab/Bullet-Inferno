package se.dat255.bulletinferno.view;

import se.dat255.bulletinferno.model.Enemy;
import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.ResourceManager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EnemyView implements Renderable {

	private final Game game;
	private Texture texture;
	private Sprite sprite;
	private ResourceManager resourceManager;

	public EnemyView(Game game, ResourceManager resourceManager) {
		this.game = game;
		this.resourceManager = resourceManager;

		this.sprite = new Sprite();
		sprite.setOrigin(0, 0);
		sprite.setSize(1, 1);
	}

	@Override
	public void render(SpriteBatch batch) {
		for(Enemy enemy : game.getEnemies()) {
			this.texture = resourceManager.getTexture(enemy.getType().getIdentifier());
			texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			this.sprite.setTexture(texture);
			sprite.setRegion(texture);

			sprite.setPosition(enemy.getPosition().x,
					enemy.getPosition().y - sprite.getHeight() / 2);
			sprite.draw(batch);
		}
	}

	@Override
	public void dispose() {
		texture.dispose();
	}
}
