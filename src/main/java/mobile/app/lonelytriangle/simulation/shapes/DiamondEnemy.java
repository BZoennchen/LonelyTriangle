package mobile.app.lonelytriangle.simulation.shapes;

import java.util.ArrayList;
import java.util.Random;

import mobile.app.lonelytriangle.resource.ResourceManager.Colors;
import mobile.app.lonelytriangle.simulation.IMoveable;
import mobile.app.lonelytriangle.simulation.IShapeFactory;
import android.util.FloatMath;

/**
 * The DiamondEnemy switch between vertical and horizontal movement. And it also shots vertical and horizontal.
 * 
 * @author Waldeck Alexander
 * @version v1.0
 * 
 */
public class DiamondEnemy extends EnemyShip
{
    
    /**
     * Construct a new DiamondEnemy with a specified position, size and color.
     * 
     * @param x x-coordinate of the position
     * @param y y-coordinate of the position
     * @param width width of the Shape
     * @param height height of the Shape
     * @param color color of the Shape
     * @param score the score value which the user gain if he destroy this enemy
     */
    protected DiamondEnemy(final float x, final float y, final float width, final float height, final Colors color, final int score)
    {
        super(x, y, width, height, color, score);
    }

    @Override
    public void update(final float delta)
    {
        super.update(delta);

        final float sin = 3 * FloatMath.sin(3 * getElapsedTime());
        int xDirection = 0;
        int yDirection = 0;

        if (sin > -2 && sin < 2)
        {
            xDirection = 0;
            yDirection = 1;
        }
        else if (sin > 2)
        {
            yDirection = 0;
            xDirection = 1;
        }
        else
        {
            yDirection = 0;
            xDirection = -1;
        }

        setY(getY() + getXVelocity() * yDirection * -delta);
        setX(getX() + getYVelocity() * xDirection * delta);

    }

    @Override
    public ArrayList<IMoveable> shot(final float elapsedTime)
    {
        final ArrayList<IMoveable> returnList = new ArrayList<IMoveable>();

        final long currentShot = System.currentTimeMillis();
        final long delta = currentShot - getLastShotElapsedTime();
        final IShapeFactory sF = ShapeFactory.getInsance();

        if (new Random().nextFloat() < getShotChance() * elapsedTime && delta > getShotInterval())
        {
            returnList.add(sF.getShot(getX() + getWidth(), getY() + getHeight() / 2, this, 0));
            returnList.add(sF.getShot(getX() + getWidth() / 2, getY() + getHeight(), this, 90));
            returnList.add(sF.getShot(getX(), getY() + getHeight() / 2, this, 180));
            returnList.add(sF.getShot(getX() + getWidth() / 2, getY(), this, 270));

            setLastShot(currentShot);
        }

        return returnList;
    }
}
