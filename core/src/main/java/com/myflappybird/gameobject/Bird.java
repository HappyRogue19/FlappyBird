package com.myflappybird.gameobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.myflappybird.frameworkhelper.AssetLoader;

public class Bird {
    private Vector2 position; //Позиция
    private Vector2 velocity; //Скорость
    private Vector2 acceleration; //Ускорение
    private float rotation; // Для обработки поворота птицы
    private int width;
    private int height;

    private Circle boundingCircle;

    private boolean isAlive;

    public Bird(float x, float y, int width, int height) {
        this.width = width;
        this.height = height;
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, width*16);
        boundingCircle = new Circle();
        isAlive = true;

    }
    public void update(float v) {

        velocity.add(acceleration.cpy().scl(v));

        if (velocity.y > 250) { // Ограничение максимальной скорости падения.
            velocity.y = 250;
        }
        if (position.y < 0) { // Если птица выходит за верхний край экрана, сбросить её позицию.
            position.y = 0;
            velocity.y = 0;
        }

        position.add(velocity.cpy().scl(v));
        boundingCircle.set(position.x+ width/2, position.y + height/2, width/2);
        // повернуть против часовой стрелки
        if (velocity.y < 0) {
            rotation -= 600 * v;

            if (rotation < -20) {
                rotation = -20;
            }
        }

        // Повернуть по часовой стрелке
        if (isFalling()|| !isAlive) {
            rotation += 480 * v;
            if (rotation > 90) {
                rotation = 90;
            }

        }
    }
    //Нажатие на кнопку мыши для взлета птицы
    public void onClick() {
        if (isAlive) {
            AssetLoader.flap.play(AssetLoader.volume_sound);
            velocity.y = -(int)(width*6);
        }
    }
    public void die() {
        isAlive = false;
        velocity.y = 0;
    }
    public void decelerate() {
        acceleration.y=0;
    }

    public void onRestart(int y) {
        rotation = 0;
        position.y = y;
        velocity.x = 0;
        velocity.y = 0;
        acceleration.x = 0;
        acceleration.y = width*16;
        isAlive = true;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getRotation() {
        return rotation;
    }
    public boolean isFalling() {
        return velocity.y > 150;
    }

    public boolean shouldntFlap() {
        return velocity.y > 70 || !isAlive;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public Circle getBoundingCircle() {
        return boundingCircle;
    }

    public void resize(int width, int height) {
        acceleration.set(0, height);
        float baseWidth = 1080f;
        float baseHeight = 920f;
        float scaleX = width / baseWidth;
        float scaleY = height / baseHeight;
        float scale = Math.min(scaleX, scaleY);
        this.width = (int)(baseWidth/16*scale);
        this.height = (int)(baseHeight/16*scale);
        velocity.y = -(int)(baseWidth/3*scale);
    }
}
