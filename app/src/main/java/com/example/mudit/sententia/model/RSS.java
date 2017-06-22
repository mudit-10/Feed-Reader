package com.example.mudit.sententia.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Created by mudit on 20/6/17.
 */

@Root(name = "rss", strict=false) // If we're skipping something
public class RSS implements Serializable {
    private static final String TAG = "rss";

    @Element(required = false,name = "channel")
    public Channel channel;

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        return "RSS{" +
                "channel='" + channel + '\'' +
                '}';
    }
}
