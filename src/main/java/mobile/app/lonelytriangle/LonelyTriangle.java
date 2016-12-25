package mobile.app.lonelytriangle;

import mobile.app.lonelytriangle.core.GameManager;
import mobile.app.lonelytriangle.core.IGameManager;
import mobile.app.lonelytriangle.resource.ResourceManager;
import mobile.app.lonelytriangle.resource.SoundManager;
import android.app.Activity;
import android.media.AudioManager;
import android.os.Bundle;

/**
 * LonelyTrinagle is the main activity. It simply delegates all the work to the IGameManager object.
 *
 * @author Benedikt Zönnchen
 * @version v1.0
 *
 */
public class LonelyTriangle extends Activity
{
    /** the game itself. */
    private IGameManager gameManager;

    /** all the Sounds. */
    private SoundManager sound;

    @Override
    public void onCreate(final Bundle savedInstanceState)
    {
        /* initialize the resource manager */
        ResourceManager.getInstance().init(getResources());
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main_layout);
        sound = new SoundManager(this, (AudioManager) getSystemService(AUDIO_SERVICE));
        //sound.startMenuMusic();
        gameManager = new GameManager(this, sound);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        sound.pauseMusic();
        gameManager.pauseGame();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        sound.startMenuMusic();
        // this.gameManager.pauseGame();
    }

    @Override
    public void onBackPressed()
    {
        if (gameManager.isPaused() || gameManager.isSimulation())
        {
            gameManager.killGame();
            sound.stopMusic();
            
            super.onBackPressed();
            System.exit(0);
        }
        else
        {
            gameManager.pauseGame();
            sound.startMenuMusic();
        }
    }
}
