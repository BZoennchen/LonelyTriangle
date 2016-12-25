package mobile.app.lonelytriangle.simulation.shapes;

import java.util.List;

import mobile.app.lonelytriangle.resource.ResourceManager.Colors;
import mobile.app.lonelytriangle.simulation.IMoveable;
import mobile.app.lonelytriangle.simulation.Vector;

/**
 * The EnemyShip implements the collision handling for the Shape. Every EnemyShip has some 
 * important values such as shot chance, shot interval. Every EnemyShip can shot multiple shoots at once.
 * 
 * @author Benedikt Zönnchen, Waldeck Alexander
 * @version v1.0
 * 
 */
public abstract class EnemyShip extends Shape
{
    private float   elapsedTime;
    private boolean destroyed;
    private long    lastShotElapsedTime;
    private float   shotChance;
    private float   shotInterval;
    private final int score;

    /**
     * Construct an EnemyShip with a specifie position, size and color.
     * @param x x-coordinate of the position
     * @param y y-coordinate of the position
     * @param width width of the Shape
     * @param height height of the Shape
     * @param color color of the Shape
     * @param score the score value which the user gain if he destroy this enemy
     */
    protected EnemyShip(final float x, final float y, final float width, final float height, final Colors color, final int score)
    {
        super(new Vector(x, y, 0), width, height, color);
        elapsedTime = 0f;
        destroyed = false;
        lastShotElapsedTime = 0;
        shotChance = 0f;
        shotInterval = 0;
        this.score = score;
    }

    @Override
    public void update(final float delta)
    {
        setElapsedTime(getElapsedTime() + delta);
    }

    /**
     * generate and returns an List of IMoveable shots. Every shot has the same display format (only smaler) as his mothership.
     * 
     * @param elTime the elapsed time
     * @return an List of IMoveable shots
     */
    public abstract List<IMoveable> shot(final float elTime);

    /**
     * Sets the destroyed flag of this EnemyShip so that the simulation can remove it.
     * 
     * @param destroyed the flag
     */
    public void setDestroyed(final boolean destroyed)
    {
        this.destroyed = destroyed;
    }

    @Override
    public boolean isDestroyed()
    {
        return destroyed;
    }

    @Override
    public void handleCollision(final IMoveable shape)
    {
        if (shape instanceof Shot || shape instanceof Ship)
        {
            setDestroyed(true);
        }
    }

    /**
     * Returns the elapsed time.
     * 
     * @return the elapsed time
     */
    protected float getElapsedTime()
    {
        return elapsedTime;
    }

    /**
     * Sets the elapsed time.
     * 
     * @param elapsedTime the new elapsed time
     */
    protected void setElapsedTime(final float elapsedTime)
    {
        this.elapsedTime = elapsedTime;
    }

    /**
     * Returns the time in millis which has elapsed after the last shot happens.
     * 
     * @return the time in millis which has elapsed after the last shot happens
     */
    protected long getLastShotElapsedTime()
    {
        return lastShotElapsedTime;
    }
    
    /**
     * Sets the time in millis which has elapsed after the last shot happens.
     * 
     * @param elapsedShotTime the new time in millis which has elapsed after the last shot happens.
     */
    protected void setLastShot(final long elapsedShotTime)
    {
        lastShotElapsedTime = elapsedShotTime;
    }

    /**
     * Returns the chance that a shot occurs.
     *
     * @return the chance that a shot occurs
     */
    protected float getShotChance()
    {
        return shotChance;
    }

    /**
     * Sets the chance that a shot occurs.
     *
     * @param shotChance the chance that a shot occurs
     */
    protected void setShotChance(final float shotChance)
    {
        this.shotChance = shotChance;
    }

    /**
     * Returns the amount of time which has to elapse after a shot can occur.
     *
     * @return the amount of time which has to elapse after a shot can occur
     */
    protected float getShotInterval()
    {
        return shotInterval;
    }

    /**
     * Sets the amount of time which has to elapse after a shot can occur.
     *
     * @param shotInterval the amount of time which has to elapse after a shot can occur
     */
    protected void setShotInterval(final float shotInterval)
    {
        this.shotInterval = shotInterval;
    }
    
    /**
     * Returns the score value which the user gain if he destroy this enemy.
     * 
     * @return the score value
     */
    public int getScore()
    {
        return score;
    }
}
