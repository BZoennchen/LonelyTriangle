package mobile.app.lonelytriangle.simulation.shapes;

import junit.framework.TestCase;
import mobile.app.lonelytriangle.resource.ResourceManager.Colors;
import mobile.app.lonelytriangle.simulation.IMoveable;

/**
 * Tests the core functionality of the shapes.
 * 
 * @author Benedikt Zönnchen
 * @version v1.0
 *
 */
public class TestShape extends TestCase
{
    /**
     * Test the intersection mechanism of the shapes while moving 2 shapes. Every shape uses the same mechanism so we only need to test
     * the abstract Shape class. if $ is the intersect operator the following is the role:
     * 
     * a $ b <=> b $ a
     */
    public void testIntersection()
    {
        int diamondWidth = 20;
        int diamondHeight = 30;
        int rectSize = 30;
        
        IMoveable rectEnemy = new RectangleEnemy(0, 0, rectSize, Colors.Blue, 10);
        IMoveable diamondEnemy = new DiamondEnemy(0, 0, diamondWidth, diamondHeight, Colors.Blue, 10);
        
        // should always return true!
        assertTrue(rectEnemy.intersect(rectEnemy));
        
        assertTrue(rectEnemy.intersect(diamondEnemy));
        assertTrue(diamondEnemy.intersect(rectEnemy));
        
        diamondEnemy.setX(100);
        assertFalse(rectEnemy.intersect(diamondEnemy));
        assertFalse(diamondEnemy.intersect(rectEnemy));
        
        diamondEnemy.setX(rectSize);
        diamondEnemy.setY(rectSize);
        
        assertFalse(rectEnemy.intersect(diamondEnemy));
        assertFalse(diamondEnemy.intersect(rectEnemy));
        
        rectEnemy.setX(0.00001f);
        assertFalse(rectEnemy.intersect(diamondEnemy));
        assertFalse(diamondEnemy.intersect(rectEnemy));
        rectEnemy.setY(0.00001f);
        
        assertTrue(rectEnemy.intersect(diamondEnemy));
        assertTrue(diamondEnemy.intersect(rectEnemy));
        
        rectEnemy.setX(-0.00001f);
        rectEnemy.setY(-0.00002f);
        
        rectEnemy.setX(rectSize - 0.00002f);
        rectEnemy.setY(rectSize - 0.00003f);
        
        assertTrue(rectEnemy.intersect(diamondEnemy));
        assertTrue(diamondEnemy.intersect(rectEnemy));
    }
    
    
}
