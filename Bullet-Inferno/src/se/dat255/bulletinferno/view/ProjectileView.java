package se.dat255.bulletinferno.view;

import se.dat255.bulletinferno.model.ModelEnvironment;
import se.dat255.bulletinferno.model.weapon.Projectile;
import se.dat255.bulletinferno.util.ResourceManager;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

public class ProjectileView implements Renderable {

	private final Sprite sprite;
	private final ModelEnvironment modelEnvironment;
	private final ResourceManager resourceManager;
	private TextureRegion texture;

	private final Vector3 minBounds = new Vector3(0, 0, 0);
	private final Vector3 maxBounds = new Vector3(0, 0, 0);
	private final BoundingBox bounds = new BoundingBox(minBounds, maxBounds);

	public ProjectileView(ModelEnvironment modelEnvironment, ResourceManager resourceManager) {
		this.resourceManager = resourceManager;
		this.modelEnvironment = modelEnvironment;
		sprite = new Sprite();
	}

	@Override
	public void render(SpriteBatch batch, Camera viewport) {
		for (Projectile projectile : modelEnvironment.getProjectiles()) {
			minBounds.x = projectile.getPosition().x;
			minBounds.y = projectile.getPosition().y;
			maxBounds.x = minBounds.x + projectile.getDimensions().x;
			maxBounds.y = minBounds.y + projectile.getDimensions().y;
			bounds.set(minBounds, maxBounds);

			if (viewport.frustum.boundsInFrustum(bounds)) {
				texture = resourceManager.getTexture(projectile.getType());
				sprite.setRegion(texture);

				sprite.setSize(projectile.getDimensions().x, projectile.getDimensions().y);
				sprite.setPosition(projectile.getPosition().x - projectile.getDimensions().x / 2,
						projectile.getPosition().y - projectile.getDimensions().y / 2);

				sprite.draw(batch);
			}
		}
	}

	@Override
	public void dispose() {
	}

}
