package se.dat255.bulletinferno.util;

import java.util.Map;

public class TextureDefinitionImpl implements TextureDefinition {
	private final Map<Integer, String> sources;
	
	/**
	 * Creates a new texture definition, from given sources.
	 * The mapping consists of density (pixels per meter) to
	 * an image source
	 * @param sources
	 */
	public TextureDefinitionImpl(Map<Integer, String> sources) {
		if(sources == null || sources.size() == 0) {
			// Take care of this
		}
		this.sources = sources;
	}
	
	@Override
	public String getTexture(int density) {
		if(sources.containsKey(density)) {
			return sources.get(density);
		}
		int closestMatch = 0;
		
		for(int i : sources.keySet()) {
			// Check if there exist a density higher than specified,
			// and find the closes one 
			if(i >= density && Math.abs(density - i) < Math.abs(density - closestMatch))  {
				closestMatch = i;
			}
		}
		
		return sources.get(closestMatch);
		
	}

}
