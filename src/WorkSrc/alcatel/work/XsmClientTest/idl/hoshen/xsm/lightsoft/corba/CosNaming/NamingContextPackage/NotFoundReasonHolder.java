package hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage;

/**
* hoshen/xsm/lightsoft/corba/CosNaming/NamingContextPackage/NotFoundReasonHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNaming.idl
* 13:10:56 GMT+02:00 ��� ����� 28 ���� 2007
*/

public final class NotFoundReasonHolder implements org.omg.CORBA.portable.Streamable
{
  public hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.NotFoundReason value = null;

  public NotFoundReasonHolder ()
  {
  }

  public NotFoundReasonHolder (hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.NotFoundReason initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.NotFoundReasonHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.NotFoundReasonHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.NotFoundReasonHelper.type ();
  }

}
