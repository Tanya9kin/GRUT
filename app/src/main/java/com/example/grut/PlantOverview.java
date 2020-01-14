package com.example.grut;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;

public class PlantOverview extends AppCompatActivity {

    private RequestQueue mQueue;

    private TextView tv_name;
    private TextView tv_type;
    private TextView tv_requirements;
    private TextView tv_state;
    private TextView tv_recommendation;
    private ImageView iv_reqTemp;
    private ImageView iv_currTemp;
    private ImageView iv_reqMoist;
    private ImageView iv_currMoist;
    private ImageView iv_reqLight;
    private ImageView iv_currLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_overview);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tv_name = findViewById(R.id.name);
        tv_type = findViewById(R.id.type);
        tv_requirements = findViewById(R.id.requirements);
        tv_state = findViewById(R.id.state);
        tv_recommendation = findViewById(R.id.recommendation);
        iv_reqTemp = findViewById(R.id.requirements_temp);
        iv_currTemp = findViewById(R.id.state_temp);
        iv_reqMoist = findViewById(R.id.requirements_moist);
        iv_currMoist = findViewById(R.id.state_moist);
        iv_reqLight = findViewById(R.id.requirements_sun);
        iv_currLight = findViewById(R.id.state_sun);

        mQueue = Volley.newRequestQueue(this);

        getPlantData();
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void getPlantData() {
        Gson gson = new Gson();
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, "https://grut-message-endpoint.azurewebsites.net/api/FetchPlant", null , response -> {
                    //TODO Reformat the data here
                    Log.wtf("GET Response", response.toString());
                    try {
                        //response.getString("id"); - not needed this is only fo the DB
                        tv_name.setText(response.getString("plantName"));
                        tv_type.setText(response.getString("type"));
                        setLightIcon(iv_currLight, response.getInt("currLight"));
                        setMoistIcon(iv_currMoist,response.getInt("currMoist"));
                        setTempIcon(iv_currTemp,response.getInt("currTemp"));
                        setLightIcon(iv_reqLight, response.getInt("optLight"));
                        setMoistIcon(iv_reqMoist,response.getInt("optMoist"));
                        setTempIcon(iv_reqTemp,response.getInt("optTemp"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //JSONArray recordsets = new JSONArray();
                    //int length = 0;
//                    try {
//                        recordsets = response.getJSONArray("recordsets");
//                        length = recordsets.getJSONArray(0).length();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    JSONObject tempObject = new JSONObject();
//                    for(int i=0 ; i < length ; i++){
//                        Plant_item temp;
//                        try {
//                            tempObject = recordsets.getJSONArray(0).getJSONObject(i);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        temp = gson.fromJson(tempObject.toString(), (Type) Plant_item.class);
//                        lst_plants.add(temp);
//                        try {
//                            Log.wtf("JSON OBJECT", tempObject.getString("plantName"));
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
                }, error -> {
                    Log.wtf("WTF on Error",error.getMessage());
                    error.printStackTrace();
                }
                );

        //Access the RequestQueue through your singleton class.
        mQueue.add(jsObjRequest);

    }

    private void setLightIcon(ImageView iv_light, int light) {
        if(light < 100){
            iv_light.setImageResource(R.drawable.ic_001_cloudy);
        } else if (light < 200) {
            iv_light.setImageResource(R.drawable.ic_001_cloudy);
        } else if (light < 300) {
            iv_light.setImageResource(R.drawable.ic_022_sun);
        } else if (light >500) {
            iv_light.setImageResource(R.drawable.ic_022_sun);
        }
    }

    private void setMoistIcon(ImageView iv_moist, int moist) {
        if(moist < 300){
            iv_moist.setImageResource(R.drawable.ic_026_flood);
        } else if (moist < 400) {
            iv_moist.setImageResource(R.drawable.ic_007_drops);
        } else if (moist < 500) {
            iv_moist.setImageResource(R.drawable.ic_007_drops);
        } else if (moist >= 500) {
            iv_moist.setImageResource(R.drawable.ic_007_drops);
        }
    }

    private void setTempIcon(ImageView iv_temp, int temp) {
        if(temp < 0){
            iv_temp.setImageResource(R.drawable.ic_020_thermometer);
        } else if (temp < 10) {
            iv_temp.setImageResource(R.drawable.ic_020_thermometer);
        } else if (temp < 20) {
            iv_temp.setImageResource(R.drawable.ic_020_thermometer);
        } else if (temp < 30) {
            iv_temp.setImageResource(R.drawable.ic_020_thermometer);
        } else if (temp >= 30) {
            iv_temp.setImageResource(R.drawable.ic_020_thermometer);
        }
    }

}