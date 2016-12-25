package mobile.app.lonelytriangle.input;

import java.util.HashMap;
import java.util.Map;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.view.MotionEvent;
import android.view.View;

/**
 * The implementation of IInputManager. Only specified Events are supported.
 * 
 * @author Benedikt Zönnchen
 * @version v1.0
 * 
 */
public class InputManager implements IInputManager
{
    /** MotionEvent.ACTION_DOWN mapped to TOUCH_ACTION_DOWN. */
    public static final int             TOUCH_ACTION_DOWN = 0;
    
    /** MotionEvent.ACTION_Move mapped to TOUCH_ACTION_MOVE. */
    public static final int             TOUCH_ACTION_MOVE = 1;
    
    /** MotionEvent.ACTION_UP mapped to TOUCH_ACTION_UP. */
    public static final int             TOUCH_ACTION_UP   = 2;

    /** the number of touch codes which will be supported. */
    private static final int            NUM_TOUCH_CODES   = 3;

    /** the x coordinate of the touch. */
    private int                         touchX            = 1;

    /** the y coordinate of the touch. */
    private int                         touchY            = 1;

    /** acceleration on the 3 axis. [0] = x, [1] = y, [2] = z **/
    private final float[]               acceleration      = new float[3];

    /** game actions for touch events. */
    private final GameAction[]          touchAction       = new GameAction[NUM_TOUCH_CODES];

    private final Map<View, GameAction> onClickActions;

    /**
     * Constructs a newly allocated InputManager.
     */
    public InputManager()
    {
        this.onClickActions = new HashMap<View, GameAction>();
    }

    @Override
    public boolean onTouch(final View v, final MotionEvent event)
    {
        final GameAction action = this.getTouchAction(event);
        this.touchX = (int) event.getX();
        this.touchY = (int) event.getY();
        if (action != null)
        {
            action.tap();
        }

        // slow down the event flood, this improve the performance!
        try
        {
            Thread.sleep(20);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        return action != null;
    }

    @Override
    public void mapToTouch(final GameAction gameAction, final int touchCode)
    {
        this.touchAction[touchCode] = gameAction;
    }

    // ignored
    @Override
    public void onAccuracyChanged(final Sensor sensor, final int accuracy)
    {
    }

    @Override
    public void onSensorChanged(final SensorEvent event)
    {
        /*
         * Little help: right is x [0] positiv top is y [1] positiv behind the device is z [2] positiv
         */
        // copy the acceleration-values to the acceleration array.
        System.arraycopy(event.values, 0, this.acceleration, 0, 3);
    }

    @Override
    public int getTouchX()
    {
        return this.touchX;
    }

    @Override
    public int getTouchY()
    {
        return this.touchY;
    }

    @Override
    public float getAccelerationX()
    {
        return this.acceleration[0];
    }

    @Override
    public float getAccelerationY()
    {
        return this.acceleration[1];
    }

    @Override
    public float getAccelerationZ()
    {
        return this.acceleration[2];
    }

    @Override
    public void onClick(View v)
    {
        GameAction gameAction = this.onClickActions.get(v);
        gameAction.tap();
    }

    @Override
    public void addView(final View view, final GameAction gameAction)
    {
        if (!this.onClickActions.containsKey(view))
        {
            this.onClickActions.put(view, gameAction);
            view.setOnClickListener(this);
        }
    }

    private GameAction getTouchAction(final MotionEvent event)
    {
        int touchCode = this.getTouchCode(event);

        if (touchCode != -1)
        {
            return this.touchAction[touchCode];
        }

        return null;
    }

    private int getTouchCode(final MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                return TOUCH_ACTION_DOWN;
            case MotionEvent.ACTION_MOVE:
                return TOUCH_ACTION_MOVE;
            case MotionEvent.ACTION_UP:
                return TOUCH_ACTION_UP;
            default:
                return -1;
        }
    }
}
