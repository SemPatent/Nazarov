package org.parser;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.process.*;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;

import org.xml.sax.helpers.DefaultHandler; 
import org.xml.sax.*;

import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;





import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

public class Main  extends JFrame   {

	public static ArrayList<IWord> getWords(String str)
	{
		ArrayList<IWord> words = new ArrayList<IWord>();
    	
    	// получаем слова
    	for(int i=0; i<str.length(); ++i)
    	{
    		
    		
    		String tmp="";
    		while(str.charAt(i) != '\n' && i <str.length())
    		{
    			tmp+=str.charAt(i);
    			++i;
    		}
    		
    		String tmp2="";
    		int lolo =0;
    		IWord tmpWord = new IWord();
    		for(int j=0; j<tmp.length(); ++j)
    		{   			
    			
    			if (tmp.charAt(j) != '\t' )
    			{
    				tmp2+=tmp.charAt(j);
    				if (j == tmp.length()-1)
        			{
        				tmpWord.inf = tmp2;
    					lolo=0;
    					words.add(tmpWord);
        			}
    			}    			
    			else 
    			{
    				if (lolo == 0)    				
    				{
    					lolo++;
    					tmpWord.word = tmp2;				
    				}
    				else if (lolo == 1)
    				{
    					lolo++;
    					tmpWord.type = tmp2;
    				}  				
    				    									
					tmp2="";					
    			}
    				
    		}    		
    	}
    	
    	return words;
	}
	
	public static ArrayList<String> getReq(String key, String word) throws MalformedURLException, IOException, ParserConfigurationException, SAXException
	{
		
		String str = "https://dictionary.yandex.net/api/v1/dicservice/lookup?key=dict.1.1.20140720T192134Z.58f7923ef8528492.1ab60b26472b47d0c03b2b2d1146958c69860d34&lang=en-en&text="+word;
		URLConnection conn = new URL(str).openConnection();

		//заполним header request парамеры, можно и не заполнять
		conn.setRequestProperty("Referer", "http://google.com/http.example.html");
		conn.setRequestProperty("Cookie", "a=1");
		//можно установить и другие парамеры, такие как User-Agent
		String html = "";
		 try
		 {
			StringBuffer b = new StringBuffer();
			InputStreamReader r = new InputStreamReader(conn.getInputStream(), "UTF-8");
			int c;
			while ((c = r.read()) != -1) 
			{
				b.append((char)c);
			}
			//читаем то, что отдал нам сервер
			html = b.toString();

		 }
		 catch(IOException ex)
		 {
			 System.out.println(ex.toString());
		 }
		 System.out.println(html);
		 
		 SAXParserFactory factory = SAXParserFactory.newInstance(); 
		 SAXParser parser = factory.newSAXParser(); 
		 SAXPars saxp = new SAXPars();
		 
		 
		 InputStream stream = new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8));
		  
		 parser.parse(stream, saxp); 
		  
		 return saxp.getResult();
	}
	
	public static ArrayList<ICortege> getCorteges(ArrayList<IWord> words) throws MalformedURLException, IOException, ParserConfigurationException, SAXException 
	{
		ArrayList<ICortege> corteges = new ArrayList<ICortege>();
		String key = "dict.1.1.20140720T192134Z.58f7923ef8528492.1ab60b26472b47d0c03b2b2d1146958c69860d34";
		
		for(int i=0; i<words.size(); ++i)
		{
			ICortege tmp = new ICortege();
			while (!words.get(i).type.equals("SENT") && i< words.size())
			{
				//String tp = words.get(i).type;
				tmp.words.add(words.get(i));
				if (words.get(i).type.charAt(0) == 'V' && words.get(i).type.charAt(1) == 'B')
					tmp.label.add(words.get(i).inf);
				
				++i;
			}
			//tmp.words.add(words.get(i));
			
			for(int j=0; j<tmp.label.size(); ++j)
			{
				ArrayList<String> tmpstr = getReq(key, tmp.label.get(j));
				for(int k=0; k<tmpstr.size(); ++k)
				{
					if (tmp.synonims.contains(tmpstr.get(k)) == false)
						tmp.synonims.add(tmpstr.get(k));							
				}
			}
			corteges.add(tmp);
		}
			
			
		String word = "time";
		
		
		
		return corteges;
	}
   
    public static void main(String[] args) throws MalformedURLException, IOException, ParserConfigurationException, SAXException 
    {        	

		
    	String str = Stanford.StanfordTagger("I liked my cat. But I hate to wash it.");    	
    	
    	ArrayList<IWord> words = getWords(str);
    	
    	
    	ArrayList<ICortege> corteges = getCorteges(words);
    	
    	
    	
    	System.out.println(str);
       
    }

   
   
}