package se.dat255.bulletinferno.view;

import se.dat255.bulletinferno.model.Enemy;
import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.Projectile;
import se.dat255.bulletinferno.model.Renderable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EnemyView implements Renderable {

	private final Game game;
	private final Texture texture;
	private final Sprite sprite;

	
	public EnemyView(Game game) {
		this.game = game;

		texture = new Texture(Gdx.files.internal("data/enemyShip.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		sprite = new Sprite(texture);
		sprite.setOrigin(0, 0);
		sprite.setSize(1, 1);
	}

	@Override
	public void render(SpriteBatch batch) {
		for (Enemy enemy : game.getEnemies()) {
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
