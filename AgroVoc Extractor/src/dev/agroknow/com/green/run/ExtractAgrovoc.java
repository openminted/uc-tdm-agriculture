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

import dev.agroknow.com.green.blocks.RecordAgroVocTerm;
import dev.agroknow.com.green.blocks.SearchTerms;
import dev.agroknow.com.green.blocks.SearchOntology;
import dev.agroknow.com.green.blocks.ScoreTermComparator;
import dev.agroknow.com.green.blocks.RecordTerm;
import dev.agroknow.com.green.blocks.ScoreAgroVocTermComparator;
import static dev.agroknow.com.green.run.ExtractLocalOntology.writeXML;
import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
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
import org.fao.aims.aos.SKOSWSService;
import org.semanticweb.owlapi.model.OWLClass;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import utilities.StopWords;

/**
 *
 * @author Agroknow
 */
public class ExtractAgrovoc {        
    
    private static String simpleSearchByMode2(java.lang.String searchString, java.lang.String searchmode, java.lang.String separator) {
        org.fao.aims.aos.SKOSWSService service = new org.fao.aims.aos.SKOSWSService();
        org.fao.aims.aos.SKOSWS port = service.getSKOSWS();
        return port.simpleSearchByMode2(searchString, searchmode, separator);
    }
    
    public static void writeXML_online(String path,String filename,ArrayList<RecordAgroVocTerm> ar,String type ){  
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();            
            
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement(type);
            doc.appendChild(rootElement);
            
            Iterator<RecordAgroVocTerm> iterator = ar.iterator();
            while (iterator.hasNext()) { 
                
                Element term_tag = doc.createElement("term");                
                
                RecordAgroVocTerm rt=(RecordAgroVocTerm)iterator.next();
                double score=rt.getScore();
                String term=rt.getTerm();
                String code=rt.getCode();
                
                Element text_tag = doc.createElement("text");
                text_tag.appendChild(doc.createTextNode(term));
                term_tag.appendChild(text_tag);
                
                Element code_tag = doc.createElement("code");
                code_tag.appendChild(doc.createTextNode(code));
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
            System.out.println("Results have been Written");
        } catch (ParserConfigurationException | TransformerConfigurationException ex) {
            Logger.getLogger(ExtractAgrovoc.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(ExtractAgrovoc.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public static void writeXML_offline(String path,String filename,ArrayList<RecordTerm> ar,String type ){        
        
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();            
            
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement(type);
            doc.appendChild(rootElement);
            
            Iterator<RecordTerm> iterator = ar.iterator();
            while (iterator.hasNext()) { 
                
                Element term_tag = doc.createElement("term");                
                
                RecordTerm rt=(RecordTerm)iterator.next();
                double score=rt.getScore();
                String term=rt.getTerm();
                
                Element text_tag = doc.createElement("text");
                text_tag.appendChild(doc.createTextNode(term));
                term_tag.appendChild(text_tag);
                
                Element score_tag = doc.createElement("score");
                score_tag.appendChild(doc.createTextNode(Double.toString(score)));
                term_tag.appendChild(score_tag);
                
		rootElement.appendChild(term_tag);
                 
            }
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(path+File.pathSeparator+filename+"."+type+".AgroVoc.xml"));
            
        } catch (ParserConfigurationException | TransformerConfigurationException ex) {
            Logger.getLogger(ExtractLocalOntology.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static ArrayList<String> exactTermSearh(String text, String lang){
        System.out.println("Searching Exact Terms");
        StopWords sw=new StopWords();
        
        String[] stopwords=sw.getStopwords();        
        String clean_text="";
        
        for (String stopword : stopwords) {
            clean_text = text.replaceAll(stopword, "");
        }
        
        String[] tokenized_text=clean_text.split("\\s+");
        
        String response="";        
        String language_token="";
        
        for(String search_term : tokenized_text){
            
            response=simpleSearchByMode2(search_term,"exact",";");
            System.out.println(response);
            String[] tokenized_response=response.split(";");
            
            for(int i=0;i<tokenized_response.length;i++){
                if(tokenized_response[i].equalsIgnoreCase(lang)){                
                    language_token=language_token+(tokenized_response[i-2]+";"+tokenized_response[i-1]+"\n").replaceAll("\\[", "");
                }
            }
        
        }
        
        String[] tokenized_keywords=language_token.split("\\n");       
        
        ArrayList<String> keywords=new ArrayList<String>();
        for(String keyword : tokenized_keywords){
            keywords.add(keyword);
        }
        
        return keywords;        
        
    }
    
    public static void AgroVocOnline(String text, String path, String filename,String lang) throws IOException{
        SearchTerms st=new SearchTerms(text);
        st.addDoc();        
        
        ArrayList<String> terms=exactTermSearh(text,lang);
        
        String term="";
        String code="";
        ArrayList<RecordAgroVocTerm> vterm=new ArrayList<>();  
        
        for(int i=0;i<terms.size();i++){        
            String[] parts=terms.get(i).split(";");
            
            code=parts[0];
            term=parts[1];
            double score=st.queryTerm(term);
            
            RecordAgroVocTerm rt=new RecordAgroVocTerm(score,term,code);
            vterm.add(rt);
        }
        
        ArrayList<RecordAgroVocTerm> vterm_without_duplicates=new ArrayList<>();  
        
        for (RecordAgroVocTerm event : vterm) {
            boolean isFound = false;                       
            for (RecordAgroVocTerm e : vterm_without_duplicates) {                
                if (e.getTerm().equalsIgnoreCase(event.getTerm())){                   
                    isFound = true;                    
                    break;
                }           
                
            }
            if (isFound==false){
                vterm_without_duplicates.add(event);
            }
        }      
        
        Collections.sort(vterm_without_duplicates, new ScoreAgroVocTermComparator());         
        writeXML_online(path,filename,vterm_without_duplicates,"AgroVoc");       
        
        System.out.println("Searching Ended");
        System.out.println("Keywords Found: "+vterm_without_duplicates.size());
        
    }
    
    public static void AgroVocOffline(String text,String path,String filename,String ontology_path,String ontology_filename) throws IOException{
        SearchTerms st=new SearchTerms(text);
        st.addDoc();
        
        SearchOntology sc=new SearchOntology(ontology_path+File.separator+ontology_filename);
        
        ArrayList<OWLClass> ontology_classes=sc.getOntology_classes();        
                
        
        String local_name="";
        ArrayList<RecordTerm> lnrt=new ArrayList<>();        
        for(int i=0;i<ontology_classes.size();i++){            
            local_name=sc.getLocalName(ontology_classes.get(i));            
            double score=st.queryTerm(local_name);
            RecordTerm rt=new RecordTerm(score,local_name);
            lnrt.add(rt);
        }
        
        Collections.sort(lnrt, new ScoreTermComparator());
        writeXML_offline(path,filename,lnrt,"local_name");
        
        String label="";
        ArrayList<RecordTerm> lrt=new ArrayList<>();
        for(int i=0;i<ontology_classes.size();i++){            
            label=sc.getLabels(ontology_classes.get(i)); 
            double score=st.queryTerm(label);
            RecordTerm rt=new RecordTerm(score,label);
            lrt.add(rt);
        }
        
        Collections.sort(lrt, new ScoreTermComparator());
        writeXML_offline(path,filename,lrt,"labels");
        
        String comments="";
        ArrayList<RecordTerm> crt=new ArrayList<>();
        for(int i=0;i<ontology_classes.size();i++){            
            comments=sc.getComments(ontology_classes.get(i));       
            double score=st.queryTerm(comments);
            RecordTerm rt=new RecordTerm(score,comments);
            crt.add(rt);
        }
        
        Collections.sort(crt, new ScoreTermComparator());       
        writeXML_offline(path,filename,crt,"comments");
        
    }
    
    public static void main(String[] args) throws IOException, ParseException, Exception {  
        
        String path="";
        String filename="";
        String lang="";
        String text="";
        
        //System.out.println(args.length);
        if(args.length<1){
            System.out.println("No Arguments"); 
            System.out.println("Running Default Test");
            text="This is a test, agriculture, apples and vitis with boys playing in the field. Vitis vitis and vitis again";
            lang="en";
            AgroVocOnline(text,path,filename,lang);
        }else if(args[3].equalsIgnoreCase("1")){
            path=args[0];
            filename=args[1];
            lang=args[2];
            System.out.println("Path "+path);
            System.out.println("Filename "+filename);
            System.out.println("Language "+lang);
            text = new String(Files.readAllBytes(Paths.get(path+File.separator+filename)));
            AgroVocOnline(text,path,filename,lang);
        
        }else if (args[3].equalsIgnoreCase("0")){
            path=args[0];
            filename=args[1];
            lang=args[2];
            String ontology_path=args[4];
            String ontology_filename=args[5];
            
            System.out.println("Path "+path);
            System.out.println("Filename "+filename);
            System.out.println("Language "+lang);
            System.out.println("Ontology Path "+ontology_path);
            System.out.println("Ontology Filename "+ontology_filename);
            
            text = new String(Files.readAllBytes(Paths.get(path+File.separator+filename)));
            AgroVocOffline(text,path,filename,ontology_path,ontology_filename);
            
        }
       
    }    
}
