package mobile.app.lonelytriangle.rendering;

import javax.microedition.khronos.opengles.GL10;

/**
 * A TrippleTriangle is a Figure which shows three triangles (base at bottom) first two in a line (vertical) and than one centered on top of these two.
 * Figure: GL_TRIANGLES
 * 
 * @author Benedikt Zönnchen
 * @version v1.0
 * 
 */
public class TrippleTriangle extends AbstractMesh
{
    private final short[] indices = {3, 4, 0, 1, 2, 0, 4, 2, 5};
    private final float[] vertices;

    /**
     * Constructs a newly allocated TrippleHorizontalTriangles object with a specified widht and height.
     * 
     * @param width the width is the (biggest x-cord - lowest x-cord) of the mesh
     * @param height the height is the (biggest y-cord - lowest y-cord) of the mesh
     */
    protected TrippleTriangle(final int width, final int height)
    {
        super(width, height);
        this.setOpenGlDrawType(GL10.GL_TRIANGLES);

        this.vertices = new float[]
        {
                width / 4f, height / 2f, 0f,        // 0
                width / 2f, 0f, 0f,                 // 1
                width * 3f / 4f, height / 2f, 0f,   // 2
                0f, height, 0f,                     // 3
                width / 2, height, 0f,              // 4
                width, height, 0f                   // 5
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
