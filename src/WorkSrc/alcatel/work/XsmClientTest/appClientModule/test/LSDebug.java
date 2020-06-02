/* Created on 07/09/2006 */
package test;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;


import hoshen.common.utils.ToStringBuilder;
import test.LSUtils;
import hoshen.xsm.lightsoft.corba.CosNotification.*;
import hoshen.xsm.lightsoft.corba.CosNotification.EventHeader;
import hoshen.xsm.lightsoft.corba.CosNotification.FixedEventHeader;
import hoshen.xsm.lightsoft.corba.CosNotification.StructuredEvent;
import hoshen.xsm.lightsoft.corba.CosTrading.Proxy;
import hoshen.xsm.lightsoft.corba.equipment.EquipmentHolder_T;
import hoshen.xsm.lightsoft.corba.equipment.Equipment_T;
import hoshen.xsm.lightsoft.corba.globaldefs.*;
import hoshen.xsm.lightsoft.corba.globaldefs.NameAndStringValue_T;
import hoshen.xsm.lightsoft.corba.globaldefs.NamingAttributesIterator_IHolder;
import hoshen.xsm.lightsoft.corba.globaldefs.NamingAttributes_THelper;
import hoshen.xsm.lightsoft.corba.managedElement.ManagedElement_T;
import hoshen.xsm.lightsoft.corba.subnetworkConnection.CrossConnect_T;
import hoshen.xsm.lightsoft.corba.subnetworkConnection.SubnetworkConnection_T;
import hoshen.xsm.lightsoft.corba.terminationPoint.TerminationPoint_T;
import hoshen.xsm.lightsoft.corba.topologicalLink.TopologicalLink_T;
import hoshen.xsm.lightsoft.corba.transmissionParameters.LayeredParameters_T;

/**
 * 
 * @author odedn
 */
public class LSDebug {
	
	
	/**
	 * print EquipmentHolder.
	 * @param holder
	 */
	public static void printEquipmentHolder(EquipmentHolder_T holder)
	{
		System.out.println("EquipmentHolder_T Object: ");
		System.out.println("===========================");
		System.out.println("name=\n" + LSUtils.printNsvString(holder.name));
		System.out.println("userLabel=" + holder.userLabel);
		System.out.println("nativeEMSName=" + holder.nativeEMSName);
		System.out.println("owner=" + holder.owner);
		System.out.println("alarmReportingIndicator=" + holder.alarmReportingIndicator);
		System.out.println("holderType="+ holder.holderType);
		System.out.println("expectedOrInstalledEquipment=\n" + LSUtils.printNsvString(holder.expectedOrInstalledEquipment));
		
		for(int i=0; i < holder.acceptableEquipmentTypeList.length; ++i) {
			System.out.println("acceptableEquipmentTypeList["+i+"]=" + holder.acceptableEquipmentTypeList[i]);			
		}
		System.out.println("holderState=" + holder.holderState.value());
		System.out.println("additionalInfo=" + LSUtils.printNsvString(holder.additionalInfo));
		System.out.println("===========================");
	}

	/**
	 * print EquipmentHolder.
	 * @param holder
	 */
	public static void printEquipment(Equipment_T eq)
	{
		System.out.println("Equipment_T Object: ");
		System.out.println("===========================");
		System.out.println("name=\n" + LSUtils.printNsvString(eq.name));
		System.out.println("userLabel=" + eq.userLabel);
		System.out.println("nativeEMSName=" + eq.nativeEMSName);
		System.out.println("owner=" + eq.owner);
		System.out.println("alarmReportingIndicator=" + eq.alarmReportingIndicator);
		System.out.println("expectedEquipmentObjectType=" + eq.expectedEquipmentObjectType);
		System.out.println("installedEquipmentObjectType=" + eq.installedEquipmentObjectType);
		System.out.println("installedPartNumber=" + eq.installedPartNumber);
		System.out.println("installedVersion=" + eq.installedVersion);
		System.out.println("installedSerialNumber=" + eq.installedSerialNumber);
		System.out.println("additionalInfo=" + LSUtils.printNsvString(eq.additionalInfo));
		System.out.println("===========================");
	}
	
	public static void printPTP(TerminationPoint_T ptp)
	{
		System.out.println("TerminationPoint_T Object: ");
		System.out.println("===========================");
		System.out.println("name=\n" + LSUtils.printNsvString(ptp.name));
		System.out.println("userLabel=" + ptp.userLabel);
		System.out.println("nativeEMSName=" + ptp.nativeEMSName);
		System.out.println("owner=" + ptp.owner);
		System.out.println("ingressTrafficDescriptorName=\n" + LSUtils.printNsvString(ptp.ingressTrafficDescriptorName));
		System.out.println("egressTrafficDescriptorName=\n" + LSUtils.printNsvString(ptp.egressTrafficDescriptorName));
		System.out.println("type=" + ptp.type.value());
		System.out.println("terminationMode=" + ptp.tpMappingMode.value());
		System.out.println("directionality=" + ptp.direction.value());
		for(int i=0; i < ptp.transmissionParams.length; ++i) {
			printLayeredParameters(ptp.transmissionParams[i]);
		}
		System.out.println("connectionState=" + ptp.connectionState.value());
		System.out.println("additionalInfo=" + LSUtils.printNsvString(ptp.additionalInfo));
		System.out.println("===========================");
		
	}

	public static void printLayeredParameters(LayeredParameters_T lp)
	{
		System.out.println("LayeredParameters_T Object: ");
		System.out.println("===========================");
		System.out.println("transmissionParams=\n" + LSUtils.printNsvString(lp.transmissionParams));
		System.out.println("layer=" + lp.layer);
		System.out.println("===========================");
	}
	
	public static void printManagedElement(ManagedElement_T me)
	{
		System.out.println("ManagedElement_T Object: ");
		System.out.println("===========================");
		System.out.println("name=\n" + LSUtils.printNsvString(me.name));
		System.out.println("userLabel=" + me.userLabel);
		System.out.println("nativeEMSName=" + me.nativeEMSName);
		System.out.println("owner=" + me.owner);
		System.out.println("Product Name=" + me.productName);
		System.out.println("versionr=" + me.version);
		System.out.println("additionalInfo=" + LSUtils.printNsvString(me.additionalInfo));
		System.out.println("===========================");
		
	}
	
	public static void printSNC(SubnetworkConnection_T snc)
	{
		System.out.println("SNC Object: ");
		System.out.println("===========================");
		System.out.println("name=\n" + LSUtils.printNsvString(snc.name));
		System.out.println("userLabel=" + snc.userLabel);
		System.out.println("nativeEMSName=" + snc.nativeEMSName);
		System.out.println("owner=" + snc.owner);
		System.out.println("aEnd=" +LSUtils.printNsvString(snc.aEnd[0].tpName));
		System.out.println("zEnd=" +LSUtils.printNsvString(snc.zEnd[0].tpName));
		System.out.println("===========================");
		
	}
	
	public static void printTlink(TopologicalLink_T tlink)
	{
		System.out.println("Tlink Object: ");
		System.out.println("===========================");
		System.out.println("name=\n" + LSUtils.printNsvString(tlink.name));
		System.out.println("userLabel=" + tlink.userLabel);
		System.out.println("nativeEMSName=" + tlink.nativeEMSName);
		System.out.println("owner=" + tlink.owner);
		System.out.println("aEnd=" +LSUtils.printNsvString(tlink.aEndTP));
		System.out.println("zEnd=" +LSUtils.printNsvString(tlink.zEndTP));
		System.out.println("===========================");
		
	}
	public static void printEvent(StructuredEvent event)
	{
		org.omg.CORBA.Object o  = null;
		System.out.println("StructuredEvent:");
		System.out.println("===========================");
		EventHeader header = event.header;
		FixedEventHeader fixedHeader = header.fixed_header;
		System.out.println("Event name = " +fixedHeader.event_name);
		System.out.println("event type.domain name = " + fixedHeader.event_type.domain_name);
		System.out.println("eventType. type name  = " + fixedHeader.event_type.type_name);
		// System.out.println("event ulong = " + event.remainder_of_body.extract_ulong());
		Property p[]  = event.filterable_data;
		System.out.println("Event Property: ");
		for(int i=0; i < p.length; ++i) {
			String name = p[i].name;
			if("notificationId".equals(name)) {
				System.out.println("name=" + p[i].name + "\tvalue=" + p[i].value.extract_string());
				continue;
			}
			if("objectType".equals(name)) {
				int n = 0;
				try {
					n  = p[i].value.extract_long();
				} catch(Exception e) {
					e.printStackTrace();
				}
				System.out.println("name=" +p[i].name + "\tvalue="  + n);
				continue;
			}
			if("objectName".equals(name)) {
				try {
					o = p[i].value.extract_Object();
				} catch(Exception e) {
					e.printStackTrace();
				}
				NameAndStringValue_T v[] = NamingAttributes_THelper.extract(p[i].value);
				System.out.println("name=" +p[i].name + "\tvalue=" +  LSUtils.printNsvString(v));
				continue;
			}
			
			if("emsTime".equals(name)) {
				try {
					o = p[i].value.extract_Object();
				} catch(Exception e) {
					e.printStackTrace();
				}
				String s = Time_THelper.extract(p[i].value);
				System.out.println("name=" +p[i].name + "\tvalue=" +  s);
				continue;
				
			}
			if("nativeEMSName".equals(name)) {
				System.out.println("name=" + p[i].name + "\tvalue=" + p[i].value.extract_string());
				continue;
			}
			if("probableCause".equals(name)) {
				System.out.println("name=" + p[i].name + "\tvalue=" + p[i].value.extract_string());
				continue;
			}
			if("nativeProbableCause".equals(name)) {
				System.out.println("name=" + p[i].name + "\tvalue=" + p[i].value.extract_string());
				continue;
			}
			if("additionalText".equals(name)) {
				System.out.println("name=" + p[i].name + "\tvalue=" + p[i].value.extract_string());
				continue;
			}
			
			if("affectedTPList".equals(name)){
				NameAndStringValue_T v[][] =NamingAttributesList_THelper.extract(p[i].value);
				for(int j=0; j < v.length; ++j) {
					NameAndStringValue_T nsv[] = v[j];
					System.out.println("name=" +p[i].name + "\tvalue=" +  LSUtils.printNsvString(nsv));
				}
			}
			if("additionalInfo".equals(name)){
				NameAndStringValue_T v[]= NVSList_THelper.extract(p[i].value);
				System.out.println("name=" +p[i].name + "\tvalue=" +  LSUtils.printNsvString(v));
			}
			
			System.out.println("name=" + p[i].name + "\tvalue=" + p[i].value.toString());
		}
		
		
	}
	
	
	public static void printCrossConnect(CrossConnect_T cc)
	{
		System.out.println("CrossConnect:");
		System.out.println("===========================");
		System.out.println("additional info =" + LSUtils.printNsvString(cc.additionalInfo));
		System.out.println("direction = " + cc.direction.value());
		System.out.println("SNCType = " + cc.ccType.value());
		System.out.println("aEndNameList: ");
		for(int i=0; i < cc.aEndNameList.length; ++i) {
			System.out.println(LSUtils.printNsvString(cc.aEndNameList[i]));
		}
		System.out.println("zEndNameList: ");
		for(int i=0; i < cc.zEndNameList.length; ++i) {
			System.out.println(LSUtils.printNsvString(cc.zEndNameList[i]));
		}
	}
	
	public static void printPTP(PrintWriter pw, TerminationPoint_T ptp)
	{
		pw.println("TerminationPoint_T Object: ");
		pw.println("===========================");
		pw.println("name=\n" + LSUtils.printNsvString(ptp.name));
		pw.println("userLabel=" + ptp.userLabel);
		pw.println("nativeEMSName=" + ptp.nativeEMSName);
		pw.println("owner=" + ptp.owner);
		pw.println("ingressTrafficDescriptorName=\n" + LSUtils.printNsvString(ptp.ingressTrafficDescriptorName));
		pw.println("egressTrafficDescriptorName=\n" + LSUtils.printNsvString(ptp.egressTrafficDescriptorName));
		pw.println("type=" + ptp.type.value());
		pw.println("terminationMode=" + ptp.tpMappingMode.value());
		pw.println("directionality=" + ptp.direction.value());
		for(int i=0; i < ptp.transmissionParams.length; ++i) {
			printLayeredParameters(pw,ptp.transmissionParams[i]);
		}
		pw.println("connectionState=" + ptp.connectionState.value());
		pw.println("additionalInfo=" + LSUtils.printNsvString(ptp.additionalInfo));
		pw.println("===========================");
		
	}

	public static void printLayeredParameters(PrintWriter pw,LayeredParameters_T lp)
	{
		pw.println("LayeredParameters_T Object: ");
		pw.println("===========================");
		pw.println("transmissionParams=\n" + LSUtils.printNsvString(lp.transmissionParams));
		pw.println("layer=" + lp.layer);
		pw.println("===========================");
	}
	
	public static void printList(PrintWriter pw, List l)
	{
		System.out.println("list size = " + l.size());
		Iterator iter = l.iterator();
		while(iter.hasNext()) {
			Object o = iter.next();
			if(o instanceof TerminationPoint_T) {
				LSDebug.printPTP(pw,(TerminationPoint_T)o);	
			}
		} // while	
	}
	
	public static void printList(List l)
	{
		Iterator iter = l.iterator();
		while(iter.hasNext()) {
			Object o = iter.next();
			if(o instanceof StructuredEvent) {
				LSDebug.printEvent((StructuredEvent)o);
				continue;
			}
			
			if(o instanceof CrossConnect_T ) {
				LSDebug.printCrossConnect((CrossConnect_T)o);
				continue;
			}
			if(o instanceof TopologicalLink_T ) {
				LSDebug.printTlink((TopologicalLink_T)o);
				continue;
			}
			
			if(o instanceof EquipmentHolder_T) {
				//LSDebug.printEquipmentHolder((EquipmentHolder_T)o);
			} else {
				if(o instanceof Equipment_T) {
					LSDebug.printEquipment((Equipment_T) o);
				} else {
					if(o instanceof TerminationPoint_T) {
						LSDebug.printPTP((TerminationPoint_T)o);
					} else {
						if(o instanceof ManagedElement_T) {
							LSDebug.printManagedElement((ManagedElement_T) o);						
						} else {
							if(o instanceof SubnetworkConnection_T) {
								LSDebug.printSNC((SubnetworkConnection_T)o);
							} else {
								System.out.println(ToStringBuilder.toString(o));
							} //if
						} // if
					} // if
				} // if
			} //if
		} // while
	}

}
