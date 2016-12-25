package mobile.app.lonelytriangle.simulation.shapes;

import mobile.app.lonelytriangle.resource.ResourceManager.Colors;
import mobile.app.lonelytriangle.simulation.IMoveable;

/**
 * A Bonus is a straight down moving thing. If the user catches it, it improves some values.
 * The improvement will be done by the ship itself.
 * 
 * @author Waldeck Alexander, Benedikt Zönnchen
 * @version v1.0
 * 
 */
public class Bonus extends Shape
{
    /**
     * The different types that a Bonus can be.
     * 
     * @author Benedikt Zönnchen
     * @version v1.0
     *
     */
    enum BonusType 
    {
        /** shorter the shot interval for a certain time. */
        FastShot, 
        
        /** gives +1 life to the player. */
        LifeUp, 
        
        /** the user shots 3 shots for a certain time. */
        TripleShot
    }
    
    private float   elapsedTime;
    private float   visibleTime;
    private boolean destroyed;
    private final BonusType type;
    
    /**
     * Construct a new Bonus with a specified position, size and color.
     * 
     * @param x x-coordinate of the position
     * @param y y-coordinate of the position
     * @param width width of the Shape
     * @param height height of the Shape
     * @param color color of the Shape
     * @param type the specified BonusType
     */
    protected Bonus(final float x, final float y, final float width, final float height, final Colors color, BonusType type)
    {
        super(x, y, width, height, color);
        elapsedTime = 0f;
        visibleTime = 0f;
        destroyed = false;
        this.type = type;
    }

    @Override
    public void update(final float delta)
    {
        elapsedTime += delta;

        setY(getY() + getYVelocity() * -delta);

        if (elapsedTime > visibleTime)
        {
            destroyed = true;
        }
    }

    @Override
    public void handleCollision(final IMoveable shape)
    {
        if (shape instanceof Ship)
        {
            destroyed = true;
        }
    }

    @Override
    public boolean isDestroyed()
    {
        return destroyed;
    }

    /**
     * Sets the visible time of the bonus.
     * 
     * @param visibleTime the visible time of the bonus
     */
    public void setVisibleTime(final float visibleTime)
    {
        this.visibleTime = visibleTime;
    }
    
    /**
     * Returns the BonusType of this bonus.
     * 
     * @return the BonusType
     */
    public BonusType getType()
    {
        return type;
    }
}
