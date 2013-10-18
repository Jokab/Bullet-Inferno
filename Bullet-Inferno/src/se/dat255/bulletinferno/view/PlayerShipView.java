package se.dat255.bulletinferno.view;

import se.dat255.bulletinferno.model.entity.PlayerShip;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.util.TextureDefinitionImpl;
import se.dat255.bulletinferno.util.Timer;
import se.dat255.bulletinferno.util.TimerImpl;
import se.dat255.bulletinferno.util.Timerable;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class PlayerShipView implements Renderable, Timerable {

	private final TextureRegion shipTexture;
	private final TextureRegion explosion;

	private Sprite shipSprite;
	private Sprite explosionSprite;
	private Timer timer;
	
	private final PlayerShipLoadoutView loadoutView;

	private static final int SMOKE_PARTICLE_COUNT = 100;
	private final TextureRegion smokeTexture;
	private final SmokeTrail smokeTrail;

	private final PlayerShip ship;

	private final Vector2 shipDimensions;

	private Vector2 lastShipPosition = new Vector2();

	private static final float EXPLOSION_TIMEOUT = 1; // second

	public PlayerShipView(final PlayerShip ship, ResourceManager resourceManager) {
		this.ship = ship;

		// this.timer = game.getTimer();
		this.timer = new TimerImpl();
		this.timer.setTime(EXPLOSION_TIMEOUT);
		this.timer.registerListener(this);

		this.shipDimensions = ship.getDimensions();

		shipTexture = resourceManager.getTexture(ship);

		explosion = resourceManager.getTexture(TextureDefinitionImpl.PLAYER_EXPLOSION);

		shipSprite = new Sprite(shipTexture);
		shipSprite.setSize(shipDimensions.x, shipDimensions.y);
		shipSprite.setOrigin(shipSprite.getWidth() / 2, shipSprite.getHeight() / 2);

		explosionSprite = new Sprite(explosion);
		explosionSprite.setSize((int)(shipDimensions.y * 2), (int)(shipDimensions.y * 2));

		// TODO: How should we do with managed textures? No disposal?
		smokeTexture = resourceManager.getTexture(TextureDefinitionImpl.SMOKE_PARTICLE);
		smokeTexture.getTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
		smokeTrail = new SmokeTrail(smokeTexture.getTexture(), SMOKE_PARTICLE_COUNT);
		
		// Load-out view is responsible for displaying the load-out we choose on our ship
		loadoutView = new PlayerShipLoadoutView(ship, resourceManager);
	}

	@Override
	public void render(SpriteBatch batch, Camera viewport) {
		// TODO: this is quite a messy program flow. you can ask me if it seems messed up (jakob)
		if (ship.isDead()) {
			drawExplosion(batch, lastShipPosition);
		} else {
			lastShipPosition = ship.getPosition();
			float x = lastShipPosition.x - shipDimensions.x / 2;
			float y = lastShipPosition.y - shipDimensions.y / 2;

			shipSprite.setPosition(x, y);
			shipSprite.draw(batch);
			
			// Make sure weapons are rendered on top of the ship
			loadoutView.render(batch, viewport);

			// TODO: Fix these values to match some texture offsets (+ break out to constants).
			smokeTrail.setSpawnPoint(
					new Vector2(lastShipPosition.x - 0.05f, lastShipPosition.y - 0.1f));
			smokeTrail.setParticleOrigin(lastShipPosition);
			smokeTrail.render(batch, viewport);

		}
	}

	// TODO: Debug, should be removed
	/*
	 * private List<Shape> shapes = new ArrayList<Shape>();
	 * private ShapeRenderer shapeRenderer = new ShapeRenderer();
	 * private void drawDebug(SpriteBatch batch) {
	 * if(shapes.isEmpty()){
	 * for(Fixture f : ship.getBody().getFixtureList()) {
	 * shapes.add(f.getShape());
	 * }
	 * }
	 * 
	 * for(Shape s : shapes) {
	 * shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
	 * if(s.getType() == Shape.Type.Circle) {
	 * shapeRenderer.begin(ShapeType.Line);
	 * Vector2 loc = ((CircleShape)s).getPosition();
	 * Float radius = ((CircleShape)s).getRadius();
	 * shapeRenderer.circle(loc.x, loc.y, radius);
	 * } else if(s.getType() == Shape.Type.Polygon) {
	 * shapeRenderer.begin(ShapeType.Filled);
	 * PolygonShape poly = (PolygonShape) s;
	 * Vector2 hej = new Vector2();
	 * for(int i = 0; i < poly.getVertexCount(); i++) {
	 * poly.getVertex(i, hej);
	 * shapeRenderer.circle(hej.x + lastShipPosition.x, hej.y + lastShipPosition.y, 0.2f);
	 * }
	 * }
	 * shapeRenderer.circle(lastShipPosition.x, lastShipPosition.y, 0.2f);
	 * shapeRenderer.end();
	 * }
	 * }
	 */

	private void drawExplosion(SpriteBatch batch, Vector2 pos) {
		if (!timer.isFinished()) {
			timer.start();
		}
		if (explosionSprite != null) {
			explosionSprite.setPosition(pos.x - 1 / 2, pos.y - shipDimensions.y / 2);
			explosionSprite.draw(batch);
		}
	}

	@Override
	public void dispose() {
	}

	@Override
	public void onTimeout(Timer source, float timeSinceLast) {
		explosionSprite = null;
		dispose();
	}
}
