package com.softeq.input;

public class Link {

    private String url;
    private final int urlDepth;

    public Link(String url, int urlDepth) {
        this.url = url;
        this.urlDepth = urlDepth;
    }
    public String getUrl() {
        return url;
    }

    public int getUrlDepth() {
        return urlDepth;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Link{" +
            "url='" + url + '\'' +
            ", urlDepth=" + urlDepth +
            '}';
    }
}
