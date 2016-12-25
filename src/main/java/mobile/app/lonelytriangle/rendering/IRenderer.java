package mobile.app.lonelytriangle.rendering;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView;

/**
 * The IRenderer do all the render work in the render method. The IRenderer renders all IDrawableMeshes on the viewport.
 * The IRenderer also extends the IScreenResizeObservable which means that Observers can be registered to receive the new width and height if the viewport is resized.
 * 
 * @author Benedikt Zönnchen
 * @version v1.0
 * 
 */
public interface IRenderer extends GLSurfaceView.Renderer, IScreenResizeObservable
{
    /**
     * Renders all the IDrawableMeshes on the viewport.
     * 
     * @param gl the GL10 object of the viewport
     */
    void render(final GL10 gl);
    
    /**
     * Returns the height of the viewport.
     * 
     * @return the height of the viewport
     */
    int getScreenHeight();
    
    /**
     * Retunrs the width of the viewport.
     * 
     * @return the width of the viewport
     */
    int getScreenWidth();
}
