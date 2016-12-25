package mobile.app.lonelytriangle.simulation;

import mobile.app.lonelytriangle.util.IGridable;
import android.graphics.RectF;

/**
 * A IMoveable is a object which can be moved, handle collision and can find intersection with other IMoveable objects.
 * 
 * @author Benedikt Zönnchen
 * @version v1.0
 * 
 */
public interface IMoveable extends IGridable
{
    /**
     * return the x-coordinate of the moveable object.
     * 
     * @return the x-coordinate of the moveable object
     */
    @Override
    float getX();

    /**
     * return the y-coordinate of the moveable object.
     * 
     * @return the y-coordinate of the moveable object
     */
    @Override
    float getY();

    /**
     * set the x-coordinate of the moveable object, this will move the object.
     * 
     * @param x the new x-coordinate of the moveable object
     */
    void setX(final float x);

    /**
     * set the y-coordinate of the moveable object, this will move the object.
     * 
     * @param y the new y-coordinate of the moveable object
     */
    void setY(final float y);

    /**
     * return the width of the moveable object.
     * 
     * @return the width of the moveable object
     */
    @Override
    float getWidth();

    /**
     * return the height of the moveable object.
     * 
     * @return the height of the moveable object
     */
    @Override
    float getHeight();

    /**
     * Returns the velocity in x direction. Notice that not every IMoveable use this value.
     * 
     * @return the velocity in x direction 
     */
    float getXVelocity();

    /**
     * Sets the velocity in x direction. Notice that not every IMoveable use this value.
     * 
     * @param velocity the velocity in x direction
     */
    void setXVecolity(final float velocity);

    /**
     * Returns the velocity in y direction. Notice that not every IMoveable use this value.
     * 
     * @return the velocity in y direction 
     */
    float getYVelocity();

    /**
     * Sets the velocity in y direction. Notice that not every IMoveable use this value.
     * 
     * @param velocity the velocity in y direction
     */
    void setYVecolity(final float velocity);

    /**
     * returns true if the moveable object intersect this object, else true. This method can be used to detect collision.
     * If $ is the intersect operator than the following is the rule:
     * 
     * 1. a $ b <=> b $ a
     * 2. a $ b && b $ c && a $ c <=> a $ b $ c
     * 
     * @param moveable the moveable that will be checked
     * @return ture if the moveable object intersect this object, else true
     */
    boolean intersect(IMoveable moveable);

    /**
     * Returns true if this shape is out of the bounding box specified by the width and the height.
     * 
     * @param width width of the bounding box
     * @param height height of the bounding box
     * @return true if this shpae is out of the bounding box, otherwise false
     */
    boolean intersect(final float width, final float height);
    
    /**
     * Returns true if this shap is out of the bounding box.
     * 
     * @param rect bounding box
     * @return true if this shap is out of the bounding box, otherwise false
     */
    boolean intersect(final RectF rect);
    
    /**
     * a Callback method which should be called by the CollisionDetector. The moveable object decides here himself what should happen after it collide with an other IMoveable.
     * 
     * @param moveable the other IMoveable
     */
    void handleCollision(final IMoveable moveable);

    /**
     * returns true if this IMoveable is destroyed (and can be deleted), else false.
     * 
     * @return true if this IMoveable is destroyed, else false
     */
    boolean isDestroyed();

    /**
     * The moveableobject updates himself, so it implements a specific behaviour.
     * 
     * @param delta elapsed time in seconds
     */
    void update(final float delta);
}
