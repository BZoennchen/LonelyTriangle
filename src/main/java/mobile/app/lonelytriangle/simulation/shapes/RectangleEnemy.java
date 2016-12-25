package mobile.app.lonelytriangle.simulation.shapes;

import java.util.ArrayList;
import java.util.Random;

import mobile.app.lonelytriangle.resource.ResourceManager.Colors;
import mobile.app.lonelytriangle.simulation.IMoveable;
import mobile.app.lonelytriangle.simulation.IShapeFactory;
import android.util.FloatMath;

/**
 * A RectangleEnemy implements a vertical sinus movement with a specified radius. Its shoots at 45, 135, 225 and 315 degree.
 * 
 * @author Waldeck Alexander, Benedikt Zönnchen
 * @version v1.0
 * 
 */
public class RectangleEnemy extends EnemyShip
{
    private float movementRadius;
    private float movementFrequency;
    private final float baseX;
    /**
     * Construct a new DiamondEnemy with a specified position, size and color.
     * 
     * @param x x-coordinate of the position
     * @param y y-coordinate of the position
     * @param size width and height of the Shape
     * @param color color of the Shape
     * @param score the score value which the user gain if he destroy this enemy
     */
    protected RectangleEnemy(final float x, final float y, final float size, final Colors color, final int score)
    {
        super(x, y, size, size, color, score);
        baseX = x;
        movementRadius = 0;
    }

    @Override
    public void update(final float delta)
    {
        super.update(delta);
        setY(getY() + getXVelocity() * -delta);
        setX(baseX + movementRadius * FloatMath.sin(movementFrequency * getElapsedTime()));
    }

    @Override
    public ArrayList<IMoveable> shot(final float elapsedTime)
    {
        final ArrayList<IMoveable> returnList = new ArrayList<IMoveable>();

        final long currentShot = System.currentTimeMillis();
        final long delta = currentShot - getLastShotElapsedTime();
        final IShapeFactory shapeFactory = ShapeFactory.getInsance();

        if (new Random().nextDouble() < getShotChance() * elapsedTime && delta > getShotInterval())
        {
            returnList.add(shapeFactory.getShot(getX(), getY(), this, 225));
            returnList.add(shapeFactory.getShot(getX() + getWidth(), getY(), this, 315));
            returnList.add(shapeFactory.getShot(getX(), getY() + getHeight(), this, 135));
            returnList.add(shapeFactory.getShot(getX() + getWidth(), getY() + getHeight(), this, 45));

            setLastShot(currentShot);
        }

        return returnList;
    }

    /**
     * Sets the movement radius which directly afflect the length of the movement in x direction.
     * 
     * @param radius the movement radius r (r * sin(x))
     */
    protected void setMovementRadius(final float radius)
    {
        movementRadius = radius;
    }

    /**
     * Set the frequenzy of the sinus movement.
     * 
     * @param rectFreq the frequency f (sin(f * x))
     */
    protected void setMovementFrequency(float rectFreq)
    {
       movementFrequency = rectFreq;
    }

}
