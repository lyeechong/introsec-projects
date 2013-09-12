/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lchong
 */
public class InstructionCheckerTest
{

    public InstructionCheckerTest()
    {
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
    }

    @Before
    public void setUp()
    {
        ReferenceMonitor rf = new ReferenceMonitor();
        SecurityLevel low = new SecurityLevel("LOW", 0);
        SecurityLevel high = new SecurityLevel("HIGH", 1);
        
        rf.createSecurityLevel(low);
        rf.createSecurityLevel(high);

        rf.createSubject("Lyle", low);
        rf.createSubject("Hal", high);

        rf.createObject("Lobj", low, 0);
        rf.createObject("Hobj", high, 0);
    }

    @After
    public void tearDown()
    {
    }

    /**
     * Test of isInstructionIsLegal method, of class InstructionChecker.
     */
    @Test
    public void testIsInstructionIsLegal()
    {
        // -- NOTE: remember we're just checking purely for SYNTAX errors!
        String[] validInstructions =
        {
            "read Hal LObj",
            "rEAd hal lOBj",
            "read Hal           LObj",
            "read lyle hobj",
            "write    hal    hobj 789",
            "write    hal    hobj 88",
            "write    lyle    hobj -48",
            "read   \t\t\t\t\n lyle  \r\n\r\n  hobj\n\n\n",
            "write\tlyle\thobj\n\t-565",
            "write Lyle LObj 10",
            "read Hal LObj ",
            "write Lyle HObj 20",
            "write Hal LObj 200",
            "read Hal HObj",
            "read Lyle LObj",
            "read Lyle HObj"
        };
        // -- NOTE: remember we're just checking purely for SYNTAX errors!
        String[] invalidInstructions =
        {
            "write Hal HObj",
            "Alas! Poor Yorick! I knew him, Horatio.",
            "Run Hal run. See Hal run.",
            "RUN HAL!! RUN FOR YOUR LIFEEEEE!!!",
            "Pᗣᗧ•••MᗣN",
            "read Hal",
            "iefjiweofjeiwfojow",
            "foo Lyle LObj",
            "Hi Lyle,This is Hal",
            "The missile launch code is 1234567",
            null,
            "write\tlyle\thobj\n\t--565",
            "write    hal    hobj",
            "fj ie fief e ",
            " a                 ",
            "",
            "   ",
            "U+19ᾐᾒᾔᾖᾘᾙᾚᾛᾜᾝᾞᾟ+FxᾠᾡᾢᾣᾤᾥᾦᾧᾨᾩᾪᾫᾬᾭᾮᾯU1BᾶᾷᾸᾹᾺΆᾼ",
            "read lal lobj",
            "read hal oobj",
            "read hal lobj 4",
            "read hal lobj 8347837499-0343-433-2",
            "read hal lobj yayyyy",
            "read hal lobj lyle",
            "read hal lyle",
            "read lyle lyle",
            "read lobj lyle",
            "read lobj lobj",
            "read hobj hobj",
            "red red red",
            "read hobj hal",
            "read hobj hal 45122",
            "write hobj hal 45122",
            "write hal hobj 4**122",
            "write lyle lobj 4+122",
            "write lyle hobj 4+122",
            "write hal lobj",
            "\\write \\Hal \\HObj",
        };
        // -- NOTE: remember we're just checking purely for SYNTAX errors!
        InstructionChecker is = new InstructionChecker();

        for (String instruction : validInstructions)
        {
            boolean legal = is.isInstructionIsLegal(instruction);
            assertTrue(instruction + " failed. It should be a legal instruction.", legal);
        }

        for (String instruction : invalidInstructions)
        {
            boolean legal = is.isInstructionIsLegal(instruction);
            assertFalse(instruction + " was marked legal when it shouldn't be. It should be an illegal instruction.", legal);
        }
    }
}
