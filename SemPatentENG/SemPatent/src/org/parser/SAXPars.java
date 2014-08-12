package org.parser;
import org.xml.sax.helpers.DefaultHandler; 
import org.xml.sax.*;
import java.util.ArrayList;
import org.xml.sax.helpers.DefaultHandler; 
import org.xml.sax.*;
import java.util.ArrayList;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;

import org.xml.sax.helpers.DefaultHandler; 
import org.xml.sax.*;

import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

	

	public class SAXPars extends DefaultHandler
	{
		ArrayList<String> synonims = new ArrayList<String>();
		boolean flag = false;
		String thisElement = ""; 
		
		public ArrayList<String> getResult(){ 
			  return synonims; 
			} 
		
		@Override 
		public void startDocument() throws SAXException 
		{ 
		  System.out.println("Start parse XML..."); 
		} 
		
		@Override 
		public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException
		{ 
		  thisElement = qName; 
		  if (thisElement == "syn")	
			  flag = true;
		 
		} 
		
		@Override 
		public void endElement(String namespaceURI, String localName, String qName) throws SAXException 
		{ 
			
				
			thisElement = ""; 
		 
		} 
		 
		@Override 
		public void characters(char[] ch, int start, int length) throws SAXException { 
		  if (thisElement == "text" && flag == true) 
		  { 
			  String tmp = new String(ch, start, length);		  
			  synonims.add(tmp);
			  flag = false;
		  }
		 
		} 
		 
		@Override 
		public void endDocument() { 
		  System.out.println("Stop parse XML..."); 
		} 
	} 

