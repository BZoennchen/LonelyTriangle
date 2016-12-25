package mobile.app.lonelytriangle.resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import mobile.app.lonelytriangle.R;
import android.content.res.Resources;

/**
 * singleton implementation of the IResourceManager. This resource manager loads the data from a config file.
 * 
 * @author Benedikt Zönnchen, Alexander Waldeck, Johannes Szeibert
 * @version v1.0
 * 
 */
public final class ResourceManager implements IResourceManager
{
    /**
     * A Colors is a simple flag for identifiy the color of an object. These are the whole colors of the game.
     * The Renderer interprets these flags in the render process.
     * @author Alexander Waldeck
     * @version v1.0
     *
     */
    public enum Colors
    {
        Yellow, Green, White, Red, Blue, Orange, Purple
    }

    //TODO: But this variable in the config file!
    /** the maximum number of shown hearts. If the user has more lifes than this number the display mode will change. */
    private static final int              MAX_NUMBER_OF_SHOWN_HEARTS = 10;
    private float                         msToSec;    
    private long                          maxFrameRate;
    private int                           levelUpFactor;

    // Colors
    private Colors                        rectColor;
    private Colors                        triColor;
    private Colors                        diamColor;
    private Colors                        shipColor;
    private Colors                        bonusTripleShotColor;
    private Colors                        bonusFastShotColor;
    private Colors                        bonusLifeColor;

    // Player
    private int                           shipWidth;
    private int                           shipHeight;
    private float                         shipVelocity;
    private float                         shipMaxDelta;
    private int                           shipShotInterval;
    private float                         shotScale;
    private float                         shotVelocity;
    private int                           initialLifes;

    // Stars
    private int                           nrOfStars;
    private float                         starVelocity;

    // Bonus
    private float                         bonusShotChance;
    private float                         bonusLifeChance;
    private int                           bonusFastShotSpeedUp;
    private float                         bonusShotDuration;
    private float                         bonusVisibleTime;
    private int                           bonusSize;
    private int                           bonusTripleShotAngle;
    private int                           bonusYVelocity;

    // Enemies
    private int                           enemySize;
    private int                           enemyMinShotInterval;
    private float                         enemyShotVelocity;
    private float                         enemyShotScale;
    private int                           rectScore;
    private int                           triScore;
    private int                           diamScore;

    // Rectangle Enemy
    private float                         rectChance;
    private float                         rectVelocity;
    private float                         rectRad;
    private float                         rectFreq;
    private float                         rectShotChance;

    // Triangle Enemy
    private float                         triChance;
    private float                         triVelocity;
    private float                         triXVelocity;
    private float                         triShotChance;

    // Diamond Enemy
    private float                         diamChance;
    private float                         diamVelocity;
    private float                         diamRad;
    private float                         diamShotChance;

    // Highscore level
    private int                           level;
    // Instance
    private static IResourceManager       instance = new ResourceManager();
    private Resources                     resource;
    private final Map<Class<?>, Class<?>> typeMap  = new HashMap<Class<?>, Class<?>>();

    /**
     * Construct a new ResourceManager, this should be called only once!
     * 
     * @param resource the resource
     */
    private ResourceManager()
    {
    }

    @Override
    public void init(final Resources resources)
    {
        resource = resources;
        loadConfig(R.raw.difficulty_hard);
    }

    /**
     * Returns the instance of this singleton.
     * 
     * @return the instance of this singleton
     */
    public static IResourceManager getInstance()
    {
        return instance;
    }

    @Override
    public void loadConfig(final int rawResource)
    {
        typeMap.put(int.class, Integer.class);
        typeMap.put(long.class, Long.class);
        typeMap.put(double.class, Double.class);
        typeMap.put(float.class, Float.class);
        typeMap.put(boolean.class, Boolean.class);
        typeMap.put(char.class, Character.class);
        typeMap.put(byte.class, Byte.class);
        typeMap.put(void.class, Void.class);
        typeMap.put(short.class, Short.class);
        typeMap.put(Colors.class, Colors.class);

        final InputStream stream = resource.openRawResource(rawResource);
        final BufferedReader br = new BufferedReader(new InputStreamReader(stream));

        try
        {
            String line = br.readLine();

            while (line != null)
            {
                if (line.startsWith("//") || line.startsWith(" ") || line.equals(""))
                {
                    line = br.readLine();
                    continue;
                }
                final int index = line.indexOf("=");
                final String name = line.substring(0, index - 1);
                final String valueString = line.substring(index + 1).trim();

                final Field field = ResourceManager.class.getDeclaredField(name);
                Object value;

                if (field.getType().isEnum())
                {
                    value = typeMap.get(field.getType()).getMethod("valueOf", String.class).invoke(null, valueString);
                }
                else
                {
                    value = typeMap.get(field.getType()).getConstructor(String.class).newInstance(valueString);
                }

                field.set(this, value);
                line = br.readLine();

            }
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
        catch (final NoSuchFieldException e)
        {
            e.printStackTrace();
        }
        catch (final NoSuchMethodException e)
        {
            e.printStackTrace();
        }
        catch (final IllegalArgumentException e)
        {
            e.printStackTrace();
        }
        catch (final InstantiationException e)
        {
            e.printStackTrace();
        }
        catch (final IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (final InvocationTargetException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public float getMsToSec()
    {
        return msToSec;
    }

    @Override
    public int getLevelUpFactor()
    {
        return levelUpFactor;
    }

    @Override
    public long getMaxFrameRate()
    {
        return maxFrameRate;
    }

    @Override
    public int getShipWidth()
    {
        return shipWidth;
    }

    @Override
    public int getShipHeight()
    {
        return shipHeight;
    }

    @Override
    public float getShotScale()
    {
        return shotScale;
    }

    @Override
    public int getEnemySize()
    {
        return enemySize;
    }

    @Override
    public float getTriChance()
    {
        return triChance;
    }

    @Override
    public float getRectChance()
    {
        return rectChance;
    }

    @Override
    public float getDiamChance()
    {
        return diamChance;
    }

    @Override
    public float getRectVelocity()
    {
        return rectVelocity;
    }

    @Override
    public float getRectRad()
    {
        return rectRad;
    }

    @Override
    public float getRectFreq()
    {
        return rectFreq;
    }
    
    @Override
    public Colors getRectColor()
    {
        return rectColor;
    }

    @Override
    public float getTriVelocity()
    {
        return triVelocity;
    }

    @Override
    public float getShotVelocity()
    {
        return shotVelocity;
    }

    @Override
    public Colors getTriColor()
    {
        return triColor;
    }

    @Override
    public float getDiamVelocity()
    {
        return diamVelocity;
    }

    @Override
    public Colors getDiamColor()
    {
        return diamColor;
    }

    @Override
    public float getShipVelocity()
    {
        return shipVelocity;
    }

    @Override
    public float getShipMaxDelta()
    {
        return shipMaxDelta;
    }

    @Override
    public int getShipShotInterval()
    {
        return shipShotInterval;
    }

    @Override
    public Colors getShipColor()
    {
        return shipColor;
    }

    @Override
    public float getTriXVelocity()
    {
        return triXVelocity;
    }

    @Override
    public float getDiamRad()
    {
        return diamRad;
    }

    @Override
    public float getRectShotChance()
    {
        return rectShotChance;
    }

    @Override
    public float getTriShotChance()
    {
        return triShotChance;
    }

    @Override
    public float getDiamShotChance()
    {
        return diamShotChance;
    }

    @Override
    public int getEnemyMinShotInterval()
    {
        return enemyMinShotInterval;
    }

    @Override
    public float getEnemyShotVelocity()
    {
        return enemyShotVelocity;
    }

    @Override
    public float getEnemyShotScale()
    {
        return enemyShotScale;
    }

    @Override
    public int getNumberOfStars()
    {
        return nrOfStars;
    }

    @Override
    public float getStarVelocity()
    {
        return starVelocity;
    }

    @Override
    public float getBonusShotChance()
    {
        return bonusShotChance;
    }

    @Override
    public float getBonusLifeChance()
    {
        return bonusLifeChance;
    }

    @Override
    public float getBonusShotDuration()
    {
        return bonusShotDuration;
    }

    @Override
    public float getBonusVisibleTime()
    {
        return bonusVisibleTime;
    }

    @Override
    public int getBonusSize()
    {
        return bonusSize;
    }

    @Override
    public int getBonusFastShotSpeedUp()
    {
        return bonusFastShotSpeedUp;
    }

    @Override
    public int getBonusTripleShotAngle()
    {
        return bonusTripleShotAngle;
    }

    @Override
    public Colors getBonusTripleShotColor()
    {
        return bonusTripleShotColor;
    }

    @Override
    public Colors getBonusFastShotColor()
    {
        return bonusFastShotColor;
    }

    @Override
    public Colors getBonusLifeColor()
    {
        return bonusLifeColor;
    }

    @Override
    public int getRectScore()
    {
        return rectScore;
    }

    @Override
    public int getTriScore()
    {
        return triScore;
    }

    @Override
    public int getDiamScore()
    {
        return diamScore;
    }

    @Override
    public int getBonusYVelocity()
    {
        return bonusYVelocity;
    }

    @Override
    public int getInitialLifes()
    {
        return initialLifes;
    }
    
    @Override
    public int getLevel()
    {
        return level;
    }

    @Override
    public int getMaxNumberOfShownHearts()
    {
        return MAX_NUMBER_OF_SHOWN_HEARTS;
    }

}
