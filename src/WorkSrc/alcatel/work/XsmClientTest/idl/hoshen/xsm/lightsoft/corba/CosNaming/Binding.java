package hoshen.xsm.lightsoft.corba.CosNaming;


/**
* hoshen/xsm/lightsoft/corba/CosNaming/Binding.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNaming.idl
* 13:10:56 GMT+02:00 ��� ����� 28 ���� 2007
*/

public final class Binding implements org.omg.CORBA.portable.IDLEntity
{
  public hoshen.xsm.lightsoft.corba.CosNaming.NameComponent binding_name[] = null;
  public hoshen.xsm.lightsoft.corba.CosNaming.BindingType binding_type = null;

  public Binding ()
  {
  } // ctor

  public Binding (hoshen.xsm.lightsoft.corba.CosNaming.NameComponent[] _binding_name, hoshen.xsm.lightsoft.corba.CosNaming.BindingType _binding_type)
  {
    binding_name = _binding_name;
    binding_type = _binding_type;
  } // Init ctor

} // class Binding