package mobile.app.lonelytriangle.util;
/**
 * A IGridable is a object which can be positioned in a Grid.
 * 
 * @author Benedikt Zönnchen
 * @version v1.0
 *
 */
public interface IGridable
{
    /**
     * Return the x-coordinate of this girdable object.
     * @return the x-coordinate of the girdable
     */
    float getX();
    
    /**
     * Return the y-coordinate of this girdable object.
     * @return the y-coordinate of the girdable
     */
    float getY();
    
    /**
     * Return the height of this girdable object.
     * @return the height of the girdable
     */
    float getHeight();
    
    /**
     * Return the width of this girdable object.
     * @return the width of the girdable
     */
    float getWidth();
}
