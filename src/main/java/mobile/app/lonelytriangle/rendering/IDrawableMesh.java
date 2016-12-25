package mobile.app.lonelytriangle.rendering;

import javax.microedition.khronos.opengles.GL10;

/**
 * A IDrawableMesh is a mesh that can be rendered by a Renderer, so it can be drawn on the viewport. 
 * 
 * @author Benedikt Zönnchen
 * @version v1.0
 * 
 */
public interface IDrawableMesh
{
    /**
     * Renders the IDrawableMesh on the viewport of the GL10 object.
     * 
     * @param gl the GL10 object of the viewport
     */
    void render(final GL10 gl);
}
