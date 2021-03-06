package se.dat255.bulletinferno.model.mock.map;

import java.util.ArrayList;
import java.util.List;

import se.dat255.bulletinferno.model.map.Obstacle;
import se.dat255.bulletinferno.model.map.ObstacleDefinition;
import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;

import com.badlogic.gdx.math.Vector2;

public class SimpleObstacleDefinitionMock implements ObstacleDefinition {
	
	public List<Obstacle> createdObstacles = new ArrayList<Obstacle>();
	
	@Override
	public Obstacle createObstacle(PhysicsEnvironment physics, Vector2 position) {
		Obstacle obstacle = new SimpleObstacleMock();
		createdObstacles.add(obstacle);
		return obstacle;
	}
}
