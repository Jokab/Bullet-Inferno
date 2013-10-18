package se.dat255.bulletinferno.util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public enum TextureDefinitionImpl implements TextureDefinition {
	DEFAULT_SHIP(texture("data/katze.png")),
	FAST_SHIP(texture("data/katze.png")),
	SLOW_SHIP(texture("data/katze.png")),

	KATZE(texture("data/katze.png")),
	SQUIB(texture("data/squib.png")),
	EHMO(texture("data/EHMO.png")),
	DRIPPER(texture("data/dripper.png")),

	// Player ship
	PLAYER_DEFAULT(texture("data/playerShip.png")),
	PLAYER_EXPLOSION(texture("data/explosion.gif")),

	// Weapons
	STANDARD_MACHINE_GUN(texture("data/machineGun.png")),
	STANDARD_MINI_GUN(texture("data/miniGun.png")),
	STANDARD_PLASMA_GUN(texture("data/plasmaGun.png")),
	HEAVY_EGG_CANNON(texture("data/eggCannon.png")),
	HEAVY_LASER_CANNON(texture("data/laserCannon.png")),
	MISSILE_LAUNCHER(texture("data/missileLauncher.png")),
	DISORDERER(texture("data/disorderer.png")),
	MISSILE_LAUNCHER_LARGE(texture("data/missileLauncherLarge.png")),
	DISORDERER_LARGE(texture("data/disordererLarge.png")),
	SNIPER_RIFLE(texture("data/sniperRifle.png")),
	LASER(texture("data/laser.png")),

	// Projectiles
	VELOCITY_BULLET(textureAtlas("data/packedProjectiles.atlas", "bullet")),
	ROUND_BULLET(textureAtlas("data/packedProjectiles.atlas", "bullet")),
	PLASMA(textureAtlas("data/packedProjectiles.atlas", "plasma")),
	EGG(textureAtlas("data/packedProjectiles.atlas", "egg")),
	RED_PROJECTILE(textureAtlas("data/packedProjectiles.atlas", "redDotProjectile")),
	GREEN_PROJECTILE(textureAtlas("data/packedProjectiles.atlas", "greenDotProjectile")),
	MISSILE(textureAtlas("data/packedProjectiles.atlas", "missile")),
	SPECIAL_ABILITY_MISSILE(textureAtlas("data/packedProjectiles.atlas", "missile")),
	HIGH_VELOCITY_PROJECTILE(textureAtlas("data/packedProjectiles.atlas", "missile")),

	// Loadout menu specific
	LOADOUT_PASSIVE_RELOADING_TIME(texture("data/reloadSpeed.png")),
	LOADOUT_PASSIVE_TAKE_DAMAGE_MODIFIER(texture("data/shieldBoost.png")),
	LOADOUT_PASSIVE_DAMAGE_MODIFIER(texture("data/damageBoost.png")),

	LOADOUT_SPECIAL_PROJECTILE_RAIN(texture("data/projectileRain.png")),
	LOADOUT_SPECIAL_NUKE(texture("data/nuke.png")),

	// Buttons
	PAUSE_SCREEN(texture("images/gui/screen_pause.png")),
	BLUE_BACKGROUND(texture("images/game/background.png")),
	GAMEOVER_SCREEN(texture("images/gui/screen_gameover.png")),
	PROJECTILE_RAIN(texture("data/projectileRain.png")),
	LOADOUT_START_BUTTON(texture("data/startBtn.png")),
	HUD_TEXTURE(texture("images/game/hud.png")),

	// Slices
	MOUNTAIN_1(textureAtlas("data/packedMountain.atlas", "mountain1")),
	MOUNTAIN_2(textureAtlas("data/packedMountain.atlas", "mountain2")),
	MOUNTAIN_3(textureAtlas("data/packedMountain.atlas", "mountain3")),
	MOUNTAIN_4(textureAtlas("data/packedMountain.atlas", "mountain4")),
	MOUNTAIN_5(textureAtlas("data/packedMountain.atlas", "mountain5")),
	MOUNTAIN_6(textureAtlas("data/packedMountain.atlas", "mountain6")),
	MOUNTAIN_7(textureAtlas("data/packedMountain.atlas", "mountain7")),
	MOUNTAIN_8(textureAtlas("data/packedMountain.atlas", "mountain8")),

	// Particles
	SMOKE_PARTICLE(texture("images/particles/smoke.png")),

	;

	private TextureHolder holder;

	private TextureDefinitionImpl(TextureHolder holder) {
		this.holder = holder;
	}

	private static TextureHolder texture(String source) {
		return new TextureHolderImpl(source);
	}

	private static TextureHolder textureAtlas(String source, String region) {
		return new TextureHolderAtlasImpl(source, region);
	}

	@Override
	public TextureRegion getTexture(AssetManager manager) {
		return holder.getTexture(manager);
	}

	@Override
	public String getSrouce() {
		return holder.getSource();
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
