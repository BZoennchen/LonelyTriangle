package mobile.app.lonelytriangle.simulation;

import junit.framework.TestCase;

/**
 * TODO: test the whole simulation methods! But we need to mock the ResourceManager or we have to init it!
 * 
 * @author Benedikt Zönnchen
 * @version v1.0
 *
 */
public class TestSimulation extends TestCase
{
    // doesnt work jet!
    public void testStarUpdate()
    {
        final int screenWidth = 1024;
        final int screenHeight = 824;
        final int starCount = 10;
        
        // new background simulation
        Simulation sim = new Simulation();
        sim.screenResize(screenWidth, screenHeight);
        //sim.initNewGame();
        
        // add stars on the screen
        sim.initBackground();
        
        // get one sample star
        IMoveable sampleStar = sim.getStars().get(0);
        float x = sampleStar.getX();
        float y = sampleStar.getY();
        
        System.out.println(y);
        
        sim.update(3000f);
        
        System.out.println(sim.getStars().get(0).getY());
        
        // star should move!
        assertFalse(y == sampleStar.getY());
        
        x = sampleStar.getX();
        y = sampleStar.getY();
        
        sim.update(1231f);
        
        // star should move again
        assertFalse(y == sampleStar.getY());
        
        assertEquals(starCount, sim.getStars().size());
    }
}
