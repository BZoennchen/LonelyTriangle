package mobile.app.lonelytriangle.simulation.collision;

import mobile.app.lonelytriangle.simulation.IGameStateModel;
import mobile.app.lonelytriangle.simulation.IMoveable;
import mobile.app.lonelytriangle.simulation.shapes.EnemyShip;
import mobile.app.lonelytriangle.util.Grid;

/**
 * Implements a simple collision detection. It only uses the intersect method of the IMoveable.
 * 
 * @author Benedikt Zönnchen
 * @version v1.0
 * 
 */
public class CollisionDetector
{
    /** the simulation and model object of the game. */
    private final IGameStateModel simulation;
    
    //private static int collisionCount = 0;

    /**
     * Construct a new CollisionDetector object which needs the ISimulation to get the information about the game.
     * 
     * @param simulation the model object of the game
     */
    public CollisionDetector(final IGameStateModel simulation)
    {
        this.simulation = simulation;
    }

    /**
     * Calculate the all the collisions and notify the affected IMoveable.
     */
    public void update()
    {
       // collisionCount = 0;
        final Grid<EnemyShip> enemies = simulation.getAllEnemies();
        final Grid<IMoveable> playerShots = simulation.getPlayerShots();
        final Grid<IMoveable> enemyShots = simulation.getEnemyShots();
        final Grid<IMoveable> boni = simulation.getBoni();

        final IMoveable ship = simulation.getShip();

        // TODO: improve performance this is to expensive O(n^2)!
        // ship shots <-> enemies
        for (final IMoveable shot : playerShots)
        {
            for (final IMoveable enemy : enemies.getElements(shot))
            {
               // collisionCount++;
                if (enemy.intersect(shot))
                {
                    enemy.handleCollision(shot);
                    shot.handleCollision(enemy);
                }
            }
        }
        
        // enemies <-> ship
        for (final IMoveable enemy :  enemies.getElements(ship))
        {
           // collisionCount++;
            if (ship.intersect(enemy))
            {
                ship.handleCollision(enemy);
                enemy.handleCollision(ship);
            }
        }
       
        // enemy shots <-> ship
        for (final IMoveable shot : enemyShots.getElements(ship))
        {
            //collisionCount++;
            if (ship.intersect(shot))
            {
                ship.handleCollision(shot);
                shot.handleCollision(ship);
            }
        }

        // boni <-> ship
        for (final IMoveable bonus :  boni.getElements(ship))
        {
           // collisionCount++;
            if (ship.intersect(bonus))
            {
                ship.handleCollision(bonus);
                bonus.handleCollision(ship);
            }
        }
        
       // System.out.println(collisionCount);
    }
}
