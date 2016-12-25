package mobile.app.lonelytriangle.resource;

/**
 * Sound Manager of the Game. Handles all the sounds that are being played.
 * 
 * @author Johannes Szeibert
 * @version v1.0
 * 
 */
import java.util.HashMap;

import mobile.app.lonelytriangle.R;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class SoundManager {

   /**
    * The Sound Pool plays all the foreground sounds.
    */
   private MediaPlayer background;

    /**
      * The Sound Pool plays all the foreground sounds.
      */
    private SoundPool foreground;

    /** all Soundpoolsounds.
     */
    HashMap<String, Integer> soundMap;

    /** gets the System Volume.
     */
    private float actualVolume;

    /**
     * Audio Manager of the Device.
     */
    private final AudioManager audioManager;

    /**
     * saves which music is currently playing or -1 is music is off.
     */
    private static int currentMusic = -1;

    /**
     * if music is on and gets paused here, the position in which the music stops gets saved.
     */
    private int currentTrackPosition;

    /**
     * true id music should be playing.
     */
    private boolean musicOn = true;

    /**
     * The context object to register the audio service on.
     */
    Context context;

    /** Constructor.
     * 
     * @param context
     * @param audioManager
     */
    public SoundManager(final Context context, final AudioManager audioManager) {
        
        

        this.audioManager = audioManager;
        this.context = context;
        foreground = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        soundMap = new HashMap<String, Integer>();
        soundMap.put("death", foreground.load(context, R.raw.death, 1));
        soundMap.put("FastShot", foreground.load(context, R.raw.extra_fast, 1));
        soundMap.put("TripleShot", foreground.load(context, R.raw.extra_tripple, 1));
        soundMap.put("LifeUp", foreground.load(context, R.raw.extra_health, 1));
        soundMap.put("shot", foreground.load(context, R.raw.shot, 2));
        soundMap.put("trippleshot", foreground.load(context, R.raw.shot_tripple, 2));

        background = new MediaPlayer();
    }

    /**
     * returns the actual current volume of the device.
     * 
     * @return float The Actual VOlume of the Device.
     */
    public void setActualVolume() {
        actualVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    /* ------------------------- Foreground ------------------------- */

    /**
     * play laser shot sound.
     */
    public void play(final String sound) {
        setActualVolume();
        foreground.play(soundMap.get(sound), actualVolume, actualVolume, 0, 0, 1);
    }
    
    /**
     * .
     */
    public void killForeground()
    {
        foreground.release();
        foreground = null;
    }

    /* ------------------------- Background ------------------------- */

    /**
     * starts the music in the game menu for "game initialize" or "music on".
     */
    public void startMusic() {
        musicOn = true;
        startMenuMusic();
    }

    /**
     * stops the music for "music off".
     */
    public void stopMusic() {
        killBackground();
        musicOn = false;
    }
    
    /**
     * cleares the mediaplayer object if a music is playing to be able to load a new one.
     */
    private void killBackground(){
        if (background != null) {
            background.stop();
            background.reset();
            background.release();
            background = null;
        }
    }
    
    /**
     * starts the game menu music.
     */
    public void startMenuMusic() {
        if(musicOn){
            killBackground();
            background = MediaPlayer.create(context, R.raw.music_menu);
            background.setLooping(true);
            currentMusic = 1;
            background.start();
        }
    }
    
    /**
     * starts the game music.
     */
    public void startGameMusic() {
        if(musicOn){
            killBackground();
            background = MediaPlayer.create(context, R.raw.music_game);
            background.setLooping(true);
            currentMusic = 0;
            background.start();
        }
    }

    /**
     * pauses the currently playing music.
     */
    public void pauseMusic() {
        if(musicOn){
            currentTrackPosition = background.getCurrentPosition();
            background.pause();
        }
    }
    
    /**
     * returns which music is currently running or -1 if no music is running.
     */
    public int isMusicPlaying(){
        return currentMusic;
    }

    /**
     * resumes the game music, if there was a pause time recorded.
     */
    public void resumeMusic()
    {
        if(musicOn){
            killBackground();
            background = MediaPlayer.create(context, R.raw.music_game);
            background.setLooping(true);
            background.seekTo(currentTrackPosition);
            currentMusic = 1;
            background.start();
        }
    }
    
    /**
     * .
     */
    public void killSoundManager()
    {
        killForeground();
        killBackground();
    }
}