package hoshen.xsm.lightsoft.corba.CosTrading;

/**
* hoshen/xsm/lightsoft/corba/CosTrading/InvalidLookupRefHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosTrading.idl
* 13:11:18 GMT+02:00 ��� ����� 28 ���� 2007
*/

public final class InvalidLookupRefHolder implements org.omg.CORBA.portable.Streamable
{
  public hoshen.xsm.lightsoft.corba.CosTrading.InvalidLookupRef value = null;

  public InvalidLookupRefHolder ()
  {
  }

  public InvalidLookupRefHolder (hoshen.xsm.lightsoft.corba.CosTrading.InvalidLookupRef initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = hoshen.xsm.lightsoft.corba.CosTrading.InvalidLookupRefHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    hoshen.xsm.lightsoft.corba.CosTrading.InvalidLookupRefHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return hoshen.xsm.lightsoft.corba.CosTrading.InvalidLookupRefHelper.type ();
  }

}
