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

import dev.agroknow.com.green.blocks.RecordGrapeVineTerm;
import dev.agroknow.com.green.blocks.SearchTerms;
import dev.agroknow.com.green.blocks.ScoreGrapeVineTermComparator;
import dev.agroknow.com.green.blocks.SearchVocabulary;
import dev.agroknow.com.green.blocks.Variety;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import vocabularies.OIV;

/**
 *
 * @author Agroknow
 */

public class ExtractOIVGrapeVines {        
    
    public static void writeXML(String path,String filename,ArrayList<RecordGrapeVineTerm> ar,String type ){  
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();            
            
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement(type);
            doc.appendChild(rootElement);
            
            Iterator<RecordGrapeVineTerm> iterator = ar.iterator();
            while (iterator.hasNext()) { 
                
                Element term_tag = doc.createElement("term");                
                
                RecordGrapeVineTerm rt=(RecordGrapeVineTerm)iterator.next();
                double score=rt.getScore();
                String term=rt.getTerm();
                String id=rt.getId();
                String barcode=rt.getBarcode();
                
                Element text_tag = doc.createElement("text");
                text_tag.appendChild(doc.createTextNode(term));
                term_tag.appendChild(text_tag);
                
                Element code_tag = doc.createElement("oiv_id");
                code_tag.appendChild(doc.createTextNode(id));
                term_tag.appendChild(code_tag);
                
                Element score_tag = doc.createElement("score");
                score_tag.appendChild(doc.createTextNode(Double.toString(score)));
                term_tag.appendChild(score_tag);
                
                Element barcode_tag = doc.createElement("barcode");
                barcode_tag.appendChild(doc.createTextNode(barcode));
                term_tag.appendChild(barcode_tag);
                
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
            Logger.getLogger(ExtractOIVGrapeVines.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(ExtractOIVGrapeVines.class.getName()).log(Level.SEVERE, null, ex);
        }
    }       
    
    public static void writeRDF(String path,String filename,String rdf,String term){        
        try {
            Files.write(Paths.get(path+File.separator+filename+"."+term+".rdf"), rdf.getBytes());
            System.out.println("Output Results to "+path+File.separator+filename+"."+term+".rdf");
            System.out.println("RDF has been Written");
        } catch (IOException ex) {
            Logger.getLogger(ExtractOIVGrapeVines.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }            
    
    public String clearMisspelingsGrapeVarieties(String text,ArrayList<String> grapeVarieties,String variety_term){
        
        for(int i=0;i<grapeVarieties.size();i++){
            text=text.replaceAll(grapeVarieties.get(i), variety_term);          
        }
        
        return text;
        
    }
    
    public static void main(String[] args) throws IOException, ParseException, Exception {
                     
        String path="";
        String filename="";
        String text="";
        
        if(args.length<1){
            System.out.println("No Arguments"); 
            System.out.println("Running Default Test");
            text="Goustolidi and Agiorgitiko also Aghiorghitiko while Aidani lefko";           
        
        }else{
            path=args[0];
            filename=args[1];
            
            text = new String(Files.readAllBytes(Paths.get(path+File.separator+filename)));
        }
        
        //SearchVocabulary sv=new SearchVocabulary(voc_path+File.separator+voc_filename);
        
        OIV oiv=new OIV();
        
        ArrayList<String> gpv=oiv.getOiv();
        //String text="Goustolidi and Agiorgitiko also Aghiorghitiko while Aidani lefko";
        ExtractOIVGrapeVines eoig=new ExtractOIVGrapeVines();
        //text=eoig.clearMisspelingsGrapeVarieties(text, gpv, text);     
        SearchTerms st=new SearchTerms(text);
        st.addDoc();
        
        ArrayList<RecordGrapeVineTerm> gpterm=new ArrayList<>();
        for(int i=0;i<gpv.size();i++){            
            String term=gpv.get(i).split(";")[1];     
            double score=st.queryTerm(term);
            String variety_id=gpv.get(i).split(";")[0];
            //String barcode=gpv.get(i).split(";")[3];            
            if(score>0.0){
                RecordGrapeVineTerm rgvt=new RecordGrapeVineTerm(score,term,"",variety_id);
                gpterm.add(rgvt);
            }            
        }
        
        Collections.sort(gpterm, new ScoreGrapeVineTermComparator());         
        writeXML(path,filename,gpterm,"GrapeVine");
        
    }
}
