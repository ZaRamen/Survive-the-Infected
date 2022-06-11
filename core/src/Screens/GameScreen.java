package Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.TopDownGame;

public class GameScreen extends Game
{
    private int widthScreen, heightScreen;

    public OrthographicCamera getCamera() {
        return camera;
    }

    private OrthographicCamera camera;
    private MainMenuScreen mainMenuScreen;
    private TutorialScreen tutorialScreen;
    private RestartScreen restartScreen;
    private TopDownGame game;

    @Override
    public void create() {
        this.widthScreen = Gdx.graphics.getWidth();
        this.heightScreen = Gdx.graphics.getHeight();

        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, widthScreen, heightScreen);
        setScreen(new MainMenuScreen(this, camera));

    }
    public  void setRestartScreen()
    {
        restartScreen = new RestartScreen(this, camera);
        setScreen(restartScreen);
    }
    public void setTopDownGameScreen()
    {
        game = new TopDownGame(camera, this);
        setScreen(game);
    }

    public void setHelpScreen()
    {
        tutorialScreen = new TutorialScreen(this, camera);
        setScreen(tutorialScreen);
    }



}
