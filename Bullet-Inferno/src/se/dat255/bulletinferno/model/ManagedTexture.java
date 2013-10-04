package se.dat255.bulletinferno.model;

import com.badlogic.gdx.graphics.Texture;

public interface ManagedTexture {

	Texture getTexture();

	void dispose(ResourceManager resourceManager);
}
