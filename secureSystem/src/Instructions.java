
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author lchong
 */
public enum Instructions
{

    WRITE("write"),
    READ("read"),
    CREATE("create"),
    DESTROY("destroy"),
    RUN("run");
    private String name;

    private Instructions(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
