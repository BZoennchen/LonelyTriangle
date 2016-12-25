package mobile.app.lonelytriangle.simulation.shapes;

import mobile.app.lonelytriangle.rendering.IMeshFactory;
import mobile.app.lonelytriangle.rendering.MeshFactory;
import mobile.app.lonelytriangle.resource.IResourceManager;
import mobile.app.lonelytriangle.resource.ResourceManager;
import mobile.app.lonelytriangle.simulation.IMoveable;
import mobile.app.lonelytriangle.simulation.IShapeFactory;
import mobile.app.lonelytriangle.simulation.shapes.Bonus.BonusType;

/**
 * The implementation of IShapeFactory as singleton.
 * 
 * @author Benedikt Zönnchen
 * @version v1.0
 * 
 */
public final class ShapeFactory implements IShapeFactory
{
    /** the IResourceManager which stores all the configuration data. */
    private final IResourceManager     resourceManager;
    
    /** the IMeshFactory for adding Meshes to the Shapes. */
    private final IMeshFactory         meshFactory;
    
    /** the instance of this singleton. */
    private static final IShapeFactory INSTANCE = new ShapeFactory();

    private ShapeFactory()
    {
        resourceManager = ResourceManager.getInstance();
        meshFactory = MeshFactory.getInstance();
    }

    /** returns the instance of this singleton. 
     *
     * @return the instance of this singleton
     */
    public static IShapeFactory getInsance()
    {
        return INSTANCE;
    }

    @Override
    public IMoveable getShot(final float x, final float y, final Shape parent, final float angle)
    {
        final float shotScale = parent instanceof EnemyShip ? resourceManager.getEnemyShotScale() : resourceManager.getShotScale();
        final float width = parent.getWidth() * shotScale;
        final float height = parent.getHeight() * shotScale;
        final Shot shot = new Shot(x, y, width, height, parent, angle);
        shot.setXVecolity(resourceManager.getShotVelocity());
        shot.setYVecolity(resourceManager.getShotVelocity());
        shot.setMesh(meshFactory.getShot(parent, width, height));
        return shot;
    }

    @Override
    public Ship getShip(final float x, final float y)
    {
        final Ship ship = new Ship(x, y, resourceManager.getShipWidth(), resourceManager.getShipHeight(), resourceManager.getShipColor(), resourceManager.getShipMaxDelta(), resourceManager.getShipShotInterval());
        ship.setXVecolity(resourceManager.getShipVelocity());
        ship.setYVecolity(resourceManager.getShipVelocity());
        ship.setInitialLifes(resourceManager.getInitialLifes());
        ship.setBonusShotDuration(resourceManager.getBonusShotDuration());
        ship.setBonusTripleShotAngle(resourceManager.getBonusTripleShotAngle());
        ship.setMesh(meshFactory.getShip());
        ship.setFastShotSpeedUp(resourceManager.getBonusFastShotSpeedUp());
        return ship;
    }

    @Override
    public EnemyShip getRectangleEnemy(final float x, final float y)
    {
        final RectangleEnemy recEnemy = new RectangleEnemy(x, y, resourceManager.getEnemySize(), resourceManager.getRectColor(), resourceManager.getRectScore());
        recEnemy.setMovementRadius(resourceManager.getRectRad());
        recEnemy.setMovementFrequency(resourceManager.getRectFreq());
        recEnemy.setXVecolity(resourceManager.getRectVelocity());
        recEnemy.setShotChance(resourceManager.getRectShotChance());
        recEnemy.setShotInterval(resourceManager.getEnemyMinShotInterval());
        recEnemy.setMesh(meshFactory.getRectangleEnemy());
        return recEnemy;
    }

    @Override
    public EnemyShip getDiamondEnemy(final float x, final float y)
    {
        final DiamondEnemy diamondEnemy = new DiamondEnemy(x, y, resourceManager.getEnemySize(), resourceManager.getEnemySize(), resourceManager.getDiamColor(), resourceManager.getDiamScore());
        diamondEnemy.setXVecolity(resourceManager.getDiamVelocity());
        diamondEnemy.setYVecolity(resourceManager.getDiamVelocity());
        diamondEnemy.setShotChance(resourceManager.getDiamShotChance());
        diamondEnemy.setShotInterval(resourceManager.getEnemyMinShotInterval());
        diamondEnemy.setMesh(meshFactory.getDiamondEnemy());
        return diamondEnemy;
    }

    @Override
    public EnemyShip getTriangleEnemy(final float x, final float y)
    {
        final TriangleEnemy triangleEnemy = new TriangleEnemy(x, y, resourceManager.getEnemySize(), resourceManager.getEnemySize(), resourceManager.getTriColor(), resourceManager.getTriScore());
        triangleEnemy.setShotChance(resourceManager.getTriShotChance());
        triangleEnemy.setShotInterval(resourceManager.getEnemyMinShotInterval());
        triangleEnemy.setMesh(meshFactory.getTriangleEnemy());
        triangleEnemy.setXVecolity(resourceManager.getTriXVelocity());
        triangleEnemy.setYVecolity(resourceManager.getTriVelocity());
        return triangleEnemy;
    }

    @Override
    public IMoveable getStar(final float x, final float y)
    {
        final Shape star = new Star(x, y, 1, 1, resourceManager.getStarVelocity());
        star.setMesh(meshFactory.getStar());
        return star;
    }

    @Override
    public IMoveable getFastShot(final float x, final float y)
    {
        return getBonus(x, y, BonusType.FastShot);
    }

    @Override
    public IMoveable getTripleShot(final float x, final float y)
    {
        return getBonus(x, y, BonusType.TripleShot);
    }

    @Override
    public IMoveable getLifeUp(final float x, final float y)
    {
        return getBonus(x, y, BonusType.LifeUp);
    }
    
    private IMoveable getBonus(final float x, final float y, final BonusType type)
    {
        //Bonus(int x, int y, final float width, final float height, final Colors color, BonusType type)
        final Bonus bonus = new Bonus(x, y, resourceManager.getBonusSize(), resourceManager.getBonusSize(), resourceManager.getBonusLifeColor(), type);
        bonus.setVisibleTime(resourceManager.getBonusVisibleTime());
        bonus.setMesh(meshFactory.getLifePlus());
        bonus.setYVecolity(resourceManager.getBonusYVelocity());
        
        switch(type)
        {
            case LifeUp :  bonus.setMesh(meshFactory.getLifePlus()); break;
            case FastShot : bonus.setMesh(meshFactory.getFastShot()); break;
            case TripleShot : bonus.setMesh(meshFactory.getTripleShot()); break;
            default : throw new IllegalArgumentException("wrong bonus type");
        }
        return bonus;
    }
}
