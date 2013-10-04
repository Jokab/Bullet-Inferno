package se.dat255.bulletinferno.view;


import java.util.List;

import se.dat255.bulletinferno.Graphics;
import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.PlayerShip;
import se.dat255.bulletinferno.view.MockSegment;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class BackgroundView implements Renderable {
	
	private PlayerShip ship;
	Texture tex;
	private List<MockSegment> sl;
	private Game game;

	public BackgroundView(PlayerShip ship, Game game) {
		this.ship = ship;		
		tex = new Texture(Gdx.files.internal("images/game/background.png"));
		this.game=game;
		
	}
	
	@Override
	public void render(SpriteBatch batch) {
		sl = game.getSegments();
		batch.disableBlending();
		batch.draw(tex, ship.getPosition().x-1, 0, 18, 9, 0, 0, 32, 1024, false, false);
		batch.enableBlending();
		for(MockSegment s : sl){
			//Gdx.app.log("BGView","Segment" +s.toString());
			float startX = s.getStart();
			float endX = s.getEnd();
			if(ship.getPosition().x <= startX || startX < ship.getPosition().x+16 ){
				
				batch.draw(s.getEndTexture(), startX, 9, 0, 0, 2, Graphics.GAME_HEIGHT, 1, 1, 180);
				batch.draw(s.getTexture(), startX, 0, 0, 0, (endX-startX-2), Graphics.GAME_HEIGHT, 1, 1, 0);
				batch.draw(s.getEndTexture(), endX-2, 0, 0, 0, 2, Graphics.GAME_HEIGHT, 1, 1, 0);
			}
			
			//TODO: Thread-safe removal of segment that has been passed
				
			/*if(s.getEnd()<ship.getPosition().x){
				game.removeSegment(s);
			}*/
		}
		
	}

	@Override
	public void dispose() {
		tex.dispose();
		
	}
}
