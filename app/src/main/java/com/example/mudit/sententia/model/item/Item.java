package com.example.mudit.sententia.model.item;

import android.util.Log;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mudit on 20/6/17.
 */

@Root(name = "item", strict=false) // If we're skipping something
public class Item implements Serializable {

    @Element(required = false, name = "title")
    private String title;

    @Element(required = false, name = "pubDate")
    private String pubDate;

    @Element(required = false, name = "creator")
    private String creator;

    @Element(required = false, name = "encoded")
    private String content;

    //Default constructors

    public Item() {
    }

    public Item(String title, String pubDate, String creator, String content) {
        this.title = title;
        this.pubDate = pubDate;
        this.creator = creator;
        this.content = content;
    }

    //Getters and Setters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Item{" +
                "title='" + title + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", creator='" + creator + '\'' +
                ", content='" + content + '\'' +
                '}'+ "\n" +
                "--------------------------------------------------------------------------------------------------------------------- \n";
    }
}
