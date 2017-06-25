package com.example.mudit.sententia.model;

import android.util.Log;

import com.example.mudit.sententia.model.item.Item;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mudit on 20/6/17.
 */

@Root(name = "channel", strict=false) // If we're skipping something
public class Channel implements Serializable {

//    @Element(required = false, name = "title")
//    private String head_title;

    @ElementList(name = "item", inline = true, required = false)
    // @Element for a single element within the tags and @ElementList for a list
    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

//    public String getHead_title() {
//        return head_title;
//    }
//
//    public void setHead_title(String head_title) {
//        this.head_title = head_title;
//    }


    @Override
    public String toString() {
        return "Channel{" +
                "items=" + items +
                '}';
    }
}
