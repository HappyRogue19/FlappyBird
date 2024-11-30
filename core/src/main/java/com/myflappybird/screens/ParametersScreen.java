package com.myflappybird.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.myflappybird.frameworkhelper.AssetLoader;
import com.myflappybird.frameworkhelper.CreateLB;

public class ParametersScreen implements Screen {
    private static final String PREFS_NAME = "MyFlappyBird";
    private static final String VOLUME_KEY = "volume";
    private Preferences prefs;
    private float volume;
    private SpriteBatch batcher;
    private OrthographicCamera camera;
    private Viewport viewport;
    private int WORLD_WIDTH = Gdx.graphics.getWidth();
    private int WORLD_HEIGHT = Gdx.graphics.getHeight();
    private Texture checkBoxOnTexture, checkBoxOffTexture;
    private Texture sliderBgTexture, sliderKnobTexture;
    private Stage stage;
    private Texture button1, button2;

    public ParametersScreen() {
        batcher = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        viewport.apply();
        camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
        camera.update();
        stage = new Stage(viewport, batcher);

        button1 = AssetLoader.button1;
        button2 = AssetLoader.button2;
        checkBoxOnTexture = AssetLoader.checkbox_on;
        checkBoxOffTexture = AssetLoader.checkbox_off;
        sliderBgTexture = AssetLoader.slider_on_bg;
        sliderKnobTexture = AssetLoader.slider_section;

        prefs = Gdx.app.getPreferences(PREFS_NAME);
        volume = prefs.getFloat(VOLUME_KEY, 1.0f);

        Label musicLabel = CreateLB.createLabel("MUSIC");
        Label soundLabel = CreateLB.createLabel("SOUND EFFECTS");
        Label volumeLabel = CreateLB.createLabel("VOLUME");

        CheckBox musicCheckBox = CreateLB.createCheckBox(checkBoxOffTexture, checkBoxOnTexture);
        CheckBox soundCheckBox = CreateLB.createCheckBox(checkBoxOffTexture, checkBoxOnTexture);

        if (AssetLoader.volume_sound!=0) {
            soundCheckBox.setChecked(true); // Звук включен по умолчанию
        }
        if (AssetLoader.volume_music!=0) {
            musicCheckBox.setChecked(true); // Музыка включена по умолчанию
        }


        ImageTextButton backButton = CreateLB.createImageTextButton("BACK", button1, button2, null);
        Slider volumeSlider = CreateLB.createSlider(sliderBgTexture, sliderKnobTexture);

        volumeSlider.setValue(volume);

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(musicLabel).pad(10);
        table.add(musicCheckBox).pad(10);
        table.row();
        table.add(soundLabel).pad(10);
        table.add(soundCheckBox).pad(10);
        table.row();
        table.add(volumeLabel).pad(10);
        table.add(volumeSlider).width(200).pad(10);
        table.row();
        table.add(backButton).colspan(2).pad(20);

        stage.addActor(table);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AssetLoader.click.play(AssetLoader.volume_sound);
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MenuScreen());
            }
        });
        musicCheckBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                AssetLoader.click.play(AssetLoader.volume_sound);
                if (musicCheckBox.isChecked()) {
                    AssetLoader.volume_music = volumeSlider.getValue();;
                    AssetLoader.menu_music.setLooping(true);
                    AssetLoader.menu_music.play();
                } else {
                    AssetLoader.menu_music.stop();
                    AssetLoader.volume_music = 0;
                }
            }
        });
        soundCheckBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                AssetLoader.click.play(AssetLoader.volume_sound);
                if (soundCheckBox.isChecked()) {
                    AssetLoader.volume_sound = volumeSlider.getValue();;
                } else {
                    AssetLoader.volume_sound = 0;
                }

            }
        });
        volumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                AssetLoader.volume_sound = volumeSlider.getValue();
                AssetLoader.volume_music = volumeSlider.getValue();
                AssetLoader.menu_music.setVolume(volumeSlider.getValue());
                AssetLoader.game_music.setVolume(volumeSlider.getValue());
                prefs.putFloat(VOLUME_KEY, volumeSlider.getValue());
                prefs.flush();
            }
        });
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
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
        checkBoxOnTexture.dispose();
        checkBoxOffTexture.dispose();
        sliderBgTexture.dispose();
        sliderKnobTexture.dispose();
        button1.dispose();
        button2.dispose();
        stage.dispose();

    }
}
