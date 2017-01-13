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
package dev.agroknow.com.green.block;

/**
 *
 * @author Agroknow
 */
public class FeedInstance {
    String title;
    String description;
    String link;
    String author;
    String guid;
    String pub_date;

    public String getTitle() {
            return title;
    }

    public void setTitle(String title) {
            this.title = title;
    }

    public String getDescription() {
            return description;
    }

    public void setDescription(String description) {
            this.description = description;
    }

    public String getLink() {
            return link;
    }

    public void setLink(String link) {
            this.link = link;
    }

    public String getAuthor() {
            return author;
    }

    public void setAuthor(String author) {
            this.author = author;
    }

    public String getGuid() {
            return guid;
    }

    public void setGuid(String guid) {
            this.guid = guid;
    }

    public String getPub_date() {
        return pub_date;
    }

    public void setPub_date(String pub_date) {
        this.pub_date = pub_date;
    }
        
    
}
