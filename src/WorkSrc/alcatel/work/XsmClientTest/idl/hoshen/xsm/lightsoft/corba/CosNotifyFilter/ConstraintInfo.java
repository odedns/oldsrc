package hoshen.xsm.lightsoft.corba.CosNotifyFilter;


/**
* hoshen/xsm/lightsoft/corba/CosNotifyFilter/ConstraintInfo.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNotifyFilter.idl
* 13:11:15 GMT+02:00 ��� ����� 28 ���� 2007
*/


/**
    * Structure used when an ID has been assigned to a constraint expression.
    */
public final class ConstraintInfo implements org.omg.CORBA.portable.IDLEntity
{
  public hoshen.xsm.lightsoft.corba.CosNotifyFilter.ConstraintExp constraint_expression = null;
  public int constraint_id = (int)0;

  public ConstraintInfo ()
  {
  } // ctor

  public ConstraintInfo (hoshen.xsm.lightsoft.corba.CosNotifyFilter.ConstraintExp _constraint_expression, int _constraint_id)
  {
    constraint_expression = _constraint_expression;
    constraint_id = _constraint_id;
  } // Init ctor

} // class ConstraintInfo
