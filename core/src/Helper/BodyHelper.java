package Helper;

import com.badlogic.gdx.physics.box2d.*;

public class BodyHelper
{
    //create body objects
    public static Body createBody(float x, float y, float width, float height, boolean isStatic, World world)
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = isStatic ? BodyDef.BodyType.StaticBody: BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x/Helper.getPPM(), y/Helper.getPPM());
        bodyDef.fixedRotation = true;
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2/Helper.getPPM(), height/2/Helper.getPPM());
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.restitution = 0;
        body.createFixture(fixtureDef);
        shape.dispose();
        return body;
    }
    public static Body createBody(float x, float y, float width, float height, boolean isStatic, World world, int density)
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = isStatic ? BodyDef.BodyType.StaticBody: BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x/Helper.getPPM(), y/Helper.getPPM());
        bodyDef.fixedRotation = true;
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2/Helper.getPPM(), height/2/Helper.getPPM());
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = density;
        fixtureDef.restitution = 0;
        body.createFixture(fixtureDef);
        shape.dispose();
        return body;
    }




}
