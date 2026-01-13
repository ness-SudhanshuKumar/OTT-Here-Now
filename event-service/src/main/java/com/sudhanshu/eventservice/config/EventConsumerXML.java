package com.sudhanshu.eventservice.config;

import java.io.StringReader;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;

public class EventConsumerXML {

	public static void main(String[] args) throws SOAPException {
		SOAPMessage soapresponse = MessageFactory.newInstance().createMessage();
		
		 StreamSource s = new StreamSource(new StringReader("Some XMl message"));
		 soapresponse.getSOAPPart().setContent(s); soapresponse.saveChanges();
		 Document doc = soapresponse.getSOAPBody().extractContentAsDocument();
		 QName qName = new QName("http://example.com", "localPart");
		 System.out.println(qName);
		
	}
}
