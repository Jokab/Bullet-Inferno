package se.dat255.bulletinferno.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class mockSegment {

	
	private float startX, endX;
	private String bgColor;
	private Texture bg;
	private TextureRegion myTexture;
	private TextureRegion endTexture;
	private String texturePath;

	public mockSegment(float startX, float endX, String texturePath){
		this.startX = startX;
		this.endX = endX;
		this.bgColor = bgColor;
		this.texturePath = texturePath;
	
	
		bg = new Texture(Gdx.files.internal("" + texturePath));
		
		myTexture = new TextureRegion(bg, 0,0,16,1024);
		endTexture = new TextureRegion(bg, 16,0,(240),1024);
	}
	
	
	
	
	public float getStart(){
		return startX;
	}
	
	public float getEnd(){
		return endX;
	}
	
	@Override
	public String toString() {
		return "mockSegment [startX=" + startX + ", endX=" + endX + ", texture=" + texturePath + "]";
	}


	public String getId(){
		return bgColor;
	}
	
	public TextureRegion getEndTexture(){
		return endTexture;
	}
	
	public TextureRegion getTexture(){
		return myTexture;
	}
	
}
