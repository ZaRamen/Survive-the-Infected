package weapon;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.Player;

public class RocketLauncher extends Weapon
{

    public RocketLauncher( Player player) {
        super(new Sprite(new Texture("Weapons/rocketLauncher.png")), new Sprite(new Texture("Weapons/rocket.png"), 64, 64),
                20, 40, player, 500, 40);
        //had to decrease the damage since it seems it hits the sprites multiple times which isn't good
        //so it ends up doing more than 80 damage(original amount)
        weaponName = "RocketLauncher";
    }

}
