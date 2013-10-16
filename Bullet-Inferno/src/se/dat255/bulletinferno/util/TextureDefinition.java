package se.dat255.bulletinferno.util;


public interface TextureDefinition {	
	/**
	 * Returns the closes match to the given pixel density.
	 * @param density in pixels per meter
	 * @return source
	 */
	public String getTexture(int pixelsPerMeter);
}
