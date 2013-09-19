package se.dat255.bulletinferno.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import se.dat255.bulletinferno.model.Projectile;
import se.dat255.bulletinferno.model.Renderable;

public class ProjectileView implements Renderable {
	
	private final Sprite sprite;
	private final Projectile projectile;
	
	public ProjectileView(Projectile projectile) {
		Texture texture = new Texture(Gdx.files.internal("data/projectile.png"));
		sprite = new Sprite(texture);
		sprite.setOrigin(0, 0);
		sprite.setSize(1, 1);
		this.projectile = projectile;
	}
	
	@Override
	public void render(SpriteBatch batch) {
		float x = projectile.getPosition().x;
		float y = projectile.getPosition().y;
		sprite.setPosition(x, y);
		sprite.draw(batch);
	}

	@Override
	public void dispose() {
		sprite.getTexture().dispose();
	}

}
