package mobile.app.lonelytriangle.rendering;

import java.lang.reflect.InvocationTargetException;

import mobile.app.lonelytriangle.resource.IResourceManager;
import mobile.app.lonelytriangle.resource.ResourceManager;
import mobile.app.lonelytriangle.simulation.shapes.Shape;

/**
 * singleton implementation of IMeshFactory. The Factory saves the IDrawableMesh objects and returns the same object for multiple use. This keeps the amount of memory
 * usage low.
 * 
 * @author Benedikt Zönnchen
 * @version v1.0
 * 
 */
public final class MeshFactory implements IMeshFactory
{
    private static MeshFactory     instance = new MeshFactory();
    private final IResourceManager resourceManager;

    private IDrawableMesh          ship;
    private IDrawableMesh          diamond;
    private IDrawableMesh          rect;
    private IDrawableMesh          triangle;
    private IDrawableMesh          life;
    private IDrawableMesh          star;
    private IDrawableMesh          fastShot;
    private IDrawableMesh          tripleShot;

    private MeshFactory()
    {
        resourceManager = ResourceManager.getInstance();
    }

    /**
     * Returns the singlton instance.
     * @return the singlton instance
     */
    public static MeshFactory getInstance()
    {
        return instance;
    }

    @Override
    public IDrawableMesh getShip()
    {
        if (ship == null)
        {
            ship = getTriangle(resourceManager.getShipWidth(), resourceManager.getShipHeight());
        }
        return ship;
    }

    @Override
    public IDrawableMesh getRectangleEnemy()
    {
        if (rect == null)
        {
            rect = getRectangle(resourceManager.getEnemySize(), resourceManager.getEnemySize());
        }
        return rect;
    }

    @Override
    public IDrawableMesh getTriangleEnemy()
    {
        if (triangle == null)
        {
            triangle = new TopDownTriangle(resourceManager.getEnemySize(), resourceManager.getEnemySize());
        }
        return triangle;
    }

    @SuppressWarnings("boxing")
    @Override
    public IDrawableMesh getShot(final Shape parent, final float width, final float height)
    {
        // TODO: improve only one mesh per enemy type!
        try
        {
            return parent.getMesh().getClass().getConstructor(int.class, int.class).newInstance((int) width, (int) height);
        }
        catch (final IllegalArgumentException e)
        {
            e.printStackTrace();
        }
        catch (final InstantiationException e)
        {
            e.printStackTrace();
        }
        catch (final IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (final InvocationTargetException e)
        {
            e.printStackTrace();
        }
        catch (final NoSuchMethodException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public IDrawableMesh getDiamondEnemy()
    {
        if (diamond == null)
        {
            diamond = new Diamond(resourceManager.getEnemySize(), resourceManager.getEnemySize());
        }
        return diamond;
    }

    @Override
    public IDrawableMesh getLifePlus()
    {
        if (life == null)
        {
            life = getHeart(resourceManager.getBonusSize(), resourceManager.getBonusSize());
        }
        return life;
    }

    @Override
    public IDrawableMesh getStar()
    {
        if (star == null)
        {
            star = new Diamond(1, 1);
        }
        return star;
    }

    @Override
    public IDrawableMesh getFastShot()
    {
        if (fastShot == null)
        {
            fastShot = new TrippleHorizontalTriangles(resourceManager.getBonusSize() / 2, resourceManager.getBonusSize());
        }
        return fastShot;
    }

    @Override
    public IDrawableMesh getTripleShot()
    {
        if (tripleShot == null)
        {
            tripleShot = new TrippleTriangle(resourceManager.getBonusSize(), resourceManager.getBonusSize());
        }
        return tripleShot;
    }

    private IDrawableMesh getHeart(final int width, final int height)
    {
        return new Heart(width, height);
    }

    private IDrawableMesh getTriangle(final int width, final int height)
    {
        return new Triangle(width, height);
    }

    private IDrawableMesh getRectangle(final int width, final int height)
    {
        return new Rectangle(width, height);
    }

}
