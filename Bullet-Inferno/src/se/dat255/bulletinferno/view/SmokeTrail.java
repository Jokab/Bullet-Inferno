package se.dat255.bulletinferno.view;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
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
		private final Vector2 position = new Vector2();

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
	private final List<Particle> particles;

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
	private final float spawnTime = 0.05f;

	private final ShaderProgram shaderProgram;

	/**
	 * A factor to apply for the distance (e.g. alpha decay). <tt>distance</tt> is applied to this,
	 * and then <tt>(1-distance)</tt> is clamped to <tt>[0, 1]</tt>.
	 */
	private static final String DISTANCE_DECAY = "0.2";

	/** The (shared) origin of all particles (only used for distance based color effects). */
	private final float[] particleOrigin = new float[] { spawnPoint.x, spawnPoint.y };

	/**
	 * @param texture
	 *        the texture to use for smoke particles.
	 * @param numberOfParticles
	 *        the number of particles to allocate. Will never display more particles than this.
	 */
	public SmokeTrail(Texture texture, int numberOfParticles) {
		this.texture = texture;
		shaderProgram = getShaderProgram();

		// Init particles:
		this.numberOfParticles = numberOfParticles;
		particles = new ArrayList<Particle>(numberOfParticles);
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
	 * Sets the (shared) origin for all particles (only used by distance based color effects).
	 */
	public void setParticleOrigin(Vector2 particleOrigin) {
		this.particleOrigin[0] = particleOrigin.x;
		this.particleOrigin[1] = particleOrigin.y;
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
	public void render(SpriteBatch batch, Camera viewport) {
		// Spawn new particles
		spawnNewParticles();

		// Update current and draw.
		batch.setShader(shaderProgram);
		shaderProgram.setUniform2fv("u_targetPosition", particleOrigin, 0, 2);
		float deltaTime = Gdx.graphics.getDeltaTime();
		for (int i = 0; i < currentParticles; i++) {
			Particle particle = particles.get(i);
			particle.position.x += velocity.x * deltaTime;
			particle.position.y += velocity.y * deltaTime;

			batch.draw(texture, particle.position.x, particle.position.y,
					PARTICLE_DIMENSIONS.x, PARTICLE_DIMENSIONS.y);
		}
		batch.setShader(null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
	}

	/**
	 * Returns the shader program used to apply effects to smoke particles (e.g. distance decay).
	 */
	private static ShaderProgram getShaderProgram() {
		// Skeletton taken by SpriteBatch#createDefaultShader, but modified for custom functionality
		final String vertexShader = (""
				+ "attribute vec4 %POSITION_ATTRIBUTE%;\n"
				+ "attribute vec2 %TEXCOORD_ATTRIBUTE:0%;\n"

				+ "uniform vec2 u_targetPosition;\n"
				+ "uniform mat4 u_projTrans;\n"

				+ "varying vec2 v_texCoords;\n"
				+ "varying float v_alpha;\n"

				+ "void main() {\n"
				+ "  v_texCoords = %TEXCOORD_ATTRIBUTE:0%;\n"

				// Better do it here to save some performance!
				+ "  v_alpha = clamp(1.0 - distance(u_targetPosition, %POSITION_ATTRIBUTE%.xy)\n" +
				"      * %DISTANCE_DECAY%, 0.0, 1.0);\n"

				+ "  gl_Position =  u_projTrans * %POSITION_ATTRIBUTE%;\n"
				+ "}\n")
				.replace("%POSITION_ATTRIBUTE%", ShaderProgram.POSITION_ATTRIBUTE)
				.replace("%TEXCOORD_ATTRIBUTE:0%", ShaderProgram.TEXCOORD_ATTRIBUTE + "0")
				.replace("%DISTANCE_DECAY%", DISTANCE_DECAY);

		final String fragmentShader = ""
				+ "#ifdef GL_ES\n"
				+ "#define LOWP lowp\n"
				+ "precision mediump float;\n"
				+ "#else\n"
				+ "#define LOWP \n"
				+ "#endif\n"

				+ "varying vec2 v_texCoords;\n"
				+ "varying float v_alpha;\n"

				+ "uniform sampler2D u_texture;\n"

				+ "void main() {\n"
				+ "  gl_FragColor = vec4(1.0, 1.0, 1.0, v_alpha)\n" +
				"      * texture2D(u_texture, v_texCoords);\n"
				+ "}";

		ShaderProgram shader = new ShaderProgram(vertexShader, fragmentShader);

		if (!shader.isCompiled()) {
			throw new IllegalStateException("shader compilation failed: " + shader.getLog());
		}

		return shader;
	}
}