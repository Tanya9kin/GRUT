package com.example.grut;

import com.google.gson.annotations.SerializedName;

public class Plant_item {
    @SerializedName("id")
    private String id;
    @SerializedName("plantName")
    private String plantName;
    @SerializedName("plantTypes")
    private String plantTypes;
    @SerializedName("optTemp")
    private Integer optTemp;
    @SerializedName("optLight")
    private Integer optLight;
    @SerializedName("optrMoist")
    private Integer optrMoist;
    @SerializedName("currTemp")
    private Integer currTemp;
    @SerializedName("currLight")
    private Integer currLight;
    @SerializedName("currMoist")
    private Integer currMoist;

    public Plant_item(String id, String name, String plantTypes,
                      Integer optTemp, Integer optLight, Integer optMoistr,
                      Integer  currTemp, Integer currLight, Integer currMoist) {
        this.id = id;
        this.plantName = name;
        this.plantTypes = plantTypes;
        this.optTemp = optTemp;
        this.optLight = optLight;
        this.optrMoist = optrMoist;
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
        return plantName;
    }

    public Integer getOptTemp() {
        return optTemp;
    }

    public Integer getOptLight() {
        return optLight;
    }

    public Integer getOptMoist() {
        return optrMoist;
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
        return plantTypes;
    }

    //Setters:


    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.plantName = name;
    }

    public void setType(String type) {
        this.plantTypes = type;
    }

    public void setOptTemp(Integer optTemp) {
        this.optTemp = optTemp;
    }

    public void setOptLight(Integer optLight) {
        this.optLight = optLight;
    }

    public void setOptMoist(Integer optrMoist) {
        this.optrMoist = optrMoist;
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
