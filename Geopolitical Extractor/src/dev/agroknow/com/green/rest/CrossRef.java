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
package dev.agroknow.com.green.rest;

import dev.agroknow.com.green.blocks.Affiliation;
import dev.agroknow.com.green.blocks.Author;
import dev.agroknow.com.green.blocks.Publication;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author SOTIRIS SON
 */
public class CrossRef {
    
    private String doi;

    public CrossRef(String doi) {
        this.doi = doi;
    }
        
    public Publication getMetadata(){      
        Publication publication=new Publication();
        try {            
            URL url = new URL("http://api.crossref.org/works/"+this.doi);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            
            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "+ connection.getResponseCode());
            }
            
            BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
            String json="";
            String line="";
            while ((line = br.readLine()) != null){
                json+=line;
            }
            
            JSONParser parser = new JSONParser();
            JSONObject object=(JSONObject)parser.parse(json);

            String status=(String)object.get("status");
            String title="";
            String publication_abstract="";
            String container_title="";
            ArrayList<String> identifier = new ArrayList<>();
            long online_date=0L;           
            
            if(status.equalsIgnoreCase("ok")){                
                JSONObject msg=(JSONObject)object.get("message");
                title=(String)(((JSONArray)msg.get("title")).get(0));
               
                container_title=(String)(((JSONArray)msg.get("container-title")).get(0));
                
                JSONArray authors=(JSONArray)msg.get("author");
                Iterator iterator=authors.iterator();
                ArrayList<Author> author_list=new ArrayList<>();
                while(iterator.hasNext()){
                    
                    JSONObject author=(JSONObject)iterator.next();
                    
                    String first_name=(String)author.get("given");
                    String last_name=(String)author.get("family");
                    String middle_name="";
                    
                    JSONArray affiliations=(JSONArray)author.get("affiliation");
                    Iterator affiliations_iterator=affiliations.iterator();
                    ArrayList<Affiliation> affs=new ArrayList<>();
                    
                    while(affiliations_iterator.hasNext()){
                        
                        String[] affiliation=((String)iterator.next()).split(",");                        
                        
                        String department=affiliation[0];
                        String university=affiliation[1];
                        String address=affiliation[2];                        
                                     
                        Affiliation aff=new Affiliation(department,university,address);
                        affs.add(aff);
                        
                    }
                    
                    Author ath=new Author(first_name,middle_name,last_name,affs,"");                                     
                    author_list.add(ath);
                    
                }
                
                JSONObject deposited= (JSONObject)msg.get("deposited");
                online_date=(long)deposited.get("timestamp");                
                JSONArray identifiers=(JSONArray)msg.get("ISSN");
                
                Iterator it_identifiers=identifiers.iterator();
                while(it_identifiers.hasNext()){                    
                    identifier.add((String)it_identifiers.next());
                }                
              
                publication=new Publication("",title,this.doi,identifier,publication_abstract,author_list,container_title,online_date);
                
            }            
            
            connection.disconnect();
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(CrossRef.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ParseException ex) {
            Logger.getLogger(CrossRef.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return publication;
        
    }
    
    public static void main(String[] args){
         CrossRef crf=new CrossRef("10.1007/s00125-016-4156-4");
         crf.getMetadata();

    }
}
    
