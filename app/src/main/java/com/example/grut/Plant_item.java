package com.example.grut;

import com.google.gson.annotations.SerializedName;

public class Plant_item {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("type")
    private String type;
    @SerializedName("optTemp")
    private Integer optTemp;
    @SerializedName("optLight")
    private Integer optLight;
    @SerializedName("optMoist")
    private Integer optMoist;
    @SerializedName("currTemp")
    private Integer currTemp;
    @SerializedName("currLight")
    private Integer currLight;
    @SerializedName("currMoist")
    private Integer currMoist;

    public Plant_item(String id, String name, String type,
                      Integer optTemp, Integer optLight, Integer optMoist,
                      Integer  currTemp, Integer currLight, Integer currMoist) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.optTemp = optTemp;
        this.optLight = optLight;
        this.optMoist = optMoist;
        this.currLight = currLight;
        this.currMoist = currMoist;
        this.currTemp = currTemp;
    }
    //will add more things later

    //Getters:

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public Integer getOptTemp() {
        return optTemp;
    }

    public Integer getOptLight() {
        return optLight;
    }

    public Integer getOptMoist() {
        return optMoist;
    }

    public Integer getCurrTemp() {
        return currTemp;
    }

    public Integer getCurrLight() {
        return currLight;
    }

    public Integer getCurrMoist() {
        return currMoist;
    }

    public String getType() {
        return type;
    }

    //Setters:


    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setOptTemp(Integer optTemp) {
        this.optTemp = optTemp;
    }

    public void setOptLight(Integer optLight) {
        this.optLight = optLight;
    }

    public void setOptMoist(Integer optMoist) {
        this.optMoist = optMoist;
    }

    public void setCurrTemp(Integer currTemp) {
        this.currTemp = currTemp;
    }

    public void setCurrLight(Integer currLight) {
        this.currLight = currLight;
    }

    public void setCurrMoist(Integer currMoist) {
        this.currMoist = currMoist;
    }
}
