
import java.util.Comparator;

/**
 * An enum for the security levels. eg: High, Low, Secret, Top Secret,
 * Unclassified
 *
 * @author shen
 */
public enum SecurityLevel
{

    // -- NOTE: keep these values in sorted order from low to high (so Java's Enum compareTo can work correctly).
    LOW(),
    HIGH();
    private String name;

    private SecurityLevel()
    {
    }
    
    // -- use name() to get the name of the enum
}
