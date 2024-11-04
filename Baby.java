package com.example.myapplication;

public class Baby {
    private String babyNum;
    private String babyName;
    private String babyBirth;
    private String babyBlood;
    private String babyGender;
    private String babyDetail;

    public Baby(String babyNum, String babyName, String babyBirth, String babyBlood, String babyGender) {
        this.babyNum = babyNum;
        this.babyName = babyName;
        this.babyBirth = babyBirth;
        this.babyBlood = babyBlood;
        this.babyGender = babyGender;
    }

    public Baby(String babyNum, String babyName, String babyBirth, String babyBlood, String babyGender, String babyDetail) {
        this.babyNum = babyNum;
        this.babyName = babyName;
        this.babyBirth = babyBirth;
        this.babyBlood = babyBlood;
        this.babyGender = babyGender;
        this.babyDetail = babyDetail;
    }

    public String getBabyNum() { return babyNum; }
    public String getBabyName() { return babyName; }
    public String getBabyBirth() { return babyBirth; }
    public String getBabyBlood() { return babyBlood; }
    public String getBabyGender() { return babyGender; }
    public String getBabyDetail() {
        return babyDetail;
    }
}
