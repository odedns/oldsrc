package hoshen.xsm.lightsoft.corba.CosEventChannelAdmin;

/**
* hoshen/xsm/lightsoft/corba/CosEventChannelAdmin/TypeErrorHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosEventChannelAdmin.idl
* 11:11:10 IST ��� ����� 6 ������ 2005
*/

public final class TypeErrorHolder implements org.omg.CORBA.portable.Streamable
{
  public hoshen.xsm.lightsoft.corba.CosEventChannelAdmin.TypeError value = null;

  public TypeErrorHolder ()
  {
  }

  public TypeErrorHolder (hoshen.xsm.lightsoft.corba.CosEventChannelAdmin.TypeError initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = hoshen.xsm.lightsoft.corba.CosEventChannelAdmin.TypeErrorHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    hoshen.xsm.lightsoft.corba.CosEventChannelAdmin.TypeErrorHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return hoshen.xsm.lightsoft.corba.CosEventChannelAdmin.TypeErrorHelper.type ();
  }

}