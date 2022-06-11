package com.mygdx.game;

import Helper.UI;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import weapon.MachineGun;
import weapon.RayGun;
import weapon.RocketLauncher;
import weapon.Weapon;

import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.ArrayList;

public class LootBox
{

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    private Sprite sprite = new Sprite(new Texture("Sprites/lootBox.png"));
    private int timer;

    public int getLifeCounter()
    {
        return lifeCounter;
    }


    private int lifeCounter;
    public boolean isTouched() {
        return isTouched;
    }

    private  boolean ammoObtained;


    private  boolean isNewWeaponUnlocked;
    private String weaponName; // when getting ammo for a weapon
    private String weaponName2; //for when unlocking new weapons

    public void setTouched(boolean touched) {
        isTouched = touched;
    }

    private boolean isTouched = false;


    private Player player;
    private Vector2 location;
    private UI userInterface;
    private int ammoAdded;
    private int counter;
    private  boolean isHealed;
    private int healedAmount;
    public boolean isTemp() {
        return isTemp;
    }


    private boolean isTemp;
    public LootBox(Player player, Vector2 location, boolean isTemp, UI userInterface)
    {
        this.player = player;
        this.location = location;
        this.isTemp = isTemp;
        this.userInterface = userInterface;
        setLocation();

    }

    public void setLocation()
    {
        sprite.setPosition(location.x, location.y);
    }

    public void update(int level)
    {
        if(!isTouched && player.getSprite().getBoundingRectangle().overlaps(sprite.getBoundingRectangle())) //if it's touched
        {
            roll(level);
            isTouched = true;
        }

        if(!isTemp)
        {
            if(isTouched) //if the lootbox was collected start the timer
            {
                timer++;
            }
            if(timer > 600) //ten seconds and the lootbox should appear again
            {
                timer = 0;
                isTouched = false;
            }
        }
        else
        {
            lifeCounter++;//increments everytime to check if the temporary lootbox has expired
        }


    }
    public void roll(int level)
    {
        ArrayList<Weapon> weapons = player.getWeapons();
        int rand = (int)(Math.random() * 10);
        switch (level)
        {
            case 1:
                if(rand >= 7) //7-9 30% of heal chance and has to be 50% and under health
                {
                    healUp();
                }
                if(!isHealed)
                {
                    addAmmo();
                }

                break;
            case 2:
               if(!checkIfWeaponGotten("MachineGun"))
               {
                   weapons.add(new MachineGun(player));
               }
               else
               {
                   if(rand >= 7) //7-9 30% of heal chance and has to be 50% and under health
                   {
                       healUp();
                   }
                   if(!isHealed)
                   {
                       addAmmo();
                   }

               }
               break;
            case 3:
                if(!checkIfWeaponGotten("RocketLauncher"))
                {
                    player.getWeapons().add(new RocketLauncher(player));
                }
                else
                {
                    if(rand >= 7) //7-9 30% of heal chance and has to be 50% and under health
                    {
                        healUp();
                    }
                    if(!isHealed)
                    {
                        addAmmo();
                    }

                }
                break;
            case 4:
            default:
                if(!checkIfWeaponGotten("RayGun"))
                {
                    player.getWeapons().add(new RayGun(player));
                }
                else
                {
                    if(rand >= 7) //50% of heal chance and has to be 50% and under health
                    {
                        healUp();
                    }
                    if(!isHealed)
                    {
                        addAmmo();
                    }
                }
                break;
        }
    }
    public void healUp()
    {
        healedAmount = 100;
        player.setHealth(100);
        isHealed = true;
    }
    public boolean checkIfWeaponGotten(String weaponType)
    {
        for(Weapon w: player.getWeapons())
        {
            if(w.getWeaponName().equals(weaponType))
            {
                return true;
            }
        }
        isNewWeaponUnlocked = true;
        weaponName2 = weaponType;
        return false;
    }
    public void addAmmo()
    {
        int rand = (int)(Math.random() * player.getWeapons().size());
        Weapon playerWeapon = player.getWeapons().get(rand);
        ArrayList<Weapon> tempWeapons = new ArrayList<>();
        boolean rerollFailed = false;
        if(playerWeapon.getAmmo() == playerWeapon.getMaxAmmo())
        {
            for(int i = 0; i < player.getWeapons().size(); i++)
            {
                Weapon w = player.getWeapons().get(i);
                if(w.getAmmo() != w.getMaxAmmo())
                {
                    tempWeapons.add(w);
                }
            }
            if(tempWeapons.size() > 0)
            {
                rand = (int) (Math.random() * tempWeapons.size());
                playerWeapon = tempWeapons.get(rand); //reroll the to add ammo to a weapon with no ammo
            }
            else
            {
                rerollFailed = true;
            }

        } 
        if(!rerollFailed)
        {
            int limit = playerWeapon.getMaxAmmo()/2;
            weaponName = playerWeapon.getWeaponName();
            if(playerWeapon.getAmmo() + limit > playerWeapon.getMaxAmmo())
            {
                ammoAdded = playerWeapon.getMaxAmmo() - playerWeapon.getAmmo();
                playerWeapon.setAmmo(playerWeapon.getMaxAmmo());
            }
            else
            {
                ammoAdded = limit;
                playerWeapon.setAmmo(playerWeapon.getAmmo() + limit);
            }
        }
        ammoObtained = true;
        
    }
    public void render(SpriteBatch batch)
    {
        if(!isTouched) //only show up if not collected
        {
            sprite.draw(batch);
        }

        if(ammoObtained)
        {
            counter++;
            userInterface.drawAmmoIncrease(weaponName, ammoAdded);
            if(counter > 120)
            {
                counter = 0;
                ammoObtained = false;
            }

        }
        if(isNewWeaponUnlocked)
        {
            counter++;
            userInterface.drawObtainNewWeapon(weaponName2);
            if(counter > 120)
            {
                counter = 0;
                isNewWeaponUnlocked = false;
            }
        }
        if(isHealed)
        {
            counter++;
            userInterface.drawHeal();
            if(counter > 120)
            {
                counter = 0;
                isHealed = false;
            }
        }

    }


}
