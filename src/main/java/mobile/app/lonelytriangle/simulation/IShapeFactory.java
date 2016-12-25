package mobile.app.lonelytriangle.simulation;

import mobile.app.lonelytriangle.simulation.shapes.EnemyShip;
import mobile.app.lonelytriangle.simulation.shapes.Shape;
import mobile.app.lonelytriangle.simulation.shapes.Ship;

/**
 * The IShapeFactory is a factory which offers methods to create new IMoveable and different Shapes.
 * 
 * @author Benedikt Zönnchen
 * @version v1.0
 * 
 */
public interface IShapeFactory
{
    /**
     * Returns a new IMoveable (a shot) with the specified x,y-coordinates and angle. The parent Shape is used to
     * get the same IDrawableMesh with a differrent width and height (defined by a scale factor).
     * 
     * @param x the x-coordinate of the IMoveable
     * @param y the y-coordinate of the IMoveable
     * @param parent the parent Shape which defines the IDrawableMesh of this IMoveable
     * @param angle the angle defines the dircetion of the shot
     * @return a new IMoveable (a shot)
     */
    IMoveable getShot(final float x, final float y, Shape parent, float angle);

    /**
     * Returns and configure a new Ship (the player ship) with the specified x,y-coordinate.
     * 
     * @param x the x-coordinate of the Ship
     * @param y the y-coordinate of the Ship
     * @return a new Ship
     */
    Ship getShip(final float x, final float y);

    /**
     * Returns and configure a new RectangleEnemy with the specified x,y-coordinate.
     * 
     * @param x the x-coordinate of the EnemyShip
     * @param y the y-coordinate of the EnemyShip
     * @return a new RectangleEnemy
     */
    EnemyShip getRectangleEnemy(final float x, final float y);

    /**
     * Returns and configure a new TriangleEnemy with the specified x,y-coordinate.
     * 
     * @param x the x-coordinate of the EnemyShip
     * @param y the y-coordinate of the EnemyShip
     * @return a new TriangleEnemy
     */
    EnemyShip getTriangleEnemy(final float x, final float y);

    /**
     * Returns and configure a new DiamondEnemy with the specified x,y-coordinate.
     * 
     * @param x the x-coordinate of the EnemyShip
     * @param y the y-coordinate of the EnemyShip
     * @return a new DiamondEnemy
     */
    EnemyShip getDiamondEnemy(final float x, final float y);

    /**
     * Returns and configure a new Star with the specified x,y-coordinate.
     * 
     * @param x the x-coordinate of the IMoveable
     * @param y the y-coordinate of the IMoveable
     * @return a new Star
     */
    IMoveable getStar(final float x, final float y);

    /**
     * Returns and configure a new FastShot with the specified x,y-coordinate.
     * 
     * @param x the x-coordinate of the IMoveable
     * @param y the y-coordinate of the IMoveable
     * @return a new FastShot
     */
    IMoveable getFastShot(final float x, final float y);

    /**
     * Returns and configure a new TripleShot with the specified x,y-coordinate.
     * 
     * @param x the x-coordinate of the IMoveable
     * @param y the y-coordinate of the IMoveable
     * @return a new TripleShot
     */
    IMoveable getTripleShot(final float x, final float y);

    /**
     * Returns and configure a new LifeUp with the specified x,y-coordinate.
     * 
     * @param x the x-coordinate of the IMoveable
     * @param y the y-coordinate of the IMoveable
     * @return a new LifeUp
     */
    IMoveable getLifeUp(final float x, final float y);

}
