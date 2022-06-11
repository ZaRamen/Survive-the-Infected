package com.mygdx.game;

import Helper.*;
import Helper.BodyHelper;
import Helper.Helper;
import Screens.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import weapon.Bullet;
import weapon.RayGun;
import weapon.RocketLauncher;

import java.util.ArrayList;
import java.util.HashMap;

public class TopDownGame extends ScreenAdapter {
	public SpriteBatch getBatch() {
		return batch;
	}

	public void setBatch(SpriteBatch batch) {
		this.batch = batch;
	}

	private SpriteBatch batch;
	public Player player;
	public static World world;
	private OrthographicCamera camera;
	private Box2DDebugRenderer debugRenderer;
	private OrthogonalTiledMapRenderer tileMapRenderer;
	private WorldCreator worldCreator;
	public static ArrayList<Bullet> bullets = new ArrayList<>();
	public static ArrayList<Bullet> devilBullets = new ArrayList<>();

	public int getLevel() {
		return level;
	}



	private int level = 1;
	private int limitCounter;
	private int enemyMultiplier = 10;
	private HashMap<Integer, Vector2> spawnLocation = new HashMap<Integer, Vector2>();
	private ArrayList<Enemy> enemies = new ArrayList<>();
	private UI userInterface;
	private ArrayList<LootBox> lootBoxes = new ArrayList<>();
	private boolean levelChange;
	private int lootBoxCounter;
	private Viewport viewport;
	private GameScreen gameScreen;
	private boolean rocketHit = false;
	private ArrayList<Bullet> rocketBullets = new ArrayList<>();
	private boolean isPaused;
	public TopDownGame(OrthographicCamera camera, GameScreen gameScreen)
	{
		this.gameScreen = gameScreen;
		this.camera = camera;
		this.batch = new SpriteBatch();
		this.world = new World(new Vector2(0, 0), false);
		this.debugRenderer = new Box2DDebugRenderer();
		this.worldCreator = new WorldCreator(this);
		this.tileMapRenderer = worldCreator.setupMap();
		this.userInterface = new UI(camera, this);
		this.viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
		viewport.apply();
		world.setContactListener(new CollisionDetector(player));
		setSpawnLocation(); //enemy
		setLootBoxes();

	}
	public void setLootBoxes() //permanent lootbox areas
	{
		lootBoxes.add(new LootBox(player, new Vector2(512, 1472), false, userInterface));
		lootBoxes.add(new LootBox(player, new Vector2(2688, 1472), false, userInterface)); //idk why but the y values or flipped on the tilemap
		lootBoxes.add(new LootBox(player, new Vector2(1280, 2624), false, userInterface));
		lootBoxes.add(new LootBox(player, new Vector2(1472, 448), false, userInterface));
	}
	public void checkIfTempLootBox()
	{

		for(int i = 0; i < lootBoxes.size(); i++)
		{
			LootBox lootBox = lootBoxes.get(i);
			if(lootBox.isTemp() && lootBox.isTouched()) //remove if the lootbox was collected by the player
			{
				lootBoxCounter++;
				if(lootBoxCounter > 60)
				{
					lootBoxCounter = 0;
					lootBoxes.remove(i); //can't remove right away when touched or the message pop up doesn't show
					i--;
				}
			}
			if(lootBox.isTemp() && !lootBox.isTouched() && lootBox.getLifeCounter() > 600) //remove if it's temp and 10 seconds//have elapsed since it spawned
			{

				lootBoxes.remove(i);
				i--;
			}

		}
	}
	public World getWorld()
	{
		return world;
	}
	public void setPlayer(Player player)
	{
		this.player =  player;
	}

	public void createEnemies()
	{
		boolean isOverlap = false;
		Vector2 pos = randomizeSpawn();
		int rand = (int)(Math.random() * 100) + 1;
		if(rand >= 50) //delays the spawn of each enemy
		{
			for(Enemy e: enemies)
			{
				if(e.getPosition() == pos)//check overlap
				{
					isOverlap = true;
				}
			}

			if(!isOverlap && limitCounter <= level * enemyMultiplier)  //create enemies if it's within the limit
			{
				Enemy temp = null;
				Body body = BodyHelper.createBody(pos.x,
						pos.y, 64, 64,
						 false, world, 30);

				int rand2 = (int)(Math.random() * 100);
				int limit = 90;
				if(level > 10)
				{
					limit = 30;
				}
				else if(level > 4)
				{
					limit = 50;
				}
				if(rand2 <= limit) //from a 90% chance to now a 50% chance after level 4 and 30% chnace after level 10 for normal zombs
				{
					temp = new Enemy(body, 0, 100 + (level * 10), player);
				}
				else
				{
					temp = new DevilEnemy(body, 0, 200 + (level * 10), player, level);
				}
				enemies.add(temp);
				limitCounter++;
				temp.arriveBehavior(player.getB2dSteeringEntity());
			}
		}
	}
	//set enemy spawn location
	public void setSpawnLocation() //intial spawn locations of enemy we use randomize spawn to increment it
	//too lazy to add all the coordinates to the hashmap
	{
		spawnLocation = new HashMap<>();
		spawnLocation.put(1, new Vector2(1792, 3072)); // level 1
		spawnLocation.put(2, new Vector2(64, 1408));
		spawnLocation.put(3, new Vector2(3712,1536));
		spawnLocation.put(4, new Vector2(1792, 64));

	}
	public Vector2 randomizeSpawn() //the spawn location of the enemies
	{
		Vector2 pos = null;
		int rand;
		switch (level)
		{
			case 1: rand = (int)(Math.random() * 4) + 1; break; // 1 to 4
			case 2: rand = (int)(Math.random() * 8) + 1; break; // 1 to 8
			case 3: rand = (int)(Math.random() * 12) + 1; break; // 1 to 12
			default: rand = (int)(Math.random() * 16) + 1; break; // 1 to 16 all spawn location for the rest of the, level 4 and beyond
		}

		switch (rand)
		{
			case 1: pos = spawnLocation.get(1); break;
			case 2: pos = new Vector2(spawnLocation.get(1).x + 64, spawnLocation.get(1).y);  break;
			case 3: pos = new Vector2(spawnLocation.get(1).x + 128, spawnLocation.get(1).y);  break;
			case 4: pos = new Vector2(spawnLocation.get(1).x + 192, spawnLocation.get(1).y);  break;
			case 5: pos = new Vector2(spawnLocation.get(2).x, spawnLocation.get(2).y);  break;
			case 6: pos = new Vector2(spawnLocation.get(2).x, spawnLocation.get(2).y + 64);  break;
			case 7: pos = new Vector2(spawnLocation.get(2).x, spawnLocation.get(2).y + 128);  break;
			case 8: pos = new Vector2(spawnLocation.get(2).x, spawnLocation.get(2).y + 192);  break;
			case 9: pos = new Vector2(spawnLocation.get(3).x, spawnLocation.get(3).y);  break;
			case 10: pos = new Vector2(spawnLocation.get(3).x, spawnLocation.get(3).y + 64);  break;
			case 11: pos = new Vector2(spawnLocation.get(3).x, spawnLocation.get(3).y + 128);  break;
			case 12: pos = new Vector2(spawnLocation.get(3).x, spawnLocation.get(3).y + 192);  break;
			case 13: pos = new Vector2(spawnLocation.get(4).x, spawnLocation.get(4).y + 192);  break;
			case 14: pos = new Vector2(spawnLocation.get(4).x + 64, spawnLocation.get(4).y);  break;
			case 15: pos = new Vector2(spawnLocation.get(4).x + 128, spawnLocation.get(4).y);  break;
			case 16: pos = new Vector2(spawnLocation.get(4).x + 192, spawnLocation.get(4).y);  break;
		}
		return pos;
	}


	@Override
	public void show() {

	}


	@Override
	public void render (float delta)
	{
		this.update(delta);
		Gdx.gl.glClearColor(4f/255,31f/255,40f/255,1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		final float darken =0.5f;

		tileMapRenderer.getBatch().setColor(darken,darken,darken,1f);


		tileMapRenderer.render();
		batch.begin();
		//render objects
		player.render(batch);
		for(Bullet b: bullets)
		{
			b.render(batch);
		}
		for(Bullet b: devilBullets)
		{
			b.render(batch);
		}
		for(Enemy e: enemies)
		{
			e.render(batch);
		}
		for(LootBox lootBox: lootBoxes)
		{
			lootBox.render(batch);
		}





		userInterface.drawUI();
		userInterface.drawPopUp();
		if(levelChange)
		{
			userInterface.drawLevelChange();
		}
		if(isPaused)
		{
			userInterface.drawPauseScreen();
		}



		batch.end();
//		debugRenderer.render(world, camera.combined.scl(Helper.getPPM()));
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


	public void setLevelChange(boolean levelChange)
	{
		 this.levelChange = levelChange;
	}

	private void update(float delta)
	{
		pauseScreen();
		if(!isPaused)
		{

			world.step(1/60f, 6, 2);
			cameraUpdate();
			batch.setProjectionMatrix(camera.combined);
			tileMapRenderer.setView(camera);


			if(player.getHealth() <= 0)
			{
				gameScreen.setRestartScreen();
				bullets.clear();
				devilBullets.clear();
			}
			player.update();


			for(LootBox lootBox: lootBoxes)
			{
				lootBox.update(level);
			}

			for(Enemy e: enemies)
			{
				e.update(delta);
			}
			for(int i = 0; i < bullets.size(); i++)
			{
				bullets.get(i).update(delta);
			}
			for(int i = 0; i <  devilBullets.size(); i++)
			{
				devilBullets.get(i).update(delta);
			}


			//spawn enemies continuosly
			createEnemies();
			//check if the bullets hit an enemy and remove them if they stay too long on screen
			checkBullets();
			//check if the enemy died and remove them from the world and calculate chance to drop a loot box
			checkEnemyDied();
			//check if the loot box is a temporary drop from a enemy
			checkIfTempLootBox();
			//check if score passes the level score requirements
			checkScore();
			player.getMouse().update(camera);
		}




	}


	public void checkBullets()
	{
		Bullet bullet = null;
		for(int i = 0; i < enemies.size(); i++)
		{
			Enemy e = enemies.get(i);
			for(int j = 0; j < bullets.size(); j++)
			{
				Bullet b = bullets.get(j);
				if(b.getSprite().getBoundingRectangle().overlaps(e.getSprite().getBoundingRectangle()))
				{
					//bullet overlaps the bullet sprite for too long(this thing runs 60 times a second)
					// so extra enemy objects are added so we have to use a variable in this case being isHit
					e.setHit(true);
					bullet = b; //only one bullet so we should be fine
				}
			 }
		}
		Bullet devilBullet = null;
		for(Bullet b: devilBullets)
		{
			if(b.getSprite().getBoundingRectangle().overlaps(player.getSprite().getBoundingRectangle()))
			{
				player.setHealth(player.getHealth() - 10);
				devilBullet = b;
			}
		}



		for(Bullet b: rocketBullets)
		{
			b.bulletRemoveRocket();
		}
		if(bullet != null)
		{
			if(player.getCurrentWeapon() instanceof RocketLauncher)
			{
				bullet.setSprite(new Sprite(new Texture("Weapons/explosion.png"), 192, 192));
				bullet.setRocketHit(true);
				rocketBullets.add(bullet);
			}
			 else if(!(player.getCurrentWeapon() instanceof RayGun))
			{
				//remove bullet after impact only if it's not a raygun
				bullets.remove(bullet);
			}
		}
		if(devilBullet != null)
		{
			devilBullets.remove(devilBullet);
		}
	}
	public void checkEnemyDied()
	{
		for(int i = enemies.size() - 1; i >= 0; i--)
		{
			Enemy enemy = enemies.get(i);

			if(enemy.isHit())
			{
				enemy.setHealth(enemy.getHealth() - player.getCurrentWeapon().getDamage());
				if(enemy.getHealth() <= 0)
				{
					int rand = (int)(Math.random() * 100);
					if(rand < 10 && lootBoxes.size() <= 15) //drop 10% of the time
					{
						Vector2 location = new Vector2(enemy.getSprite().getX(),
								enemy.getSprite().getY());
						lootBoxes.add(new LootBox(player, location, true, userInterface)); //spawn lootbox
					}
					else if(enemy instanceof DevilEnemy) //100% drop if devil enemy
					{
						if(lootBoxes.size() <= 20) //can't have too much lootboxes
						{
							Vector2 location = new Vector2(enemy.getSprite().getX(),
								enemy.getSprite().getY());
							lootBoxes.add(new LootBox(player, location, true, userInterface));
						}

					}
					world.destroyBody(enemy.getBody()); //destroy the enemy body that was hit(basically it died)
					enemies.remove(enemy);
					player.setScore(player.getScore() + 10); //increment player score
				}
				enemy.setHit(false);

			}
		}
	}



	public void checkScore()
	{
		//each zombie killed is 10 points so the amount of kills to advance is level * enemyMultiplier/1.2f
		//1.2f so the player doesn't have to kill every enemy to win
		if(player.getScore() >= (level * enemyMultiplier * 10)/1.01f)
		{
			level++;
			enemyMultiplier += 5;
			//increments by 1 so level 1: (1 * 10)/1.2f
			//level 2: (1 * 11)/1.2f
			levelChange = true;
		}
	}
	private void pauseScreen()
	{
		if(Gdx.input.isKeyJustPressed(Input.Keys.P))
		{
			isPaused = !isPaused;
		}
	}

	private void cameraUpdate()
	{
		Vector3 position = camera.position;
		//get player position bring it to our world position and then do round and divide by 10
		position.x = Math.round(player.getBody().getPosition().x * Helper.getPPM() * 10)/10f;
		position.y = Math.round(player.getBody().getPosition().y * Helper.getPPM() * 10)/10f;

		MapProperties mapProperties = worldCreator.getTiledMap().getProperties();
		int levelWidth = mapProperties.get("width", Integer.class) * 64;
		int levelHeight = mapProperties.get("height", Integer.class) * 64;


		//makes it so the camera doesn't go out of bounds
		//do levelWidth - camera.viewportWidth/Height * 2 to account for extra space
		//when doing camera.viewportWidth/2, we start at the midpoint since the
		//camera is in the middle of the screen
		boundary(camera, camera.viewportWidth/2, camera.viewportHeight/2,
				levelWidth - camera.viewportWidth/2 * 2,
				levelHeight - camera.viewportHeight/2 * 2);

		camera.position.set(position);

		camera.update();
	}

	private void boundary(Camera cam, float startX, float startY, float mapWidth, float mapHeight)
	{
		Vector3 position = cam.position;
		if(position.x < startX)
		{
			position.x = startX;
		}
		if(position.y < startY)
		{
			position.y = startY;
		}
		if(position.x > startX + mapWidth)
		{
			position.x = startX + mapWidth;

		}
		if(position.y > startY + mapHeight)
		{
			position.y = startY + mapHeight;
		}
	}




	@Override
	public void dispose () {
		batch.dispose();
		userInterface.dispose();

	}
}
