package hoshen.xsm.lightsoft.corba.CosTrading.LookupPackage;


/**
* hoshen/xsm/lightsoft/corba/CosTrading/LookupPackage/InvalidPolicyValue.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosTrading.idl
* 13:11:19 GMT+02:00 ��� ����� 28 ���� 2007
*/

public final class InvalidPolicyValue extends org.omg.CORBA.UserException implements org.omg.CORBA.portable.IDLEntity
{
  public hoshen.xsm.lightsoft.corba.CosTrading.Policy the_policy = null;

  public InvalidPolicyValue ()
  {
    super(InvalidPolicyValueHelper.id());
  } // ctor

  public InvalidPolicyValue (hoshen.xsm.lightsoft.corba.CosTrading.Policy _the_policy)
  {
    super(InvalidPolicyValueHelper.id());
    the_policy = _the_policy;
  } // Init ctor

  public InvalidPolicyValue (String $reason, hoshen.xsm.lightsoft.corba.CosTrading.Policy _the_policy)
  {
    super(InvalidPolicyValueHelper.id() + "  " + $reason);
    the_policy = _the_policy;
  } // ctor

} // class InvalidPolicyValue