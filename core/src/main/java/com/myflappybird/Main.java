package com.myflappybird;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.myflappybird.frameworkhelper.AssetLoader;
import com.myflappybird.screens.GameScreen;
import com.myflappybird.screens.MenuScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

    @Override
    public void create() {
        Gdx.app.log("myFlappyBird", "Creating the game");
        Gdx.graphics.setVSync(true);
        AssetLoader.load();
        setScreen(new MenuScreen());
    }

    @Override
    public void dispose() {
        super.dispose();
        AssetLoader.dispose();
    }
}
