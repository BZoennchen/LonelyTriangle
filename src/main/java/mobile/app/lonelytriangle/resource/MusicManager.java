package mobile.app.lonelytriangle.resource;

import mobile.app.lonelytriangle.R;
import android.content.Context;
import android.media.MediaPlayer;

public class MusicManager
{

    public static final int     MUSIC_MENU     = 0;
    public static final int     MUSIC_GAME     = 1;

    private static int          currentMusic   = -1;
    
    static MediaPlayer musicPlayer;
    
    public MusicManager(){
        musicPlayer = new MediaPlayer();
    }
    
    public static void start(Context context, int music){
        if (musicPlayer != null)
        {
            if (!musicPlayer.isPlaying())
            {
                musicPlayer.start();
            }
        }
        else
        {
            if (music == MUSIC_MENU)
            {
                musicPlayer = MediaPlayer.create(context, R.raw.music_menu);
            }
            else if (music == MUSIC_GAME)
            {
                musicPlayer = MediaPlayer.create(context, R.raw.music_game);
            }
        }
    }
}
