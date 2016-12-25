package mobile.app.lonelytriangle.simulation;

import java.util.List;

import mobile.app.lonelytriangle.simulation.shapes.EnemyShip;
import mobile.app.lonelytriangle.util.Grid;

/**
 * The IGameStateModel offers only methods to get informations of the game model. There is no
 * possibility to change the model object (accept manipulating the grids, this should be avoided). 
 * This is useful to seperate different access rights.
 * 
 * @author Benedikt Zönnchen
 * @version v1.0
 *
 */
public interface IGameStateModel
{
    /**
     * return all visible Player shots.
     * 
     * @return all visible Player shots
     */
    Grid<IMoveable> getPlayerShots();

    /**
     * return all visible Enemy shots.
     * 
     * @return all visible enemy shots
     */
    Grid<IMoveable> getEnemyShots();

    /**
     * return all visible enemies.
     * 
     * @return all visible enemies
     */
    Grid<EnemyShip> getAllEnemies();

    /**
     * return all stars.
     * 
     * @return all stars
     */
    List<IMoveable> getStars();

    /**
     * return the player ship.
     * 
     * @return the player ship
     */
    IMoveable getShip();

    /**
     * return the boni.
     * 
     * @return the boni
     */
    Grid<IMoveable> getBoni();

    /**
     * return the score value of the user.
     * 
     * @return the score value of the user
     */
    int getScore();

}
