package com.example.grut;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PlantOverview extends AppCompatActivity {

    //private RequestQueue mQueue;

    //private String id;

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

        //id = getIntent().getStringExtra("id");

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

        Bundle extras = getIntent().getExtras();
        tv_name.setText(extras.getString("name"));
        tv_type.setText(extras.getString("type"));
        setLightIcon(iv_currLight, extras.getInt("currLight"));
        setMoistIcon(iv_currMoist,extras.getInt("currMoist"));
        setTempIcon(iv_currTemp,extras.getInt("currTemp"));
        setLightIcon(iv_reqLight, extras.getInt("optLight"));
        setMoistIcon(iv_reqMoist,extras.getInt("optMoist"));
        setTempIcon(iv_reqTemp,extras.getInt("optTemp"));

        //mQueue = Volley.newRequestQueue(this);

        //getPlantData();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.hide();
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

//        Gson gson = new Gson();
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("id", id);
//        JSONObject jsonObj = new JSONObject(params);
//        Log.wtf("ingetplantdata", id);
//        JsonObjectRequest jsObjRequest = new JsonObjectRequest
//                (Request.Method.GET, "https://grut-message-endpoint.azurewebsites.net/api/FetchPlant", null , response -> {
//                    //TODO Reformat the data here
//                    Log.wtf("GET Response", response.toString());
//                    try {
//
//                        //response.getString("id"); - not needed this is only fo the DB
//                        tv_name.setText(response.getString("plantName"));
//                        tv_type.setText(response.getString("type"));
//                        setLightIcon(iv_currLight, response.getInt("currLight"));
//                        setMoistIcon(iv_currMoist,response.getInt("currMoist"));
//                        setTempIcon(iv_currTemp,response.getInt("currTemp"));
//                        setLightIcon(iv_reqLight, response.getInt("optLight"));
//                        setMoistIcon(iv_reqMoist,response.getInt("optMoist"));
//                        setTempIcon(iv_reqTemp,response.getInt("optTemp"));
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }, error -> {
//                    Log.wtf("WTF on Error",error.getMessage());
//                    error.printStackTrace();
//                }
//                );
//
//        //Access the RequestQueue through your singleton class.
//        mQueue.add(jsObjRequest);

    }

    private void setLightIcon(ImageView iv_light, int light) {
        if(light < 100){
            iv_light.setImageResource(R.drawable.ic_033_super_cloudy);
        } else if (light < 200) {
            iv_light.setImageResource(R.drawable.ic_011_cloudy);
        } else if (light < 300) {
            iv_light.setImageResource(R.drawable.ic_044_sun);
        } else if (light >500) {
            iv_light.setImageResource(R.drawable.ic_005_hot);
        }
    }

    private void setMoistIcon(ImageView iv_moist, int moist) {
        if(moist < 300){
            iv_moist.setImageResource(R.drawable.ic_025_flood);
        } else if (moist < 400) {
            iv_moist.setImageResource(R.drawable.ic_020_drop);
        } else if (moist < 500) {
            iv_moist.setImageResource(R.drawable.ic_009_drops);
        } else if (moist >= 500) {
            iv_moist.setImageResource(R.drawable.ic_050_drought);
        }
    }

    private void setTempIcon(ImageView iv_temp, int temp) {
        if(temp < 0){
            iv_temp.setImageResource(R.drawable.ic_008_snowflake);
        } else if (temp < 10) {
            iv_temp.setImageResource(R.drawable.ic_027_cold);
        } else if (temp < 30) {
            iv_temp.setImageResource(R.drawable.ic_017_warm_1);
        } else {
            iv_temp.setImageResource(R.drawable.ic_007_super_hot);
        }
    }

}
