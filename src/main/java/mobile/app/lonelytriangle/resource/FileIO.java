package mobile.app.lonelytriangle.resource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;
import android.os.Environment;

public class FileIO
{
	public FileIO(final Context context)
	{

	}

	public void store(final String string, final String name)
	{
		if (canWriteSD())
		{

			try
			{
				File file = new File(Environment.getExternalStorageDirectory(), "LonelyTriangle");
				if (!file.exists())
				{
					file.mkdir();
				}

				file = new File(file, name);
				if (!file.exists())
				{
					file.createNewFile();
				}

				final FileOutputStream out = new FileOutputStream(file);
				out.write(string.getBytes());
				out.close();
			}

			catch (final FileNotFoundException e)
			{
				e.printStackTrace();
			}
			catch (final IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public String read(final String name)
	{
		if (canReadSD())
		{

			final StringBuilder out = new StringBuilder();
			try
			{
				final File file = new File(Environment.getExternalStorageDirectory(), "/LonelyTriangle/" + name);
				final BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

				String line = br.readLine();
				while (line != null)
				{
					out.append(line);
					line = br.readLine();
				}
			}

			catch (final FileNotFoundException e)
			{
				e.printStackTrace();
			}
			catch (final IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return out.toString();
		}
		return null;
	}

	public boolean canReadSD()
	{
		boolean available = false;
		final String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state))
		{
			// We can read and write the media
			available = true;
		}

		return available;
	}

	public boolean canWriteSD()
	{
		boolean writeable = false;
		final String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state))
		{
			// We can read and write the media
			writeable = true;
		}

		return writeable;
	}
}
