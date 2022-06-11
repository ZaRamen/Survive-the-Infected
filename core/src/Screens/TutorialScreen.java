package Screens;

import Screens.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TutorialScreen implements Screen
{

    private FreeTypeFontGenerator freeTypeFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("UI/pixelFont.ttf"));
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    private  BitmapFont font;
    private SpriteBatch batch;
    private GameScreen gameScreen;
    private OrthographicCamera camera;
    private Viewport viewport;

    public TutorialScreen(GameScreen gameScreen, OrthographicCamera camera)
    {
       parameter.size = 50;
       parameter.color = Color.WHITE;
       font = freeTypeFontGenerator.generateFont(parameter);
       batch = new SpriteBatch();
       this.viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
       this.gameScreen = gameScreen;
       this.camera = camera;
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(.1f, 50/255f, .16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        font.draw(batch, "W A S D to Move", 400, 600);
        font.draw(batch, "Cursor to shoot", 400, 500);
        font.draw(batch, "Obtain Lootboxes to get Ammo or Health", 100, 400);

        font.draw(batch, "Press enter to start playing", 300, 300);
        if(Gdx.input.isKeyPressed(Input.Keys.ENTER))
        {
            this.dispose();
          gameScreen.setTopDownGameScreen();

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
