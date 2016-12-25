package mobile.app.lonelytriangle.rendering;

import javax.microedition.khronos.opengles.GL10;

/**
 * Figure: GL_TRIANGLE_FAN
 * 
 * A Trianglefan from the middle of the circle to points on the circle.
 * 
 * @author Benedikt Zönnchen
 * @version v1.0
 * 
 */
public class Circle extends AbstractMesh
{
    /** The Constant which defines how many points should be calculated (RADIUS_TO_POINT_FACTOR * RADIUS = Number of Points). */
    private static final int RADIUS_TO_POINT_FACTOR = 10;
    private final int        diameter;

    /**
     * Constructs a newly allocated Circle object.
     * @param diameter the diameter of the circle (radius = diameter/2)
     */
    protected Circle(final int diameter)
    {
        super(diameter, diameter);
        this.diameter = diameter;
        this.setOpenGlDrawType(GL10.GL_TRIANGLE_FAN);
    }

    @Override
    protected float[] getVerticies()
    {
        return drawLoop(this.diameter / 2, (this.diameter / 2) * RADIUS_TO_POINT_FACTOR);
    }

    /**
     * Generate lots of vertices on a circle, to approximate the cicle rendering.
     * 
     * @param radius the radius of the circle
     * @param points the number of points
     * @return
     */
    private static float[] drawLoop(final int radius, final int points)
    {
        final float[] vertices = new float[(points + 1) * 3];
        for (int i = 3; i < (points + 1) * 3; i += 3)
        {
            final float rad = (float) ((i * 360 / points * 3) * (3.14 / 180));
            vertices[i] = android.util.FloatMath.cos(rad) * radius;
            vertices[i + 1] = android.util.FloatMath.sin(rad) * radius;
            vertices[i + 2] = 0f;
        }
        return vertices;
    }

    @Override
    protected short[] getIndicies()
    {
        // we dont have indicies for a cycle!
        return null;
    }
}
