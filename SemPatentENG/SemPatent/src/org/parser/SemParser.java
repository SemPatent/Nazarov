package org.parser;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.process.Morphology;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

import org.annolab.tt4j.*;
import org.maltparser.MaltParserService;
import org.maltparser.core.exception.MaltChainedException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Iterator;
import java.util.regex.*;

import static java.util.Arrays.asList;

public class SemParser {
    private static String symbolTab = "\t";
    private static String magSymbol = "\n";//������ �������� ������ �������� ����� ������� ������� ��������
    public static String namePatent="PatentNumber";
    
    public static void shouldAcceptENG() throws IOException{

        Path currentRelativePath = Paths.get("");
        String absolutePath = currentRelativePath.toAbsolutePath().toString();

        String separator = File.separator;
        String modelNameMalt = "engmalt.linear-1.7";

        String resultClaim = fileRead("claim_en0.txt");
        resultClaim = Segment.segmentEng(resultClaim);
        //fileWrite("text_en0.txt", resultClaim);
        resultClaim = Stanford.StanfordTagger(resultClaim);
        resultClaim = Conll.conllEng(resultClaim);
        //fileWrite("conll_en0.txt", resultClaim);
        resultClaim = MaltParser.maltParserEng(modelNameMalt, resultClaim);
        //fileWrite("malt_en0.txt", resultClaim);
        
        File file1 = new File("parser.log");
        file1.delete();

    }

    public static void sortMap(Map<Integer,String> map) {
        Set s = map.entrySet();
        Iterator it = s.iterator();
        while ( it.hasNext() ) {
           Map.Entry entry = (Map.Entry) it.next();
           Integer key = (Integer) entry.getKey();
           String value = (String) entry.getValue();
        }//while
    }
    
    /** fileWrite - ������ ��������� �� ������ � ����
     *
     * ������� ���������:
     * String fileName - ������ ���� � ������ �����, ���� ���������� ������
     * String content - �������� ������.
     *
     * �������� ������:
     *
     */
    public static void fileWrite(String fileName, String content){
        try
        {
            OutputStream f = new FileOutputStream(fileName, false);
            OutputStreamWriter writer = new OutputStreamWriter(f, "UTF-8");
            BufferedWriter out = new BufferedWriter(writer);
            out.write(content);
            out.flush();
            f.close();
            writer.close();
        }
        catch(IOException ex)
        {
            System.err.println(ex);
        }
    }

    /** fileRead - ������ ���� �������� � ��������� ����������
     *
     * ������� ���������:
     * String fileName - ������ ���� � ������ ����� (���� ������� ������ ��� �����, �� �������� ��������� ����� �
     * ����������� ������)
     *
     * �������� ������:
     * String - ������ �������� ��������, �������� �� ������� � ����������� �������� �������� ������
     */
    public static String fileRead(String fileName){
        String result = "";
        BufferedReader input = null;

        try {
            input = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        try {
            String tmp;
            while ((tmp = input.readLine()) != null){
                result += tmp;
                result += "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            input.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return result;
    }
    
}
