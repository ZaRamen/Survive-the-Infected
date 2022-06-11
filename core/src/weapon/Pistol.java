package weapon;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.Player;

public class Pistol extends Weapon
{

    public Pistol(Player player)
    {
        //20 frames or 1/3 of a second
        super(new Sprite(new Texture("Weapons/pistol.png")), new Sprite(new Texture("Weapons/bullet.png")),
                10, 10, player, 400, 999); //infinite ammo
        weaponName = "Pistol";
    }
}
