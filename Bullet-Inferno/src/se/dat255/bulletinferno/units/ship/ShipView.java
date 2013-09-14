package se.dat255.bulletinferno.units.ship;

import java.util.Random;

import se.dat255.bulletinferno.MyGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

public class ShipView {
	private Texture texture;
	private Sprite sprite;

	private Camera camera;

	private float y;
	private float x;

	private int finger = 0;

	public ShipView(final Camera camera, final int finger) {
		this.camera = camera;
		this.finger = finger;

		texture = new Texture(Gdx.files.internal("data/ship.png"));
		texture.setFilter(Texture.TextureFilter.Linear,
				Texture.TextureFilter.Linear);

		TextureRegion region = new TextureRegion(texture, 0, 0,
				texture.getWidth(), texture.getHeight());

		sprite = new Sprite(region);
		sprite.setSize(32f * 3, 32f * 3);
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		sprite.setRotation(-90f);
		
		y = 0 - sprite.getHeight() / 2;
		x = -(MyGame.VIRTUAL_WIDTH /2);

		
		// Random color for the ships 
		Random rand = new Random();
		float r = rand.nextFloat();
		float g = rand.nextFloat();
		float b = rand.nextFloat();
		sprite.setColor(new Color(r, g, b, 1));

	}

	public void render(SpriteBatch batch) {
		if (Gdx.input.isTouched(finger)) {
			Vector3 touch = new Vector3(Gdx.input.getX(finger),
					Gdx.input.getY(finger), 0f);
			camera.unproject(touch);
			y = touch.y - sprite.getHeight() / 2;
		}

		sprite.setPosition(x, y);
		sprite.draw(batch);
	}

	public void dispose() {
		texture.dispose();
	}
}
