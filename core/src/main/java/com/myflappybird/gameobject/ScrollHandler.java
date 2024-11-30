package com.myflappybird.gameobject;
import com.myflappybird.frameworkhelper.AssetLoader;
import com.myflappybird.gameworld.GameWorld;

public class ScrollHandler {
    private Grass frontGrass, backGrass;
    private Pipe pipe1, pipe2, pipe3;
    public  int SCROLL_SPEED;
    private GameWorld gameWorld;
    private int gameHeight;
    private int gameWidth;
    public  int PIPE_GAP;

    public ScrollHandler(GameWorld gameWorld, float yPos, int gameHeight, int gameWidth) {
        this.gameHeight = gameHeight;
        this.gameWidth = gameWidth;
        this.gameWorld = gameWorld;
        PIPE_GAP = gameWidth/3;
        SCROLL_SPEED = -(gameWidth/4);
        frontGrass = new Grass(0, yPos, gameWidth, 50, SCROLL_SPEED);
        backGrass = new Grass(frontGrass.getTailX(), yPos, gameWidth, 50, SCROLL_SPEED);
        pipe1 = new Pipe(gameWidth/2, 0, PIPE_GAP/4, gameHeight/2, SCROLL_SPEED, yPos);
        pipe2 = new Pipe(pipe1.getTailX() + PIPE_GAP, 0, PIPE_GAP/4, gameHeight/3, SCROLL_SPEED, yPos);
        pipe3 = new Pipe(pipe2.getTailX() + PIPE_GAP, 0, PIPE_GAP/4, gameHeight/4, SCROLL_SPEED, yPos);
    }

    public void update(float v) {
        frontGrass.update(v);
        backGrass.update(v);
        pipe1.update(v);
        pipe2.update(v);
        pipe3.update(v);

        if (pipe1.isScrolledLeft()) {
            pipe1.reset(pipe3.getTailX() + PIPE_GAP);
        } else if (pipe2.isScrolledLeft()) {
            pipe2.reset(pipe1.getTailX() + PIPE_GAP);

        } else if (pipe3.isScrolledLeft()) {
            pipe3.reset(pipe2.getTailX() + PIPE_GAP);
        }

        if (frontGrass.isScrolledLeft()) {
            frontGrass.reset(backGrass.getTailX());

        } else if (backGrass.isScrolledLeft()) {
            backGrass.reset(frontGrass.getTailX());
        }
    }

    public void stop() {
        frontGrass.stop();
        backGrass.stop();
        pipe1.stop();
        pipe2.stop();
        pipe3.stop();
    }

    public boolean collides(Bird bird) {
        if (!pipe1.isScored() && pipe1.getX() + (pipe1.getWidth() / 2) < bird.getX() + bird.getWidth()) {
            addScore(1);
            pipe1.setScored(true);
            AssetLoader.coin.play(AssetLoader.volume_sound);
        } else if (!pipe2.isScored() && pipe2.getX() + (pipe2.getWidth() / 2) < bird.getX() + bird.getWidth()) {
            addScore(1);
            pipe2.setScored(true);
            AssetLoader.coin.play(AssetLoader.volume_sound);
        } else if (!pipe3.isScored() && pipe3.getX() + (pipe3.getWidth() / 2) < bird.getX() + bird.getWidth()) {
            addScore(1);
            pipe3.setScored(true);
            AssetLoader.coin.play(AssetLoader.volume_sound);
        }
        return (pipe1.collides(bird) || pipe2.collides(bird) || pipe3.collides(bird));
    }

    private void addScore(int increment) {
        pipe1.setVelocity(-5);
        pipe2.setVelocity(-5);
        pipe3.setVelocity(-5);
        frontGrass.setVelocity(-5);
        backGrass.setVelocity(-5);
        gameWorld.addScore(increment);
    }

    public void onRestart() {
        frontGrass.onRestart(0, SCROLL_SPEED);
        backGrass.onRestart(frontGrass.getTailX(), SCROLL_SPEED);
        pipe1.onRestart(gameWidth/2, SCROLL_SPEED);
        pipe2.onRestart(pipe1.getTailX() + PIPE_GAP, SCROLL_SPEED);
        pipe3.onRestart(pipe2.getTailX() + PIPE_GAP, SCROLL_SPEED);
    }

    public Grass getFrontGrass() {
        return frontGrass;
    }

    public Grass getBackGrass() {
        return backGrass;
    }

    public Pipe getPipe1() {
        return pipe1;
    }

    public Pipe getPipe2() {
        return pipe2;
    }

    public Pipe getPipe3() {
        return pipe3;
    }
}
