//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.4-b18-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2004.12.09 at 10:27:36 GMT+02:00 
//


package ex6.message;


/**
 * Java content class for message element declaration.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/C:/ws-lab/exercises/ex6-jaxb/message.xsd line 4)
 * <p>
 * <pre>
 * &lt;element name="message">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element name="destination" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="subject" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="content" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;/sequence>
 *       &lt;/restriction>
 *     &lt;/complexContent>
 *   &lt;/complexType>
 * &lt;/element>
 * </pre>
 * 
 */
public interface Message
    extends javax.xml.bind.Element, ex6.message.MessageType
{


}
