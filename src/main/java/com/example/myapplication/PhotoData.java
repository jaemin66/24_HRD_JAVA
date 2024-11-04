package com.example.myapplication;

public class PhotoData {
    private byte[] imageData;
    private String date;

    public PhotoData(byte[] imageData, String date) {
        this.imageData = imageData;
        this.date = date;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public String getDate() {
        return date;
    }
}
