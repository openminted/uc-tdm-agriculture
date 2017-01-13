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
package dev.agroknow.com.green.readers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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
public class AGRISReader {
    
    private String local_file="N/A";
    private String title="N/A";
    private String subject="N/A";
    private String agris_id="N/A";
    private String url="N/A";       
    private String doi="N/A";
    private ArrayList<String> authors=new ArrayList(); 
    
    public AGRISReader() {
    }
    
    public void readAGRISJSON(String path,String filename) throws ParseException{
        
        JSONParser parser = new JSONParser();
        JSONObject Object=(JSONObject)parser.parse(path+File.pathSeparator+filename);
        
        this.local_file=(String)Object.get("local_file");
        this.title=(String)Object.get("title");
        this.subject=(String)Object.get("subject");
        this.agris_id=(String)Object.get("agris_id");
        this.url=(String)Object.get("url");
        this.doi=(String)Object.get("doi");
        
        JSONArray authors=(JSONArray)Object.get("authors");
       
    }

    public String getLocal_file() {
        return local_file;
    }

    public void setLocal_file(String local_file) {
        this.local_file = local_file;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAgris_id() {
        return agris_id;
    }

    public void setAgris_id(String agris_id) {
        this.agris_id = agris_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    } 
    
}
