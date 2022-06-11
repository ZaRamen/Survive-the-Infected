package com.mygdx.game;

import Helper.BodyHelper;
import Helper.Helper;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;


public class WorldCreator
{
    public TiledMap getTiledMap() {
        return map;
    }

    private TiledMap map;
    private TopDownGame game;
    public WorldCreator(TopDownGame game)
    {
        this.game = game;
    }


    public OrthogonalTiledMapRenderer setupMap()
    {
        map = new TmxMapLoader().load("Maps/Map0.tmx");
        setupMapObjects(map.getLayers().get("obstacles").getObjects());

        return new OrthogonalTiledMapRenderer(map);
    }

    public void setupMapObjects(MapObjects mapObjects)
    {
        for(MapObject mapObject: mapObjects)
        {
            if(mapObject instanceof PolygonMapObject)
            {
                createStaticBody((PolygonMapObject) mapObject);
            }
            if(mapObject instanceof RectangleMapObject)
            {
                Rectangle rec = ((RectangleMapObject)mapObject).getRectangle();
                if(mapObject.getName().equals("player"))
                {
                    Body body = BodyHelper.createBody(rec.getX() + rec.getWidth()/2, rec.getY() +
                                    rec.getHeight(), rec.getWidth(), rec.getHeight(),
                            false, game.getWorld());


                    game.setPlayer(new Player(rec.getWidth(), rec.getHeight(), body));
                }
            }

        }

    }


    public void createStaticBody(PolygonMapObject polygonMapObject)
    {
        BodyDef bodyDef = new BodyDef();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        //create body in the world
        Body body = game.getWorld().createBody(bodyDef);
        //set the shape to a polygon
        Shape shape = createPolygonShape(polygonMapObject);
        body.createFixture(shape, 1000);
        shape.dispose();

    }

    private Shape createPolygonShape(PolygonMapObject polygonMapObject)
    {
        float[] vertices = polygonMapObject.getPolygon().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length/2];

        for(int i = 0; i < vertices.length/2; i++)
        {
            Vector2 current = new Vector2(vertices[i * 2]/ Helper.getPPM(), vertices[i * 2 + 1]/ Helper.getPPM());
            worldVertices[i] = current;
        }
        PolygonShape shape = new PolygonShape();
        shape.set(worldVertices);
        return shape;
    }
}
