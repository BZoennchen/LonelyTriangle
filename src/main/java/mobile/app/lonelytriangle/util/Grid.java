package mobile.app.lonelytriangle.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * A Grid is a collection of IGridables. A Grid represents a area with a sprecified width and height. The grid seperate this area to different cells of a table with a specified number of rows and number of columns.
 * If u add an IGridable to a grid it will be part of all the cells which it intersect. The grid can return all IGridables of all cells of another IGridable. This is very useful for improve the collision detection
 * in the game. Becareful its forbidden to change the IGridables after adding them to a grid. This will result in an inconsistent grid!!! Generally the cell with and cell height of a gird should be greater
 * than its IGridable width and height. If this is the case, a IGridable can intersect 4 cells, otherwise more than 4!
 * 
 * @author Benedikt Zönnchen
 * @version v1.0
 * @param <T>-Tag the grid is for Objects which are IGridable
 * 
 */
public class Grid<T extends IGridable> implements Iterable<T>
{
    /** the numberOfCells 2^n (n is a natural number bigger than zero). */
    private final int      numberOfCells;

    private final int      numberOfColumns;
    private final int      numberOfRows;
  
    private final float    cellWidth;
    private final float    cellHeight;
    

    /** the grid. */
    private final List<T>[][] grid;
    
    private final List<T> elements;

    /**
     * Construct a new Grid collection. There is no support to remove elements.
     * 
     * @param width the width of the grid (viewport width)
     * @param height the height of the grid (viewport height)
     * @param numberOfRows the number of rows
     * @param numberOfColumns the number of colums
     */
    @SuppressWarnings("unchecked")
    public Grid(final float width, final float height, final int numberOfRows, final int numberOfColumns)
    {
        if (numberOfRows < 1 || numberOfColumns < 1)
        {
            throw new IllegalArgumentException("number of rows and number of colums in grid have to be bigger then zero");
        }
        
        numberOfCells = numberOfRows * numberOfColumns;
        this.numberOfColumns = numberOfColumns;
        this.numberOfRows = numberOfRows;
        cellWidth = width / numberOfRows;
        cellHeight = height / numberOfColumns;   
        elements = new ArrayList<T>();

        // construct grid
        grid = new List[numberOfColumns][numberOfRows];
        
        
        for (int i = 0; i < numberOfColumns; i++)
        {
            for (int j = 0; j < numberOfRows; j++)
            {
                grid[i][j] = new ArrayList<T>();
            }
        }
    }

    /**
     * Add an element to one or more cells (max 4).
     * 
     * @param element the gridable element
     */
    public void add(final T element)
    {       
        int minColNumber = getColumnNumber(element.getX());
        int minRowNumber = getRowNumber(element.getY());
        
        int maxColNumber = getColumnNumber(element.getX() + element.getWidth());
        int maxRowNumber = getRowNumber(element.getY() + element.getHeight());
        
        for (int col = minColNumber; col <= maxColNumber; col++)
        {
            for (int row = minRowNumber; row <= maxRowNumber; row++)
            {
                grid[col][row].add(element);
            }
        }
        elements.add(element);
    }

    /**
     * Convert x-coordinate to a column number.
     * 
     * @param x x-coordinate
     * @return column number for this x value
     */
    protected int getColumnNumber(final float x)
    {
        if (x < 0)
        {
            return 0;
        }
        return Math.min((int)(x / cellWidth), numberOfColumns - 1);
    }

    /**
     * Convert y-coordinate to a row number.
     * 
     * @param y y-coordinate
     * @return row number for this y value
     */
    protected int getRowNumber(final float y)
    {
        if (y < 0)
        {
            return 0;
        }
        return Math.min((int)(y / cellHeight), numberOfRows - 1);
    }

    /**
     * Returns one or more cells (max 4) which contains elements.
     * 
     * @param element the element which identify the correct cells of the grid
     * @return one or more cells (max 4) which contains elements
     */
    public Iterable<T> getElements(IGridable element)
    {
        // TODO: find a better solution than addAll because it is too expensive!
        Set<T> setOfElements = new LinkedHashSet<T>();
        int minColNumber = getColumnNumber(element.getX());
        int minRowNumber = getRowNumber(element.getY());
        
        int maxColNumber = getColumnNumber(element.getX() + element.getWidth());
        int maxRowNumber = getRowNumber(element.getY() + element.getHeight());
        
        for (int col = minColNumber; col <= maxColNumber; col++)
        {
            for (int row = minRowNumber; row <= maxRowNumber; row++)
            {
                setOfElements.addAll(grid[col][row]);
            }
        }
        
        return setOfElements;
    }

    /**
     * Return the number of columns.
     * 
     * @return the number of columns
     */
    public int getNumberOfColumns()
    {
        return numberOfColumns;
    }

    /**
     * Return the number of rows.
     * 
     * @return the number of rows
     */
    public int getNumberOfRows()
    {
        return numberOfRows;
    }

    /**
     * Returns the number of cells of this grid.
     * 
     * @return the numberOfCells
     */
    public int getNumberOfCells()
    {
        return numberOfCells;
    }

    @Override
    public Iterator<T> iterator()
    {
        return elements.iterator();
    }

}
