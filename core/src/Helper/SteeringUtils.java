package Helper;

import com.badlogic.gdx.math.Vector2;

public class SteeringUtils
{
    public static float vectorToAngle(Vector2 vector2)
    {
        return (float)Math.atan2(-vector2.x, vector2.y);
    }
    public static Vector2 angleToVector(Vector2 vector2, float angle)
    {
        vector2.x = -(float)Math.sin(angle);
        vector2.y = (float)Math.cos(angle);
        return vector2;
    }
}
