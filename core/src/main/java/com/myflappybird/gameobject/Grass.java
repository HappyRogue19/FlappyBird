package com.myflappybird.gameobject;

public class Grass extends Scrollable{

    public Grass(float x, float y, int width, int height, float scrollSpeed) {
        super(x, y, width, height, scrollSpeed);
    }
    public void onRestart(float x, float scrollSpeed) {
        position.x = x;
        velocity.x = scrollSpeed;
    }
    public void resize(int width, int height) {
        this.width = width;
        this.height = height/16;
    }
}
