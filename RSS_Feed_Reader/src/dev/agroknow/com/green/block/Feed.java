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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Agroknow
 */
public class Feed {
    final String title;
    final String link;
    final String description;
    final String language;
    final String copyright;
    final String pubDate;
    String original_link="";

    final List<FeedInstance> entries = new ArrayList<FeedInstance>();

    public Feed(String title, String link, String description, String language,
                    String copyright, String pubDate) {
            this.title = title;
            this.link = link;
            this.description = description;
            this.language = language;
            this.copyright = copyright;
            this.pubDate = pubDate;
    }

    public Feed(String title, String link, String description, String language,String copyright, String pubDate,String original_link) {
            this.title = title;
            this.link = link;
            this.description = description;
            this.language = language;
            this.copyright = copyright;
            this.pubDate = pubDate;
            this.original_link=original_link;
    }

    public List<FeedInstance> getMessages() {
            return entries;
    }

    public String getTitle() {
            return title;
    }

    public String getLink() {
            return link;
    }

    public String getDescription() {
            return description;
    }

    public String getLanguage() {
            return language;
    }

    public String getCopyright() {
            return copyright;
    }

    public String getPubDate() {
            return pubDate;
    }

    public String getOriginal_link() {
        return original_link;
    }

    public void setOriginal_link(String original_link) {
        this.original_link = original_link;
    }
        
}
