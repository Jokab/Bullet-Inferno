package se.dat255.bulletinferno.view;

import se.dat255.bulletinferno.model.ModelEnvironment;
import se.dat255.bulletinferno.model.entity.Enemy;
import se.dat255.bulletinferno.util.ResourceManager;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

public class EnemyView implements Renderable {

	private final ModelEnvironment models;
	private TextureRegion texture;
	private final Sprite sprite;
	private final ResourceManager resourceManager;

	private final Vector3 minBounds = new Vector3(0, 0, 0);
	private final Vector3 maxBounds = new Vector3(0, 0, 0);
	private final BoundingBox bounds = new BoundingBox(minBounds, maxBounds);

	public EnemyView(ModelEnvironment models, ResourceManager resourceManager) {
		this.models = models;
		this.resourceManager = resourceManager;

		sprite = new Sprite();
		// sprite.setOrigin(0, 0);

	}

	@Override
	public void render(SpriteBatch batch, Camera viewport) {
		for (Enemy enemy : models.getEnemies()) {
			minBounds.x = enemy.getPosition().x;
			minBounds.y = enemy.getPosition().y;
			maxBounds.x = minBounds.x + enemy.getDimensions().x;
			maxBounds.y = minBounds.y + enemy.getDimensions().y;
			bounds.set(minBounds, maxBounds);

			if (viewport.frustum.boundsInFrustum(bounds)) {
				texture = resourceManager.getTexture(enemy);

				sprite.setRegion(texture);
				sprite.setSize(enemy.getDimensions().x, enemy.getDimensions().y);
				sprite.setPosition(enemy.getPosition().x - sprite.getWidth() / 2,
						enemy.getPosition().y - sprite.getHeight() / 2);
				sprite.draw(batch);
			}
		}
	}

	@Override
	public void dispose() {
	}
}
