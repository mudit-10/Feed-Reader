package com.example.mudit.sententia;

/**
 * Created by mudit on 21/6/17.
 */

public class Post {
    private String title;
    private String creator;
    private String pubDate;
    private String content;
    private String link;

    public Post(String title, String creator, String pubDate, String content, String link) {
        this.title = title;
        this.creator = creator;
        this.pubDate = pubDate;
        this.content = content;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
