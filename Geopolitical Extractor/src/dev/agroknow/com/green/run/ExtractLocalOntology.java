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

import dev.agroknow.com.green.blocks.RecordTerm;
import dev.agroknow.com.green.blocks.ScoreTermComparator;
import dev.agroknow.com.green.blocks.SearchOntology;
import dev.agroknow.com.green.blocks.SearchTerms;
import java.io.IOException;
import java.io.File;
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
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.lucene.queryparser.classic.ParseException;
import org.semanticweb.owlapi.model.OWLClass;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Agroknow
 */
public class ExtractLocalOntology {        
    
    public static void writeXML(String path,String filename,ArrayList<RecordTerm> ar,String type ){        
        
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
            StreamResult result = new StreamResult(new File(path+File.pathSeparator+filename+"."+type+".xml"));
            
        } catch (ParserConfigurationException | TransformerConfigurationException ex) {
            Logger.getLogger(ExtractLocalOntology.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) throws IOException, ParseException {       
        
        String ontology_path="C:\\Users\\SOTIRIS SON\\Documents";
        String ontology_filename="geopolitical.owl";
        
        String path="";
        String filename="";
        
        
        String text="This is a test, agriculture, apples and vitis with boys playing in the field. Vitis vitis and vitis again";
        //String text = new String(Files.readAllBytes(Paths.get(path+File.pathSeparator+filename)));
        
        SearchTerms st=new SearchTerms(text);
        st.addDoc();
        
        SearchOntology sc=new SearchOntology(ontology_path+File.separator+ontology_filename);
        
        ArrayList<OWLClass> ontology_classes=sc.getOntology_classes();        
                
        
        String local_name="";
        ArrayList<RecordTerm> lnrt=new ArrayList<>();        
        for(int i=0;i<ontology_classes.size();i++){            
            local_name=sc.getLocalName(ontology_classes.get(i));
            if(local_name==null){
                continue;
            }
            double score=st.queryTerm(local_name);
            RecordTerm rt=new RecordTerm(score,local_name);
            lnrt.add(rt);
        }
        
        Collections.sort(lnrt, new ScoreTermComparator());
        writeXML(path,filename,lnrt,"local_name");
        
        String label="";
        ArrayList<RecordTerm> lrt=new ArrayList<>();
        for(int i=0;i<ontology_classes.size();i++){           
            label=sc.getLabels(ontology_classes.get(i));
            if(label==null){
                continue;
            } 
            double score=st.queryTerm(label);
            RecordTerm rt=new RecordTerm(score,label);
            lrt.add(rt);
        }
        
        Collections.sort(lrt, new ScoreTermComparator());
        writeXML(path,filename,lrt,"labels");
        
        String comments="";
        ArrayList<RecordTerm> crt=new ArrayList<>();
        for(int i=0;i<ontology_classes.size();i++){
            comments=sc.getComments(ontology_classes.get(i));       
            if(comments==null){
                continue;
            } 
            double score=st.queryTerm(comments);
            RecordTerm rt=new RecordTerm(score,comments);
            crt.add(rt);
        }
        
        Collections.sort(crt, new ScoreTermComparator());       
        writeXML(path,filename,crt,"comments");
        
    }
    
}
