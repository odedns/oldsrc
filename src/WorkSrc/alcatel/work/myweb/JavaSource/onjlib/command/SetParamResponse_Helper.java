/**
 * SetParamResponse_Helper.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * jdk0450.04 v122904173847
 */

package onjlib.command;

public class SetParamResponse_Helper {
    // Type metadata
    private static com.ibm.ws.webservices.engine.description.TypeDesc typeDesc =
        new com.ibm.ws.webservices.engine.description.TypeDesc(SetParamResponse.class);

    /**
     * Get Custom Serializer
     */
    public static com.ibm.ws.webservices.engine.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class javaType,  
           javax.xml.namespace.QName xmlType) {
        return 
          new SetParamResponse_Ser(
            javaType, xmlType, typeDesc);
    };

    /**
     * Get Custom Deserializer
     */
    public static com.ibm.ws.webservices.engine.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class javaType,  
           javax.xml.namespace.QName xmlType) {
        return 
          new SetParamResponse_Deser(
            javaType, xmlType, typeDesc);
    };

}
