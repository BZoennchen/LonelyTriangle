package mobile.app.lonelytriangle.core;

/**
 * The IScreenResizeListener will be notified when the screen will be resized. The Renderer should notify the IScreenResizeListeners.
 * 
 * @author Benedikt Zönnchen
 * @version v1.0
 *
 */
public interface IScreenResizeListener
{
    /**
     * Tells the IGameManager which width and which height he can expect.
     * 
     * @param width the width of the viewport
     * @param height the height of the viewport
     */
    void screenResize(int width, int height);
}
