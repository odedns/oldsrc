package hoshen.xsm.lightsoft.corba.CosNotifyFilter;


/**
* hoshen/xsm/lightsoft/corba/CosNotifyFilter/MappingConstraintInfo.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNotifyFilter.idl
* 13:11:16 GMT+02:00 ��� ����� 28 ���� 2007
*/


/**
    * Structure used when an ID has been assigned to a mapping
    * constraint expression.
    */
public final class MappingConstraintInfo implements org.omg.CORBA.portable.IDLEntity
{
  public hoshen.xsm.lightsoft.corba.CosNotifyFilter.ConstraintExp constraint_expression = null;
  public int constraint_id = (int)0;
  public org.omg.CORBA.Any value = null;

  public MappingConstraintInfo ()
  {
  } // ctor

  public MappingConstraintInfo (hoshen.xsm.lightsoft.corba.CosNotifyFilter.ConstraintExp _constraint_expression, int _constraint_id, org.omg.CORBA.Any _value)
  {
    constraint_expression = _constraint_expression;
    constraint_id = _constraint_id;
    value = _value;
  } // Init ctor

} // class MappingConstraintInfo
