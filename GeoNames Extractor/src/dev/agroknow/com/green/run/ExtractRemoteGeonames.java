/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of version 3 of the GNU General Public License as published by
 * the Free Software Foundation, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 */
package dev.agroknow.com.green.run;

import dev.agroknow.com.green.blocks.RecordGeoTerm;
import dev.agroknow.com.green.blocks.SearchTerms;
import dev.agroknow.com.green.blocks.ScoreGeoTermComparator;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.lucene.queryparser.classic.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Agroknow
 */
public class ExtractRemoteGeonames {        
    
    public static JSONObject getCountryNames(String username){
        JSONObject jsonObject=null;
        try {
            URL url = new URL("http://api.geonames.org/countryInfoJSON?username="+username);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }
            
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            
            String json_string="";
            String output="";
            while ((output = br.readLine()) != null) {
                json_string+=output;          
            }          
            
            conn.disconnect();
            
            JSONParser parser = new JSONParser();           
            Object obj = parser.parse(json_string);
            
            jsonObject = (JSONObject) obj;
        } catch (MalformedURLException ex) {
            Logger.getLogger(ExtractRemoteGeonames.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ExtractRemoteGeonames.class.getName()).log(Level.SEVERE, null, ex);
        } catch (org.json.simple.parser.ParseException ex) {
            Logger.getLogger(ExtractRemoteGeonames.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return jsonObject;
    }
    
    public static String getRDFbyGeoname(String username, String Geoname, int MaxRows){
        String rdf_string="";
        try {           
           URL url = new URL("http://api.geonames.org/search?q="+Geoname+"&maxRows="+MaxRows+"&type=rdf&username="+username);

           HttpURLConnection conn = (HttpURLConnection) url.openConnection();
           conn.setRequestMethod("GET");
           conn.setRequestProperty("Accept", "application/rdf");

           if (conn.getResponseCode() != 200) {
               throw new RuntimeException("Failed : HTTP error code : "
                       + conn.getResponseCode());
           }             

           BufferedReader br = new BufferedReader(new InputStreamReader(
                   (conn.getInputStream())));


           String output="";
           while ((output = br.readLine()) != null) {
               rdf_string+=output; 
           }          

           conn.disconnect();           
           
        } catch (MalformedURLException ex) {
            Logger.getLogger(ExtractRemoteGeonames.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ExtractRemoteGeonames.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rdf_string;
    }
    
    public static void writeXML(String path,String filename,ArrayList<RecordGeoTerm> ar,String type ){  
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();            
            
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement(type);
            doc.appendChild(rootElement);
            
            Iterator<RecordGeoTerm> iterator = ar.iterator();
            while (iterator.hasNext()) { 
                
                Element term_tag = doc.createElement("term");                
                
                RecordGeoTerm rt=(RecordGeoTerm)iterator.next();
                double score=rt.getScore();
                String term=rt.getTerm();
                long geoname_id=rt.getGeonameId();
                
                Element text_tag = doc.createElement("text");
                text_tag.appendChild(doc.createTextNode(term));
                term_tag.appendChild(text_tag);
                
                Element code_tag = doc.createElement("geoname_id");
                code_tag.appendChild(doc.createTextNode(Long.toString(geoname_id)));
                term_tag.appendChild(code_tag);
                
                Element score_tag = doc.createElement("score");
                score_tag.appendChild(doc.createTextNode(Double.toString(score)));
                term_tag.appendChild(score_tag);
                
		rootElement.appendChild(term_tag);
                 
            }
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(path+File.separator+filename+"."+type+".xml"));
            
            transformer.transform(source, result);
            
            System.out.println("Output Results to "+path+File.separator+filename+"."+type+".xml");
            System.out.println("XML has been Written");
        } catch (ParserConfigurationException | TransformerConfigurationException ex) {
            Logger.getLogger(ExtractRemoteGeonames.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(ExtractRemoteGeonames.class.getName()).log(Level.SEVERE, null, ex);
        }
    }       
    
    public static void writeRDF(String path,String filename,String rdf,String term){        
        try {
            Files.write(Paths.get(path+File.separator+filename+"."+term+".rdf"), rdf.getBytes());
            System.out.println("Output Results to "+path+File.separator+filename+"."+term+".rdf");
            System.out.println("RDF has been Written");
        } catch (IOException ex) {
            Logger.getLogger(ExtractRemoteGeonames.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }            
    
    public static void main(String[] args) throws IOException, ParseException, Exception {
        
        String username="";        
        String path="";
        String filename="";
        boolean semantic=true;
        int maxrows=10;
        
        String text ="";
        if(args.length<1){
            System.out.println("No Arguments"); 
            System.out.println("Running Default Test");
            text="This is a test, agriculture, apples and vitis with boys playing in the field. Vitis vitis and vitis again in Greece and Italy";
            username="sleeper";
        
        }else{
            path=args[0];
            filename=args[1];
            username=args[2];            
            maxrows=Integer.parseInt(args[3]);
            text = new String(Files.readAllBytes(Paths.get(path+File.separator+filename)));
        }
        
        
        JSONObject jsonObject=getCountryNames(username);
        
        SearchTerms st=new SearchTerms(text);
        st.addDoc();
        
        JSONArray  name = (JSONArray) jsonObject.get("geonames");
        Iterator<JSONObject> iterator = name.iterator();
        ArrayList<RecordGeoTerm> geoterms=new ArrayList<RecordGeoTerm>();
        
	while (iterator.hasNext()) {
            JSONObject country=((JSONObject)iterator.next());
            long geoname_id=(long)country.get("geonameId");
            RecordGeoTerm rgt=new RecordGeoTerm(0.0,(String)country.get("countryName"),geoname_id);            
            geoterms.add(rgt);
        }        
        
        ArrayList<RecordGeoTerm> gterm=new ArrayList<>();
        for(int i=0;i<geoterms.size();i++){            
            String term=geoterms.get(i).getTerm();     
            double score=st.queryTerm(term);
            long geoname_id=geoterms.get(i).getGeonameId();
            if(score>0.0){
                RecordGeoTerm rgt=new RecordGeoTerm(score,term,geoname_id);
                gterm.add(rgt);
            }            
        }
        
        Collections.sort(gterm, new ScoreGeoTermComparator());         
        writeXML(path,filename,gterm,"Geonames");  
        
        if(semantic==true){
            for(int i=0;i<gterm.size();i++){
                String term=gterm.get(i).getTerm();
                String rdf=getRDFbyGeoname(username,term,maxrows);
                writeRDF(path,filename,rdf,term);
            }
        }
    }
}
