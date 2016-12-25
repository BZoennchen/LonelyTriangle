package mobile.app.lonelytriangle.highscore;

/**
 * TODO.
 * 
 * @author Johannes Szeibert
 * @version v1.0
 * 
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import mobile.app.lonelytriangle.R;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

public class Highscore extends AsyncTask<String, Integer, List<HighscoreEntry>> {

	private final ListView list;
	private final Context context;
	private final String basepath = "http://rest.triangle.jones.szeibert.de/";
	private final int level;
	private final int maxEntrys = 10;
	private String name = null;
	private int score = 0;
    private boolean run = false;

	public Highscore(final Context context, final ListView list, final int level) {
		this.context = context;
		this.list = list;
		this.level = level;
	}

	public Highscore(final Context context, final ListView list, final int level, int score, String name) {
		this.context = context;
		this.list = list;
		this.level = level;
		this.name = name;
		this.score = score;
	}

    /** show procesbar while loading highscore */
    @Override
    protected void onPreExecute() {
        if(isOnline())
        {
            ((Activity) context).setProgressBarIndeterminateVisibility(true);
            run  = true;
        }
        else 
        {
            CharSequence text = "No Internet Connection";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

	/**
	 * parses the number of maxEntrys from the Highscore rest server
	 * @return returns up to >maxEntry< HighscoreEntrys or null if there are non
	 */
	@Override
	protected List<HighscoreEntry> doInBackground(String... arg0) {
	    final List<HighscoreEntry> result = new ArrayList<HighscoreEntry>();
	    if(run)
	    {
    		if (name != null) {
    			try {
    				URL url = new URL("http://rest.triangle.jones.szeibert.de/" + level + "_" + name + "_" + score);
    				HttpURLConnection HttpConnection = (HttpURLConnection) url.openConnection();
    				HttpConnection.setDoOutput(true);
    				HttpConnection.setRequestMethod("PUT");
    				Log.i("HttpConnection", HttpConnection.getResponseCode() + " Code");
    				HttpConnection.disconnect();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    		
    
    		try 
    		{
    			final URL url = new URL(basepath + level);
    			final URLConnection connection = url.openConnection();
    			final BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    
    			String line = reader.readLine();
    
    			int i = 0;
    
    			while (line != null && i < maxEntrys) 
    			{
    				String[] temp = line.split(";");
    				if (temp.length > 1) 
    				{
    					result.add(new HighscoreEntry(temp[0], temp[1]));
    					i++;
    				}
    				line = reader.readLine();
    			}
    			reader.close();
    		} catch (final IOException e) {
    			// TODO evtl. einen toast ausgeben
    			Log.e("Highscore.java", e.toString());
    		} catch (final NullPointerException e) {
    			// TODO evtl. einen toast ausgeben
    			Log.e("Highscore.java", e.toString());
    		}
	    }

		return result;
	}

	/** hide the processbar after loading highscore and update listview
	* @param the loaded highscores
	*/
	@Override
	protected void onPostExecute(final List<HighscoreEntry> result) {
	    if(run){
    		((Activity) context).setProgressBarIndeterminateVisibility(false);
    
    		if (!result.isEmpty()) {
    			HighscoreEntry data[] = new HighscoreEntry[result.size()];
    			result.toArray(data);
    
    			HighscoreAdapter adapter = new HighscoreAdapter(context, R.layout.highscore_row, data);
    
    			list.setAdapter(adapter);
    		} else {
    			HighscoreAdapter adapter = new HighscoreAdapter(context, R.layout.highscore_row, new HighscoreEntry[] { new HighscoreEntry("No", "Highscore") });
    
    			list.setAdapter(adapter);
    		}
	    }
	}
	
	/**
	 * Checks if there is an active Internet connection.
	 * To prevent errors while in airplane mode or no Network
	 * @return true if there is an active Internet connection
	 */
	public boolean isOnline() {
	    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
	}

}
