package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class Entity
{
    public float x;
    public float y;
    protected float velX;
    protected float velY;
    protected float speed;
    protected float width, height;
    protected Body body;

    public Entity(float width, float height, Body body)
    {
        this.x = body.getPosition().x;
        this.y = body.getPosition().y;
        this.width = width;
        this.height = height;
        this.velX = 0;
        this.velY = 0;
        this.speed = 0;
        this.body = body;
    }

    //implemented later
    public abstract void update();
    public abstract void render(SpriteBatch spriteBatch);

    public Body getBody() {
        return body;
    }
}
