package hoshen.xsm.lightsoft.corba.CosNotifyFilter;

/**
* hoshen/xsm/lightsoft/corba/CosNotifyFilter/MappingConstraintInfoHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNotifyFilter.idl
* 13:11:16 GMT+02:00 ��� ����� 28 ���� 2007
*/


/**
    * Structure used when an ID has been assigned to a mapping
    * constraint expression.
    */
public final class MappingConstraintInfoHolder implements org.omg.CORBA.portable.Streamable
{
  public hoshen.xsm.lightsoft.corba.CosNotifyFilter.MappingConstraintInfo value = null;

  public MappingConstraintInfoHolder ()
  {
  }

  public MappingConstraintInfoHolder (hoshen.xsm.lightsoft.corba.CosNotifyFilter.MappingConstraintInfo initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = hoshen.xsm.lightsoft.corba.CosNotifyFilter.MappingConstraintInfoHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    hoshen.xsm.lightsoft.corba.CosNotifyFilter.MappingConstraintInfoHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return hoshen.xsm.lightsoft.corba.CosNotifyFilter.MappingConstraintInfoHelper.type ();
  }

}
