package com.myflappybird.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.myflappybird.frameworkhelper.InputHandler;
import com.myflappybird.gameworld.GameRenderer;
import com.myflappybird.gameworld.GameWorld;

public class GameScreen implements Screen {

    private GameWorld world;
    private GameRenderer renderer;
    private float runTime = 0;

    public GameScreen() {
        float gameWidth = Gdx.graphics.getWidth();
        float gameHeight = Gdx.graphics.getHeight();

        int midPointY = (int) (gameHeight / 2);

        world = new GameWorld(midPointY, (int)gameHeight, (int)gameWidth);
        renderer = new GameRenderer(world, (int)gameHeight, (int)gameWidth, midPointY);
        Gdx.input.setInputProcessor(new InputHandler(world));
    }

    @Override
    public void show() {
        Gdx.app.log("GameScreen", "show called");
    }

    @Override
    public void render(float v) {
        runTime += v;
        world.update(v);
        renderer.render(runTime);
    }

    @Override
    public void resize(int width, int height) {
        float gameWidth = width;
        float gameHeight = height;

        int midPointY = (int) (gameHeight / 2);
        world = new GameWorld(midPointY, (int)gameHeight, (int)gameWidth);
        renderer = new GameRenderer(world, (int)gameHeight, (int)gameWidth, midPointY);
        Gdx.input.setInputProcessor(new InputHandler(world));
        world.getBird().resize(width, height);
        world.getScroller().getPipe1().resize(width, height);
        world.getScroller().getPipe2().resize(width, height);
        world.getScroller().getPipe3().resize(width, height);
        world.getScroller().getFrontGrass().resize(width, height);
        world.getScroller().getBackGrass().resize(width, height);
        renderer.resize(width, height);
    }

    @Override
    public void pause() {
        Gdx.app.log("GameScreen", "pause called");
    }

    @Override
    public void resume() {
        Gdx.app.log("GameScreen", "resume called");
    }

    @Override
    public void hide() {
        Gdx.app.log("GameScreen", "hide called");
    }

    @Override
    public void dispose() {

    }
}
