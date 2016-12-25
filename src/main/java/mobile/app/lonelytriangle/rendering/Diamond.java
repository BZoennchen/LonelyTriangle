package mobile.app.lonelytriangle.rendering;

/**
 * A Diamond Mesh.
 * Figure: GL_TRIANGLE_STRIP
 * 
 *      3
 *  1       2
 *      0 
 * @author Benedikt Zönnchen
 * @version v1.0
 * 
 */
public class Diamond extends AbstractMesh
{
    private final short[] indices = {1, 0, 2, 3, 1};
    private final float[] vertices;

    /**
     * Constructs a newly allocated Diamond object, bound in an rectangle with a specified width and height.
     * 
     * @param width the width is the (biggest x-cord - lowest x-cord) of the mesh
     * @param height the height is the (biggest y-cord - lowest y-cord) of the mesh
     */
    public Diamond(final int width, final int height)
    {
        super(width, height);
        this.vertices = new float[]
        {
                width / 2f, 0f, 0f,     // 0
                0f, height / 2f, 0f,    // 1
                width, height / 2f, 0f, // 2
                width / 2f, height, 0f  // 3
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
