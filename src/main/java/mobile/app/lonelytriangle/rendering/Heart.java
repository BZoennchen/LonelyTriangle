package mobile.app.lonelytriangle.rendering;

/**
 * The Heart Mesh is a Mesh which approximate the Shape of a Heart by using triangles. Figure: GL_TRIANGLE_STRIP
 * 
 * 1 draw: 0-1-2-5-4-3-6-7-0
 * 
 * @author Benedikt Zönnchen
 * @version v1.0
 * 
 */
public class Heart extends AbstractMesh
{
    /** the draw order. */
    private final short[] indices = {0, 1, 2, 3, 0, 4, 5};
    private final float[] vertices;

    /**
     * Constructs a newly allocated Herat object with a specified widht and height.
     * 
     * @param width the width is the (biggest x-cord - lowest x-cord) of the mesh
     * @param height the height is the (biggest y-cord - lowest y-cord) of the mesh
     */
    protected Heart(final int width, final int height)
    {
        super(width, height);

        this.vertices = new float[]
        {
                width / 2f, 0f, 0f,                 // 0
                width, height / 3f * 2f, 0f,        // 1
                width / 4f * 3f, height, 0f,        // 2
                width / 2f, height / 3f * 2f, 0f,   // 3
                width / 4f, height, 0f,             // 4
                0f, height / 3f * 2f, 0f,           // 5
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
