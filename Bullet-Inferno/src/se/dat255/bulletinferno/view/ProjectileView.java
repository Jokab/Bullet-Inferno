package se.dat255.bulletinferno.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.Projectile;
import se.dat255.bulletinferno.model.ResourceManager;

public class ProjectileView implements Renderable {

	private Sprite sprite;
	private final Game game;
	private ResourceManager resourceManager;
	private Texture texture;

	public ProjectileView(Game game, ResourceManager resourceManager) {
		this.resourceManager = resourceManager;
		this.game = game;
		
		
		
	}

	@Override
	public void render(SpriteBatch batch) {
		for (Projectile projectile : game.getProjectiles()) {
			texture = resourceManager.getTexture(projectile.getType().getIdentifier());
			sprite = new Sprite(texture);
			sprite.setOrigin(0, 0);
			sprite.setSize(0.2f, 0.2f);
			sprite.setPosition(projectile.getPosition().x,
					projectile.getPosition().y - sprite.getHeight() / 2);
			sprite.draw(batch);
		}
	}

	@Override
	public void dispose() {
		sprite.getTexture().dispose();
	}

}
