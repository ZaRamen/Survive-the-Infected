package Helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.TopDownGame;
import weapon.Pistol;

import java.util.ArrayList;

public class UI {

    private Camera camera;
    private TopDownGame game;
    private Vector3 worldPos3D = new Vector3();
    private FreeTypeFontGenerator freeTypeFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("UI/pixelFont.ttf"));
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    private BitmapFont fontBig;
    private BitmapFont fontSmall;
    private BitmapFont fontMedium;
    private int counter;
    private int pauseCounter;
    private Texture infinity = new Texture("UI/infinity.png");
    private ArrayList<String> messages = new ArrayList<>();

    public UI(Camera camera, TopDownGame game) {
        this.game = game;
        this.camera = camera;
        parameter.size = 30;
        parameter.color = Color.WHITE;
        fontSmall = freeTypeFontGenerator.generateFont(parameter);
        parameter.size = 100;
        parameter.color = Color.WHITE;
        fontBig = freeTypeFontGenerator.generateFont(parameter);
        parameter.size = 50;
        parameter.color = Color.WHITE;
        fontMedium = freeTypeFontGenerator.generateFont(parameter);
    }

    public void drawUI() {

        setPos(50, 50);
        fontMedium.draw(game.getBatch(), "Score: " + game.player.getScore(), worldPos3D.x, worldPos3D.y);

        setPos(Gdx.graphics.getWidth()/2, 50);
        if(game.player.getCurrentWeapon() instanceof Pistol)
        {
            int pos = Gdx.graphics.getWidth()/2 - 50;
            setPos(pos, 50);
            fontMedium.draw(game.getBatch(), "Ammo Remaining: Infinite", worldPos3D.x, worldPos3D.y);
            setPos(Gdx.graphics.getWidth()/2 + 450, 145);
//            game.getBatch().draw(infinity, worldPos3D.x, worldPos3D.y, 150, 150);
        }
        else
        {
            fontMedium.draw(game.getBatch(), "Ammo Remaining: " + game.player.getCurrentWeapon().getAmmo(), worldPos3D.x, worldPos3D.y);
        }
    }

    public void drawPauseScreen()
    {
        setPos(Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/2);
        fontBig.draw(game.getBatch(), "PAUSED", worldPos3D.x, worldPos3D.y);
    }
    public void drawLevelChange() {
        counter++;
        setPos(Gdx.graphics.getWidth()/3, 200);
        fontBig.draw(game.getBatch(), "Level: " + game.getLevel(), worldPos3D.x, worldPos3D.y);
        if (counter > 300) {
            counter = 0;
            game.setLevelChange(false);
        }


    }

    public void drawAmmoIncrease(String weaponName, int ammo) {
        if (ammo > 0) {
            messages.add(weaponName + " restored " + ammo + " Ammo");
        } else {
            messages.add("Max Ammo Limit Reached");
        }
    }

    public void drawHeal()
    {
        messages.add("Fully Healed");
    }
    public void drawPopUp()
    {
        if(messages.size() > 0)
        {
            int y = 0;
            for(String s: messages)
            {
                setPos(Gdx.graphics.getWidth()/3, 600 + y);
                fontMedium.draw(game.getBatch(), s + "\n", worldPos3D.x, worldPos3D.y);
                y += 40;
            }
            messages.clear();

        }
    }
    public void drawObtainNewWeapon(String weaponName)
    {
        messages.add(weaponName + " unlocked");
    }

    public void setPos(int x, int y)
    {
        worldPos3D.x = x;
        worldPos3D.y = y;
        camera.unproject(worldPos3D);
    }
    public void dispose()
    {
        fontBig.dispose();
        fontSmall.dispose();
    }
}
