package hoshen.xsm.lightsoft.corba.CosTrading.ProxyPackage;

/**
* hoshen/xsm/lightsoft/corba/CosTrading/ProxyPackage/IllegalRecipeHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosTrading.idl
* 13:11:21 GMT+02:00 ��� ����� 28 ���� 2007
*/

public final class IllegalRecipeHolder implements org.omg.CORBA.portable.Streamable
{
  public hoshen.xsm.lightsoft.corba.CosTrading.ProxyPackage.IllegalRecipe value = null;

  public IllegalRecipeHolder ()
  {
  }

  public IllegalRecipeHolder (hoshen.xsm.lightsoft.corba.CosTrading.ProxyPackage.IllegalRecipe initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = hoshen.xsm.lightsoft.corba.CosTrading.ProxyPackage.IllegalRecipeHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    hoshen.xsm.lightsoft.corba.CosTrading.ProxyPackage.IllegalRecipeHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return hoshen.xsm.lightsoft.corba.CosTrading.ProxyPackage.IllegalRecipeHelper.type ();
  }

}