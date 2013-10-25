Changelog
=========
For a list of all releases, please see https://github.com/Jokab/Bullet-Inferno/releases.

Release 6 (2013-10-27)
----------------------
* Fixed a bug where devices with a not 16:9 resolution would not have the entire player ship and/or background visible. 
* Added a new background to the loading screen.
* The "damageAll" special effect now only damages enemies currently seen on the screen.
* Polished multiple textures.
* The special ability now signals availability and displays the remaining cooldown.
* The loadout screen's passive abilities now have a title label explaining them.
* Fine-tuning damage, hitboxes and other minor polish.

Release 5 (2013-10-18)
----------------------
* Fixed a crash that would occur if the boss was killed, as he had no death sound.
* Added a button to the in-game HUD for activating the special ability.
* The loading screen now features a loading bar instead of only a text indicator.
* The special bullet rain ability no longer has their bullets colliding with the ground.
* The player is no longer instantly killed if colliding with an enemy, but rather has it health reduced by a large portion.
* The game over screen now covers the entire screen, it was off by ~1px.
* Loadout menu selection buttons no longer require two clicks to show list of choices.
* All textures has had their scaling filters improved, resulting in major improvements to sharpness of many textures throughout the game. 
* A new high-def player ship texture has been added.
* The player can no longer move outside the screen.
* The HUD health bar is now faded when the player is at full health, and also properly updates after a  death from collision. 
* Added new enemies and bosses.
* Textures are now chosen depending on device resolution, resulting in better looking textures on most devices!
* New weapons for both the enemies and the player.
* Bosses no longer spawn on a slice where the player has its movement restricted.
* The player can no longer collide with the trees on the map.
* The player no longer receives a passive score from fighting a boss, as to make the player focus on killing the boss instead of staying alive as long as possible.
* The player had its hitbox tweaked.
* The loadout screen has had a major graphical overhaul.
* Most projectiles had their damage adjusted.

Release 4 (2013-10-11)
-----------------------
* Fixed a bug where the explosion texture was not displayed, and corrected its position and size.
* Fixed a bug where resuming the game did not actually resume the game.
* Fixed a bug where the bosses did not "move" onto the screen, but instead stopped right outside.
* Fixed a bug where the player could fire even when he was dead.
* The app name displayed on android devices is now "Bullet-Inferno", instead of the previous placeholder name.
* Optimized the settings when starting from an Android device to match the features used by the game.
* Added score display to the HUD.
* Added heavy weapons.
* Made heavy weapons fire on touch and standard weapons auto-fire.
* Changed the loadout screen to allow the player to change all equipable "things".
* The ship now has a smoke trail following it.
* Updated the slice textures.
* Added a cooldown to the heavy weapons.
* Added audio effects to the game (currently only for enemy deaths)
* Major code changes and optimizations.

Release 3 (2013-10-04)
-----------------------
* Fixed a bug that made the game crash when an enemy was killed.
* Fixed a bug that made all enemies share the same texture.
* Fixed a rare crash that occurred if an enemy both died and got outside the screen on the same frame.
* Implemented moving segments over a static background.
* Added a new enemy texture.
* Made sure to remove enemies they are outside the screen.
* Added two special abilities, one that damages all enemies instantly and one that spawns a "bullet rain".
* Added randomized map segments to the game!
* Added a game over screen to the game.
* Added a first boss to the game.

Release 2 (2013-09-27)
----------------------
* Added basic physics to the game.
* Implemented keyboard movement and smooth movement by touch, mouse and keyboard.
* Added a first menu screen to the game, loadout.
* Made projectiles deal damage and remove themselves on hit.
* Added pause icon and it's view.
* Added collision code with regards to teams (i.e. enemies might not want to collide with enemies).

Release 1 (2013-09-20)
----------------------
Initial release.