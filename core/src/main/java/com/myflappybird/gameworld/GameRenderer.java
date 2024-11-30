package com.myflappybird.gameworld;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.myflappybird.frameworkhelper.AssetLoader;
import com.myflappybird.frameworkhelper.CreateLB;
import com.myflappybird.frameworkhelper.InputHandler;
import com.myflappybird.gameobject.Bird;
import com.myflappybird.gameobject.Grass;
import com.myflappybird.gameobject.Pipe;
import com.myflappybird.gameobject.ScrollHandler;
import com.myflappybird.screens.MenuScreen;

import static com.badlogic.gdx.utils.Align.center;

public class GameRenderer {
    private GameWorld myWorld;
    private OrthographicCamera camera, camera1;
    private ShapeRenderer shapeRenderer;
    private Viewport viewport;
    private SpriteBatch batcher, batcher1;
    private  Label score;
    private int midPointY;
    private int gameHeight;
    private int gameWidth;
    private Stage stage;
    private Bird bird;
    private ScrollHandler scroller;
    private Grass frontGrass, backGrass;
    private Pipe pipe1, pipe2, pipe3;
    private TextureRegion bg, grass;
    private Animation birdAnimation, birdAnimation2;
    private TextureRegion birdMid, birdMid2;
    private TextureRegion skullUp, skullDown, bar;
    private Texture button1, button2;
    private ImageTextButton restartButton, menuButton;
    private int countBird = 0;

    public GameRenderer(GameWorld world, int gameHeight, int gameWidth, int midPointY) {
        myWorld = world;
        this.gameHeight = gameHeight;
        this.midPointY = midPointY;
        this.gameWidth = gameWidth;

        camera = new OrthographicCamera();
        camera.setToOrtho(true, gameWidth,gameHeight);

        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(camera.combined);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);

        initGameObjects();
        initAssets();
    }
    private void initGameObjects() {
        bird = myWorld.getBird();
        scroller = myWorld.getScroller();
        frontGrass = scroller.getFrontGrass();
        backGrass = scroller.getBackGrass();
        pipe1 = scroller.getPipe1();
        pipe2 = scroller.getPipe2();
        pipe3 = scroller.getPipe3();
        setupStage();
    }
    private void initAssets() {
        bg = AssetLoader.bg;
        grass = AssetLoader.grass;
        birdAnimation = AssetLoader.birdAnimation;
        birdAnimation2 = AssetLoader.birdAnimation2;
        birdMid = AssetLoader.bird;
        birdMid2 = AssetLoader.bird2;
        skullUp = AssetLoader.skull;
        skullDown = AssetLoader.skull;
        bar = AssetLoader.bar;
    }
    public void render(float runTime) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Отрисуем Background цвет
        shapeRenderer.setColor(138/ 255.0f, 229/ 255.0f, 250/ 255.0f, 1);
        shapeRenderer.rect(0, 0, gameWidth, gameHeight);
        shapeRenderer.end();

        batcher.begin();

        batcher.draw(bg, center, midPointY, gameWidth, midPointY);
        drawGrass();
        if (countBird%2==0) {
            if (bird.shouldntFlap()) {
                batcher.draw(birdMid, bird.getX(), bird.getY(),
                    bird.getWidth() / 2.0f, bird.getHeight() / 2.0f,
                    bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());

            } else {
                batcher.draw((TextureRegion)birdAnimation.getKeyFrame(runTime),
                    bird.getX(), bird.getY(),
                    bird.getWidth() / 2.0f, bird.getHeight() / 2.0f,
                    bird.getWidth(), bird.getHeight(),
                    1, 1, bird.getRotation());
            }
        } else {
            if (bird.shouldntFlap()) {
                batcher.draw(birdMid2, bird.getX(), bird.getY(),
                    bird.getWidth() / 2.0f, bird.getHeight() / 2.0f,
                    bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());

            } else {
                batcher.draw((TextureRegion)birdAnimation2.getKeyFrame(runTime),
                    bird.getX(), bird.getY(),
                    bird.getWidth() / 2.0f, bird.getHeight() / 2.0f,
                    bird.getWidth(), bird.getHeight(),
                    1, 1, bird.getRotation());
            }
        }

        if (myWorld.isReady()) {
            GlyphLayout layout = new GlyphLayout(AssetLoader.font2, "PRESS TO START!");
            AssetLoader.font2.draw(batcher, "PRESS TO START!", gameWidth/2-layout.width/2, gameHeight/2);

        } else {
            drawPipes();
            drawSkulls();
            AssetLoader.font2.draw(batcher, "" + myWorld.getScore(), 10, 10);
        }

        batcher.end();

        //Отрисовка земли
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(147 / 255.0f, 80 / 255.0f, 27 / 255.0f, 1);
        shapeRenderer.rect(0, frontGrass.getY()+frontGrass.getHeight()-5 , gameWidth, gameHeight - frontGrass.getY());
        shapeRenderer.end();

        if (myWorld.isGameOver()) {
            if (myWorld.isGameOver()) {
                button1 = AssetLoader.button1;
                button2 = AssetLoader.button2;
                GlyphLayout layout = new GlyphLayout(AssetLoader.font2, "HIGH SCORE: ");
                Label gameOver = CreateLB.createLabel("GAME OVER");
                Label highScore = CreateLB.createLabel("HIGH SCORE: " + AssetLoader.getHighScore() + " ");
                score = CreateLB.createLabel("SCORE: " + myWorld.getScore()+" ");
                restartButton = CreateLB.createImageTextButton("RESTART", button1, button2, null);
                menuButton = CreateLB.createImageTextButton("MENU", button1, button2, null);


                Table table = new Table();
                table.setSize(layout.width*3, layout.height*12);
                table.setPosition(gameWidth / 2f - table.getWidth() / 2f, gameHeight / 2f - table.getHeight() / 2f);
                TextureRegionDrawable back_ground_table = new TextureRegionDrawable(new TextureRegion(AssetLoader.background_table));
                table.setBackground(back_ground_table);

                table.add(gameOver).height(layout.height).padBottom(layout.height).colspan(2);
                table.row();
                table.add(highScore).height(layout.height).padBottom(layout.height).colspan(2);
                table.row();
                table.add(score).height(layout.height).padBottom(layout.height).colspan(2);
                table.row();
                table.add(restartButton).size(layout.width, layout.height*2).padRight(10);
                table.add(menuButton).size(layout.width, layout.height*2);

                stage.addActor(table);
                camera1.update();
                stage.getViewport().apply();
                Gdx.input.setInputProcessor(stage);
                stage.act();
                stage.draw();
                restartButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        AssetLoader.click.play();
                        Gdx.input.setInputProcessor(new InputHandler(myWorld));
                        myWorld.restart();
                        countBird+=1;
                    }
                });

                menuButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        AssetLoader.click.play();
                        AssetLoader.menu_music.setLooping(true);
                        AssetLoader.menu_music.setVolume(AssetLoader.volume_music);
                        AssetLoader.menu_music.play();
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new MenuScreen());
                    }
                });
            }
        }
    }

    private void drawGrass() {
        batcher.draw(grass, frontGrass.getX(), frontGrass.getY(), frontGrass.getWidth(), frontGrass.getHeight());
        batcher.draw(grass, backGrass.getX(), backGrass.getY(), backGrass.getWidth(), backGrass.getHeight());
    }

    private void drawPipes() {

        batcher.draw(bar, pipe1.getBarUp().x, pipe1.getBarUp().y, pipe1.getBarUp().width, pipe1.getBarUp().height);
        batcher.draw(bar, pipe1.getBarDown().x, pipe1.getBarDown().y, pipe1.getBarDown().width, pipe1.getBarDown().height);

        batcher.draw(bar, pipe2.getBarUp().x, pipe2.getBarUp().y, pipe2.getBarUp().width, pipe2.getBarUp().height);
        batcher.draw(bar, pipe2.getBarDown().x, pipe2.getBarDown().y, pipe2.getBarDown().width, pipe2.getBarDown().height);

        batcher.draw(bar, pipe3.getBarUp().x, pipe3.getBarUp().y, pipe3.getBarUp().width, pipe3.getBarUp().height);
        batcher.draw(bar, pipe3.getBarDown().x, pipe3.getBarDown().y, pipe3.getBarDown().width, pipe3.getBarDown().height);
    }

    private void drawSkulls() {
        batcher.draw(skullUp, pipe1.getSkullUp().x+pipe1.getWidth()*2-5, pipe1.getSkullUp().y+5,
            pipe1.getSkullUp().width+10, pipe1.getSkullUp().height);
        batcher.draw(skullDown, pipe1.getSkullDown().x+pipe1.getWidth()*2-5, pipe1.getSkullDown().y,
            pipe1.getSkullDown().width+10, pipe1.getSkullDown().height);

        batcher.draw(skullUp, pipe2.getSkullUp().x+pipe2.getWidth()*2-5, pipe2.getSkullUp().y+5,
            pipe2.getSkullUp().width+10, pipe2.getSkullUp().height);
        batcher.draw(skullDown, pipe2.getSkullDown().x+pipe2.getWidth()*2-5, pipe2.getSkullDown().y,
            pipe2.getSkullDown().width+10, pipe2.getSkullDown().height);

        batcher.draw(skullUp, pipe3.getSkullUp().x+pipe3.getWidth()*2-5, pipe3.getSkullUp().y+5,
            pipe3.getSkullUp().width+10, pipe3.getSkullUp().height);
        batcher.draw(skullDown, pipe3.getSkullDown().x+pipe3.getWidth()*2-5, pipe3.getSkullDown().y,
            pipe3.getSkullDown().width+10, pipe3.getSkullDown().height);
    }
    public void setupStage() {
        GlyphLayout layout = new GlyphLayout(AssetLoader.font2, "HIGH SCORE: ");
        batcher1 = new SpriteBatch();
        camera1 = new OrthographicCamera();
        viewport = new FitViewport(gameWidth, gameHeight, camera1);
        viewport.apply();
        camera1.position.set(layout.width*3, layout.height*12, 0);
        camera1.update();
        stage = new Stage(viewport, batcher1);
    }

    public void resize(int width, int height) {
        float baseWidth = 1080f;  // замените на ваш исходный размер ширины
        float baseHeight = 920f; // замените на ваш исходный размер высоты

        // Рассчитываем масштаб шрифта на основе пропорций окна
        float scaleX = width / baseWidth;
        float scaleY = height / baseHeight;

        // Устанавливаем минимальный масштаб, чтобы избежать слишком маленького текста
        float scale = Math.min(scaleX, scaleY);

        AssetLoader.font2.getData().setScale(scale);
    }
}
