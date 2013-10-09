package se.dat255.bulletinferno.view;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Shows a trail of smoke.
 */
public class SmokeTrail implements Renderable {

	/**
	 * Class representing a particle. Please note that particle velocity is shared!
	 */
	private class Particle {

		/** Current position, bottom-left world coordinates. */
		private Vector2 position = new Vector2();

	}

	/** Standard particle dimensions, in world coordinates. */
	private static final Vector2 PARTICLE_DIMENSIONS = new Vector2(0.3f, 0.3f);

	/** The texture to use for particles. */
	private final Texture texture;

	/** Velocity set upon particle "creation". */
	private Vector2 velocity = new Vector2(-0.5f, 0.3f);

	/** The spawn point for new particles. */
	private Vector2 spawnPoint = new Vector2(0, 0);

	/** All particles, inflated with numberOfParticles ones immediately upon construction. */
	private List<Particle> particles;

	/** Total number of particles (even non-displayed). */
	private final int numberOfParticles;

	/** Current number of displayed particles, excluding hidden (not yet displayed) ones. */
	private int currentParticles = 0;

	/** The oldest particle. Will be the next candidate for replacement if the system is full. */
	private int oldestParticle = 0;

	/** Accumulates time, spawnTime eats from this when it reaches over spawnTime. Seconds. */
	private float timeAccumulator = 0f;

	/**
	 * The time in seconds to wait between spawns.
	 * 
	 * @see SmokeTrail#timeAccumulator
	 */
	private float spawnTime = 0.1f;

	/**
	 * @param texture
	 *        the texture to use for smoke particles.
	 * @param numberOfParticles
	 *        the number of particles to allocate. Will never display more particles than this.
	 */
	public SmokeTrail(Texture texture, int numberOfParticles) {
		this.texture = texture;

		// Init particles:
		this.numberOfParticles = numberOfParticles;
		this.particles = new ArrayList<Particle>(numberOfParticles);
		for (int i = 0; i < numberOfParticles; i++) {
			particles.add(new Particle());
		}
	}

	/**
	 * Sets the standard velocity for new particles in this system.
	 */
	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	/**
	 * Sets the spawn point for newly created particles.
	 */
	public void setSpawnPoint(Vector2 spawnPoint) {
		this.spawnPoint = spawnPoint;
	}

	/**
	 * Makes sure the particles held are up-to-date (time dependent).
	 */
	private void spawnNewParticles() {
		timeAccumulator += Gdx.graphics.getDeltaTime();

		for (; timeAccumulator >= spawnTime; timeAccumulator -= spawnTime) {
			// Set position (moving from center coordinates to bottom-left coordinates).
			Particle particle = particles.get(oldestParticle);
			particle.position.x =
					spawnPoint.x + velocity.x * timeAccumulator - PARTICLE_DIMENSIONS.x / 2;
			particle.position.y =
					spawnPoint.y + velocity.y * timeAccumulator - PARTICLE_DIMENSIONS.y / 2;

			oldestParticle++;
			if (oldestParticle >= numberOfParticles) {
				oldestParticle = 0;
			}

			if (currentParticles < numberOfParticles - 1) {
				currentParticles++;
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render(SpriteBatch batch) {
		// Spawn new particles
		spawnNewParticles();

		// Update current and draw.
		float deltaTime = Gdx.graphics.getDeltaTime();
		for (int i = 0; i < currentParticles; i++) {
			Particle particle = particles.get(i);
			particle.position.x += velocity.x * deltaTime;
			particle.position.y += velocity.y * deltaTime;

			float scale = 1.0f;// + (i % 2) / 20f + (i % 3) / 20f + (i % 5) / 20f;
			batch.draw(texture, particle.position.x, particle.position.y,
					PARTICLE_DIMENSIONS.x * scale, PARTICLE_DIMENSIONS.y * scale);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
	}

}