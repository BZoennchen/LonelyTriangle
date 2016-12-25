package mobile.app.lonelytriangle.rendering;

import javax.microedition.khronos.opengles.GL10;
// unsed
/**
 * 
 * @author Alexander Waldeck
 * @version v1.0
 * 
 */
public class One extends AbstractMesh
{
    private final short[] indices =
                                  { 0, 1, 2, 2, 3, 1, 1, 5, 4};
    private final float[] vertices;

    /**
     * Constructs a newly allocated TrippleHorizontalTriangles object with a specified widht and height.
     * 
     * @param width the width is the (biggest x-cord - lowest x-cord) of the mesh
     * @param height the height is the (biggest y-cord - lowest y-cord) of the mesh
     */
    protected One(final int width, final int height)
    {
        super(width, height);
        setOpenGlDrawType(GL10.GL_TRIANGLES);
        vertices = new float[]
        { 
            width * 4 / 7f, 0f, 0f, // 0
            width * 4 / 7f, height * 2 / 3f, 0f, // 1
            width * 3 / 7f, 0f, 0f, // 2
            width * 3 / 7f, height * 2 / 3f, 0f, // 3
            width / 7f, height * 2 / 3f, 0f, // 4
            width * 4 / 7f, height, 0f, // 5
        };
    }

    @Override
    protected short[] getIndicies()
    {
        return indices;
    }

    @Override
    protected float[] getVerticies()
    {
        return vertices;
    }
}
