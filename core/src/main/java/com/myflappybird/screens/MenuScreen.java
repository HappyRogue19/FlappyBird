package com.myflappybird.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.*;
import com.myflappybird.frameworkhelper.AssetLoader;
import com.myflappybird.frameworkhelper.CreateLB;

public class MenuScreen implements Screen {

    private SpriteBatch batcher;
    private OrthographicCamera camera;
    private Viewport viewport;
    private int WORLD_WIDTH = Gdx.graphics.getWidth();
    private int WORLD_HEIGHT = Gdx.graphics.getHeight();
    private Stage stage;
    private Texture button1, button2;

    public MenuScreen() {
        batcher = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        viewport.apply();
        camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
        camera.update();
        stage = new Stage(viewport, batcher);

        button1 = AssetLoader.button1;
        button2 = AssetLoader.button2;
        ImageTextButton playButton = CreateLB.createImageTextButton("PLAY!", button1, button2, null);
        ImageTextButton parametrsButton = CreateLB.createImageTextButton("OPTIONS", button1, button2, null);
        ImageTextButton exitButton = CreateLB.createImageTextButton("EXIT?", button1, button2, null);
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(playButton).size(WORLD_WIDTH*0.3f, WORLD_HEIGHT*0.1f).pad(10);
        table.row();
        table.add(parametrsButton).size(WORLD_WIDTH*0.3f, WORLD_HEIGHT*0.1f).pad(10);
        table.row();
        table.add(exitButton).size(WORLD_WIDTH*0.3f, WORLD_HEIGHT*0.1f).pad(10);

        stage.addActor(table);

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AssetLoader.menu_music.stop();
                AssetLoader.click.play(AssetLoader.volume_sound);
                ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen());
            }
        });

        parametrsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AssetLoader.click.play(AssetLoader.volume_sound);
                ((Game) Gdx.app.getApplicationListener()).setScreen(new ParametersScreen());
            }
        });
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AssetLoader.click.play(AssetLoader.volume_sound);
                AssetLoader.menu_music.stop();
                Gdx.app.exit();
            }
        });
    }

    @Override
    public void show() {
        AssetLoader.menu_music.setVolume(AssetLoader.volume_music);
        AssetLoader.menu_music.setLooping(true);
        AssetLoader.menu_music.play();
        Gdx.input.setInputProcessor(stage);
        Gdx.app.log("InputProcessor", "Current processor: " + Gdx.input.getInputProcessor());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f/255, 155f/255, 149f/255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
       stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batcher.dispose();
        stage.dispose();
        button1.dispose();
        button2.dispose();
    }
}
