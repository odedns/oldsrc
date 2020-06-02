package hoshen.xsm.lightsoft.corba.CosTrading;

/**
* hoshen/xsm/lightsoft/corba/CosTrading/FollowOptionHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosTrading.idl
* 13:11:18 GMT+02:00 ��� ����� 28 ���� 2007
*/


/** This enum is used to determine the behaviour 
 * of the trader when traders are linked together.
 * <pre>
 * local_only	- Do not use other linked traders.
 * if_no_local	- Use linked traders if a query cannot be resolved locally.
 * always		- Always use linked traders.
 * </pre>
 */
public final class FollowOptionHolder implements org.omg.CORBA.portable.Streamable
{
  public hoshen.xsm.lightsoft.corba.CosTrading.FollowOption value = null;

  public FollowOptionHolder ()
  {
  }

  public FollowOptionHolder (hoshen.xsm.lightsoft.corba.CosTrading.FollowOption initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = hoshen.xsm.lightsoft.corba.CosTrading.FollowOptionHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    hoshen.xsm.lightsoft.corba.CosTrading.FollowOptionHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return hoshen.xsm.lightsoft.corba.CosTrading.FollowOptionHelper.type ();
  }

}