package se.dat255.bulletinferno.util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public enum TextureDefinitionImpl implements TextureDefinition {
	DEFAULT_SHIP(texture("data/defaultEnemy.png")),
	FAST_SHIP(texture("data/defaultEnemy.png")),
	SLOW_SHIP(texture("data/defaultEnemy.png")),
	MAP_MOUNTAIN(texture("images/game/mountain.png")),

	DEFAULT_ENEMY_SHIP(texture("data/defaultEnemy.png")),
	SPECIAL_ENEMY_SHIP(texture("data/specialEnemy.png")),
	HARD_BOSS_SHIP(texture("data/boss.png")),
	EASY_BOSS_SHIP(texture("data/bossEnemy.png")),

	// Player ship
	PLAYER_DEFAULT(texture("data/playerShip.png")),
	PLAYER_EXPLOSION(texture("data/explosion.gif")),

	// Weapons
	MISSILE_LAUNCHER(texture("data/missileLauncher.png")),
	DISORDERER(texture("data/disorderer.png")),
	MISSILE_LAUNCHER_LARGE(texture("data/missileLauncherLarge.png")),
	DISORDERER_LARGE(texture("data/disordererLarge.png")),
	SNIPER_RIFLE(texture("data/sniperRifle.png")),

	// Projectiles
	RED_PROJECTILE(textureAtlas("data/projectiles/packedProjectiles.atlas", "redDotProjectile.png")),
	GREEN_PROJECTILE(textureAtlas("data/projectiles/packedProjectiles.atlas", "greenDotProjectile")),
	MISSILE(textureAtlas("data/projectiles/packedProjectiles.atlas", "missile")),
	PLASMA(textureAtlas("data/projectiles/packedProjectiles.atlas", "plasma")),
	HIGH_VELOCITY_PROJECTILE(textureAtlas("data/projectiles/packedProjectiles.atlas", "missile")),

	// Buttons
	PAUSE_SCREEN(texture("images/gui/screen_pause.png")),
	BLUE_BACKGROUND(texture("images/game/background.png")),
	GAMEOVER_SCREEN(texture("images/gui/screen_gameover.png")),
	PROJECTILE_RAIN(texture("data/projectileRain.png")),
	TAKE_DAMAGE_MODIFIER(texture("data/shieldMenu.png")),
	LOADOUT_START_BUTTON(texture("data/startBtn.png")),
	HUD_TEXTURE(texture("images/game/hud.png")),

	// Particles
	SMOKE_PARTICLE(texture("images/particles/smoke.png")),

	;

	private TextureHolder holder;

	private TextureDefinitionImpl(TextureHolder holder) {
		this.holder = holder;
	}
	
	@Override
	public TextureRegion getTexture(AssetManager manager) {
		return holder.getTexture(manager);
	}

	@Override
	public String getSrouce() {
		return holder.getSource();
	}
	
	private static TextureHolder texture(String source) {
		return new TextureHolderImpl(source);
	}

	private static TextureHolder textureAtlas(String source, String region) {
		return new TextureHolderAtlasImpl(source, region);
	}
	
	@Override
	public void dispose() {
		holder.dispose();
	}
	
	@Override
	public void loadSource(AssetManager manager) {
		holder.loadSource(manager);
	}

	@Override
	public void unloadSource(AssetManager manager) {
		holder.unloadSource(manager);
	}
}
