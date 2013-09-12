
/**
 * An class for the security levels. eg: High, Low, Secret, Top Secret,
 * Unclassified
 *
 * @author lchong
 */
public class SecurityLevel implements Comparable<SecurityLevel>
{

    private String name;
    /**
     * Levels are of increasing value.
     * eg: 4 is of higher clearance than 1
     */
    private int level;

    public SecurityLevel(String name, int level)
    {
        this.name = name;
        this.level = level;
    }

    public int getLevel()
    {
        return level;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public int compareTo(SecurityLevel other)
    {
        return Integer.compare(this.getLevel(), other.getLevel());
    }

    public boolean isHigherOrEqualClearanceThan(SecurityLevel other)
    {
        return this.compareTo(other) >= 1;
    }

    public boolean isLowerOrEqualClearanceThan(SecurityLevel other)
    {
        return this.compareTo(other) <= 1;
    }
}
