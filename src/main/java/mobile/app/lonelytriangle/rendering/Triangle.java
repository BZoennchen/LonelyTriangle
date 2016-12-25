package mobile.app.lonelytriangle.rendering;

import javax.microedition.khronos.opengles.GL10;

/**
 * A Triangle is a Triangle which has the base at bottom. Figure: GL_TRIANGLES
 * 
 * 
 * @author Benedikt Zönnchen
 * @version v1.0
 * 
 */
public class Triangle extends AbstractMesh
{
    private final float[] vertices;
    private final short[] indicies = new short[] {0, 1, 2};

    /**
     * Constructs a newly allocated Triangle object with a specified widht and height.
     * 
     * @param width the width is the (biggest x-cord - lowest x-cord) of the mesh
     * @param height the height is the (biggest y-cord - lowest y-cord) of the mesh
     */
    public Triangle(final int width, final int height)
    {
        super(width, height);
        this.setOpenGlDrawType(GL10.GL_TRIANGLES);
        this.vertices = new float[]
        {
                0f, 0f, 0f,             // 0
                width, 0f, 0f,          // 1
                width / 2, height, 0f   // 2
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
