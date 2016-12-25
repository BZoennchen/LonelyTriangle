package mobile.app.lonelytriangle.rendering;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.HashSet;
import java.util.Set;

import javax.microedition.khronos.opengles.GL10;

/**
 * The AbstractMesh draws every Mesh defined by its verticies and optional by his indicies. Every Mesh has only implement the getIndicies and the getVerticies methods. If u need some special rendering u should override the render method.
 * 
 * @author Benedikt Zönnchen
 * @version v1.0
 * 
 */
@SuppressWarnings("boxing")
public abstract class AbstractMesh implements IDrawableMesh
{
    private final int                 width;
    private final int                 height;
    private short[]                   indices;
    private FloatBuffer               vertexBuffer;
    private ShortBuffer               indexBuffer;
    private float[]                   vertices;
    private boolean                   initialized;
    private int                       openGlDrawType              = GL10.GL_TRIANGLE_STRIP;
    private static final Set<Integer> SUPPORTED_OPENGL_DRAW_TYPES = new HashSet<Integer>();

    static
    {
        SUPPORTED_OPENGL_DRAW_TYPES.add(GL10.GL_TRIANGLE_FAN);
        SUPPORTED_OPENGL_DRAW_TYPES.add(GL10.GL_TRIANGLE_STRIP);
        SUPPORTED_OPENGL_DRAW_TYPES.add(GL10.GL_TRIANGLES);
    }

    /**
     * Constructs a newly allocated AbstractMesh with a specified width and height.
     * 
     * @param width the width of the AbstractMesh
     * @param height the height of the AbstractMesh
     */
    protected AbstractMesh(final int width, final int height)
    {
        this.width = width;
        this.height = height;
        this.initialized = false;
    }

    /**
     * has to be called before firt time rendering.
     */
    protected void init()
    {
        this.vertices = this.getVerticies();
        this.indices = this.getIndicies();

        final ByteBuffer vbb = ByteBuffer.allocateDirect(this.vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        this.vertexBuffer = vbb.asFloatBuffer();
        this.vertexBuffer.put(this.vertices);
        this.vertexBuffer.position(0);

        if (this.indices != null)
        {
            final ByteBuffer ibb = ByteBuffer.allocateDirect(this.indices.length * 2);
            ibb.order(ByteOrder.nativeOrder());
            this.indexBuffer = ibb.asShortBuffer();
            this.indexBuffer.put(this.indices);
            this.indexBuffer.position(0);
            this.initialized = true;
        }
    }

    /**
     * Sets the OpenGLDrawType GL10.GL_TRIANGLES, GL10.GL_TRIANGLE_FAN or GL10.GL_TRIANGLE_STRIP.
     * 
     * @param openGlDrawType the openGL draw type (GL10.GL_TRIANGLES, GL10.GL_TRIANGLE_FAN, GL10.GL_TRIANGLE_STRIP)
     */
    protected void setOpenGlDrawType(final int openGlDrawType)
    {
        if (!SUPPORTED_OPENGL_DRAW_TYPES.contains(openGlDrawType))
        {
            throw new IllegalArgumentException("this openGL draw type is not supportet (" + openGlDrawType + ")");
        }
        this.openGlDrawType = openGlDrawType;
    }

    /**
     * Retunrs true if the AbstractMesh is initialized. This means that the buffers are ready. You can only
     * render a AbstractMesh if it is initialized.
     * 
     * @return true if the AbstractMesh is initialized, else false
     */
    protected boolean isInitialised()
    {
        return this.initialized;
    }

    /**
     * Returns the width of the AbstractMesh.
     * 
     * @return the width of the AbstractMesh
     */
    protected int getWidth()
    {
        return this.width;
    }

    /**
     * Returns the height of the AbstractMesh.
     * 
     * @return the height of the AbstractMesh
     */
    protected int getHeight()
    {
        return this.height;
    }

    /**
     * Returns the indicies of the AbstractMesh, if they are availible, otherwise null.
     * 
     * @return the indicies of the AbstractMesh, if they are availible, otherwise null.
     */
    protected abstract short[] getIndicies();

    /**
     * Returns the vertices of the AbstractMesh.
     * 
     * @return the vertices of the AbstractMesh
     */
    protected abstract float[] getVerticies();

    @Override
    public void render(final GL10 gl)
    {
        if (!this.isInitialised())
        {
            this.init();
        }

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, this.vertexBuffer);

        if (this.indices != null)
        {
            gl.glDrawElements(this.openGlDrawType, this.indices.length, GL10.GL_UNSIGNED_SHORT, this.indexBuffer);
        }
        else
        {
            gl.glDrawArrays(this.openGlDrawType, 0, 50);
        }

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }
}
