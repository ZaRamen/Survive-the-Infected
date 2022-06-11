package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;


public class Mouse extends Sprite
{
    private Vector2 mouseInWorld2D = new Vector2();
    private Vector3 mouseInWorld3D = new Vector3();

    public boolean isLeftClick() {
        return leftClick;
    }
    public float x;
    public float y;
    private Player player;
    private TopDownGame game;
    boolean isHeld = false;
    public Mouse(Player player)
    {
        this.player = player;
        setMouseClickDetector();
    }

    private boolean leftClick = false;
    public void update(Camera camera)
    {
        //Gdx.input.getX() returns the x value of the cursor relative to the screen that based on coordinates
        //of the screen size which is 1200 by 900, by doing unporject we get teh real world position of the x and
        //y based on the tiled map.
        mouseInWorld3D.x = Gdx.input.getX();
        mouseInWorld3D.y = Gdx.input.getY();
        mouseInWorld3D.z = 0;
        camera.unproject(mouseInWorld3D);
        mouseInWorld2D.x = mouseInWorld3D.x;
        mouseInWorld2D.y = mouseInWorld3D.y;
        if(isHeld)
        {
            x = mouseInWorld2D.x;
            y = mouseInWorld2D.y;
            player.shoot();
        }
    }
    public void setMouseClickDetector()
    {

        Gdx.input.setInputProcessor(new InputAdapter() {
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (button == Input.Buttons.LEFT) {
                    // do something
                   isHeld = true;
                }
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                if(button == Input.Buttons.LEFT)
                {
                    isHeld = false;
                }
                return false;
            }
        });



    }
    public Vector2 getMouseInWorld2D() {
        return mouseInWorld2D;
    }




}
