package mobile.app.lonelytriangle.simulation.shapes;

import mobile.app.lonelytriangle.rendering.IDrawableMesh;
import mobile.app.lonelytriangle.resource.ResourceManager.Colors;
import mobile.app.lonelytriangle.simulation.IMoveable;
import mobile.app.lonelytriangle.simulation.Vector;
import android.graphics.RectF;

/**
 * A shape is a abstract moveable object without a specific moving behavior.
 * But with a specified position, width, height, velocity color and mesh (display format).
 * The moving behavior and the collision handling should be implemented in sub classes.
 * 
 * @author Benedikt Zönnchen
 * @version v1.0
 * 
 */
public abstract class Shape implements IMoveable
{
    /** the current position (x,y-coordinates) of this shape. */
    private final Vector  position;

    /** the velocity in y direction. */
    private float         xVelocity;

    /** the velocity in y direction. */
    private float         yVelocity;

    /** the width of the shape. */
    private final float   width;

    /** the height of the shape. */
    private final float   height;

    /** the color of the shape. */
    private final Colors  color;

    /** mesh of the shape. */
    private IDrawableMesh mesh;

    /**
     * Construct a new shape.
     * 
     * @param x x-coordinate of the shape
     * @param y y-coordinate of the shape
     * @param width width of the shape
     * @param height height of the shape
     * @param color the color of this shape
     */
    protected Shape(final float x, final float y, final float width, final float height, final Colors color)
    {
        this(new Vector(x, y, 0), width, height, color);
    }

    /**
     * Construct a new shape.
     * 
     * @param position position of the shape (x,y-coordinate wrapped in a vector)
     * @param width width of the shape
     * @param height height of the shape
     * @param color the color of this shape
     */
    protected Shape(final Vector position, final float width, final float height, final Colors color)
    {
        this.position = position;
        this.width = width;
        this.height = height;
        this.color = color;
        
        if (width <= 0)
        {
            throw new IllegalArgumentException("width is lower or equals zero!");
        }
        
        if (height <= 0)
        {
            throw new IllegalArgumentException("height is lower or equals zero!");
        }
    }

    @Override
    public boolean intersect(final IMoveable moveable)
    {
        final IMoveable left = getX() < moveable.getX() ? this : moveable;
        final IMoveable right = getX() > moveable.getX() ? this : moveable;
        final IMoveable top = getY() > moveable.getY() ? this : moveable;
        final IMoveable bottom = getY() < moveable.getY() ? this : moveable;

        return right.getX() < left.getX() + left.getHeight() && top.getY() < bottom.getY() + bottom.getHeight();
    }

    @Override
    public float getX()
    {
        return position.getX();
    }

    @Override
    public float getY()
    {
        return position.getY();
    }

    @Override
    public void setX(final float x)
    {
        position.setX(x);
    }

    @Override
    public void setY(final float y)
    {
        position.setY(y);
    }

    @Override
    public float getWidth()
    {
        return width;
    }

    @Override
    public float getHeight()
    {
        return height;
    }

    @Override
    public boolean isDestroyed()
    {
        return false;
    }

    /**
     * Returns the color of the Shape.
     * 
     * @return the color of the Shape
     */
    public Colors getColor()
    {
        return color;
    }

    /**
     * Returns the IDrawableMesh which defines the display format.
     * 
     * @return the IDrawableMesh which defines the display format
     */
    public IDrawableMesh getMesh()
    {
        return mesh;
    }

    /**
     * Sets the IDrawableMesh which defines the display format.
     * This Mesh should has the same width and height of the Shape otherwise the collsion detection and the drawing dont fit together.
     * 
     * @param mesh the IDrawableMesh which defines the display format
     */
    public void setMesh(final IDrawableMesh mesh)
    {
        this.mesh = mesh;
    }

    @Override
    public float getXVelocity()
    {
        return xVelocity;
    }

    @Override
    public void setXVecolity(final float velocity)
    {
        xVelocity = velocity;
    }

    @Override
    public float getYVelocity()
    {
        return yVelocity;
    }

    @Override
    public void setYVecolity(final float velocity)
    {
        yVelocity = velocity;
    }

    // do nothing is the standard behavior.
    @Override
    public void handleCollision(final IMoveable shape)
    {
    }
    
    @Override
    public boolean intersect(final float widthf, final float heightf)
    {
        return intersect(new RectF(0f, 0f, widthf, heightf));
    }
    
    @Override
    public boolean intersect(final RectF rect)
    {
        return new RectF(getX(), getY(), getX() + getWidth(), getY() + getHeight()).intersect(rect);  
    }
}
