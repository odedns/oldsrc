package hoshen.xsm.lightsoft.corba.CosTrading;

/**
* hoshen/xsm/lightsoft/corba/CosTrading/PropertyTypeMismatchHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosTrading.idl
* 13:11:18 GMT+02:00 ��� ����� 28 ���� 2007
*/

public final class PropertyTypeMismatchHolder implements org.omg.CORBA.portable.Streamable
{
  public hoshen.xsm.lightsoft.corba.CosTrading.PropertyTypeMismatch value = null;

  public PropertyTypeMismatchHolder ()
  {
  }

  public PropertyTypeMismatchHolder (hoshen.xsm.lightsoft.corba.CosTrading.PropertyTypeMismatch initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = hoshen.xsm.lightsoft.corba.CosTrading.PropertyTypeMismatchHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    hoshen.xsm.lightsoft.corba.CosTrading.PropertyTypeMismatchHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return hoshen.xsm.lightsoft.corba.CosTrading.PropertyTypeMismatchHelper.type ();
  }

}
