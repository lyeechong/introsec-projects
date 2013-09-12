
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author lchong
 */
public class SecurityLevelTest
{

    public SecurityLevelTest()
    {
    }

    @BeforeClass
    public static void setUpClass()
    {
    }

    @AfterClass
    public static void tearDownClass()
    {
    }

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

    /**
     * Test of compare method, of class SecurityLevel.
     */
    @Test
    public void testCompareTo()
    {
        System.out.println("compare");
        SecurityLevel slH = new SecurityLevel("high",5);
        SecurityLevel slL = new SecurityLevel("blah",0);
        assert (slH.compareTo(slL) > 0);
    }
}