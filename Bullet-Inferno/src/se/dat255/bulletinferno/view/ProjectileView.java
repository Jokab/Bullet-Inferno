package se.dat255.bulletinferno.view;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.ManagedTexture;
import se.dat255.bulletinferno.model.Projectile;
import se.dat255.bulletinferno.model.ResourceManager;

public class ProjectileView implements Renderable {

	private Sprite sprite;
	private final Game game;
	private ResourceManager resourceManager;
	private Texture texture;
	private List<ManagedTexture> managedTextures;

	public ProjectileView(Game game, ResourceManager resourceManager) {
		this.resourceManager = resourceManager;
		this.game = game;
		this.managedTextures = new ArrayList<ManagedTexture>();
		
	}

	@Override
	public void render(SpriteBatch batch) {
		for (Projectile projectile : game.getProjectiles()) {
			ManagedTexture mTexture = resourceManager.getManagedTexture(projectile.getType());
			if(!managedTextures.contains(mTexture)) {
				managedTextures.add(mTexture);
			}
			texture = mTexture.getTexture();
			sprite = new Sprite(texture);
			
			sprite.setTexture(texture);
			sprite.setOrigin(0, 0);
			sprite.setSize(0.5f, 0.3f);
			sprite.setPosition(projectile.getPosition().x,
					projectile.getPosition().y - sprite.getHeight() / 2);
			sprite.draw(batch);
		}
	}

	@Override
	public void dispose() {
		for(ManagedTexture mTexture : managedTextures) {
			mTexture.dispose(resourceManager);
		}
	}

}
