package Helper;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Enemy;
import com.mygdx.game.Player;
import weapon.Bullet;

import java.util.ArrayList;

public class CollisionDetector implements ContactListener
{

    private static ArrayList<Boolean> isTouching; //stores the number of collisions that are happening

    public CollisionDetector(Player player)
    {
        this.isTouching = new ArrayList<>();
    }
    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if(fa == null || fb == null)
        {
            return;
        }
        if(fa.getUserData() == null || fb.getUserData() == null)
        {
            return;
        }

        if(isEnemy(fa, fb))
        {
            System.out.println("Collision Detected");
            isTouching.add(true); //adds an element that represents every enemy that touches the player
        }
    }

    @Override
    public void endContact(Contact contact)
    {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if(isEnemy(fa, fb))
        {
            System.out.println("Collision Stopped");
            isTouching.remove(true);
        }

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public boolean isEnemy(Fixture a, Fixture b)
    {
        if(a.getUserData() instanceof Enemy || b.getUserData() instanceof Enemy)
        {
            if(a.getUserData() instanceof Player || b.getUserData() instanceof Player)
            {
                return true;
            }
        }
        return false;
    }
    public boolean isBullet(Fixture a, Fixture b)
    {
        if(a.getUserData() instanceof Enemy || b.getUserData() instanceof Enemy)
        {
            if(a.getUserData() instanceof Bullet || b.getUserData() instanceof Bullet)
            {
                return true;
            }
        }
        return false;
    }
    public static ArrayList<Boolean> getIsTouching() {
        return isTouching;
    }

    public static void setIsTouching(ArrayList<Boolean> isTouching) {
        CollisionDetector.isTouching = isTouching;
    }

}
