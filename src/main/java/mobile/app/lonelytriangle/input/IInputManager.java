package mobile.app.lonelytriangle.input;

import android.hardware.SensorEventListener;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

/**
 * The IInputManager receive all ui-input events of this application. The IInputManager tells the binded GameAction,
 * that an event occurs. So every object which has an reference of the GameActions can pull these information (once). 
 * 
 * @author Benedikt Zönnchen
 * @version v1.0
 * 
 */
public interface IInputManager extends OnTouchListener, SensorEventListener, TextView.OnClickListener
{
    /**
     * Returns the x coordinate of the last touch.
     * 
     * @return the x coordinate of the last touch
     */
    int getTouchX();

    /**
     * Returns the y coordinate of the last touch.
     * 
     * @return the y coordinate of the last touch
     */
    int getTouchY();

    /**
     * Returns the acceleration on the x-Axis of the device.
     * 
     * @return the acceleration on the x-Axis of the device
     */
    float getAccelerationX();

    /**
     * Returns the acceleration on the y-Axis of the device.
     * 
     * @return the acceleration on the y-Axis of the device
     */
    float getAccelerationY();

    /**
     * Returns the acceleration on the z-Axis of the device.
     * 
     * @return the acceleration on the z-Axis of the device
     */
    float getAccelerationZ();

    /**
     * Maps the GameAction to the specified touchCode. If the touchCode contains an
     * GameAction this GameAction overrides the old one.
     * 
     * @param gameAction the GameAction which will be mapped to a touchCode
     * @param touchCode the touchCode which will be map the GameAction
     */
    void mapToTouch(final GameAction gameAction, final int touchCode);

    /**
     * Binds the GameAction to the View. This means the GameAction is bind to the onClick
     * Event of the View. If the event accurse, the inputManager tells this the GameAction.
     * The same View object can only bound once, but one GameAction can bind to multiple views.
     * 
     * @param view the android view component
     * @param gameAction the GameAction which binds the view to it.
     */
    void addView(final View view, final GameAction gameAction);
}
