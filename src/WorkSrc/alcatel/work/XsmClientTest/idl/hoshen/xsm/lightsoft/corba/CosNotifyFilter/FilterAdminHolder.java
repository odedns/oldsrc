package hoshen.xsm.lightsoft.corba.CosNotifyFilter;

/**
* hoshen/xsm/lightsoft/corba/CosNotifyFilter/FilterAdminHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNotifyFilter.idl
* 13:11:16 GMT+02:00 ��� ����� 28 ���� 2007
*/


/**
    * Interface for filter administrators.
    */
public final class FilterAdminHolder implements org.omg.CORBA.portable.Streamable
{
  public hoshen.xsm.lightsoft.corba.CosNotifyFilter.FilterAdmin value = null;

  public FilterAdminHolder ()
  {
  }

  public FilterAdminHolder (hoshen.xsm.lightsoft.corba.CosNotifyFilter.FilterAdmin initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = hoshen.xsm.lightsoft.corba.CosNotifyFilter.FilterAdminHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    hoshen.xsm.lightsoft.corba.CosNotifyFilter.FilterAdminHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return hoshen.xsm.lightsoft.corba.CosNotifyFilter.FilterAdminHelper.type ();
  }

}
