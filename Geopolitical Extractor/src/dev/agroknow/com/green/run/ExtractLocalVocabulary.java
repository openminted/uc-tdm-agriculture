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
import dev.agroknow.com.green.blocks.SearchTerms;
import dev.agroknow.com.green.blocks.SearchVocabulary;
import static dev.agroknow.com.green.run.ExtractLocalOntology.writeXML;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import org.apache.lucene.queryparser.classic.ParseException;

/**
 *
 * @author SOTIRIS SON
 */
public class ExtractLocalVocabulary {
    
    public static void main(String[] args) throws IOException, ParseException {
        
        String vocabulary_path=" ";
        String vocabulary_filename="";
        
        String path="";
        String filename="";
        
        String text = new String(Files.readAllBytes(Paths.get(path+File.pathSeparator+filename)));
        
        SearchTerms st=new SearchTerms(text);
        st.addDoc();
        
        SearchVocabulary sv=new SearchVocabulary(vocabulary_path+File.pathSeparator+vocabulary_filename);
        ArrayList<String> terms=sv.getVocabulary_terms();
        
        String term="";
        ArrayList<RecordTerm> vterm=new ArrayList<>();        
        for(int i=0;i<terms.size();i++){            
            term=terms.get(i);     
            double score=st.queryTerm(term);
            RecordTerm rt=new RecordTerm(score,term);
            vterm.add(rt);
        }
        
        Collections.sort(vterm, new ScoreTermComparator()); 
        writeXML(path,filename,vterm,"vocbulary");       
        
    }
    
}
