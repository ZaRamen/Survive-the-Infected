package Screens;

import Screens.GameScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.badlogic.gdx.Gdx.app;

public class MainMenuScreen implements Screen
{
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private FreeTypeFontGenerator freeTypeFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("UI/pixelFont.ttf"));
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    private BitmapFont font;
    private BitmapFont fontHover;
    private GameScreen game;
    private Viewport viewport;
    private Texture texture = new Texture("Sprites/lootBox.png");
    private Stage stage;
    private Image button;
    private Vector3 mouseInWorld3D  = new Vector3();
    public MainMenuScreen(GameScreen game, OrthographicCamera camera)
    {


        this.camera = camera;
        this.game = game;
        parameter.size = 100;
        parameter.color = Color.WHITE;
        this.font = freeTypeFontGenerator.generateFont(parameter);
        parameter.size = 100;
        parameter.color = Color.GREEN;
        this.fontHover = freeTypeFontGenerator.generateFont(parameter);
        this.viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        viewport.apply();
        this.batch = new SpriteBatch();

    }
    @Override
    public void show() {
    }


    public void render(float delta)
    {
        Gdx.gl.glClearColor(.1f, 50/255f, .16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        font.draw(batch, "Survive the INFECTED", 50, 800);

        //actually gave up on the start being a button that works for any aspect ratio
        font.draw(batch, "Start", 500, 300);


        if(Gdx.input.getX() >= 500 && Gdx.input.getX() <= 738 && Gdx.input.getY() >= 500 && Gdx.input.getY() <= 676)
        {
            fontHover.draw(batch, "Start", 500, 300);
            if(Gdx.input.isTouched())
            {
                this.dispose();
                game.setHelpScreen();
            }
        }

        batch.end();

    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

}
