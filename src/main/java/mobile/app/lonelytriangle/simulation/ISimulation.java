package mobile.app.lonelytriangle.simulation;

import mobile.app.lonelytriangle.core.IScreenResizeListener;


/**
 * The ISimulation is the model and the simulation object of the game. It holds all the information of a game state in it and calculate all the moving, shooting and so on.
 * 
 * @author Benedikt Zönnchen, Alexander Waldeck
 * @version v1.0
 * 
 */
public interface ISimulation extends IGameStateModel, IScreenResizeListener
{
    /**
     * A Enum that represents the different difficult states.
     * 
     * @author Benedikt Zönnchen
     * @version v1.0
     *
     */
    public enum Difficulty
    {
        /** Easy difficulty. */
        Easy("Easy"), 
        /** Hard difficulty. */
        Hard("Hard"), 
        /** Insane difficulty. */
        Insane("Insane");
        
        /**
         * Returns the difficulty for a given String representation.
         * @param name the String representation
         * @return the difficulty for a given String representation
         */
        public static Difficulty getDifficulty(final String name)
        {
            if (Easy.toString().equals(name))
            {
                return Easy;
            }
            else if (Hard.toString().equals(name))
            {
                return Hard;
            }
            else if (Insane.toString().equals(name))
            {
                return Insane;
            }
            else
            {
                throw new IllegalArgumentException("dont fount such a difficulty!");
            }
        }
        
        /** name of this Difficulty enum. */
        private final String name;
        
        /**
         * Construct a new Difficulty enum.
         * 
         * @param name the name of this Difficulty
         */
        Difficulty(final String name)
        {
            this.name = name;
        }
        
        /**
         * Increment the Difficulty (one step).
         * 
         * @return the new incremented Difficulty
         */
        public Difficulty increment()
        {
            switch(this)
            {
                case Easy : return Hard; 
                case Hard : return Insane;
                case Insane : return Easy;
                default : throw new IllegalArgumentException("increment: wrong difficulty");
            }
        }
        
        /**
         * Decrement the Difficulty (one step).
         * 
         * @return the new decremented Difficulty
         */
        public Difficulty decrement()
        {
            switch(this)
            {
                case Easy : return Insane; 
                case Hard : return Easy;
                case Insane : return Hard;
                default : throw new IllegalArgumentException("decrement: wrong difficulty");
            }
        }
 
        @Override
        public String toString()
        {
            return name;
        }
    }
    
    /**
     * update the game world.
     * 
     * @param delta the time in seconds that is elapsed
     */
    void update(final float delta);

    
    /**
     * reset the simulation to use for playing the game.
     */
    void initGame();
    

    /**
     * reset the simulation to use for background simulation.
     */
    void initSimulation();

    //void initBackground();

   // void updateScreen(int width, int height);

    /**
     * Returns the difficulty of the game.
     * 
     * @return the difficulty of the game
     */
    Difficulty getDifficulty();

    /**
     * Resets the difficulty to Hard.
     */
    void resetDifficulty();

    /**
     * Increment the difficulty.
     */
    void incrementDifficulty();

    /**
     * diable the shooting of the player ship.
     */
    void disableShipShoting();

    /**
     * enable the shooting of the player ship.
     */
    void enableShipShoting();

    /**
     * Sets the new target of the player ship.
     * 
     * @param targetX the x-coordinate of the target of the player ship
     */
    void setTargetX(int targetX);

    /**
     * Sets the new target of the player ship.
     * 
     * @param targetY the y-coordinate of the target of the player ship
     */
    void setTargetY(int targetY);

    /**
     * Returns true if the sound is on, else false.
     * 
     * @return true if the sound is on, else false
     */
    boolean isSoundOn();

    /**
     * Sets the sound on/off.
     * 
     * @param soundOn true => sound off, else sound on
     */
    void setSoundOn(boolean soundOn);
    
    /**
     * Returns true if this simulation model is only for simulating the background of the menu.
     * 
     * @return true if the simulation model simulates the background, otherwise false
     */
    boolean isBackground();
    
    /**
     * Set the difficulty of the simulation.
     * @param difficulty the difficulty
     */
    void setDifficulty(final Difficulty difficulty);
}
