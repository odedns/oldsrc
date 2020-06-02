package hoshen.xsm.lightsoft.corba.CosTrading;


/**
* hoshen/xsm/lightsoft/corba/CosTrading/OfferIdIteratorHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosTrading.idl
* 13:11:18 GMT+02:00 ��� ����� 28 ���� 2007
*/


/** This interface supports operations to iterate through
 * lists of OfferIds returned by the CosTrading::Admin
 * interface.
 */
abstract public class OfferIdIteratorHelper
{
  private static String  _id = "IDL:omg.org/CosTrading/OfferIdIterator:1.0";

  public static void insert (org.omg.CORBA.Any a, hoshen.xsm.lightsoft.corba.CosTrading.OfferIdIterator that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static hoshen.xsm.lightsoft.corba.CosTrading.OfferIdIterator extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (hoshen.xsm.lightsoft.corba.CosTrading.OfferIdIteratorHelper.id (), "OfferIdIterator");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static hoshen.xsm.lightsoft.corba.CosTrading.OfferIdIterator read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_OfferIdIteratorStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, hoshen.xsm.lightsoft.corba.CosTrading.OfferIdIterator value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static hoshen.xsm.lightsoft.corba.CosTrading.OfferIdIterator narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof hoshen.xsm.lightsoft.corba.CosTrading.OfferIdIterator)
      return (hoshen.xsm.lightsoft.corba.CosTrading.OfferIdIterator)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      return new hoshen.xsm.lightsoft.corba.CosTrading._OfferIdIteratorStub (delegate);
    }
  }

  public static hoshen.xsm.lightsoft.corba.CosTrading.OfferIdIterator unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof hoshen.xsm.lightsoft.corba.CosTrading.OfferIdIterator)
      return (hoshen.xsm.lightsoft.corba.CosTrading.OfferIdIterator)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      return new hoshen.xsm.lightsoft.corba.CosTrading._OfferIdIteratorStub (delegate);
    }
  }

}