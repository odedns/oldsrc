package hoshen.xsm.lightsoft.corba.equipment;


/**
* hoshen/xsm/lightsoft/corba/equipment/EquipmentHolder_T.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/equipment.idl
* 13:11:34 GMT+02:00 ��� ����� 28 ���� 2007
*/


/**
   * <p>Represents the physical resource of a network element that is 
   * capable of holding other physical resources. Examples of resources 
   * are equipment racks, shelves, or slots.</p>
   * An equipment holder object may contain 
   * a number of instances of other equipment holder objects
   * (for instance representing 
   * slots within a shelf, or shelves within a rack), 
   * and/or a single equipment object.
   *
   * globaldefs::NamingAttributes_T <b>name</b>:
   * <dir>An equipment holder is identified by a unique name.
   * The EMS is responsible for guaranteeing the uniqueness of the name
   * within the context of the ManagedElement.
   * The naming for equipment is deterministic, see
   * <a href=supportingDocumentation/objectNaming.html>Object Naming</a>.
   * </dir>
   *
   * string <b>userLabel</b>:
   * <dir>The userLabel is provisionable by the NMS. This attribute can be set 
   * by the NMS through the Common_I interface service
   * <a href=_common.Common_I.html#common::Common_I::setUserLabel>setUserLabel</a>. 
   * It is a read/write attribute.</dir>
   *
   * string <b>nativeEMSName</b>:
   * <dir> Represents how the equipment holder is referred to on EMS/NE displays. Its
   * aim is to provide a "nomenclature bridge" to aid relating information
   * presented on NMS displays to EMS/NE displays (via GUI cut through).
   * May be a NULL string.</dir>
   *
   * string <b>owner</b>:
   * <dir>owner may be specified by the NMS. May be empty.</dir>
   *
   * boolean <b>alarmReportingIndicator</b>:
   * <dir> Provides an indication whether alarm reporting for this instance 
   * is active or not. It is a read/write attribute.</dir>
   *
   * EquipmentHolderType_T <b>holderType</b>:
   * <dir>Indicates the type of equipment holder.</dir>
   *
   * NamingAttributes_T <b>expectedOrInstalledEquipment</b>: 
   * <dir>The equipment object expected or installed in the equipment holder, if any.
   * NULL if the equipment holder is empty or if it only contains other equipment holders.
   * </dir>
   *
   * EquipmentObjectTypeList_T <b>acceptableEquipmentTypeList</b>: 
   * <dir>Represents the types of equipment objects that can be supported 
   * directly by the equipment holder.  This is an empty list if the equipment holder
   * can only contain other equipment holders.</dir>
   *
   * HolderState_T <b>holderState</b>: 
   * <dir>Represents the state of the equipment holder.</dir>
   *
   * globaldefs::NVSList_T <b>additionalInfo</b>:
   * <dir>This attribute allows the communication from the EMS to the NMS of additional 
   * information which is not explicitly modelled.
   * It is a readonly attribute.</dir>
   **/
public final class EquipmentHolder_T implements org.omg.CORBA.portable.IDLEntity
{
  public hoshen.xsm.lightsoft.corba.globaldefs.NameAndStringValue_T name[] = null;
  public String userLabel = "";
  public String nativeEMSName = "";
  public String owner = "";
  public boolean alarmReportingIndicator = false;
  public String holderType = "";
  public hoshen.xsm.lightsoft.corba.globaldefs.NameAndStringValue_T expectedOrInstalledEquipment[] = null;
  public String acceptableEquipmentTypeList[] = null;
  public hoshen.xsm.lightsoft.corba.equipment.HolderState_T holderState = null;
  public hoshen.xsm.lightsoft.corba.globaldefs.NameAndStringValue_T additionalInfo[] = null;

  public EquipmentHolder_T ()
  {
  } // ctor

  public EquipmentHolder_T (hoshen.xsm.lightsoft.corba.globaldefs.NameAndStringValue_T[] _name, String _userLabel, String _nativeEMSName, String _owner, boolean _alarmReportingIndicator, String _holderType, hoshen.xsm.lightsoft.corba.globaldefs.NameAndStringValue_T[] _expectedOrInstalledEquipment, String[] _acceptableEquipmentTypeList, hoshen.xsm.lightsoft.corba.equipment.HolderState_T _holderState, hoshen.xsm.lightsoft.corba.globaldefs.NameAndStringValue_T[] _additionalInfo)
  {
    name = _name;
    userLabel = _userLabel;
    nativeEMSName = _nativeEMSName;
    owner = _owner;
    alarmReportingIndicator = _alarmReportingIndicator;
    holderType = _holderType;
    expectedOrInstalledEquipment = _expectedOrInstalledEquipment;
    acceptableEquipmentTypeList = _acceptableEquipmentTypeList;
    holderState = _holderState;
    additionalInfo = _additionalInfo;
  } // Init ctor

} // class EquipmentHolder_T
