package weapon;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.Player;

public class RayGun extends Weapon{

    public RayGun(Player player)
    {
        //1/6 of a second delay //have to decreasie damage
        super(new Sprite(new Texture("Weapons/rayGun.png")),  new Sprite(new Texture("Weapons/laserBeam.png")),
                40, 10, player, 1000, 60);
        weaponName = "RayGun";
    }
}
