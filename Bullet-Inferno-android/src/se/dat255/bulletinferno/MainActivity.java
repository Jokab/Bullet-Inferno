package se.dat255.bulletinferno;

import se.dat255.bulletinferno.controller.MasterController;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

/**
 * Main entry for the Android deployment
 * Game development should be done in the "Bullet-Inferno" project.
 * Here all the menu screens should be made.
 * @author Marc Jamot
 * @version 1.0
 * @since 2013-09-12
 */
public class MainActivity extends AndroidApplication {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useAccelerometer = false;
        cfg.useCompass = false;
        cfg.useGL20 = true;
        cfg.useWakelock = true;
        cfg.getTouchEventsForLiveWallpaper = false;
        cfg.hideStatusBar = true;
        
        initialize(new MasterController(), cfg);
    }
}