package com.example.grut;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class MainActivity extends AppCompatActivity {


    private Context mContext;

    /**
     * recycler view stuff
     */
    private PlantListRecyclerAdapter adapter;
    private RecyclerView rv_plantsList;
    private List<Plant_item> lst_plants;
    /**
     * For Connection to Azure
     */
    private RequestQueue mQueue;
    private JSONObject params;

    /**
     * For popup to add new plant
     */
    private Dialog popUpAddItem;
    private EditText et_name;
    private Spinner sp_type;
    private Button btn_confirm;
    private Button btn_cancel;
    private TextView tv_id;
    private Button btn_readNFC;
    private EditText et_temp_id;

    /**
        NFC
     */
    //private NfcAdapter nfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        popUpAddItem = new Dialog(this);
        mContext = getApplicationContext();
        mQueue = Volley.newRequestQueue(this);

        lst_plants = new ArrayList<>();
        getPlants();

        //nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setImageDrawable(getDrawable(R.drawable.ic_add_white_24dp));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
//                if(nfcAdapter != null && nfcAdapter.isEnabled()){
//                    Toast.makeText(getApplicationContext(),"NFC availiable",Toast.LENGTH_SHORT).show();
//                    openDialog();
//                } else {
//                    Toast.makeText(getApplicationContext(),"NFC not availiable, open NFC to be able to add plant",Toast.LENGTH_LONG).show();
//                }

            }
        });
    }


    /**
     * Dialog for adding a new plant
     */
    private void openDialog() {
        popUpAddItem.setContentView(R.layout.popup_add_item);
        et_name = popUpAddItem.findViewById(R.id.plant_name);
        sp_type = popUpAddItem.findViewById(R.id.plant_type);
        btn_cancel = popUpAddItem.findViewById(R.id.cancel);
        btn_confirm = popUpAddItem.findViewById(R.id.confirm);
        tv_id = popUpAddItem.findViewById(R.id.nfcID);
        btn_readNFC = popUpAddItem.findViewById(R.id.nfcButton);
        tv_id.setVisibility(View.INVISIBLE);
        btn_readNFC.setVisibility(View.INVISIBLE);
        et_temp_id = popUpAddItem.findViewById(R.id.temp_plant_id);

//        btn_readNFC.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String tagContent = null;
////                try {
////                    byte[] payload = "what".getBytes();
////                } catch (UnsupportedEncodingException e) {
////
////                }
//            }
//        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = String.valueOf(et_name.getText());
                String type = String.valueOf(sp_type.getSelectedItem());
                String id = String.valueOf(et_temp_id.getText());
                if(name.length() < 4 || id.length() < 1 || sp_type.getSelectedItem() == null){
                    Toast.makeText(mContext, "Some required fields are not complete", Toast.LENGTH_SHORT).show();
                } else {
                    addPlant(id,name,type);
                    popUpAddItem.dismiss();
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpAddItem.dismiss();
            }
        });

        String[] types = getResources().getStringArray(R.array.plant_type);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, types);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_type.setAdapter(spinnerAdapter);

        popUpAddItem.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popUpAddItem.show();
    }

    //    public void setUpRequestQueue(){
//// Instantiate the RequestQueue.
//        mQueue = Volley.newRequestQueue(this);
//        String url ="http://www.google.com";
//
//// Request a string response from the provided URL.
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        // Display the first 500 characters of the response string.
//                        //textView.setText("Response is: "+ response.substring(0,500));
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                //textView.setText("That didn't work!");
//            }
//        });

// Add the request to the RequestQueue.
//        mQueue.add(stringRequest);
//    }

    private void setUpRecyclerView(){
        rv_plantsList = findViewById(R.id.plants_list);
        adapter = new PlantListRecyclerAdapter(getApplicationContext(),lst_plants);
        rv_plantsList.setAdapter(adapter);
        rv_plantsList.setLayoutManager(new LinearLayoutManager(getApplication()));
        rv_plantsList.setHasFixedSize(true);

        adapter.setOnItemClickListener(new PlantListRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View itemView) {
                Intent toSinglePlant = new Intent(getApplicationContext(), PlantOverview.class);
                Plant_item currentPlant = adapter.mData.get(position);
                toSinglePlant.putExtra("id", currentPlant.getId());
                toSinglePlant.putExtra("name", currentPlant.getName());
                toSinglePlant.putExtra("type", currentPlant.getType());
                toSinglePlant.putExtra("currTemp", currentPlant.getCurrTemp());
                toSinglePlant.putExtra("currLight", currentPlant.getCurrLight());
                toSinglePlant.putExtra("currMoist", currentPlant.getCurrMoist());
                toSinglePlant.putExtra("optTemp", currentPlant.getOptTemp());
                toSinglePlant.putExtra("optLight", currentPlant.getOptLight());
                toSinglePlant.putExtra("optMoist", currentPlant.getOptMoist());
                startActivity(toSinglePlant);
            }
        });
    }


    public void getPlants(){
        Gson gson = new Gson();
        //AtomicReference<Boolean> success_flag = new AtomicReference<>(false);
        //while (!success_flag.get()) {
            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.GET, "https://grut-message-endpoint.azurewebsites.net/api/FetchAllPlants", null, response -> {
                        Log.wtf("GET Response", response.toString());
                        JSONArray recordsets = new JSONArray();
                        int length = 0;
                        try {
                            recordsets = response.getJSONArray("recordsets");
                            length = recordsets.getJSONArray(0).length();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        JSONObject tempObject = new JSONObject();
                        for (int i = 0; i < length; i++) {
                            Plant_item temp;
                            try {
                                tempObject = recordsets.getJSONArray(0).getJSONObject(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            temp = gson.fromJson(tempObject.toString(), (Type) Plant_item.class);
                            lst_plants.add(temp);
                            try {
                                Log.wtf("JSON OBJECT", tempObject.getString("plantName"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        //success_flag.set(true);
                        setUpRecyclerView();
                    }, error -> {
                        Log.wtf("WTF on Error", error.getMessage());
                        Toast.makeText(this,"Couldn't get plants, please try closing and opening the app",Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                    );
            mQueue.add(jsObjRequest);
        //}
         //Access the RequestQueue through your singleton class.


    }

    public void addPlant(String id, String name, String type){
        Gson gson = new Gson();

        Plant_item plantItem = new Plant_item(id ,name, type,0,0,0,0,0,0);
        String jsonStringParams = gson.toJson(plantItem);

        JsonObjectRequest jsObjRequest = null;
        try {
            jsObjRequest = new JsonObjectRequest
                    (Request.Method.POST, "https://grut-message-endpoint.azurewebsites.net/api/sqlTest", new JSONObject(jsonStringParams), response -> {
                        Log.wtf("WTF onResponse",response.toString());
                        Log.wtf("WTF onResponse",jsonStringParams);
                        lst_plants.add(plantItem);
                        adapter.notifyDataSetChanged();
                    }, error -> {
                        Toast.makeText(this,"Server Error - couldn't add plant :(\nPlease try again later",Toast.LENGTH_SHORT).show();
                        Log.wtf("WTF on Error",error.getMessage());
                        error.printStackTrace();
                    }
                    );
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Access the RequestQueue through your singleton class.
        mQueue.add(jsObjRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            Intent to_about = new Intent(getApplicationContext(), About.class);
            startActivity(to_about);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
