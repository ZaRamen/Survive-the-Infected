package com.mygdx.game;


import Helper.Helper;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;


public class Enemy extends B2dSteeringEntity
{

    private HealthBar healthBar = new HealthBar(64, 32, false);

    public boolean isHit() {
        return isHit;
    }

    public void setHit(boolean hit) {
        isHit = hit;
    }

    private boolean isHit = false;

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public HealthBar getHealthBar() {
        return healthBar;
    }

    public void setHealthBar(HealthBar healthBar) {
        this.healthBar = healthBar;
    }

    private int health;
    private int maxHealth;

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    private Sprite sprite;

    public TextureRegion[][] getFrames() {
        return frames;
    }

    public void setImage(TextureRegion[][] frames) {
        this.frames = frames;
    }

    private TextureRegion[][] frames;
    private int spriteNum = 1;
    private int spriteCounter;
    float x, y;
    private int prevHealth;
    private int healthTimer;

    public Player getPlayer() {
        return player;
    }

    private Player player;
    public Enemy(Body body, float boundingRadius, int health, Player player) {
        super(body, boundingRadius, 5, 1);
        this.maxHealth = health;
        this.health = health;
        this.prevHealth = health;
        this.frames = TextureRegion.split(new Texture("Sprites/enemy2.png"),  64, 64);
        this.sprite = new Sprite(frames[0][1]);
        this.player = player;
        setUserData();


    }
   public void setUserData()
   {
       for(Fixture f: getBody().getFixtureList())
       {
           f.setUserData(this);

       }
   }
   public void update(float delta)
   {
       super.update(delta);
       updateSprite();
   }
    public void updateSprite()
    {
         x = this.getPosition().x * Helper.getPPM() - (Helper.getPPM()/2); //substract half to offset
         y = this.getPosition().y * Helper.getPPM() - (Helper.getPPM()/2);

        sprite.setPosition(x, y);
        //always point to the player
        if(player.x > x)
        {
            setFrames(0); //move right
        }
        if(player.x < x)
        {
            setFrames(1);
        }
        spriteCounter++;
        if(spriteCounter > 10)
        {
            if(spriteNum == 1)
            {
                spriteNum = 2;

            }
            else if(spriteNum ==  2)
            {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

        healthBar.setPosition(x - maxHealth/2,  y + 50);
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
    public void render(SpriteBatch spriteBatch)
    {
        if(sprite.getX() != 0) //basically don't draw unless it's not at the bottom left corner super weird
        {
            sprite.draw(spriteBatch);
            if(prevHealth != health || healthTimer != 0)
            {

                healthTimer++;
                if(healthTimer > 120)
                {
                    healthTimer = 0;
                }
                healthBar.draw(spriteBatch, healthBar.getX(), healthBar.getY(), health, maxHealth);
            }
            prevHealth = health;

        }
    }
    public void arriveBehavior(B2dSteeringEntity target)
    {
        Arrive<Vector2> arrive = new Arrive<>(this,  target)
                .setTimeToTarget(0.01f).setArrivalTolerance(2f).setDecelerationRadius(0);
        this.setBehavior(arrive);
    }


}
