package se.dat255.bulletinferno.util;

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import se.dat255.bulletinferno.test.Common;

public class ResourceManagerImplTest {

	@BeforeClass
	public static void beforeTests() {
		Common.loadEssentials();
	}
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testAllTextureTypesHavePath() {
		// Make sure every TextureType has a path
		for (TextureDefinitionImpl textureType : TextureDefinitionImpl.values()) {
			assertNotNull("Each TextureType should have a non-null path",
					textureType.getSource());
		}
	}

	@Test
	public void loadNotExistingTextureIdentifier() throws RuntimeException {
		thrown.expect(RuntimeException.class);
		thrown.expectMessage(String.format(
				"Resource with identifier '%s' could not be found", "NOT-EXISTING"));

		ResourceManagerImpl manager = new ResourceManagerImpl();
		ResourceIdentifier notExistingIdent = new ResourceIdentifier() {
			@Override
			public String getIdentifier() {
				return "NOT-EXISTING";
			}
		};

		manager.getTexture(notExistingIdent);
	}

	@Test
	public void loadNotLoadedTexture() throws RuntimeException {
		TextureDefinitionImpl definition = TextureDefinitionImpl.values()[0];

		thrown.expect(RuntimeException.class);
		thrown.expectMessage("Texture " + definition.getSource() + " is not loaded.");

		ResourceManagerImpl manager = new ResourceManagerImpl();

		manager.getTexture(definition);
	}
}
