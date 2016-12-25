package mobile.app.lonelytriangle.rendering;

import javax.microedition.khronos.opengles.GL10;

/**
 * A TrippleHorizontalTriangles is a Figure which has three triangles (base at bottom) in one line (horizontal).
 * Figure: GL_TRIANGLES
 * 
 * @author Benedikt Zönnchen
 * @version v1.0
 * 
 */
public class TrippleHorizontalTriangles extends AbstractMesh
{
    private final short[] indices = {1, 0, 2, 3, 4, 5, 6, 7, 8};
    private final float[] vertices;

    /**
     * Constructs a newly allocated TrippleHorizontalTriangles object with a specified widht and height.
     * 
     * @param width the width is the (biggest x-cord - lowest x-cord) of the mesh
     * @param height the height is the (biggest y-cord - lowest y-cord) of the mesh
     */
    protected TrippleHorizontalTriangles(final int width, final int height)
    {
        super(width, height);
        this.setOpenGlDrawType(GL10.GL_TRIANGLES);
        this.vertices = new float[]
        {
                0f, 0f, 0f,                         // 0
                width, 0f, 0f,                      // 1
                width / 2, height / 3f, 0f,         // 2
                0f, height / 3f, 0f,                // 3
                width, height / 3f, 0f,             // 4
                width / 2, height * 2f / 3f, 0f,    // 5
                0f, height * 2f / 3f, 0f,           // 6
                width, height * 2f / 3f, 0f,        // 7
                width / 2, height, 0f,              // 8
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
