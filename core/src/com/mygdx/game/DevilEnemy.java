package com.mygdx.game;

import Helper.Helper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import weapon.Bullet;

import static com.badlogic.gdx.math.MathUtils.cos;
import static com.badlogic.gdx.math.MathUtils.sin;
import static com.mygdx.game.TopDownGame.bullets;
import static com.mygdx.game.TopDownGame.devilBullets;

public class DevilEnemy extends Enemy
{
    private TextureRegion[][] frames =
            TextureRegion.split(new Texture("Sprites/devilEnemy.png"),  64, 64);

    private int count;
    private Sprite attackSprite = new Sprite(new Texture("Weapons/fireball.png"));
    private int level;
    private int speed;
    public DevilEnemy(Body body, float boundingRadius, int health, Player player, int level)
    {
        super(body, boundingRadius, health, player);
        setImage(frames);
        this.level = level;

    }
    public void  update(float delta)
    {
        super.update(delta);
        count++;
        if(count > 120 && x != 0)
        {
            if(level < 4)
            {
                speed = 400;
            }
            else
            {
                speed = 500;
            }
            Bullet b = new Bullet(getPlayer(), new Vector2(x, y),speed, attackSprite.getTexture(), 64);
            devilBullets.add(b);
            count = 0;
        }
    }

}
