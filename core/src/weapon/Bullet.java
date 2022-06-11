package weapon;

import Helper.Helper;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.Mouse;
import com.mygdx.game.Player;
import Helper.*;
import com.mygdx.game.TopDownGame;

import static com.badlogic.gdx.math.MathUtils.*;
import static com.mygdx.game.TopDownGame.bullets;
import static com.mygdx.game.TopDownGame.devilBullets;


public class Bullet
{
    public static int speed;

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    private Sprite sprite;
    public float x, y;
    float angle;
    float velocityX;
    float velocityY;
    private int timer;
    private Texture bulletImage;
    private int rocketTimer;

    public boolean isRocketHit() {
        return rocketHit;
    }

    public void setRocketHit(boolean rocketHit) {
        this.rocketHit = rocketHit;
    }

    private boolean rocketHit;
    public Bullet(Player player, Mouse mouse, int speed, Texture bulletImage, int dimension)
    {

        this.speed = speed;
        this.bulletImage = bulletImage;
        this.angle = (float) Math.atan2(mouse.y - player.y, mouse.x - player.x);
        this.velocityX = speed * cos(angle);
        this.velocityY = speed * sin(angle);
        this.x = player.x  + cos(angle) * Helper.getPPM()/2 + 5;
        this.y = player.y + sin(angle) * Helper.getPPM()/2 + 10;
        this.sprite = new Sprite(bulletImage, dimension, dimension);
    }
    public Bullet(Player player, Vector2 pos, int speed, Texture bulletImage, int dimension)
    {

        this.speed = speed;
        this.bulletImage = bulletImage;
        this.angle = (float) Math.atan2(player.y - pos.y, player.x - pos.x);
        this.velocityX = speed * cos(angle);
        this.velocityY = speed * sin(angle);
        this.x = pos.x  + cos(angle) * Helper.getPPM()/2 + 5;
        this.y = pos.y + sin(angle) * Helper.getPPM()/2 + 10;
        this.sprite = new Sprite(bulletImage, dimension, dimension);
    }

    public void update(float delta)
    {

        x += velocityX * delta;
        y += velocityY * delta;

        timer++;
        bulletRemove();

        sprite.setRotation(angle * Helper.getPPM());
        sprite.setPosition(x , y );

    }
    public void bulletRemove() //remove if stays too long on screen
    {
        if(timer >= 150)
        {
            for(int i = 0; i < bullets.size(); i++)
            {
                if(bullets.get(i).equals(this))
                {
                    bullets.remove(i);
                    i--;
                }
            }
            for(int i = 0; i < devilBullets.size(); i++)
            {
                if(devilBullets.get(i).equals(this))
                {
                    devilBullets.remove(i);
                    i--;
                }
            }
        }
    }
    public void bulletRemoveRocket() //respective to a rocketLaunchers i should probably put this somewhere else but im too lazy
    {
        rocketTimer++;
        if(rocketTimer > 10)
        {
            bullets.remove(this);
            rocketTimer = 0;
            rocketHit = false;
        }
    }

    public void render(SpriteBatch batch)
    {
        //no clue why I have to put this here or else the sprite starts showing up on the bottom left corner for a split second
        sprite.setPosition(x, y);
        sprite.setRotation(angle * Helper.getPPM());
        sprite.draw(batch);
    }

}
