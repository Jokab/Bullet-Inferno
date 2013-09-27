package se.dat255.bulletinferno.view;

import java.util.ArrayList;
import java.util.List;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.PlayerShip;
import se.dat255.bulletinferno.model.Renderable;
import se.dat255.bulletinferno.model.mockSegment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class BackgroundView implements Renderable {
	
	private TextureAtlas atlas;
	private TextureRegion[] layers;
	private PlayerShip ship;
	private float SEG_OFFSET;
	Texture tex;
	private final List<mockSegment> sl;

	public BackgroundView(PlayerShip ship, Game game) {
		this.ship = ship;		
		tex = new Texture(Gdx.files.internal("data/backgrounds/bg.png"));
		
		sl = game.getSegments();
	}
	
	@Override
	public void render(SpriteBatch batch) {
		batch.disableBlending();
		batch.draw(tex, ship.getPosition().x, 0, 16, 9, 0, 0, 256, 1024, false, false);
		batch.enableBlending();
		for(mockSegment s : sl){
			Gdx.app.log("BGView","Segment" +s.toString());
			float startX = s.getStart();
			float endX = s.getEnd();
			if(ship.getPosition().x <= startX || startX < ship.getPosition().x+16 ){
				
				batch.draw(s.getEndTexture(), startX, 9, 0, 0, 2, 9, 1, 1, 180);
				batch.draw(s.getTexture(), startX, 0, 0, 0, (endX-startX-2), 9, 1, 1, 0);
				batch.draw(s.getEndTexture(), endX-2, 0, 0, 0, 2, 9, 1, 1, 0);
			}
		}
	}

	@Override
	public void dispose() {
		tex.dispose();
		
	}
}
