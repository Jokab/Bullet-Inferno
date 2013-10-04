package se.dat255.bulletinferno.model.mock.map;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.Obstacle;
import se.dat255.bulletinferno.model.map.ObstacleDefinition;

public class SimpleObstacleDefinitionMock implements ObstacleDefinition {
	
	public List<Obstacle> createdObstacles = new ArrayList<Obstacle>();
	
	@Override
	public Obstacle createObstacle(Game game, Vector2 position) {
		Obstacle obstacle = new SimpleObstacleMock();
		createdObstacles.add(obstacle);
		return obstacle;
	}

}
