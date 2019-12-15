package com.example.grut;

import com.google.gson.annotations.SerializedName;

public class Plant_item {
    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;

    public Plant_item(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
    //will add more things later

    public Integer getId(){
        return id;
    }

    public String getName(){
        return name;
    }
}
