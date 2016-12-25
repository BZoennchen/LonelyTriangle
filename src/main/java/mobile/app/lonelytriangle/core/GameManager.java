package mobile.app.lonelytriangle.core;

import java.util.Timer;
import java.util.TimerTask;

import mobile.app.lonelytriangle.R;
import mobile.app.lonelytriangle.input.GameAction;
import mobile.app.lonelytriangle.input.IInputManager;
import mobile.app.lonelytriangle.input.InputManager;
import mobile.app.lonelytriangle.menu.MenuHandler;
import mobile.app.lonelytriangle.rendering.IRenderer;
import mobile.app.lonelytriangle.rendering.Renderer;
import mobile.app.lonelytriangle.resource.IResourceManager;
import mobile.app.lonelytriangle.resource.ResourceManager;
import mobile.app.lonelytriangle.resource.SoundManager;
import mobile.app.lonelytriangle.simulation.ISimulation;
import mobile.app.lonelytriangle.simulation.ISimulation.Difficulty;
import mobile.app.lonelytriangle.simulation.Simulation;
import mobile.app.lonelytriangle.simulation.shapes.Ship;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.util.Log;

/**
 * Controller of the game. The game manager holds and put all importand managing objects (InputHandler, Simulator,
 * Renderer) together. The manager manage the game loop, pulls the user input information from the InputHandler and take
 * care of this input. The manager also take care of the correct frame rate and delegates all render work to the
 * renderer the simulation work to the simulator.
 * 
 * @author Benedikt Zönnchen, Alexander Waldeck, Johannes Szeibert
 * @version v1.0
 * 
 */
public class GameManager implements IGameManager
{

    /** renders the whole game. */
    private final IRenderer        renderer;

    /**
     * simulate the whole game and holds all information of the game (model) (update positions, delete ships, shots and
     * so on).
     */
    private final ISimulation      simulation;

    /** handles all the user input. */
    private final IInputManager    inputManager;

    /** target of the renderer. */
    private final GLSurfaceView    glSurfaceView;

    /** the factor to transfer nano seconds into seconds. */
    private final float            msToSec;

    /** the frame rate of the game, the rate may slightly above this number. */
    private final long             maxFrameRate;

    private final IResourceManager resource;

    /** the minimum amount of time that the game should wait before start loop iteration. */
    private final long             minDelta;

    /** start time of last frame in nano seconds. **/
    private long                   lastFrameStart;

    /** timer for GameLoop. */
    private final Timer            iterate;

    /** the menu of the game, only a view component. */
    private final MenuHandler      menuHandler;

    /** the game actions which are bind to ui events. */
    private GameAction             startGameAction;
    private GameAction             resumeGameAction;
    private GameAction             playMusicAction;
    private GameAction             changeDifficultyAction;
    private GameAction             showHighscoreAction;
    private GameAction             startMovingAndShooting;
    private GameAction             stopShooting;

    /** the main activity. */
    private final Activity         activity;

    /** the current game of this game. */
    private GameState              state = GameState.Init;

    /** the gamestate before Pause. */
    private GameState              gameStateBeforePause;

    /** The game states of the GameManager. */
    private enum GameState
    {
        Pause, /** when game is paused. */
        Running, /** when game is running. */
        Simulation, /** before we start the game, but we see the option menu. */
        Init
        /** before the the gamemanager is installed. */
    }

    /** all the Sounds. */
    private final SoundManager sound;

    /**
     * Constructs a newly allocated GameManager object that holds the (main) activity in it.
     * 
     * @param activity the main activity
     * @param sound the sound manager of the game
     */
    public GameManager(final Activity activity, final SoundManager sound)
    {
        this.activity = activity;
        this.sound = sound;
        resource = ResourceManager.getInstance();
        simulation = new Simulation();
        
        renderer = new Renderer(simulation);
        renderer.addScreenResizeListener(simulation);
        inputManager = new InputManager();
        glSurfaceView = (GLSurfaceView) activity.findViewById(R.id.graphics_glsurfaceview); // new
                                                                                            // GLSurfaceView(activity);
        msToSec = resource.getMsToSec();
        maxFrameRate = resource.getMaxFrameRate();
        minDelta = (long) (msToSec / maxFrameRate);
        menuHandler = new MenuHandler(activity);

        install();

        // start!
        iterate = new Timer();
        iterate.scheduleAtFixedRate(new LoopIterationTask(), 0, (long) msToSec / maxFrameRate);
        lastFrameStart = 0;

        setState(GameState.Simulation);
    }

    @Override
    public void loopIteration()
    {
        long elapsedTime = calculateElapseTime();
        if (elapsedTime < minDelta)
        {
            try
            {
                Thread.sleep(minDelta - elapsedTime);
            }
            catch (final InterruptedException e)
            {
                e.printStackTrace();
            }
            elapsedTime = minDelta;
        }
        lastFrameStart = System.currentTimeMillis();

        handleInput();

        if (state == GameState.Running || state == GameState.Simulation)
        {
            // TODO slow down!
            simulation.update(elapsedTime / msToSec);

            if (state == GameState.Running)
            {
                final Ship ship = (Ship) simulation.getShip();

                if (ship.getcurrentShotType() && ship.isGenerateShot())
                {
                    sound.play("trippleshot");
                } 
                else if (ship.isGenerateShot())
                {
                    sound.play("shot");
                }   

                menuHandler.setScorePoints(String.valueOf(simulation.getScore()));

                int life = ((Ship) simulation.getShip()).getLife();
                if (life > resource.getMaxNumberOfShownHearts())
                {
                    menuHandler.setLifes(((Ship) simulation.getShip()).getLife());
                    menuHandler.setLifeVisible(true);
                }
                else
                {
                    menuHandler.setLifeVisible(false);
                }

                if (ship.getLife() <= 0)
                {
                    endGame();
                }

                // play the different Bonus sounds if there was a Bonus collected
                if (ship.collectedBonus())
                {
                    sound.play(ship.pullcollectedBonusName());
                }

            }
            glSurfaceView.requestRender();
        }
    }

    /**
     * change the current state of the GameManager to the argument state.
     * 
     * @param state the state that should be the new state of the GameManager
     */
    private void setState(GameState state)
    {
        Log.i(GameManager.class.toString(), state.toString());
        this.state = state;
    }

    private void handleInput()
    {
        if (state == GameState.Running)
        {
            if (startMovingAndShooting.isPressed())
            {
                simulation.enableShipShoting();
                simulation.setTargetX(inputManager.getTouchX());
                simulation.setTargetY((int) touchHeightToOpenGLHeight(inputManager.getTouchY()));
            }
            else if (stopShooting.isPressed())
            {
                simulation.disableShipShoting();
            }
        }
        else if (state == GameState.Pause || state == GameState.Simulation)
        {
            if (startGameAction.isPressed())
            {
                createNewGame();
            }

            if (changeDifficultyAction.isPressed())
            {
                switchDifficulty(simulation.getDifficulty().increment());
            }

            if (resumeGameAction.isPressed() && state == GameState.Pause)
            {
                resumeGame();
            }

            if (playMusicAction.isPressed())
            {
                if (!simulation.isSoundOn())
                {
                    menuHandler.setMusicButtonText("Music Off");
                    sound.stopMusic();
                }
                else
                {
                    menuHandler.setMusicButtonText("Music On");
                    sound.startMusic();
                }
                simulation.setSoundOn(!simulation.isSoundOn());
            }

            if (showHighscoreAction.isPressed())
            {
                showHighScores();
            }
        }
    }

    private void showHighScores()
    {
        final Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://triangle.jones.szeibert.de/#highscore"));
        activity.startActivity(intent);
    }

    /**
     * throws an IllegalStateException if the difficultiy has an invalid value.
     */
    private void switchDifficulty(final Difficulty diff)
    {
        simulation.setDifficulty(diff);
        menuHandler.setDifficultyButtonText(diff.toString());

        switch (diff)
        {
            case Hard: // Hard
                ResourceManager.getInstance().loadConfig(R.raw.difficulty_hard);
                menuHandler.updateHighscore(0);
                break;
            case Insane: // Insane
                ResourceManager.getInstance().loadConfig(R.raw.difficulty_insane);
                menuHandler.updateHighscore(1);
                break;
            case Easy: // Easy
                ResourceManager.getInstance().loadConfig(R.raw.difficulty_easy);
                menuHandler.updateHighscore(2);
                break;
            default:
                throw new IllegalStateException("wrong value for the difficulty attribute");
        }
        if (state == GameState.Simulation)
        {
            simulation.initSimulation();
        }
        else
        {
            simulation.initGame();
        }
        menuHandler.setResumeButtonVisible(false);
    }

    /**
     * calculate the elapsed time.
     * 
     * @return the elapsed time
     */
    private long calculateElapseTime()
    {
        return lastFrameStart == 0 ? minDelta : (System.currentTimeMillis() - lastFrameStart);
    }

    /**
     * initialise the game: Binds the IRenderer to the Surfaceview, binds all view component (which receive ui-events)
     * to a GameAction by the IInputManager and finally set the menu visible.
     * 
     * @param activity
     */
    private void install()
    {
        // create all the management objects
        glSurfaceView.setRenderer(renderer);
        glSurfaceView.setOnTouchListener(inputManager);
        glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        menuHandler.initMenu();
        glSurfaceView.onResume();

        final SensorManager manager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);

        // is the accelerometer available for this device? TODO: impl. an
        // alternative input controller.
        if (manager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() > 0)
        {
            // register this Activity! There is generally only one.
            final Sensor accelerometer = manager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
            manager.registerListener(inputManager, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        }

        // install GameActions
        playMusicAction = new GameAction("play music", GameAction.DETECT_INITAL_PRESS_ONLY);
        changeDifficultyAction = new GameAction("change difficulty", GameAction.DETECT_INITAL_PRESS_ONLY);
        startGameAction = new GameAction("start game", GameAction.DETECT_INITAL_PRESS_ONLY);
        showHighscoreAction = new GameAction("show highscore", GameAction.DETECT_INITAL_PRESS_ONLY);
        resumeGameAction = new GameAction("resume game", GameAction.DETECT_INITAL_PRESS_ONLY);

        startMovingAndShooting = new GameAction("move and shoot", GameAction.DETECT_INITAL_PRESS_ONLY);
        stopShooting = new GameAction("stop move and shoot", GameAction.DETECT_INITAL_PRESS_ONLY);

        // map actions to buttons of menu handler
        inputManager.addView(menuHandler.getMusicButton(), playMusicAction);
        inputManager.addView(menuHandler.getDifficultyButton(), changeDifficultyAction);
        inputManager.addView(menuHandler.getResumeButton(), resumeGameAction);
        inputManager.addView(menuHandler.getStartButton(), startGameAction);
        inputManager.addView(menuHandler.getTalesButton(), showHighscoreAction);

        inputManager.mapToTouch(startMovingAndShooting, InputManager.TOUCH_ACTION_DOWN);
        inputManager.mapToTouch(startMovingAndShooting, InputManager.TOUCH_ACTION_MOVE);
        inputManager.mapToTouch(stopShooting, InputManager.TOUCH_ACTION_UP);
        menuHandler.setOn();
        
        /*SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        simulation.setSoundOn("true".equals(appSharedPrefs.getString("MusicOn", "")));
        Difficulty diff = Difficulty.getDifficulty(appSharedPrefs.getString("Difficulty", "Hard"));
        switchDifficulty(diff);
        simulation.setDifficulty(Difficulty.getDifficulty(appSharedPrefs.getString("Difficulty", "Hard")));
       
        if (simulation.isSoundOn())
        {
            sound.startMenuMusic();
        }*/
    }

    @Override
    public void createNewGame()
    {
        // this.simulation.updateScreen(this.screenWidth, this.screenHeight);
        simulation.initGame();
        glSurfaceView.onResume();
        setState(GameState.Running);
        menuHandler.setOff();
        menuHandler.setStatsOn();
        sound.startGameMusic();
    }

    @Override
    public void endGame()
    {
        sound.play("death");
        final int score = simulation.getScore();
        simulation.initSimulation();
        state = GameState.Simulation;
        sound.startMenuMusic();
        menuHandler.showScore(score);
        menuHandler.setResumeButtonVisible(false);
    }

    @Override
    public void pauseGame()
    {
        if (state != GameState.Simulation)
        {
            gameStateBeforePause = state;
            setState(GameState.Pause);
        }
        sound.pauseMusic();
        menuHandler.setOn();
        menuHandler.setResumeButtonVisible(true);

        // TODO: Dirty hack cause of bug: the option menu is not well painted
        menuHandler.setDifficultyButtonText(menuHandler.getDifficultyButton().getText().toString());
    }

    @Override
    public void resumeGame()
    {
        glSurfaceView.onResume();
        setState(gameStateBeforePause);
        sound.resumeMusic();
        menuHandler.setOff();
    }

    @Override
    public void killGame()
    {
        iterate.cancel();
        /*SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        Editor prefsEditor = appSharedPrefs.edit();
        prefsEditor.putString("MusicOn", simulation.isSoundOn() ? "true" : "false");
        prefsEditor.putString("Difficulty", simulation.getDifficulty().toString());
        prefsEditor.commit();*/
    }

    /**
     * Convert the height of the view to the height of openGL (0,0 is the upper left corner in view and the buttom left
     * corner in openGL).
     * 
     * @param height the height (y-coordinate) of the view
     * @return the y-coordinate of openGL
     */
    private float touchHeightToOpenGLHeight(final float height)
    {
        return renderer.getScreenHeight() - height;
    }

    @Override
    public boolean isPaused()
    {
        return state == GameState.Pause;
    }

    @Override
    public boolean isSimulation()
    {
        return state == GameState.Simulation;
    }

    /**
     * A helper TimeTask which simple run the loopIteration of the GameManager.
     * 
     * @author Alexander Waldeck, Johannes Szeibert
     * @version v1.0
     * 
     */
    private class LoopIterationTask extends TimerTask
    {
        @Override
        public void run()
        {
            loopIteration();
        }
    }
}
