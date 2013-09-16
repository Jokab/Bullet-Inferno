Instructions for starting to develop for Android
------------------------------------------------
1. Download Android SDK
    http://developer.android.com/sdk/index.html
   It includes eclipse and all you need to get started!

   
2. Clone the repo


3.1. In eclipse: File -> Import -> General -> Existing...

3.2. Select the repo folder

3.3. Mark "Bullet-Inferno" and "Bullet-Inferno-desktop" (NOT "Bullet-Inferno-android"!)

3.4. Finish


4.1. In eclipse: File -> Import -> Android -> Existing...

4.2. Select the repo folder

4.3. Finish


5.1. In eclipse: Window -> Android SDK Manager

5.2. Mark "Android 4.0.3 (API15)" and press "Install X packages"


6. Restart Eclipse and you're ready to deploy!

By clicking the -android project and pressing deploy, you deploy to device

By clicking the -desktop project and pressing deploy, you deploy to computer


Good to know:
-------------
- Wiki with loads of info about LibGDX: https://code.google.com/p/libgdx/wiki/TableOfContents
- Javadocs online for LibGDX: http://libgdx.badlogicgames.com/nightlies/docs/api/
- Forums with lots of great help and info: http://www.badlogicgames.com/forum/
- On computer deployment, you can log to console with both System.out.println() and Gdx.app.log(tag, message). 
  But if you use Gdx.app.log(...) it also works on Android when deploying!

  
Good to read:
-------------
- Graphics: https://code.google.com/p/libgdx/wiki/SpriteBatch
- Loading assets asynchronously: https://code.google.com/p/libgdx/wiki/AssetManager
- Music: https://code.google.com/p/libgdx/wiki/AudioMusic
- SFX: https://code.google.com/p/libgdx/wiki/AudioSound
  

Common errors:
--------------
- If you have HTC, you might need to get their usb drivers, exists at:

  http://www.mediafire.com/download/qabo1tue2v1ky75/HTCDriver_4.1.0.001.msi

- If the Android project shows weird errors, rightclick the project and click:

  Android Tools -> Fix Project Properties

- If the Android project refuses to compile, with an error containing the class R.java

  There's probably something wrong with a XML layout, check the ones you changed
  
- The Desktop project can't find an asset (image, sound etc) you added

  Make sure it's in the Android project under the assets folder
  
  Try restarting eclipse (or manually refreshing the projects with F5)
