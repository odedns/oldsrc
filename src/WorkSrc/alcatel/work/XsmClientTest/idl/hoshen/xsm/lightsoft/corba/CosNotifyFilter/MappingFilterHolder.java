package hoshen.xsm.lightsoft.corba.CosNotifyFilter;

/**
* hoshen/xsm/lightsoft/corba/CosNotifyFilter/MappingFilterHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNotifyFilter.idl
* 13:11:16 GMT+02:00 ��� ����� 28 ���� 2007
*/


/**
    * Interface for a mapping filter.
    */
public final class MappingFilterHolder implements org.omg.CORBA.portable.Streamable
{
  public hoshen.xsm.lightsoft.corba.CosNotifyFilter.MappingFilter value = null;

  public MappingFilterHolder ()
  {
  }

  public MappingFilterHolder (hoshen.xsm.lightsoft.corba.CosNotifyFilter.MappingFilter initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = hoshen.xsm.lightsoft.corba.CosNotifyFilter.MappingFilterHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    hoshen.xsm.lightsoft.corba.CosNotifyFilter.MappingFilterHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return hoshen.xsm.lightsoft.corba.CosNotifyFilter.MappingFilterHelper.type ();
  }

}
