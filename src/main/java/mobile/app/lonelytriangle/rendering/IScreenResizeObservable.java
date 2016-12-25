package mobile.app.lonelytriangle.rendering;

import mobile.app.lonelytriangle.core.IScreenResizeListener;

/**
 * The IScreenResizeObservable can add IScreenResizeListener which will be notified if the screen resized.
 * 
 * @author Benedikt Zönnchen
 * @version v1.0
 *
 */
public interface IScreenResizeObservable
{
    /**
     * Register a new IScreenResizeListener to this Observalbe.
     * 
     * @param listener the listener which will be notified after the screen has resized
     */
    void addScreenResizeListener(final IScreenResizeListener listener);
}
