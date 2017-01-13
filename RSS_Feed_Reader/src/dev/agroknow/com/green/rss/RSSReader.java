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
package dev.agroknow.com.green.rss;

import dev.agroknow.com.green.block.Feed;
import dev.agroknow.com.green.block.FeedInstance;
import dev.agroknow.com.green.block.FeedParser;
import dev.agroknow.com.green.block.FeedWrite;
import java.io.File;
import static java.lang.System.exit;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;

/**
 *
 * @author Agroknow
 */
public class RSSReader {
     public static void main(String[] args) {
         
        String path="";
        String filename="";
        
        
        if(args.length<1){
            System.out.println("No Arguments");
            exit(0);
        
        }else{
            path=args[0];
        } 
         
        FeedParser parser_water = new FeedParser("http://feeds.feedburner.com/drinking-water-rss?format=xml");
        Feed feed_water = parser_water.readFeed();
        for (FeedInstance message : feed_water.getMessages()) {           
            
            String author = message.getAuthor();
            String title = message.getTitle();
            String description = message.getDescription();
            String original_link = message.getGuid();
            String rss_link = message.getLink();         
            String pub_date=message.getPub_date();
            
            Feed rssFeeder = new Feed(title, rss_link, description, "EN","", pub_date,original_link);
            
            FeedInstance feed = new FeedInstance();
            feed.setTitle(title);
            feed.setDescription(description);
            feed.setAuthor(author);
            feed.setGuid(original_link);
            feed.setLink(rss_link);
            feed.setPub_date(pub_date);
            rssFeeder.getMessages().add(feed);

            // now write the file
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());            
            FeedWrite writer = new FeedWrite(rssFeeder, "WaterWorld"+timestamp.getTime()+".rss");
            try {
                writer.write();
            } catch (Exception e) {
                    e.printStackTrace();
            }
        } 
        
        FeedParser parser_food = new FeedParser("http://feeds.lexblog.com/foodsafetynews/mRcs");
        Feed feed_food = parser_food.readFeed();
        for (FeedInstance message : feed_food.getMessages()) {           
            
            String author = message.getAuthor();
            String title = message.getTitle();
            String description = message.getDescription();
            String original_link = message.getGuid();
            String rss_link = message.getLink();         
            String pub_date=message.getPub_date();
            
            Feed rssFeeder = new Feed(title, rss_link, description, "EN","", pub_date,original_link);
            
            FeedInstance feed = new FeedInstance();
            feed.setTitle(title);
            feed.setDescription(description);
            feed.setAuthor(author);
            feed.setGuid(original_link);
            feed.setLink(rss_link);
            feed.setPub_date(pub_date);
            rssFeeder.getMessages().add(feed);

            // now write the file
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            FeedWrite writer = new FeedWrite(rssFeeder, "FoodSafety"+timestamp.getTime()+".rss");
            try {
                writer.write();
            } catch (Exception e) {
                    e.printStackTrace();
            }
        } 
    }
    
}
