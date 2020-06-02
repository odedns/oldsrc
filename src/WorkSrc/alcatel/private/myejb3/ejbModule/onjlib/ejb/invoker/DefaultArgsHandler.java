
package onjlib.ejb.invoker;


// Referenced classes of package hoshen.ejb.dynproxy:
//            ArgsHandlerIF

public class DefaultArgsHandler
    implements ArgsHandlerIF
{

    public DefaultArgsHandler()
    {
    }

    public Object packArgs(Object args)
    {
        System.out.println("in DefaultArgsHandler.packArgs()");
        return args;
    }

    public Object unpackArgs(Object args)
    {
        System.out.println("in DefaultArgsHandler.unpackArgs()");
        return args;
    }
}
