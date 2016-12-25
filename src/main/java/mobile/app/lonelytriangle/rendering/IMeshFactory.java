package mobile.app.lonelytriangle.rendering;

import mobile.app.lonelytriangle.simulation.shapes.Shape;

/**
 * The MeshFactory produces all meshes of the application.
 * If you want a Mesh use this IMeshFactory. The Factory optimize the duplicated use of Meshes.
 * 
 * @author Benedikt Zönnchen
 * @version v1.0
 * 
 */
public interface IMeshFactory
{
    /**
     * Returns a IDrawableMesh that represents the Playership.
     * @return a IDrawableMesh that represents the Playership.
     */
    IDrawableMesh getShip();

    /**
     * Returns a IDrawableMesh that represents a shot of the parent Shape.
     * 
     * @param parent the parent Shape of this shot
     * @param width the width of the shot
     * @param height the height of the shot
     * @return a IDrawableMesh that represents a shot of the parent Shape
     */
    IDrawableMesh getShot(Shape parent, float width, float height);

    /**
     * Returns a IDrawableMesh that represents a RectangleEnemy.
     * 
     * @return a IDrawableMesh that represents a RectangleEnemy
     */
    IDrawableMesh getRectangleEnemy();

    /**
     * Returns a IDrawableMesh that represents a TriangleEnemy.
     * 
     * @return a IDrawableMesh that represents a TriangleEnemy
     */
    IDrawableMesh getTriangleEnemy();

    /**
     * Returns a IDrawableMesh that represents a Star.
     * 
     * @return a IDrawableMesh that represents a Star
     */
    IDrawableMesh getStar();

    /**
     * Returns a IDrawableMesh that represents a fast Shot.
     * 
     * @return a IDrawableMesh that represents a fast Shot
     */
    IDrawableMesh getFastShot();

    /**
     * Returns a IDrawableMesh that represents a triple Shot.
     * 
     * @return a IDrawableMesh that represents a triple Shot
     */
    IDrawableMesh getTripleShot();

    /**
     * Returns a IDrawableMesh that represents a PlusLife.
     * 
     * @return a IDrawableMesh that represents a PlusLife
     */
    IDrawableMesh getLifePlus();

    /**
     * Returns a IDrawableMesh that represents a DiamondEnemy.
     * 
     * @return a IDrawableMesh that represents a DiamondEnemy
     */
    IDrawableMesh getDiamondEnemy();
}
