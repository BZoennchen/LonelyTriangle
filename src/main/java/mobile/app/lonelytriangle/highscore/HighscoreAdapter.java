package mobile.app.lonelytriangle.highscore;

/**
 * TODO.
 * 
 * @author Johannes Szeibert
 * @version v1.0
 * 
 */
import mobile.app.lonelytriangle.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class HighscoreAdapter extends ArrayAdapter<HighscoreEntry> {

	Context context;
	int layoutResourceId;
	HighscoreEntry data[] = null;
	public static int number = 1;

	public HighscoreAdapter(Context context, int layoutResourceId, HighscoreEntry[] data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		WeatherHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new WeatherHolder();
			holder.score = (TextView) row.findViewById(R.id.score);
			holder.name = (TextView) row.findViewById(R.id.name);

			row.setTag(holder);
		} else {
			holder = (WeatherHolder) row.getTag();
		}

		HighscoreEntry entry = data[position];
		//holder.name.setText(number++ + ". " + entry.name);
        holder.name.setText(entry.name);
		holder.score.setText(entry.score);
		
		final Typeface type = Typeface.createFromAsset(context.getAssets(), "fonts/ARCADE_I.TTF");
		holder.name.setTypeface(type);
		holder.score.setTypeface(type);

		return row;
	}

	static class WeatherHolder {
        TextView name;
		TextView score;
	}
}
