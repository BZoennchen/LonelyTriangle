package mobile.app.lonelytriangle.menu;

import mobile.app.lonelytriangle.R;
import mobile.app.lonelytriangle.highscore.Highscore;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * The MenuHandler is a composition of view elements. It offers methods to change the view components by any thread.
 * 
 * @author Alex, Bene
 * @version 1.0
 */
public class MenuHandler
{
    /** activity of the Program. */
    private final Activity activity;

    /** linearLayout for menu. */
    private LinearLayout   linearLayout;

    /** grey Alpha-Layer for Menubackground. */
    private ImageView      background;

    /** Button to start the Game. */
    private TextView       startButton;
    
    /** Button to change the difficulty. */
    private TextView       difficultyButton;
    
    /** Button to turn sound on and off. */
    private TextView       musicButton;
    
    /** Button to show Credits. */
    private TextView       talesButton;
    
    /** the TextView that holds "Highscore". */
    private TextView       scoreButton;
    
    /** the HighScore ListView. */
    private ListView       scores;
    
    /** the HighScore ListViewAdapter. */
    private Highscore      highscore;
    
    /** Button to resume the game. */
    private TextView       resumeButton;
    /** TextView to show the Word "score :.". */
    private TextView       scoreView;
    
    /** to show the actual score. */
    private TextView       scorePoints;
    
    /** to show the actual life. */
    private TextView       lifeView;
    
    /** to show the actual life. */
    private TextView       lifePoints;
    
    /** the current difficulty */
    private int difficulty = 0;

    /**
     * Constructor for instantiating a MenuHandler.
     * 
     * @param activity MainActivity. Needed for getting systemresources (config Files)
     * @param difficuty of the game at start of the menu
     */
    public MenuHandler(final Activity activity)
    {
        this.activity = activity;
    }

    /**
     * Creates the Menu, the background and all buttons. Also sets the onClickListener for the menu points
     */
    public void initMenu()
    {
        linearLayout = (LinearLayout) activity.findViewById(R.id.linearLayout1);
        background = (ImageView) activity.findViewById(R.id.background);
        startButton = (TextView) activity.findViewById(R.id.button_start);
        resumeButton = (TextView) activity.findViewById(R.id.button_resume);
        difficultyButton = (TextView) activity.findViewById(R.id.button_dif);
        musicButton = (TextView) activity.findViewById(R.id.button_music);
        talesButton = (TextView) activity.findViewById(R.id.button_tales);
        scoreButton = (TextView) activity.findViewById(R.id.button_score);
        scores = (ListView) activity.findViewById( R.id.scoreListView );
        scoreView = (TextView) activity.findViewById(R.id.textView_stats_score);
        scorePoints = (TextView) activity.findViewById(R.id.textView_stats_points);
        lifePoints = (TextView) activity.findViewById(R.id.textView_stats_lifes);
        lifeView = (TextView) activity.findViewById(R.id.textView_stats_life);
        //ProgressBar progressBar = (ProgressBar) activity.findViewById(R.id.processBar);
        //progressBar.setImportantForAccessibility(1);
        
        // load highscore for the first time
        updateHighscore(0);

        // margin for the Menu
        final FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        final int margin = 40;
        layoutParams.setMargins(margin, margin, margin, margin);
        linearLayout.setLayoutParams(layoutParams);
        background.setLayoutParams(layoutParams);

        // Setting the font on the menupoints
        final Typeface type = Typeface.createFromAsset(activity.getAssets(), "fonts/ARCADE_I.TTF");
        startButton.setTypeface(type);
        resumeButton.setTypeface(type);
        difficultyButton.setTypeface(type);
        musicButton.setTypeface(type);
        talesButton.setTypeface(type);
        scoreButton.setTypeface(type);
        
        resumeButton.setVisibility(TextView.INVISIBLE);

        setStatsOff();

        // turn Menu on
        linearLayout.setVisibility(View.VISIBLE);
    }
    
    /**
     * Sets the score points in the upper left corner. This method can be called from any Thread.
     * 
     * @param scores the new score points
     */
    public void setScorePoints(final String scores)
    {
        activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                scorePoints.setText(scores);
                scorePoints.invalidate();
            }
        });
    }

    /**
     * Sets the life points in the upper right corner. This method can be called from any Thread.
     * 
     * @param lifes the new life points
     */
    public void setLifes(final int lifes)
    {
        activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                lifePoints.setText(lifes+"");
                lifePoints.invalidate();
            }
        });
    }
    
    /**
     * Turns Menu on. This method can be called from any Thread.
     */
    public void setOn()
    {
        activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                linearLayout.setVisibility(TextView.VISIBLE);
                background.setVisibility(TextView.VISIBLE);
            }
        });
    }

    /**
     * Turns Menu off. This method can be called from any Thread.
     */
    public void setOff()
    {
        activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                linearLayout.setVisibility(TextView.INVISIBLE);
                background.setVisibility(TextView.INVISIBLE);
            }
        });
    }

    /**
     * Sets the Stats on. This method can be called from any Thread.
     */
    public void setStatsOn()
    {
        activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                scorePoints.setVisibility(TextView.VISIBLE);
                scoreView.setVisibility(TextView.VISIBLE);
                lifePoints.setVisibility(TextView.VISIBLE);
                lifeView.setVisibility(TextView.VISIBLE);
            }
        });
    }

    /**
     * Sets the stats off. This method can be called from any Thread.
     */
    public void setStatsOff()
    {
        activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                scorePoints.setVisibility(TextView.INVISIBLE);
                scoreView.setVisibility(TextView.INVISIBLE);
                lifePoints.setVisibility(TextView.INVISIBLE);
                lifeView.setVisibility(TextView.INVISIBLE);
            }
        });
    }

    /**
     * Sets the life display on or off. This method can be called from any Thread.
     * @param visible true => visible, otherwise => invisble
     */
    public void setLifeVisible(final boolean visible)
    {
        activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                lifePoints.setVisibility(visible ? TextView.VISIBLE : TextView.INVISIBLE);
                lifeView.setVisibility(visible ? TextView.VISIBLE : TextView.INVISIBLE);
            }
        });
    }

    
    /**
     * Opens a dialog and shows the current score. This method can be called from any Thread.
     * 
     * @param score the final score of the user.
     */
    public void showScore(final int score)
    {
        activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                MenuHandler.this.onShowScore(score);
            }
        });
    }
    
    /**
     * Hide or show the resume button.
     * 
     * @param visible true => visible, false => hidden
     */
    public void setResumeButtonVisible(final boolean visible)
    {
        activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                resumeButton.setVisibility(visible ? TextView.VISIBLE : TextView.INVISIBLE);
            }
        });
    }
    
    private void onShowScore(final int score)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setTitle("Game Over!");
        builder.setMessage("Your Score is " + score + " Points\nPlease enter your name");

        // Set an EditText view to get user input
        final EditText input = new EditText(activity);
        builder.setView(input);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                String name = input.getText().toString();
                if (name != "")
                {
                    new Highscore(activity, scores, difficulty, score, name).execute();                    
                }
            }
        });

        builder.show();
        
        // TODO: maybe place this in gamemanager
        MenuHandler.this.setStatsOff();
        MenuHandler.this.setOn();
    }

    /**
     * Returns the start button reference.
     * 
     * @return the start button.
     */
    public TextView getStartButton()
    {
        return startButton;
    }

    /**
     * Returns the difficultyButton reference.
     * 
     * @return the start difficultyButton.
     */
    public TextView getDifficultyButton()
    {
        return difficultyButton;
    }

    /**
     * Returns the musicButton reference.
     * 
     * @return the musicButton.
     */
    public TextView getMusicButton()
    {
        return musicButton;
    }

    /**
     * Returns the talesButton reference.
     * 
     * @return the talesButton.
     */
    public TextView getTalesButton()
    {
        return talesButton;
    }

    /**
     * Returns the resumeButton reference.
     * 
     * @return the resumeButton.
     */
    public TextView getResumeButton()
    {
        return resumeButton;
    }

    /**
     * Sets the text of the difficulty button. This method can be called from any Thread.
     * 
     * @param text the new text of the difficulty button
     */
    public void setDifficultyButtonText(final String text)
    {
        activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                difficultyButton.setText(text);
            }
        });
    }

    /**
     * Sets the text of the music button. This method can be called from any Thread.
     * 
     * @param text the new text of the music button
     */
    public void setMusicButtonText(final String text)
    {
        activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                musicButton.setText(text);
            }
        });
    }
    
    class HighscoreEntry{
        TextView    month;
        TextView    content;
    }

    public void updateHighscore(final int difficulty)
    {
        activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                highscore = new Highscore(activity, scores, difficulty);
                highscore.execute();
            }
        });
        
        this.difficulty = difficulty;
    }
}
