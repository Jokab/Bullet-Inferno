package se.dat255.bulletinferno.util;

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;

import se.dat255.bulletinferno.test.Common;

public class GameActionImplTest {
	@BeforeClass
	public static void beforeTests() {
		Common.loadEssentials();
	}
	
	@Test
	public void testGetAction() {
		for(GameActionImpl action : GameActionImpl.values()) {
			assertNotNull("Each GameActionImpl should have a non-null value for getAction", 
					action.getAction());
		}
	}
}
