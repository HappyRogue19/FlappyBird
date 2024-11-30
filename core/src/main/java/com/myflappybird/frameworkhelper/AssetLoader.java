package com.myflappybird.frameworkhelper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {
    public static Preferences prefs = Gdx.app.getPreferences("MyFlappyBird");;
    public static Texture texture;
    public static Texture background;
    public static Texture bar_grass;
    public static TextureRegion bg;
    public static float volume_sound = prefs.getFloat("volume");
    public static float volume_music = prefs.getFloat("volume");
    public static Texture button1, button2;

    public static Animation birdAnimation, birdAnimation2;
    public static TextureRegion bird, birdUp;
    public static TextureRegion bird2, birdUp2;
    public static TextureRegion skull, bar, grass, menu_t;
    public static Sound dead, coin, flap, click;
    public static Music menu_music, game_music;
    public static Texture checkbox_on, checkbox_off;
    public static Texture slider_on_bg, slider_section;
    public static Texture background_table;
    public static BitmapFont font, font2;

    public static void load() {
        if (!prefs.contains("HighScore")) {
            prefs.putInteger("HighScore", 0);
        }

        background_table = new Texture(Gdx.files.internal("data/backgrounds/back_table.png"));

        background = new Texture(Gdx.files.internal("data/backgrounds/back_game.png"));
        bg = new TextureRegion(background, 0, 0, 1024, 1024);
        bg.flip(false, true);

        texture = new Texture(Gdx.files.internal("data/textures/texture.png"));
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        bird = new TextureRegion(texture, 122, 162, 272, 191);
        bird.flip(false, true);

        birdUp = new TextureRegion(texture, 634, 162, 272, 191);
        birdUp.flip(false, true);

        bird2 = new TextureRegion(texture, 122, 674, 272, 191);
        bird2.flip(false, true);

        birdUp2 = new TextureRegion(texture, 634, 674, 272, 191);
        birdUp2.flip(false, true);

        TextureRegion[] birds = {bird, birdUp};
        birdAnimation = new Animation(0.06f, birds);
        birdAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        TextureRegion[] birds2 = {bird2, birdUp2};
        birdAnimation2 = new Animation(0.06f, birds2);
        birdAnimation2.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        button1 = new Texture(Gdx.files.internal("data/button/button_rectangle_depth_flat.png"));
        button2 = new Texture(Gdx.files.internal("data/button/button_rectangle_flat.png"));

        checkbox_off = new Texture(Gdx.files.internal("data/checkbox/check_square_color.png"));
        checkbox_on = new Texture(Gdx.files.internal("data/checkbox/check_square_color_checkmark.png"));

        slider_on_bg = new Texture(Gdx.files.internal("data/slider/slide_horizontal_color.png"));
        slider_section = new Texture(Gdx.files.internal("data/slider/slide_horizontal_color_section.png"));

        bar_grass = new Texture(Gdx.files.internal("data/textures/texture_bar_grass.png"));
        skull = new TextureRegion(bar_grass, 671, 223, 184, 56);
        grass = new TextureRegion(bar_grass, 0, 731, 512, 56);
        bar = new TextureRegion(bar_grass, 184, 232, 136, 40);
        bar.flip(false, true);

        dead = Gdx.audio.newSound(Gdx.files.internal("data/sound/fail.wav"));
        coin = Gdx.audio.newSound(Gdx.files.internal("data/sound/coin.wav"));
        flap = Gdx.audio.newSound(Gdx.files.internal("data/sound/flap.wav"));
        click = Gdx.audio.newSound(Gdx.files.internal("data/sound/click.wav"));

        menu_music = Gdx.audio.newMusic(Gdx.files.internal("data/music/menu_music.wav"));
        game_music = Gdx.audio.newMusic(Gdx.files.internal("data/music/game_music.wav"));

        font = new BitmapFont(Gdx.files.internal("data/text.fnt"), Gdx.files.internal("data/text.png"), true);
        font2 = new BitmapFont(Gdx.files.internal("data/text.fnt"),  Gdx.files.internal("data/text.png"), true);

    }

    public static void setHighScore(int val) {
        prefs.putInteger("highScore", val);
        prefs.flush();
    }

    public static int getHighScore() {
        return prefs.getInteger("highScore");
    }

    public static void dispose() {
        button1.dispose();
        button2.dispose();
        texture.dispose();
        background.dispose();
        bar_grass.dispose();
        dead.dispose();
        coin.dispose();
        flap.dispose();
        click.dispose();
        menu_music.stop();
        menu_music.dispose();
        game_music.stop();
        game_music.dispose();
        font.dispose();
        font2.dispose();
        slider_on_bg.dispose();
        slider_section.dispose();
        checkbox_off.dispose();
        background.dispose();
        checkbox_on.dispose();
    }
}
