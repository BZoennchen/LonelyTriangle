package mobile.app.lonelytriangle.simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mobile.app.lonelytriangle.resource.IResourceManager;
import mobile.app.lonelytriangle.resource.ResourceManager;
import mobile.app.lonelytriangle.simulation.collision.CollisionDetector;
import mobile.app.lonelytriangle.simulation.shapes.EnemyShip;
import mobile.app.lonelytriangle.simulation.shapes.ShapeFactory;
import mobile.app.lonelytriangle.simulation.shapes.Ship;
import mobile.app.lonelytriangle.util.Grid;

/**
 * The simulation holds all the objects which are moving or change the game world in any way. It calculate all the
 * movement, collision (by a collision detector) and all the things which change the game world.
 * 
 * @author Benedikt Zönnchen, Waldeck Alexander
 * @version v1.0
 * 
 */
public class Simulation implements ISimulation
{
    private Grid<EnemyShip>         enemies;
    private Grid<IMoveable>         enemyShots;
    private List<IMoveable>         stars;
    private Grid<IMoveable>         boni;    
    private Grid<IMoveable>         playerShots;

    private Ship                    ship;
    private int                     screenWidth;
    private int                     screenHeight;
    private boolean                 initialized;
    private boolean                 resized;
    private final Random            random;
    private final CollisionDetector collisionDetector;
    private final IShapeFactory     shapeFactory;

    private int                     score;
    private Difficulty              difficulty = Difficulty.Hard;
    private boolean                 soundOn;
    private static final int        NUMBER_OF_GRID_COLUMNS = 4;
    private static final int        NUMBER_OF_GRID_ROWS = 4;
    
    private final IResourceManager        resourceManager;

    /**
     * true => this simulation model is for the background (no collision detection and other functionality is not
     * necessary), otherwise false (game mode).
     */
    private boolean                 background;

    /**
     * Construct a new Simulation object.
     */
    public Simulation()
    {
        background = true;
        initialized = false;
        resized = false;
        soundOn = false;
        score = 0;
        random = new Random();
        collisionDetector = new CollisionDetector(this);
        shapeFactory = ShapeFactory.getInsance();
        resourceManager = ResourceManager.getInstance();
        stars      = new ArrayList<IMoveable>();
    }

    @Override
    public synchronized void update(final float delta)
    {
       
        // the game has been initialised.
        if (initialized)
        {
            // 1. update game functionality
            if (!isBackground())
            {
                // 1.1 detect all collisions and handle it
                collisionDetector.update();

                // 1.2 update shots of the ship
                playerShots = updateShapes(delta, ship.getShots());
                ship.setShots(playerShots);

                // 1.3 update the ship
                ship.update(delta);

                // 1.4 update boni
                boni = updateShapes(delta, boni);
                //System.out.println("p-s: " + ship.getShots().size());
            }

            // 2. add new enemies to the simulation
            generateEnemies(delta);

            // 3. update enemy shots
            enemyShots = updateShapes(delta, enemyShots);

            // 4. update enemies
            enemies = updateEnemies(delta, enemies);

            // 5. update stars
            stars = updateStars(delta, stars);
        }
    }

    /**
     * This methods adds 0-3 enemies, randomly.
     * 
     * @param delta the amount of elapsed time since the last update
     */
    protected void generateEnemies(final float delta)
    {
        final double factor = 1.0 + score / resourceManager.getLevelUpFactor();
        final double rectChance = resourceManager.getRectChance() * factor;
        final double triChance = resourceManager.getTriChance() * factor;
        final double diamChance = resourceManager.getDiamChance() * factor;

        if (random.nextDouble() < rectChance * delta)
        {
            enemies.add(shapeFactory.getRectangleEnemy(random.nextInt(screenWidth), screenHeight));
        }

        if (random.nextDouble() < triChance * delta)
        {
            enemies.add(shapeFactory.getTriangleEnemy(random.nextInt(screenWidth), screenHeight));
        }

        if (random.nextDouble() < diamChance * delta)
        {
            enemies.add(shapeFactory.getDiamondEnemy(random.nextInt(screenWidth), screenHeight));
        }
    }

    /**
     * Updates the shots (position update). Delete shots out of the screen.
     * 
     * @param delta the amount of elapsed time since the last update.
     * @param oldShapes the old shots
     * @return the updated shots
     */
    protected Grid<IMoveable> updateShapes(final float delta, final Iterable<IMoveable> oldShapes)
    {
        final Grid<IMoveable> shapes = new Grid<IMoveable>(screenWidth, screenHeight, 4, 4);

        for (final IMoveable shot : oldShapes)
        {
            if (!shot.isDestroyed() && shot.intersect(screenWidth, screenHeight))
            {
                shapes.add(shot);
                shot.update(delta);
            }
        }

        return shapes;
    }

    /**
     * Update the enemies (position update, generating new shots and delete old destroyed enemies). Delete also enemies
     * out of the screen in y-direction.
     * 
     * @param delta the amount of elapsed time since the last update.
     * @param enemyList old enemies
     * @return the updated enemies
     */
    protected Grid<EnemyShip> updateEnemies(final float delta, final Iterable<EnemyShip> enemyList)
    {
        final Grid<EnemyShip> shapes = new Grid<EnemyShip>(screenWidth, screenHeight, 4, 4);

        for (final EnemyShip enemy : enemyList)
        {
            if (enemy.isDestroyed())
            {
                createBonus(enemy.getX(), enemy.getY());
                score += enemy.getScore();
            }
            else if (enemy.getY() > -enemy.getHeight())
            {
                shapes.add(enemy);
                
                for (IMoveable shot : enemy.shot(delta))
                {
                    enemyShots.add(shot);
                }
                enemy.update(delta);
            }
        }
        return shapes;
    }

    /**
     * Update (resporn stars which are out of the screen) the stars of the game (part of the background).
     * 
     * @param delta the amount of elapsed time since the last update.
     * @param starList old starts
     * @return updated stars
     */
    protected List<IMoveable> updateStars(final float delta, final List<IMoveable> starList)
    {
        for (final IMoveable star : starList)
        {
            final float y = star.getY();

            // reposition star!
            if (y < 0)
            {
                star.setY(screenHeight);
                star.setX(random.nextInt(screenWidth + 1));
            }
            else
            {
                star.update(delta);
            }
        }
        return starList;
    }

    /**
     * Adds randomly 0-1 boni to the simulation.
     * 
     * @param x x-position of the bonus
     * @param y y-position of the bonus
     */
    protected void createBonus(final float x, final float y)
    {
        if (random.nextDouble() < resourceManager.getBonusShotChance())
        {
            if (random.nextBoolean())
            {
                boni.add(shapeFactory.getFastShot(x, y));
            }
            else
            {
                boni.add(shapeFactory.getTripleShot(x, y));
            }
        }
        else if (random.nextDouble() < resourceManager.getBonusLifeChance())
        {
            boni.add(shapeFactory.getLifeUp(x, y));
        }
    }

    @Override
    public Grid<EnemyShip> getAllEnemies()
    {
        return enemies;
    }

    @Override
    public Grid<IMoveable> getEnemyShots()
    {
        return enemyShots;
    }

    @Override
    public Grid<IMoveable> getPlayerShots()
    {
        if (!isBackground())
        {
            return ship.getShots();
        }
        return null;
    }

    @Override
    public List<IMoveable> getStars()
    {
        return stars;
    }

    @Override
    public Grid<IMoveable> getBoni()
    {
        return boni;
    }

    @Override
    public Ship getShip()
    {
        return ship;
    }

    @Override
    public int getScore()
    {
        return score;
    }

    @Override
    public synchronized void initGame()
    {
        ship = shapeFactory.getShip(screenWidth / 2 - ResourceManager.getInstance().getShipWidth() / 2, 0);
        boni = new Grid<IMoveable>(screenWidth, screenHeight, NUMBER_OF_GRID_COLUMNS, NUMBER_OF_GRID_ROWS);
        enemies = new Grid<EnemyShip>(screenWidth, screenHeight, NUMBER_OF_GRID_COLUMNS, NUMBER_OF_GRID_ROWS);
        enemyShots = new Grid<IMoveable>(screenWidth, screenHeight, NUMBER_OF_GRID_COLUMNS, NUMBER_OF_GRID_ROWS);
        playerShots = new Grid<IMoveable>(screenWidth, screenHeight, NUMBER_OF_GRID_COLUMNS, NUMBER_OF_GRID_ROWS); 
        ship.setShots(playerShots);
        score = 0;
        background = false;
        initBackground();
        initialized = true;
    }

    /**
     * initialize the background (generate the stars inside the viewport).
     */
    protected synchronized void initBackground()
    {
        final int numberOfStars = resourceManager.getNumberOfStars();
        if (numberOfStars < 0)
        {
            throw new IllegalArgumentException("number of stars lower than zero!");
        }

        stars.clear();

        for (int i = 0; i < numberOfStars; i++)
        {
            stars.add(shapeFactory.getStar(random.nextInt(screenWidth), random.nextInt(screenHeight)));
        }
    }

    @Override
    public synchronized void initSimulation()
    {
        ship = null;
        enemies = new Grid<EnemyShip>(screenWidth, screenHeight, NUMBER_OF_GRID_COLUMNS, NUMBER_OF_GRID_ROWS);
        enemyShots = new Grid<IMoveable>(screenWidth, screenHeight, NUMBER_OF_GRID_COLUMNS, NUMBER_OF_GRID_ROWS);
        boni = new Grid<IMoveable>(screenWidth, screenHeight, NUMBER_OF_GRID_COLUMNS, NUMBER_OF_GRID_ROWS);
        score = 0;
        background = true;
        initBackground();
        initialized = true;
    }

    @Override
    public Difficulty getDifficulty()
    {
        return difficulty;
    }

    @Override
    public void resetDifficulty()
    {
        difficulty = Difficulty.Hard;
        initBackground();
    }

    @Override
    public void incrementDifficulty()
    {
        difficulty = difficulty.increment();
        initBackground();
    }

    @Override
    public void enableShipShoting()
    {
        ship.setShoting(true);
    }

    @Override
    public void disableShipShoting()
    {
        ship.setShoting(false);
    }

    @Override
    public void setTargetX(final int targetX)
    {
        ship.setTargetX(targetX);
    }

    @Override
    public void setTargetY(final int targetY)
    {
        ship.setTargetY(targetY);
    }

    @Override
    public boolean isSoundOn()
    {
        return soundOn;
    }

    @Override
    public void setSoundOn(boolean soundOn)
    {
        this.soundOn = soundOn;
    }

    @Override
    public synchronized void screenResize(int width, int height)
    {
        screenWidth = width;
        screenHeight = height;
        
        // this is to initialise the simulation after the first resize (before the first game start)
        if (!resized)
        {
            initSimulation();
            resized = true;
            initialized = true;
            background = true;
        }
        
        initBackground();
    }

    @Override
    public boolean isBackground()
    {
        return background;
    }

    @Override
    public void setDifficulty(Difficulty difficulty)
    {
        this.difficulty = difficulty;
    }
}
