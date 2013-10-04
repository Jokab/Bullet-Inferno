package se.dat255.bulletinferno.model.mock.map;

import se.dat255.bulletinferno.model.map.Obstacle;
import se.dat255.bulletinferno.model.physics.Collidable;

public class SimpleObstacleMock implements Obstacle {

	@Override
	public void preCollided(Collidable other) {		
	}

	@Override
	public void postCollided(Collidable other) {
	}

	@Override
	public void dispose() {
	}

}
