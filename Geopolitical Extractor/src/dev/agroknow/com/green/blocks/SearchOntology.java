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
package dev.agroknow.com.green.blocks;

import dev.agroknow.com.green.owl.OwlApiParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.queryparser.classic.ParseException;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

/**
 *
 * @author SOTIRIS SON
 */
public class SearchOntology {
    
    private String ontology_path="";
    private ArrayList<OWLClass> ontology_classes=null;
    private OwlApiParser oap=null;

    public String getOntology_path() {
        return ontology_path;
    }

    public void setOntology_path(String ontology_path) {
        this.ontology_path = ontology_path;
    }

    public ArrayList<OWLClass> getOntology_classes() {
        return ontology_classes;
    }

    public void setOntology_classes(ArrayList<OWLClass> ontology_classes) {
        this.ontology_classes = ontology_classes;
    }
    
    public SearchOntology(String ontology_path) {
        
        this.ontology_path=ontology_path;
        
        this.oap=new OwlApiParser();
        try {            
            this.oap.loadLocalOntology(this.ontology_path);
            
        } catch (OWLOntologyCreationException ex) {
            Logger.getLogger(SearchOntology.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.oap.readOntologyElements();
        this.ontology_classes=oap.getClasses();
    }
    
    public String getLocalName(OWLClass ontologyClass){
        return this.oap.getClassLocalName(ontologyClass);
    }
    
    public String getLabels(OWLClass ontologyClass){        
        return this.oap.getClassLabel(ontologyClass);        
    }
    
    public String getComments(OWLClass ontologyClass){
        return this.oap.getClassComment(ontologyClass);
    }
    
    /*public static void main(String[] args) throws IOException, ParseException {       
        
        //SearchOntology sc=new SearchOntology("C:\\Users\\SOTIRIS SON\\Documents\\NetBeansProjects\\PDFXmlizedParser\\ontology_v3.1.rdf");
        ArrayList<OWLClass> owlclass=sc.getOntology_classes();
        
        System.out.println(owlclass.isEmpty());
        
        //sc.getOntology_classes().get(0).getC
        
    }*/
    
    
}
