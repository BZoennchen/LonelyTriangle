package mobile.app.lonelytriangle.rendering;

import javax.microedition.khronos.opengles.GL10;

/**
 * The TopDownTriangle is a Triangle which has the base at top. Figure: GL_TRIANGLES
 * 
 * @author Benedikt Zönnchen
 * @version v1.0
 * 
 */
public class TopDownTriangle extends AbstractMesh
{
    private final float[] vertices;
    private final short[] indicies = new short[] {0, 1, 2};
    
    /**
     * Constructs a newly allocated TopDownTriangle object with a specified widht and height.
     * 
     * @param width the width is the (biggest x-cord - lowest x-cord) of the mesh
     * @param height the height is the (biggest y-cord - lowest y-cord) of the mesh
     */
    public TopDownTriangle(final int width, final int height)
    {
        super(width, height);
        this.setOpenGlDrawType(GL10.GL_TRIANGLES);
        this.vertices = new float[]
        { 
                width / 2, 0f, 0f,  // 0
                width, height, 0f,  // 1
                0f, height, 0f      // 2
        };
    }

    @Override
    protected short[] getIndicies()
    {
        return this.indicies;
    }

    @Override
    protected float[] getVerticies()
    {
        return this.vertices;
    }
}
