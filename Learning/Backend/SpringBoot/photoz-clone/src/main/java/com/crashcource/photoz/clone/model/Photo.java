package com.crashcource.photoz.clone.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotEmpty;

public class Photo {

    private String id;

    @NotEmpty
    private String fileName;

    private String contentType;

    @JsonIgnore
    private byte[] data;

    public Photo() {
        this.id = null;
        this.fileName = null;
        this.contentType = null;
    }
    public Photo(String id, String fileName) {
        this.id = id;
        this.fileName = fileName;
        this.contentType = contentType;
    }

    //raw data

    

    //Getters
    public String getId() {
        return id;
    }
    public String getFileName() {
        return fileName;
    }
    public byte[] getData() {
        return data;
    }
    public String getContentType() {
        return contentType;
    }

    //Setters
    public void setId(String id) {
        this.id = id;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public void setData(byte[] data) {
        this.data = data;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    
}
