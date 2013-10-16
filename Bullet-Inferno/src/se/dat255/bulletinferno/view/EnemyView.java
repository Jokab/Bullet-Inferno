package se.dat255.bulletinferno.view;

import se.dat255.bulletinferno.model.ModelEnvironment;
import se.dat255.bulletinferno.model.entity.Enemy;
import se.dat255.bulletinferno.util.ResourceManager;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

public class EnemyView implements Renderable {

	private final ModelEnvironment models;
	private Texture texture;
	private Sprite sprite;
	private ResourceManager resourceManager;
	
	private Vector3 minBounds = new Vector3(0, 0, 0);
	private Vector3 maxBounds = new Vector3(0, 0, 0);
	private BoundingBox bounds = new BoundingBox(minBounds, maxBounds);
	
	public EnemyView(ModelEnvironment models, ResourceManager resourceManager) {
		this.models = models;
		this.resourceManager = resourceManager;
		
		this.sprite = new Sprite();
		//sprite.setOrigin(0, 0);
		
	}

	@Override
	public void render(SpriteBatch batch, Camera viewport) {
		for(Enemy enemy : models.getEnemies()) {
			minBounds.x = enemy.getPosition().x;
			minBounds.y = enemy.getPosition().y;
			maxBounds.x = minBounds.x + enemy.getDimensions().x;
			maxBounds.y = minBounds.y + enemy.getDimensions().y;
			bounds.set(minBounds, maxBounds);

			if(viewport.frustum.boundsInFrustum(bounds)) {
				this.texture = resourceManager.getTexture(enemy);
				this.sprite.setTexture(texture);
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
