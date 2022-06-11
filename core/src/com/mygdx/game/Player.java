package com.mygdx.game;

import Helper.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import weapon.*;

import java.util.ArrayList;

import static com.mygdx.game.TopDownGame.bullets;

public class Player extends Entity
{


    private HealthBar healthBar = new HealthBar(64, 64, true);

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    private Sprite sprite;



    private TextureRegion[][] frames =
            TextureRegion.split(new Texture("Sprites/playerSpritesMilitary.png"),  64, 64);
    private int spriteNum = 1;
    private int count = 0;
    private B2dSteeringEntity b2dSteeringEntity;
    private int invincibleCounter = 0;
    private int health = 100;
    private int maxHealth = 100;
    private String justPressed = "";
    private int gunDelayCounter = 0;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    private int score = 0;

    public Weapon getCurrentWeapon() {
        return currentWeapon;
    }



    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }


    private ArrayList<Weapon> weapons = new ArrayList<>(); //store all weapons
    public Weapon currentWeapon;
    public Mouse getMouse() {
        return mouse;
    }
    private Mouse mouse = new Mouse(this);

    public Player(float width, float height, Body body) {
        super(width, height, body);
        this.speed = 4;
        this.b2dSteeringEntity = new B2dSteeringEntity(body, 10, 0, 0);
        sprite = new Sprite(frames[0][0].getTexture(), 64, 64);
        this.currentWeapon = new Pistol(this);
        weapons.add(currentWeapon);
        for(Fixture f: body.getFixtureList())
        {
            f.setUserData(this);
        }

    }

    public void shoot()
    {
        if (gunDelayCounter >= currentWeapon.getDelay() && currentWeapon.getAmmo() > 0)
        {

            Bullet b = new Bullet(this, mouse, currentWeapon.getBulletSpeed(), currentWeapon.getBulletSprite().getTexture(),
                    (int) currentWeapon.getBulletSprite().getWidth());
            bullets.add(b);
            gunDelayCounter = 0;
            if(!(currentWeapon instanceof  Pistol))
            {
                currentWeapon.setAmmo(currentWeapon.getAmmo() - 1);
            }

        }
    }
    @Override
    public void update()
    {

        x = body.getPosition().x * Helper.getPPM() - (Helper.getPPM()/2); //substract half to offset
        y = body.getPosition().y * Helper.getPPM() - (Helper.getPPM()/2);
        sprite.setPosition(x, y);
        hitDetection();
        gunDelayCounter++;
        checkInput();
    }

    public void hitDetection()
    {
        boolean isHit = false;
        int count = 0;
        for(Boolean b: CollisionDetector.getIsTouching())
        {
            if(b)
            {
               isHit = true;
               count++;
            }
        }
        if(isHit)
        {
            invincibleCounter++;
            if(invincibleCounter > 30)
            {
                invincibleCounter = 0;

                health -= count * 5;
                if(health <= 0)
                {
                    health = 0;
                }

            }
        }
    }
    @Override
    public void render(SpriteBatch spriteBatch)
    {
        sprite.draw(spriteBatch);
        currentWeapon.draw(spriteBatch);//rotate the gun based on the mousess
        healthBar.draw(spriteBatch, x - 50, y + 50, health, maxHealth);

    }

    public void checkInput()
    {
        //reset
        velX = 0;
        velY = 0;
        if(Gdx.input.isKeyPressed(Input.Keys.W))
        {
            if(justPressed.equals("A"))
            {
                setFrames(1);
            }
            else
            {
                setFrames(0);
            }
            velY = 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S))
        {
            if(justPressed.equals("A"))
            {
                setFrames(1);
            }
            else
            {
                setFrames(0);
            }
            velY = -1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D))
        {
            justPressed = "D";
            setFrames(0); // 0 is right
            velX = 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A))
        {
            justPressed = "A";
            setFrames(1);
            velX = -1;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.NUM_1))
        {
            checkWeaponInput(0); // 0 based index

        }
        else if (Gdx.input.isKeyPressed(Input.Keys.NUM_2))
        {
           checkWeaponInput(1);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.NUM_3))
        {
            checkWeaponInput(2);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.NUM_4))
        {
            checkWeaponInput(3);
        }

        count++;
        if(count > 10)
        {
            if(spriteNum == 1)
            {
                spriteNum = 2;

            }
            else if(spriteNum ==  2)
            {
                spriteNum = 1;
            }
            count = 0;
        }
        b2dSteeringEntity.getBody().setLinearVelocity(velX * speed, velY * speed);
        body.setLinearVelocity(velX * speed, velY * speed);
    }
    public void checkWeaponInput(int num) //check if player changes weapons and it's not null
    {
        if(num <= weapons.size() - 1)
        {
            if(weapons.get(num) != null)
            {
                currentWeapon = weapons.get(num);
            }
        }
    }

    public void setFrames(int direction)
    {

        if(spriteNum == 1)
        {
            sprite.setRegion(frames[0][direction]);

        }
        else
        {
            sprite.setRegion(frames[1][direction]);
        }

    }
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public B2dSteeringEntity getB2dSteeringEntity() {
        return b2dSteeringEntity;
    }


}
