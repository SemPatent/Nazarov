package practic;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;

import org.xml.sax.helpers.DefaultHandler; 
import org.xml.sax.*;

import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;





public class Main 
{
	public static void main(String[] args) throws MalformedURLException, IOException, ParserConfigurationException, SAXException 
	{
		// TODO Auto-generated method stub
		// итак сделаем код, который будет
		
		
		
		String ttt = "time";
		String str = "https://dictionary.yandex.net/api/v1/dicservice/lookup?key=dict.1.1.20140720T192134Z.58f7923ef8528492.1ab60b26472b47d0c03b2b2d1146958c69860d34&lang=en-en&text="+ttt;
				
		
		
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
		 
		  
		 
		  ArrayList<String> synonims = saxp.getResult();
		 
		for(int i=0; i < synonims.size(); ++i)
		{
			System.out.println(synonims.get(i));
		}
		 
		
		//читаем то, что отдал нам сервер		

		//выводим информацию в консоль
		//System.out.println("URL:" + url);
		
	}
}
