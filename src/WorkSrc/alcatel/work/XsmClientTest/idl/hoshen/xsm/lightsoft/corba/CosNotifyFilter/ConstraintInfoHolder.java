package hoshen.xsm.lightsoft.corba.CosNotifyFilter;

/**
* hoshen/xsm/lightsoft/corba/CosNotifyFilter/ConstraintInfoHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNotifyFilter.idl
* 13:11:15 GMT+02:00 ��� ����� 28 ���� 2007
*/


/**
    * Structure used when an ID has been assigned to a constraint expression.
    */
public final class ConstraintInfoHolder implements org.omg.CORBA.portable.Streamable
{
  public hoshen.xsm.lightsoft.corba.CosNotifyFilter.ConstraintInfo value = null;

  public ConstraintInfoHolder ()
  {
  }

  public ConstraintInfoHolder (hoshen.xsm.lightsoft.corba.CosNotifyFilter.ConstraintInfo initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = hoshen.xsm.lightsoft.corba.CosNotifyFilter.ConstraintInfoHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    hoshen.xsm.lightsoft.corba.CosNotifyFilter.ConstraintInfoHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return hoshen.xsm.lightsoft.corba.CosNotifyFilter.ConstraintInfoHelper.type ();
  }

}