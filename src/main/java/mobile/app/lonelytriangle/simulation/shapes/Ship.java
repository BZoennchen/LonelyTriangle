package mobile.app.lonelytriangle.simulation.shapes;

import mobile.app.lonelytriangle.resource.ResourceManager.Colors;
import mobile.app.lonelytriangle.simulation.IMoveable;
import mobile.app.lonelytriangle.util.Grid;
import android.util.FloatMath;

/**
 * The Ship represents the player ship, its the most complex Shape. The player ship is moving towards the targetX and
 * targetY coordinates with constant movement speed (x,y-vecility).
 * 
 * @author Benedikt Zönnchen, Waldeck Alexander, Johannes Szeibert
 * @version v1.0
 * 
 */
public class Ship extends Shape
{
    /** this is a tolerance length (for length in x and y direction) in within the ship the ship will not move. */
    private final float           notMovingTolerance;

    /** the time that has to elapsed after a shot (single or triple) happens. It is affected by fastShotSpeedUp. */
    private final int             shotInterval;

    /** saves the time (in millis) when a shot happens. */
    private long                  lastShot;

    /** the period of time in which the shots are triple shots. */
    private float                 tripleShotTime;

    /** the period of ellapsing time in which the shot interval is shorten by the fastShotSpeedUp. */
    private float                 fastShotTime;

    /**
     * the period of ellapsing time which will set to fastShotSpeedUp and tripleShotTime after the specific bonus is
     * catched.
     */
    private float                 bonusShotDuration;

    /** the amount of time which will shortern the shot duration time. */
    private float                 fastShotSpeedUp;

    /** the angle of the bonus shots (0, 0-bonusTripleShotAngle, 0+bonusTripleShotAngle). */
    private float                 bonusTripleShotAngle;

    /** the life of the player ship, if this value is equals zero, the game will be over. */
    private int                   life;

    /** the x-coordinate of the location the player wants the ship. */
    private float                 targetX;

    /** the y-coordinate of the location the player wants the ship. */
    private float                 targetY;

    /** indicate that this Shape is destroyed. This happens if the life is equals to zero. */
    private boolean               destroyed;

    /** indicate that the ship is trying to shoot as fast as possible. */
    private boolean               shoting;

    /** indicate that one or more shots were generated. This depends on the shotinterval. */
    private boolean               generateShot;

    /** TODO . */
    private Grid<IMoveable> shots;

    /** last collected Bonus. */
    private Bonus lastBonus = null;

    /** current shot type. */
    private boolean currentShotType = false;

    /**
     * Construct a new Bonus with a specified position, size and color.
     * 
     * @param x x-coordinate of the position
     * @param y y-coordinate of the position
     * @param width width of the Shape
     * @param height height of the Shape
     * @param color color of the Shape
     * @param notMovingTolerance the tolerance length (for length in x and y direction) in within the ship the ship will
     *        not move
     * @param shotInterval the duration of time which has to elapse after a new shot can generate by the ship
     */
    protected Ship(final float x, final float y, final float width, final float height, final Colors color, final float notMovingTolerance, final int shotInterval)
    {
        super(x, y, width, height, color);
        lastShot = System.currentTimeMillis();
        targetX = getX();
        targetY = getY();
        this.notMovingTolerance = notMovingTolerance;
        this.shotInterval = shotInterval;
        tripleShotTime = 0f;
        fastShotTime = 0f;
        bonusShotDuration = 0f;
        fastShotSpeedUp = 0f;
        life = 3;
        bonusTripleShotAngle = 10;
        destroyed = false;
        shoting = false;
        generateShot = false;
    }

    @Override
    public void update(final float delta)
    {
        final float xlength = Math.abs(targetX - getX());
        final float ylength = Math.abs(targetY - getY());
        final float length = FloatMath.sqrt(xlength * xlength + ylength * ylength);

        if (xlength > notMovingTolerance)
        {
            final float dx = xlength / length * getXVelocity() * delta;
            setX(targetX > getX() ? getX() + dx : getX() - dx);
        }

        if (ylength > notMovingTolerance)
        {
            final float dy = ylength / length * getYVelocity() * delta;
            setY(targetY > getY() ? getY() + dy : getY() - dy);
        }

        tripleShotTime -= delta;
        fastShotTime -= delta;

        if (tripleShotTime < 0)
        {
            tripleShotTime = 0;
        }

        if (fastShotTime < 0)
        {
            fastShotTime = 0;
        }

        if (isShoting())
        {
            generateShot = shot();
        }
    }

    private boolean shot()
    {
        final long currentShot = System.currentTimeMillis();
        final long delta = currentShot - lastShot;
        float shotIntervalOffset = 0;

        if (fastShotTime > 0)
        {
            shotIntervalOffset = fastShotSpeedUp;
        }

        final boolean shouldShot = delta > shotInterval - shotIntervalOffset;

        if (shouldShot)
        {
            shots.add(ShapeFactory.getInsance().getShot(getX() + getWidth() / 2, getY() + getHeight(), this, 90));
            currentShotType = false;

            if (tripleShotTime > 0)
            {
                shots.add(ShapeFactory.getInsance().getShot(getX() + getWidth() / 2, getY() + getHeight(), this, 90 + bonusTripleShotAngle));
                shots.add(ShapeFactory.getInsance().getShot(getX() + getWidth() / 2, getY() + getHeight(), this, 90 - bonusTripleShotAngle));
                currentShotType = true;
                
            }

            lastShot = currentShot;
        }

        return shouldShot;
    }

    /**
     * Sets the targetX.
     * 
     * @param targetX the x location where the ship trys to move (slightly)
     */
    public void setTargetX(final float targetX)
    {
        this.targetX = targetX;
    }

    /**
     * Sets the targetX.
     * 
     * @param targetY the y location where the ship trys to move (slightly).
     */
    public void setTargetY(final float targetY)
    {
        this.targetY = targetY;
    }

    /**
     * Sets the life of this ship. For each hit (except bonus items) the ship will lose one of these lifes. A LifeUp
     * Bonus will increment this value.
     * 
     * @param lifes the lifes of this ship
     */
    public void setInitialLifes(int lifes)
    {
        life = lifes;
    }

    /**
     * Returns the current shots of this ship.
     * 
     * @return the current shots of this ship
     */
    public Grid<IMoveable> getShots()
    {
        return shots;
    }
    
    /**
     * Sets the current shots of this ship.
     * 
     * @param shots the new shots of this ship
     */
    public void setShots(final Grid<IMoveable> shots)
    {
        this.shots = shots;
    }
    
    /**
     * Returns the amount of lifes.
     * 
     * @return the amount of lifes.
     */
    public int getLife()
    {
        return life;
    }

    @Override
    public void handleCollision(final IMoveable shape)
    {
        if (shape instanceof Bonus)
        {
            final Bonus bonus = (Bonus) shape;
            switch (bonus.getType())
            {
                case FastShot:
                    fastShotTime += bonusShotDuration;
                    break;
                case TripleShot:
                    tripleShotTime += bonusShotDuration;
                    break;
                case LifeUp:
                    life++;
                    break;
                default:
            }
            
            lastBonus = bonus;
        }
        else
        {
            life--;
            destroyed = true;
        }
    }

    /**
     * Returns true if the ship was shooting, otherwise false.
     * 
     * @return true if the ship was shooting, otherwise false.
     */
    public boolean isShoting()
    {
        return shoting;
    }
    
    /**
     * Returns true if a trippleShot was fired last.
     * 
     * @return true if trippleShot, otherwise false.
     */
    public boolean getcurrentShotType()
    {
        return currentShotType;
    }

    /**
     * Indicates that the ship has the permission to shoot.
     * 
     * @param shoting the shoting to set
     */
    public void setShoting(boolean shoting)
    {
        this.shoting = shoting;
    }

    @Override
    public boolean isDestroyed()
    {
        return destroyed;
    }
    
    /**
     * 
     * @return true if lastBonus not null
     */
    public boolean collectedBonus()
    {
        return (lastBonus != null);
    }
    
    /**
     * returns and removes the last collected Bonus.
     * @return the last collected Bonus
     */
    public String pullcollectedBonusName(){
        String temp = lastBonus.getType().toString();
        lastBonus = null;
        return temp;
    }

    /**
     * Sets the bonus shot duration.
     * 
     * @param bonusShotDuration the bonus shoot duration
     */
    public void setBonusShotDuration(final float bonusShotDuration)
    {
        this.bonusShotDuration = bonusShotDuration;
    }

    /**
     * Sets the fast shot speed up (time).
     * 
     * @param fastShotSpeedUp the fast shot speed up (time).
     */
    public void setFastShotSpeedUp(final float fastShotSpeedUp)
    {
        this.fastShotSpeedUp = fastShotSpeedUp;
    }

    /**
     * Sets the triple shot angle.
     * 
     * @param bonusTripleShotAngle the triple shot angle.
     */
    public void setBonusTripleShotAngle(final float bonusTripleShotAngle)
    {
        this.bonusTripleShotAngle = bonusTripleShotAngle;
    }

    /**
     * Returns true if a shot was generated by this ship. This is helpful for the controller to react on a shoting event.
     * 
     * @return true if the ship has generated a shot.
     */
    public boolean isGenerateShot()
    {
        return generateShot;
    }
}
