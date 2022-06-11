package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;


public class HealthBar extends Actor {

    private NinePatchDrawable background;
    private int width, height;
    private NinePatchDrawable bar;

    public HealthBar(int width, int height, boolean isPlayer) {
        this.width = width;
        this.height = height;
        TextureAtlas skinAtlas = new TextureAtlas(Gdx.files.internal("UI/UiAssets.atlas"));
        NinePatch healthBarBackground = new NinePatch(skinAtlas.findRegion("Health-Bar"), 5, 5, 4, 4);
        NinePatch healthBar = new NinePatch(skinAtlas.findRegion("Red-Health"), 5, 5, 4, 4);

        if(isPlayer) {
            healthBar = new NinePatch(skinAtlas.findRegion("Player-Health"), 5, 5, 4, 4);
        }
        bar = new NinePatchDrawable(healthBar);
        background = new NinePatchDrawable(healthBarBackground);
    }

    public void draw(Batch batch, float x, float y, float currentHealth, float maxHealth)
    {
        background.draw(batch, x, y, width * (maxHealth/40), height);

        if(currentHealth != 0)
        {
            bar.draw(batch, x, y, width * (currentHealth/40), height);
        }

    }



}
