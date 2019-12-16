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
}
