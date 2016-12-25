package mobile.app.lonelytriangle.simulation.shapes;

import mobile.app.lonelytriangle.simulation.IMoveable;
import android.util.FloatMath;

/**
 * A Shot is a smaller version of his mothership. The movement 
 * 
 * @author Benedikt Zönnchen, Johannes Szeibert
 * @version v1.0
 * 
 */
public class Shot extends Shape
{

    /** indicate that his shot is destroyed. */
    private boolean                destroyed;
    
    /** the angle of the flying shot 0 is equals orthogonal up (y direction). */
    private final float            angle;

    /**
     * Construct a new Shot with a specified position, size and color.
     * 
     * @param x x-coordinate of the position
     * @param y y-coordinate of the position
     * @param width width of the Shape
     * @param height height of the Shape
     * @param parent the mothership
     * @param angle the of the shot direction (0 is straight up)
     */
    protected Shot(final float x, final float y, final float width, final float height, final Shape parent, final float angle)
    {
        super(x, y, width, height, parent.getColor());
        this.angle = angle;
        destroyed = false;
    }

    @Override
    public void update(final float delta)
    {
        setY(getY() + getXVelocity() * delta * FloatMath.sin((float) Math.PI * angle / 180));
        setX(getX() + getYVelocity() * delta * FloatMath.cos((float) Math.PI * angle / 180));
    }

    @Override
    public boolean isDestroyed()
    {
        return destroyed;
    }

    @Override
    public void handleCollision(final IMoveable shape)
    {
        destroyed = true;
    }
}
