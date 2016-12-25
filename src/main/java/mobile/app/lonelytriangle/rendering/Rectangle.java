package mobile.app.lonelytriangle.rendering;

/**
 * The Rectangle Mesh. Figure: GL_TRIANGLE_STRIP
 * 
 * 1 draw: 0-1-2-5-4-3-6-7-0
 * 
 * @author Benedikt Zönnchen
 * @version v1.0
 * 
 */
public class Rectangle extends AbstractMesh
{
    private final short[] indices = {0, 1, 2, 3, 0};
    private final float[] vertices;

    /**
     * Constructs a newly allocated Rectangle object with a specified widht and height.
     * 
     * @param width the width is the (biggest x-cord - lowest x-cord) of the mesh
     * @param height the height is the (biggest y-cord - lowest y-cord) of the mesh
     */
    public Rectangle(final int width, final int height)
    {
        super(width, height);
        this.vertices = new float[]
        { 
                0f, height, 0f,     // 0
                0f, 0f, 0f,         // 1
                width, 0f, 0f,      // 2
                width, height, 0f   // 3
        };
    }

    @Override
    protected short[] getIndicies()
    {
        return this.indices;
    }

    @Override
    protected float[] getVerticies()
    {
        return this.vertices;
    }
}
