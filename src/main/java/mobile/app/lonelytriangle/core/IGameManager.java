package mobile.app.lonelytriangle.core;

/**
 * The GameManager represents the game.
 * 
 * @author Benedikt Zönnchen
 * @version v1.0
 * 
 */
public interface IGameManager
{

    /**
     * This method represents the main game loop: simulating physics and animations, rendering, handling user input.
     */
    void loopIteration();

    /**
     * Pause the current game. The simulation stops and a menu should be displayed.
     */
    void pauseGame();
    
    /**
     * Create a new Game. Reset the model.
     */
    void createNewGame();

    /**
     * Resume the game. This should be called to resume a game after pausing.
     */
    void resumeGame();

    /**
     * End the game. This should be called after the user dies.
     */
    void endGame();
    
    /**
     * Returns true if the game is in state Paused, otherwise false.
     * 
     * @return true if the game is paused, else false
     */
    boolean isPaused();

    /**
     * Returns true if the game is in state Simulation, otherwise false.
     * 
     * @return true if the game is in simulating mode, else false
     */
    boolean isSimulation();

    /**
     * Terminates the TimerTask for the gameloop.
     */
    void killGame();

}
