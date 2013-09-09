/**
 * A label to classify objects and subjects. eg: (Top Secret, {Crypto, Nuclear})
 * @author lchong
 */
public class SecurityLabel
{
    private SecurityLevel securityLevel;

    public SecurityLabel(SecurityLevel securityLevel)
    {
        this.securityLevel = securityLevel;
    }

    public SecurityLevel getSecurityLevel()
    {
        return securityLevel;
    }    
}
