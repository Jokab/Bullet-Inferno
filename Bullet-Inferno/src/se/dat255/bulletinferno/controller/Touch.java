package se.dat255.bulletinferno.controller;

import se.dat255.bulletinferno.Graphics;
import se.dat255.bulletinferno.model.PlayerShip;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

/**
 * The main touch controller More info:
 * https://code.google.com/p/libgdx/wiki/InputEvent
 * 
 * @author Marc Jamot
 * @version 1.0
 * @since 13-09-12
 */
public class Touch implements InputProcessor {

	private int UPKEY = 51;
	private int DOWNKEY = 47; 
	private int FIREKEY = 62;
	
	/**
	 * The game camera. This is needed to unproject x/y values to the virtual
	 * screen size.
	 */
	private final Graphics graphics;

	/**
	 * Hard reference to the ship model. TODO: Probably shouldn't be directly
	 * accessed?
	 */
	private final PlayerShip ship;

	/**
	 * The finger index controlling the position of the ship.
	 */
	private int steeringFinger = -1;

	public Touch(final Graphics graphics, final PlayerShip ship) {
		this.graphics = graphics;
		this.ship = ship;
	}

	@Override
	public boolean keyDown(int keycode) {
		if(keycode==UPKEY){
			ship.moveTo(Graphics.GAME_HEIGHT);
		}
		if(keycode==DOWNKEY){
			ship.moveTo(0f);
		}
		if(keycode==FIREKEY){
			ship.fireWeapon();
		}
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {	
		if(keycode==UPKEY){
			ship.stopMovement();
		}
		if(keycode==DOWNKEY){
			ship.stopMovement();
		}
		
		
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		
		// Unproject the touch location to the virtual screen.
		Vector2 touchVector = new Vector2(screenX, screenY);
		Graphics.screenToWorld(touchVector);

		Gdx.app.log("Touch", "Down id = " + pointer);

		if (touchVector.x <= Graphics.GAME_WIDTH / 2) {
			// Left half of the screen
				//Move ship by giving the touch coordinate to the moveTo-method
				ship.stopMovement();
				if(touchVector.y > ship.getPosition().y + 0.1f){
					ship.moveTo(touchVector.y);
				}else if(touchVector.y < ship.getPosition().y - 0.1f){
					ship.moveTo(touchVector.y);
				}else{
				;	
				}
			//}
		} else {
			// Right half of the screen
			// Currently bugged. Needs a load timer for weapon.
			// Dragging causes MANY projectiles
			//ship.fireWeapon();
		}
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			ship.stopMovement();
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		//Calls touchDown with default (left) button.
		touchDown(screenX, screenY, pointer, Input.Buttons.LEFT);
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		//Same as touchDragged but for desktop
		touchDown(screenX, screenY, 0, Input.Buttons.LEFT);
		
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
