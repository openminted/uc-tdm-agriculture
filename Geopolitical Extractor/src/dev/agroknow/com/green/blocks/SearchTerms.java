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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;


/**
 *
 * @author SOTIRIS SON
 */
public class SearchTerms {
    
    private String text="";
    
    StandardAnalyzer analyzer = new StandardAnalyzer();
    Directory index = new RAMDirectory();
    IndexWriter writer = null;

    public SearchTerms(String text) {
        this.text=text;
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        try { 
            writer = new IndexWriter(index, config);
        } catch (IOException ex) {
            Logger.getLogger(SearchTerms.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
    
    public void addDoc() throws IOException {
        Document doc = new Document();
        doc.add(new TextField("text", this.text, Field.Store.YES));
        writer.addDocument(doc);
        writer.close();
    }

    public double queryTerm(String query_string){
        
        double score=-1.0;
        
        try {
            Query q = new QueryParser("text", analyzer).parse(query_string);
            int hitsPerPage = 10;
            IndexReader reader = DirectoryReader.open(index);
            IndexSearcher searcher = new IndexSearcher(reader);
            
            TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage);
            searcher.search(q, collector);
            ScoreDoc[] hits = collector.topDocs().scoreDocs;   
            if(hits.length>0){
                score=hits[0].score;
            }
            
            
        } catch (ParseException | IOException ex) {
            Logger.getLogger(SearchTerms.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return score;
        
    }
    
    public static void main(String[] args) throws IOException, ParseException {        
        String text="Maritina is in the sky of diamonds Athens and Berlin with diamonds and University of Informatics diamonds.";
        
        SearchTerms st=new SearchTerms(text);
        st.addDoc();
        System.out.println(st.queryTerm("diamond*"));
       
    }
    
}
