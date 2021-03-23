package com.example.achoo;

public class PdfMaker {
    public String key;
    public String name;
    public String url;

    public PdfMaker(){}

    public PdfMaker(String key, String name, String url) {
        this.key = key;
        this.name = name;
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
