package mobile.app.lonelytriangle.rendering;

import java.util.HashSet;
import java.util.Set;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import mobile.app.lonelytriangle.core.IScreenResizeListener;
import mobile.app.lonelytriangle.resource.IResourceManager;
import mobile.app.lonelytriangle.resource.ResourceManager;
import mobile.app.lonelytriangle.resource.ResourceManager.Colors;
import mobile.app.lonelytriangle.simulation.IGameStateModel;
import mobile.app.lonelytriangle.simulation.IMoveable;
import mobile.app.lonelytriangle.simulation.shapes.EnemyShip;
import mobile.app.lonelytriangle.simulation.shapes.Shape;
import mobile.app.lonelytriangle.simulation.shapes.Ship;
import android.opengl.GLU;

/**
 * The renderer renders all shapes (by using the mesh objects) and take care of any change on the surface.
 * 
 * @author Benedikt Zönnchen
 * @version v1.0
 * 
 */

public class Renderer implements IRenderer
{
    /** the model of the game. */
    private final IGameStateModel            simulation;

    /** the with of the viewport. */
    private int                              width;

    /** the height of the viewport. */
    private int                              height;
    
    private final IResourceManager resource;

    /** the set of all IScreenResizeListener. */
    private final Set<IScreenResizeListener> resizeListener;

    /**
     * Constructs a newly allocated Renderer object.
     * 
     * @param simulation the model object of the game
     */
    public Renderer(final IGameStateModel simulation)
    {
        this.simulation = simulation;
        resource = ResourceManager.getInstance();
        resizeListener = new HashSet<IScreenResizeListener>();
    }

    @Override
    public void render(final GL10 gl)
    {
        initialRenderPhase(gl);
        renderShapeList(gl, simulation.getStars());
        renderEnemyList(gl, simulation.getAllEnemies());
        renderShapeList(gl, simulation.getBoni());
        renderShapeList(gl, simulation.getEnemyShots());
        renderShapeList(gl, simulation.getPlayerShots());
        renderShape(gl, simulation.getShip());
        renderLife(gl);
        endRenderPhase(gl);
    }

    @Override
    public void onDrawFrame(final GL10 gl)
    {
        render(gl);
    }

    @Override
    public void onSurfaceChanged(final GL10 gl, final int newWidth, int newHeight)
    {
        if (height == 0)
        { // Prevent A Divide By Zero By
            height = 1; // Making Height Equal One
        }
        width = newWidth;
        height = newHeight;

        for (IScreenResizeListener listener : resizeListener)
        {
            listener.screenResize(newWidth, newHeight);
        }
        // this.simulation.init(width, height);
    }

    @Override
    public void onSurfaceCreated(final GL10 gl, final EGLConfig config)
    {
    }

    /*
     * Helper mehtods for rendering different shapes!
     */
    private void initialRenderPhase(final GL10 gl)
    {
        gl.glViewport(0, 0, width, height);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        GLU.gluOrtho2D(gl, 0, width, 0, height);
    }

    private void renderLife(final GL10 gl)
    {
        final Ship ship = (Ship) simulation.getShip();
        if (ship != null)
        {
            final int lifes = ship.getLife();
            if (lifes <= resource.getMaxNumberOfShownHearts())
            {
                final IDrawableMesh heart = MeshFactory.getInstance().getLifePlus();
                for (int i = 1; i <= lifes; i++)
                {
                    setColor(gl, Colors.Red);
                    gl.glPushMatrix();
                    gl.glTranslatef(width - (i * (ResourceManager.getInstance().getBonusSize() + 10)), height - ResourceManager.getInstance().getBonusSize() - 5, 0);
                    heart.render(gl);
                    gl.glPopMatrix();
                    
                }
            }
        }
    }

    private void endRenderPhase(final GL10 gl)
    {

    }

    private void renderShape(final GL10 gl, final IMoveable shape)
    {
        if (shape != null)
        {
            setColor(gl, ((Shape) shape).getColor());
            gl.glPushMatrix();
            gl.glTranslatef(shape.getX(), shape.getY(), 0); // move to correct
                                                            // position
            ((Shape) shape).getMesh().render(gl);
            gl.glPopMatrix();
        }
    }

    private void renderShapeList(final GL10 gl, final Iterable<IMoveable> shapes)
    {
        synchronized (simulation)
        {
            if (shapes != null)
            {
                for (final IMoveable shape : shapes)
                {
                    renderShape(gl, shape);
                }
            }
        }
    }

    private void renderEnemyList(final GL10 gl, final Iterable<EnemyShip> shapes)
    {
        synchronized (simulation)
        {
            if (shapes != null)
            {
                for (final IMoveable shape : shapes)
                {
                    renderShape(gl, shape);
                }
            }
        }

    }

    private void setColor(final GL10 gl, final Colors color)
    {
        switch (color)
        {
            case White:
                gl.glColor4f(1, 1, 1, 1);
                break;

            case Yellow:
                gl.glColor4f(1, 1, 0, 1);
                break;

            case Green:
                gl.glColor4f(0, 0.75f, 0, 1);
                break;

            case Blue:
                gl.glColor4f(0, 0, 1, 1);
                break;

            case Red:
                gl.glColor4f(1, 0, 0, 1);
                break;

            case Orange:
                gl.glColor4f(1, 0.5f, 0, 1);
                break;

            case Purple:
                gl.glColor4f(0.75f, 0, 1, 1);
                break;
            default:
                throw new IllegalArgumentException("wrong color");
        }
    }

    @Override
    public void addScreenResizeListener(final IScreenResizeListener listener)
    {
        resizeListener.add(listener);
    }

    @Override
    public int getScreenHeight()
    {
        return height;
    }

    @Override
    public int getScreenWidth()
    {
        return width;
    }
}
