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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import org.apache.lucene.queryparser.classic.ParseException;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

/**
 *
 * @author SOTIRIS SON
 */
public class SearchVocabulary {
    
    private String vocabulary_path="";
    private ArrayList<String> vocabulary_terms=new ArrayList<String>();
    
    public SearchVocabulary(String vocabulary_path) {        
        this.vocabulary_path=vocabulary_path;        
        loadVocabulary();        
    }
    
    public void loadVocabulary(){        
        try {
            Stream<String> lines = Files.lines(Paths.get(this.vocabulary_path));
            lines.forEach(s->vocabulary_terms.add(s));            
        } catch (IOException ex) {
            Logger.getLogger(SearchVocabulary.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public String getVocabulary_path() {
        return vocabulary_path;
    }

    public void setVocabulary_path(String vocabulary_path) {
        this.vocabulary_path = vocabulary_path;
    }

    public ArrayList<String> getVocabulary_terms() {
        return vocabulary_terms;
    }

    public void setVocabulary_terms(ArrayList<String> vocabulary_terms) {
        this.vocabulary_terms = vocabulary_terms;
    }   
    
}
