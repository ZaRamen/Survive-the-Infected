package weapon;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.Player;

public class MachineGun extends  Weapon{

    public MachineGun(Player player) {
        super(new Sprite(new Texture("Weapons/machineGun.png")), new Sprite(new Texture("Weapons/machineBullet.png")),
                20, 5, player, 800, 150);
        weaponName = "MachineGun";
    }
}
