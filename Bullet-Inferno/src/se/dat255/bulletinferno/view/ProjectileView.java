package se.dat255.bulletinferno.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.Projectile;
import se.dat255.bulletinferno.model.Renderable;

public class ProjectileView implements Renderable {
	
	private final Sprite sprite;
	private final Game game;
	
	public ProjectileView(Game game) {
		this.game = game;		
		Texture texture = new Texture(Gdx.files.internal("data/projectile.png"));
		sprite = new Sprite(texture);
        sprite.setOrigin(0, 0);
		sprite.setSize(0.2f, 0.2f);
	}
	
	@Override
	public void render(SpriteBatch batch) {
		for(Projectile projectile : game.getProjectiles()) {
			sprite.setPosition(projectile.getPosition().x, projectile.getPosition().y);
			sprite.draw(batch);
		}
	}

	@Override
	public void dispose() {
		sprite.getTexture().dispose();
	}

}
