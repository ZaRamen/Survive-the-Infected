package weapon;

import Helper.Helper;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Player;

public class Weapon
{
    public int getDamage() {
        return damage;
    }

    protected int damage;


    public int getDelay() {
        return delay;
    }

    protected final int delay;

    public int getMaxAmmo() {
        return maxAmmo;
    }


    protected int maxAmmo;

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    protected int ammo;


    public int getBulletSpeed() {
        return bulletSpeed;
    }

    public String getWeaponName() {
        return weaponName;
    }

    public void setWeaponName(String weaponName) {
        this.weaponName = weaponName;
    }

    protected String weaponName;
    protected int bulletSpeed;

    public Sprite getSprite() {
        return sprite;
    }

    protected Sprite sprite;
    private Player player;


    public Sprite getBulletSprite() {
        return bulletSprite;
    }

    public void setBulletSprite(Sprite bulletSprite) {
        this.bulletSprite = bulletSprite;
    }

    private Sprite bulletSprite;

    public Weapon(Sprite sprite, Sprite bulletSprite, int damage, int delay, Player player, int bulletSpeed, int maxAmmo)
    {
        this.sprite = sprite;
        this.bulletSprite = bulletSprite;
        this.damage = damage;
        this.delay = delay;
        this.player = player;
        this.bulletSpeed = bulletSpeed;
        this.maxAmmo = maxAmmo;
        this.ammo = maxAmmo;
    }
    public void draw(SpriteBatch batch)
    {
        sprite.setPosition(player.x, player.y);
        float angle = (float) Math.atan2(player.getMouse().getMouseInWorld2D().y - sprite.getY(), player.getMouse().getMouseInWorld2D().x - sprite.getX());
        sprite.setRotation(angle * Helper.getPPM());
        sprite.draw(batch);
    }

}
