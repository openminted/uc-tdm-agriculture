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
package dev.agroknow.com.green.owl;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//import gr.unipi.ds.ai.utils.text.Stemmer;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.search.EntitySearcher;

/**
 *
 * @author akukurik
 */
public class OwlApiParser {
    private final OWLOntologyManager manager;
    private OWLOntology ontology;
    private final OWLDataFactory factory;
    
    private ArrayList<OWLClass> classes;
    private ArrayList<OWLObjectProperty> objectProperties;
    private ArrayList<OWLDataProperty> dataProperties;
    
    public OwlApiParser(){
        manager = OWLManager.createOWLOntologyManager();
        factory = OWLManager.getOWLDataFactory();
        classes = new ArrayList<>();
        objectProperties = new ArrayList<>();
        dataProperties = new ArrayList<>();
    }
    
    public void loadLocalOntology(String ontologyDocumentPath) throws OWLOntologyCreationException{
        ontology = manager.loadOntologyFromOntologyDocument(new File(ontologyDocumentPath));
    }
    
    public void loadLocalOntology(File ontologyDocument) throws OWLOntologyCreationException{
        ontology = manager.loadOntologyFromOntologyDocument(ontologyDocument);
    }
    
    public void readOntologyElements(){
        Set<OWLClass> ontoClasses = ontology.getClassesInSignature();
        Iterator<OWLClass> iterator = ontoClasses.iterator();
        while (iterator.hasNext()){
            OWLClass currentClass = iterator.next();
            this.classes.add(currentClass);
        }
        Set<OWLObjectProperty> properties = ontology.getObjectPropertiesInSignature();
        Iterator<OWLObjectProperty> objectPropertyIterator = properties.iterator();
        while (objectPropertyIterator.hasNext()){
            OWLObjectProperty currentProperty = objectPropertyIterator.next();
            objectProperties.add(currentProperty);
        }
        
        Set<OWLDataProperty> ontodataProperties = ontology.getDataPropertiesInSignature();
        Iterator<OWLDataProperty> dataPropertyIterator = ontodataProperties.iterator();
        while (dataPropertyIterator.hasNext()){
            OWLDataProperty currentProperty = dataPropertyIterator.next();
            this.dataProperties.add(currentProperty);
        }
    }
    
    public String getClassLabel(OWLClass ontologyClass){        
        for(OWLAnnotation a : EntitySearcher.getAnnotations(ontologyClass, ontology, factory.getRDFSLabel())) {            
            OWLAnnotationValue val = a.getValue();
            if (val instanceof OWLLiteral){                
                return ((OWLLiteral) val).getLiteral();
            }   
        }
        return null;
    }
    
    public String getClassComment(OWLClass ontologyClass){
        for(OWLAnnotation a : EntitySearcher.getAnnotations(ontologyClass, ontology, factory.getRDFSComment())) {
            OWLAnnotationValue val = a.getValue();
            if (val instanceof OWLLiteral){
                //System.out.println(((OWLLiteral) val).getLiteral());
                return ((OWLLiteral) val).getLiteral();
            }   
        }
        return null;
    }
    
    public String getClassLocalName(OWLClass ontologyClass){
        return ontologyClass.getIRI().getFragment();
    }
    
    public String getDataPropertyLabel(OWLDataProperty ontologyDataProperty){
        for(OWLAnnotation a : EntitySearcher.getAnnotations(ontologyDataProperty, ontology, factory.getRDFSLabel())) {
            OWLAnnotationValue val = a.getValue();
            if (val instanceof OWLLiteral){
                //System.out.println(((OWLLiteral) val).getLiteral());
                return ((OWLLiteral) val).getLiteral();
            }   
        }
        return null;
    }
    
    public String getDataPropertyComment(OWLDataProperty ontologyDataProperty){
        for(OWLAnnotation a : EntitySearcher.getAnnotations(ontologyDataProperty, ontology, factory.getRDFSComment())) {
            OWLAnnotationValue val = a.getValue();
            if (val instanceof OWLLiteral){
                //System.out.println(((OWLLiteral) val).getLiteral());
                return ((OWLLiteral) val).getLiteral();
            }   
        }
        return null;
    }
    
    public String getDataPropertyLocalName(OWLDataProperty ontologyDataProperty){
        return ontologyDataProperty.getIRI().getFragment();
    }
    
    public String getObjectPropertyLabel(OWLObjectProperty ontologyObjectProperty){
        for(OWLAnnotation a : EntitySearcher.getAnnotations(ontologyObjectProperty, ontology, factory.getRDFSLabel())) {
            OWLAnnotationValue val = a.getValue();
            if (val instanceof OWLLiteral){
                //System.out.println(((OWLLiteral) val).getLiteral());
                return ((OWLLiteral) val).getLiteral();
            }   
        }
        return null;
    }
    
    public String getObjectPropertyComment(OWLObjectProperty ontologyObjectProperty){
        for(OWLAnnotation a : EntitySearcher.getAnnotations(ontologyObjectProperty, ontology, factory.getRDFSComment())) {
            OWLAnnotationValue val = a.getValue();
            if (val instanceof OWLLiteral){
                //System.out.println(((OWLLiteral) val).getLiteral());
                return ((OWLLiteral) val).getLiteral();
            }   
        }
        return null;
    }
    
    public String getObjectPropertyLocalName(OWLObjectProperty ontologyObjectProperty){
        return ontologyObjectProperty.getIRI().getFragment();
    }
    
    public ArrayList<String> getWordsInOntology(){
        ArrayList<String> words = new ArrayList<>();
        
        ArrayList<OWLClass> ontologyClasses = this.getClasses();
        ArrayList<OWLDataProperty> ontologyDataProperties = this.getDataProperties();
        ArrayList<OWLObjectProperty> ontologyObjectProperties = this.getObjectProperties();
        
        String text = "";
        
        for (OWLClass currentClass : ontologyClasses){
            String name = this.getClassLocalName(currentClass);
            String comment = this.getClassComment(currentClass);
            String label = this.getClassLabel(currentClass);
            String lexicalization = name + " " + comment + " " + label;
            lexicalization = lexicalization.replace("null", "");
            lexicalization += " ";
            text += lexicalization;
        }
        
        for (OWLDataProperty currentDataProperty : ontologyDataProperties){
            String name = this.getDataPropertyLocalName(currentDataProperty);
            String comment = this.getDataPropertyComment(currentDataProperty);
            String label = this.getDataPropertyLabel(currentDataProperty);
            String lexicalization = name + " " + comment + " " + label;
            lexicalization = lexicalization.replace("null", "");
            lexicalization += " ";
            text += lexicalization;
        }
        for (OWLObjectProperty currentObjectProperty : ontologyObjectProperties){
            String name = this.getObjectPropertyLocalName(currentObjectProperty);
            String comment = this.getObjectPropertyComment(currentObjectProperty);
            String label = this.getObjectPropertyLabel(currentObjectProperty);
            String lexicalization = name + " " + comment + " " + label;
            lexicalization = lexicalization.replace("null", "");
            lexicalization += " ";
            text += lexicalization;
        }
        
        //Stemmer stemmer = new Stemmer("english");
        //stemmer.stem(text);
        //words = stemmer.getStems();
        //stemmer.printStems();
        return words;
    }
    
    public ArrayList<OWLObjectProperty> getClassProperties(OWLClass ontologyClass){
        ArrayList<OWLObjectProperty> result = new ArrayList<>();
        
        Set<OWLObjectProperty> objectProps = ontology.getObjectPropertiesInSignature();
        ArrayList<OWLObjectProperty> props = new ArrayList<>();
        props.addAll(objectProps);
        /*for (OWLObjectProperty currentProperty : props){
            Set<OWLClassExpression> classes = currentProperty.getDomains(ontology);
            if (classes.contains(ontologyClass)){
                result.add(currentProperty);
            }
        }*/
        return result;
    }
    
    public void getOntologyInfo(){
        System.out.println(classes.size() + " classes");
        int properties = objectProperties.size() + dataProperties.size();
        System.out.println(properties + " properties");
    }

    public ArrayList<OWLClass> getClasses() {
        return classes;
    }

    public void setClasses(ArrayList<OWLClass> classes) {
        this.classes = classes;
    }

    public ArrayList<OWLObjectProperty> getObjectProperties() {
        return objectProperties;
    }

    public void setObjectProperties(ArrayList<OWLObjectProperty> objectProperties) {
        this.objectProperties = objectProperties;
    }

    public ArrayList<OWLDataProperty> getDataProperties() {
        return dataProperties;
    }

    public void setDataProperties(ArrayList<OWLDataProperty> dataProperties) {
        this.dataProperties = dataProperties;
    }

    public OWLOntology getOntology() {
        return ontology;
    }

    public void setOntology(OWLOntology ontology) {
        this.ontology = ontology;
    }
    
    
}
