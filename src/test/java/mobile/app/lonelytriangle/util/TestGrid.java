package mobile.app.lonelytriangle.util;

import junit.framework.TestCase;

/**
 * Test grid. not completed jet! TODO!
 * 
 * @author Benedikt Zönnchen
 * @version v1.0
 *
 */
public class TestGrid extends TestCase
{
    public void testGridInitialisation()
    {
        Grid grid = new Grid(300, 400, 4, 4);
        assertEquals("result: " + grid.getNumberOfCells(), grid.getNumberOfCells(), 16);
        assertEquals(grid.getNumberOfCells(), 16);
        assertEquals(grid.getNumberOfColumns(), 4);
        assertEquals(grid.getNumberOfRows(), 4);
    }
}
