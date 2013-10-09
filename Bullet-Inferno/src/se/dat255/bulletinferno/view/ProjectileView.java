package se.dat255.bulletinferno.view;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.ModelEnvironment;
import se.dat255.bulletinferno.model.entity.EntityEnvironment;
import se.dat255.bulletinferno.model.weapon.Projectile;
import se.dat255.bulletinferno.util.ManagedTexture;
import se.dat255.bulletinferno.util.ResourceManager;

public class ProjectileView implements Renderable {

	private Sprite sprite;
	private final ModelEnvironment modelEnvironment;
	private ResourceManager resourceManager;
	private Texture texture;
	private List<ManagedTexture> managedTextures;

	public ProjectileView(ModelEnvironment modelEnvironment, ResourceManager resourceManager) {
		this.resourceManager = resourceManager;
		this.modelEnvironment = modelEnvironment;
		this.managedTextures = new ArrayList<ManagedTexture>();
		
	}

	@Override
	public void render(SpriteBatch batch) {
		for (Projectile projectile : modelEnvironment.getProjectiles()) {
			ManagedTexture mTexture = resourceManager.getManagedTexture(projectile.getType());
			if(!managedTextures.contains(mTexture)) {
				managedTextures.add(mTexture);
			}
			texture = mTexture.getTexture();
			sprite = new Sprite(texture);
			
			sprite.setTexture(texture);
			sprite.setSize(projectile.getDimensions().x, projectile.getDimensions().y);
			sprite.setPosition(projectile.getPosition().x-projectile.getDimensions().x/2,
					projectile.getPosition().y-projectile.getDimensions().y/2);
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
