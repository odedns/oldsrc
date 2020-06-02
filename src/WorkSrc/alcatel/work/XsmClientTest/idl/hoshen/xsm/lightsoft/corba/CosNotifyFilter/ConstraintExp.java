package hoshen.xsm.lightsoft.corba.CosNotifyFilter;


/**
* hoshen/xsm/lightsoft/corba/CosNotifyFilter/ConstraintExp.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNotifyFilter.idl
* 13:11:14 GMT+02:00 ��� ����� 28 ���� 2007
*/


/**
    * Definition of a constraint. It is a two-tuple of a sequence of event
    * types and a constraint expression.
    */
public final class ConstraintExp implements org.omg.CORBA.portable.IDLEntity
{
  public hoshen.xsm.lightsoft.corba.CosNotification.EventType event_types[] = null;
  public String constraint_expr = "";

  public ConstraintExp ()
  {
  } // ctor

  public ConstraintExp (hoshen.xsm.lightsoft.corba.CosNotification.EventType[] _event_types, String _constraint_expr)
  {
    event_types = _event_types;
    constraint_expr = _constraint_expr;
  } // Init ctor

} // class ConstraintExp