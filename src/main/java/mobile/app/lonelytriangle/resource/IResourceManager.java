package mobile.app.lonelytriangle.resource;

import mobile.app.lonelytriangle.resource.ResourceManager.Colors;
import android.content.res.Resources;

//TODO: javadoc is missing!

/**
 * Loads all external resources like config files and so on. IResourceManager offers methods to get config informations.
 * 
 * @author Benedikt Zönnchen, Alexander Waldeck, Johannes Szeibert
 * @version v1.0
 * 
 */
public interface IResourceManager
{
    /**
     * Loads a new config on the fly.
     * @param rawResource
     */
    void loadConfig(final int rawResource);

    /**
     * Initialize the IResourceManager. This method have to be called before you use the IResourceManager.
     * 
     * @param resources the Resources object of the activity
     */
    void init(final Resources resources);
    
    float getMsToSec();

    long getMaxFrameRate();

    int getShipWidth();

    int getShipHeight();

    float getShotScale();

    int getEnemySize();

    float getTriChance();

    float getRectChance();

    float getDiamChance();

    float getRectVelocity();

    float getRectRad();

    Colors getRectColor();

    float getTriVelocity();

    Colors getTriColor();

    float getDiamVelocity();

    Colors getDiamColor();

    float getShipVelocity();

    float getShipMaxDelta();

    int getShipShotInterval();

    Colors getShipColor();

    float getTriXVelocity();

    float getDiamRad();

    float getShotVelocity();

    float getRectShotChance();
   
    float getRectFreq();

    float getTriShotChance();

    float getDiamShotChance();

    int getEnemyMinShotInterval();

    float getEnemyShotVelocity();

    float getEnemyShotScale();

    int getNumberOfStars();

    float getStarVelocity();

    float getBonusShotChance();

    float getBonusLifeChance();

    float getBonusShotDuration();

    float getBonusVisibleTime();

    int getBonusSize();

    int getBonusFastShotSpeedUp();

    int getBonusTripleShotAngle();

    Colors getBonusTripleShotColor();

    Colors getBonusFastShotColor();

    Colors getBonusLifeColor();

    int getRectScore();

    int getTriScore();

    int getDiamScore();

    int getInitialLifes();

    int getBonusYVelocity();

    int getLevelUpFactor();

    int getLevel();
    
    int getMaxNumberOfShownHearts();
}
