package hoshen.xsm.lightsoft.corba.CosTrading;

/**
* hoshen/xsm/lightsoft/corba/CosTrading/TraderComponentsHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosTrading.idl
* 13:11:18 GMT+02:00 ��� ����� 28 ���� 2007
*/


/** This interface has references to the componet services
 * that make up a trader. Not all services may be present,
 * depending on the type of trader.
 */
public final class TraderComponentsHolder implements org.omg.CORBA.portable.Streamable
{
  public hoshen.xsm.lightsoft.corba.CosTrading.TraderComponents value = null;

  public TraderComponentsHolder ()
  {
  }

  public TraderComponentsHolder (hoshen.xsm.lightsoft.corba.CosTrading.TraderComponents initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = hoshen.xsm.lightsoft.corba.CosTrading.TraderComponentsHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    hoshen.xsm.lightsoft.corba.CosTrading.TraderComponentsHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return hoshen.xsm.lightsoft.corba.CosTrading.TraderComponentsHelper.type ();
  }

}
