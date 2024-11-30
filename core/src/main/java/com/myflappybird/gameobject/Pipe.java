package com.myflappybird.gameobject;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;


public class Pipe extends Scrollable{
    private Random r;
    private boolean isScored = false;

    private Rectangle skullUp, skullDown, barUp, barDown;
    public static int VERTICAL_GAP;
    public static int SKULL_WIDTH;
    public static int SKULL_HEIGHT;
    private float groundY;
    private int gameheight;

    public Pipe(float x, float y, int width, int height, float scrollSpeed, float groundY) {
        super(x, y, width, height, scrollSpeed);
        r = new Random();
        skullUp = new Rectangle();
        skullDown = new Rectangle();
        barUp = new Rectangle();
        barDown = new Rectangle();
        this.groundY = groundY;
        VERTICAL_GAP = height;
        SKULL_WIDTH = width/12;
        SKULL_HEIGHT = VERTICAL_GAP/6;
        gameheight = height*2;
    }
    @Override
    public void reset(float newX) {
        super.reset(newX);
        height=r.nextInt(300)+SKULL_HEIGHT;
        isScored = false;
    }

    @Override
    public void update(float v) {
        super.update(v);

        barUp.set(position.x, position.y, width, height);
        barDown.set(position.x, position.y + height + VERTICAL_GAP, width,groundY - (position.y + height + VERTICAL_GAP));

        skullUp.set(position.x - SKULL_WIDTH - width, position.y + height - SKULL_HEIGHT, SKULL_WIDTH, SKULL_HEIGHT);
        skullDown.set(position.x - SKULL_WIDTH - width, barDown.y, SKULL_WIDTH, SKULL_HEIGHT);
    }
    public boolean collides(Bird bird) {
        if (position.x < bird.getX() + bird.getWidth()) {
            return (Intersector.overlaps(bird.getBoundingCircle(), barUp)
                || Intersector.overlaps(bird.getBoundingCircle(), barDown)
                || Intersector.overlaps(bird.getBoundingCircle(), skullUp) || Intersector
                .overlaps(bird.getBoundingCircle(), skullDown));
        }
        return false;
    }
    public void onRestart(float x, float scrollSpeed) {
        velocity.x = scrollSpeed;
        reset(x);
    }
    public Rectangle getSkullUp() {
        return skullUp;
    }
    public Rectangle getSkullDown() {
        return skullDown;
    }
    public Rectangle getBarUp() {
        return barUp;
    }
    public Rectangle getBarDown() {
        return barDown;
    }
    public boolean isScored() {
        return isScored;
    }
    public void setScored(boolean b) {
        isScored = b;
    }
    public float getGroundY() {
        return groundY;
    }
    public float getHighY() {
        return 0;
    }

    public void resize(int width, int height) {
        this.width = width / 12;
        this.height = height / 6;
        VERTICAL_GAP = height / 5;
        SKULL_WIDTH = width / 12;
        SKULL_HEIGHT = height / 16;
    }
}
