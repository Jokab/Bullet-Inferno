package se.dat255.bulletinferno.view;

import se.dat255.bulletinferno.model.Enemy;
import se.dat255.bulletinferno.model.Renderable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class EnemyView implements Renderable {

	private final Enemy enemy;
	private final Texture texture;
	private final Sprite sprite;

	public EnemyView(Enemy enemy) {
		this.enemy = enemy;

		texture = new Texture(Gdx.files.internal("data/enemyShip.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		sprite = new Sprite(texture);
		sprite.setSize(2, 2);
		sprite.setOrigin(0, 0);
		sprite.setPosition(0, 0);
	}

	@Override
	public void render(SpriteBatch batch) {
		mirrorPosIfOutsideView();
		sprite.draw(batch);
	}

	private void mirrorPosIfOutsideView() {
		// if(enemy.getPosition().x > Gdx.graphics.getWidth() / 2) {
		// enemy.getPosition().x = -Gdx.graphics.getWidth()/2 -
		// sprite.getWidth();
		// }
	}
}
