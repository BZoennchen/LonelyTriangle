package mobile.app.lonelytriangle.simulation.shapes;

import mobile.app.lonelytriangle.resource.ResourceManager.Colors;

/**
 * A Star is only for the background and has no logic for collision detection or collision handling.
 * Its moving straight down.
 * 
 * @author Waldeck Alexander
 * @version v1.0
 * 
 */
public class Star extends Shape
{

    /**
     * Construct a new Star with a specified position, size and color.
     * 
     * @param x x-coordinate of the position
     * @param y y-coordinate of the position
     * @param width width of the Shape
     * @param height height of the Shape
     * @param velocity the y vecolity of the Shape
     */
    protected Star(final float x, final float y, final float width, final float height, final float velocity)
    {
        super(x, y, width, height, Colors.White);
        this.setYVecolity(velocity);
    }

    @Override
    public void update(final float delta)
    {
        this.setY(this.getY() + this.getYVelocity() * -delta);
    }
}
