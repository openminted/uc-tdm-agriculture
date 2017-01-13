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

/**
 *
 * @author Agroknow
 */
public class Author {
    
    private String first_name;
    private String middle_name;
    private String last_name;
    private String email;
    
    private ArrayList<Affiliation> affiliation;
    
    public Author(String first_name, String middle_name, String last_name, ArrayList<Affiliation> affiliation,String email) {
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.last_name = last_name;
        this.affiliation=affiliation;              
        this.email=email;
    }

    public Author(String first_name,String last_name) {
        this.first_name = first_name;       
        this.last_name = last_name;              
    }       

    public Author() {      
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public ArrayList<Affiliation> getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(ArrayList<Affiliation> affiliation) {
        this.affiliation = affiliation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
 
}
