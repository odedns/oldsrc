package hoshen.xsm.lightsoft.corba.CosTrading.LinkPackage;

/**
* hoshen/xsm/lightsoft/corba/CosTrading/LinkPackage/DefaultFollowTooPermissiveHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosTrading.idl
* 13:11:20 GMT+02:00 ��� ����� 28 ���� 2007
*/

public final class DefaultFollowTooPermissiveHolder implements org.omg.CORBA.portable.Streamable
{
  public hoshen.xsm.lightsoft.corba.CosTrading.LinkPackage.DefaultFollowTooPermissive value = null;

  public DefaultFollowTooPermissiveHolder ()
  {
  }

  public DefaultFollowTooPermissiveHolder (hoshen.xsm.lightsoft.corba.CosTrading.LinkPackage.DefaultFollowTooPermissive initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = hoshen.xsm.lightsoft.corba.CosTrading.LinkPackage.DefaultFollowTooPermissiveHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    hoshen.xsm.lightsoft.corba.CosTrading.LinkPackage.DefaultFollowTooPermissiveHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return hoshen.xsm.lightsoft.corba.CosTrading.LinkPackage.DefaultFollowTooPermissiveHelper.type ();
  }

}
