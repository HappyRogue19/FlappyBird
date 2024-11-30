package com.myflappybird.gameworld;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.myflappybird.frameworkhelper.AssetLoader;
import com.myflappybird.gameobject.*;

public class GameWorld {
    private Bird bird;
    private ScrollHandler scroller;
    private Rectangle ground;
    private int score = 0;
    private int gameHeight;
    private int midPointY;
    private int gameWidth;
    private GameState currentState;
    public enum GameState {
        READY, RUNNING, GAME_OVER, HIGHSCORE
    }

    public GameWorld(int midPointY, int gameHeight, int gameWidth) {
        this.gameHeight = gameHeight;
        this.gameWidth = gameWidth;
        currentState = GameState.READY;
        this.midPointY = midPointY;
        bird = new Bird(33, midPointY, gameWidth/16, gameHeight/16);
        scroller = new ScrollHandler(this,gameHeight-100, gameHeight, gameWidth); //начало травы
        ground = new Rectangle(0, gameHeight-50 - getScroller().getFrontGrass().getHeight(), gameWidth, 50);
    }

    public void update(float v) {
        switch (currentState) {
            case READY:
                updateReady(v);
                break;
            case RUNNING:
                updateRunning(v);
                break;
            case GAME_OVER:
                scroller.stop();
            default: break;
        }
    }

    private void updateReady(float v) {
        AssetLoader.game_music.setVolume(AssetLoader.volume_music);
        AssetLoader.game_music.setLooping(true);
        AssetLoader.game_music.play();
    }
    public void updateRunning(float v) {
        if (v > .15f) {
            v = .15f;
        }
        bird.update(v);
        scroller.update(v);
        if (scroller.collides(bird) && bird.isAlive()) {
            scroller.stop();
            AssetLoader.dead.play(AssetLoader.volume_sound);
            AssetLoader.game_music.stop();
            bird.die();
            bird.decelerate();
            currentState = GameState.GAME_OVER;
            if (score > AssetLoader.getHighScore()) {
                AssetLoader.setHighScore(score);
            }
        }
        if (Intersector.overlaps(bird.getBoundingCircle(), ground)) {
            scroller.stop();
            AssetLoader.dead.play(AssetLoader.volume_sound);
            AssetLoader.game_music.stop();
            bird.die();
            bird.decelerate();
            currentState = GameState.GAME_OVER;
            if (score > AssetLoader.getHighScore()) {
                AssetLoader.setHighScore(score);
                currentState = GameState.HIGHSCORE;
            }
        }

    }
    public void restart() {
        score = 0;
        bird.onRestart(midPointY - 50);
        scroller.onRestart();
        currentState = GameState.READY;
    }

    public boolean isReady() {
        return currentState == GameState.READY;
    }
    public void start() {
        currentState = GameState.RUNNING;
    }
    public boolean isGameOver() {
        return currentState == GameState.GAME_OVER;
    }
    public Bird getBird() {
        return bird;
    }
    public ScrollHandler getScroller() {
        return scroller;
    }
    public int getScore() {
        return score;
    }
    public void addScore(int increment) {
        score += increment;
    }
    public boolean isHighScore() {
        return currentState == GameState.HIGHSCORE;
    }
}
