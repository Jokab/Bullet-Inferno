package se.dat255.bulletinferno.units.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class EnemyView {

	private Enemy enemy;
	private Texture texture;
	private Sprite sprite;
	
	public EnemyView(Enemy enemy) {
		this.enemy = enemy;
		
		texture = new Texture(Gdx.files.internal("data/enemyShip.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		TextureRegion region = new TextureRegion(texture, 0, 0,
				texture.getWidth(), texture.getHeight());
		
		sprite = new Sprite(region);
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setSize(sprite.getWidth(), sprite.getHeight());
		
		sprite.setRotation(180);
	}
	
	public void render(SpriteBatch batch) {
		mirrorPosIfOutsideView();
		
		sprite.setPosition(enemy.getPosition().x, enemy.getPosition().y);
		sprite.draw(batch);
	}

	private void mirrorPosIfOutsideView() {
		if(enemy.getPosition().x > Gdx.graphics.getWidth() / 2) {
			enemy.getPosition().x = -Gdx.graphics.getWidth()/2 - sprite.getWidth();
		}
	}
}
