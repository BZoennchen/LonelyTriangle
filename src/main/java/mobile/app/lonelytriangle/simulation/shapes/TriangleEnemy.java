package mobile.app.lonelytriangle.simulation.shapes;

import java.util.ArrayList;
import java.util.Random;

import mobile.app.lonelytriangle.resource.ResourceManager.Colors;
import mobile.app.lonelytriangle.simulation.IMoveable;
import mobile.app.lonelytriangle.simulation.IShapeFactory;
import android.util.FloatMath;

/**
 * A TriangleEnemy implements a linear down, switching left, right movement.
 * 
 * @author Waldeck Alexander
 * @version v1.0
 * 
 */
public class TriangleEnemy extends EnemyShip
{
    /**
     * Construct a new TriangleEnemy with a specified position, size and color.
     * 
     * @param x x-coordinate of the position
     * @param y y-coordinate of the position
     * @param width width of the Shape
     * @param height height of the Shape
     * @param color color of the Shape
     * @param score the score value which the user gain if he destroy this enemy
     */
    protected TriangleEnemy(final float x, final float y, final float width, final float height, final Colors color, final int score)
    {
        super(x, y, width, height, color, score);
    }

    @Override
    public void update(final float delta)
    {
        super.update(delta);
        setY(getY() + getYVelocity() * -delta);
        final int direction = FloatMath.sin(3 * getElapsedTime()) > 0 ? 1 : -1;
        setX(getX() + getXVelocity() * direction * delta);
    }

    @Override
    public ArrayList<IMoveable> shot(final float elapsedTime)
    {
        final ArrayList<IMoveable> returnList = new ArrayList<IMoveable>();

        final long currentShot = System.currentTimeMillis();
        final long delta = currentShot - getLastShotElapsedTime();
        final IShapeFactory sF = ShapeFactory.getInsance();

        if (new Random().nextDouble() < getShotChance() * elapsedTime && delta > getShotInterval())
        {
            returnList.add(sF.getShot(getX() + getWidth() / 2, getY(), this, 270));
            returnList.add(sF.getShot(getX() + getWidth(), getY() + getHeight(), this, 30));
            returnList.add(sF.getShot(getX(), getY() + getHeight(), this, 150));
            setLastShot(currentShot);
        }

        return returnList;
    }

}
