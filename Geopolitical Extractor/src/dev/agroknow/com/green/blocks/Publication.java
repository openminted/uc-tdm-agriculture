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

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 *
 * @author SOTIRIS SON
 */
public class Publication {
    
    private String title;
    private String file_id;

    private String doi;    
    private ArrayList<String> identifier;
    private String container_title;
            
    private String publication_abstract;
    private ArrayList<Author> authors;
    private ArrayList<String> keywords;
    
    private long online_date;

    public Publication(String file_id,String title, String doi, ArrayList<String> identifier, String publication_abstract, ArrayList<Author> authors,String container_title,long online_date) {
        this.title = title;
        this.doi = doi;
        this.identifier = identifier;
        this.publication_abstract = publication_abstract;
        this.authors = authors;
        this.container_title=container_title;
        this.online_date=online_date;
        this.file_id=file_id;
    }

    public Publication() {
        
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public ArrayList<String> getIdentifier() {
        return identifier;
    }

    public void setIdentifier(ArrayList<String> identifier) {
        this.identifier = identifier;
    }

    public ArrayList<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<Author> authors) {
        this.authors = authors;
    }
    
    public String getPublication_abstract() {
        return publication_abstract;
    }

    public void setPublication_abstract(String publication_abstract) {
        this.publication_abstract = publication_abstract;
    }

    public String getContainer_title() {
        return container_title;
    }

    public void setContainer_title(String container_title) {
        this.container_title = container_title;
    }

    public long getOnline_date() {
        return online_date;
    }

    public void setOnline_date(long online_date) {
        this.online_date = online_date;
    }

    public ArrayList<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(ArrayList<String> keywords) {
        this.keywords = keywords;
    }

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }
    
    
    
    
}
