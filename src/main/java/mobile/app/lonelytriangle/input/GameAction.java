package mobile.app.lonelytriangle.input;

/**
 * The GameAction class is an abstract to a user-initiated action, like jumping or moving. GameActions can be mapped to keys or the mouse with the InputManager.
 */
public class GameAction
{

    /**
     * Normal behavior. The isPressed() method returns true as long as the key is held down.
     */
    public static final int  NORMAL                    = 0;

    /**
     * Initial press behavior. The isPressed() method returns true only after the key is first pressed, and not again until the key is released and pressed again.
     */
    public static final int  DETECT_INITAL_PRESS_ONLY  = 1;

    private static final int STATE_RELEASED            = 0;
    private static final int STATE_PRESSED             = 1;
    private static final int STATE_WAITING_FOR_RELEASE = 2;

    private final String     name;
    private final int        behavior;
    private int              amount;
    private int              state;

    /**
     * Create a new GameAction with the NORMAL behavior.
     * 
     * @param name the name of the GameAction, only for identification
     */
    public GameAction(String name)
    {
        this(name, NORMAL);
    }

    /**
     * Create a new GameAction with the specified behavior.
     * 
     * @param name the name of the GameAction, only for identification
     * @param behavior the behavior of the GameAction
     */
    public GameAction(String name, int behavior)
    {
        this.name = name;
        this.behavior = behavior;
        this.reset();
    }

    /**
     * Gets the name of this GameAction.
     * 
     * @return the name of the GameAction
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Resets this GameAction so that it appears like it hasn't been pressed.
     */
    public void reset()
    {
        this.state = STATE_RELEASED;
        this.amount = 0;
    }

    /**
     * Taps this GameAction. Same as calling press() followed by release().
     */
    public synchronized void tap()
    {
        this.press();
        this.release();
    }

    /**
     * Signals that the key was pressed.
     */
    public synchronized void press()
    {
        this.press(1);
    }

    /**
     * Signals that the key was pressed a specified number of times, or that the mouse move a spcified distance.
     * 
     * @param newAmount the amount of presses
     */
    public synchronized void press(final int newAmount)
    {
        if (this.state != STATE_WAITING_FOR_RELEASE)
        {
            this.amount += newAmount;
            this.state = STATE_PRESSED;
        }

    }

    /**
     * Signals that the key was released.
     */
    public synchronized void release()
    {
        this.state = STATE_RELEASED;
    }

    /**
     * Returns whether the key was pressed or not since last checked.
     * 
     * @return true if the key was pressed after last check, else false
     */
    public synchronized boolean isPressed()
    {
        return this.getAmount() != 0;
    }

    /**
     * For keys, this is the number of times the key was pressed since it was last checked. For mouse movement, this is the distance moved.
     *
     * @return returns the number of times the key was pressed since it was last checked or mouse movenemt (not for android)
     */
    public synchronized int getAmount()
    {
        int retVal = this.amount;
        if (retVal != 0)
        {
            if (this.state == STATE_RELEASED)
            {
                this.amount = 0;
            }
            else if (this.behavior == DETECT_INITAL_PRESS_ONLY)
            {
                this.state = STATE_WAITING_FOR_RELEASE;
                this.amount = 0;
            }
        }
        return retVal;
    }
}
