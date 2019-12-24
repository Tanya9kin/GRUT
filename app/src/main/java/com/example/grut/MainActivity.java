package com.example.grut;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.http.OkHttpClientFactory;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.sync.MobileServiceSyncContext;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.ColumnDataType;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.MobileServiceLocalStoreException;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.SQLiteLocalStore;
import com.microsoft.windowsazure.mobileservices.table.sync.synchandler.SimpleSyncHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

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

        //setUpRecyclerView();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setImageDrawable(getDrawable(R.drawable.ic_add_white_24dp));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
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

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = String.valueOf(et_name.getText());
                String type = String.valueOf(sp_type.getSelectedItem());
                if(name.length() < 4){
                    Toast.makeText(mContext, "Name should be 4 characters or longer", Toast.LENGTH_SHORT).show();

                } else if(sp_type.getSelectedItem() == null) {
                    Toast.makeText(mContext, "Choose a type for the plant", Toast.LENGTH_SHORT).show();
                } else {
                    addPlant(name,type);
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

//        adapter.setOnItemClickListener(new InventoryRecyclerAdapter.OnItemClickListener() {
//            @Override
//            public void onPlusClick(int position,View itemView) {
//                StoreItem currItem = adapter.getItem(position);
//                EditText et_addAmount = itemView.findViewById(R.id.add_amount);
//
//                final Integer oldAmount = currItem.getStock();
//                final Integer amountToAdd;
//                if(et_addAmount.getText().toString().isEmpty()){
//                    amountToAdd = 0;
//                } else {
//                    amountToAdd = Integer.parseInt(et_addAmount.getText().toString());
//                }
//
//                if(amountToAdd <= 0 || amountToAdd > 10000){
//                    //Toast.makeText(getApplicationContext(),"Amount is invalid!",Toast.LENGTH_SHORT).show();
//                    Snackbar.make(findViewById(R.id.screen_inventory),"Amount is invalid!", Snackbar.LENGTH_SHORT).show();
//                    return;
//                }
//
//                cr_inventoryItems.whereEqualTo("name", currItem.getName()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        queryDocumentSnapshots.getDocuments().get(0).getReference().update("stock",amountToAdd + oldAmount).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                //Toast.makeText(getApplicationContext(),"Stock succesfully updated!",Toast.LENGTH_SHORT).show();
//                                Snackbar.make(findViewById(R.id.screen_inventory),"Stock succesfully updated!", Snackbar.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//                });
//                adapter.notifyItemChanged(position);
//                et_addAmount.setText("");
//            }
//
//            @Override
//            public boolean onLongClick(int position) {
//                /**Edit item popup goes up and gives the manager the option to edit a chosen item**/
//                StoreItem currItem = adapter.getItem(position);
//                editItemPopup(position);
//                adapter.notifyItemChanged(position);
//                return false;
//            }
//        });
    }

    public void getPlants(){
        Gson gson = new Gson();
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
//                (Request.Method.GET, "https://grut-message-endpoint.azurewebsites.net/api/FetchAllPlants", null, response -> {
//                    Log.wtf("GET Response", response.toString());
//                    JSONArray tempArray = null;
//                    try {
//                        tempArray = new JSONArray(response);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    try {
//                        tempArray = response.getJSONArray(0);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    for(int i=0; i<response.length();i++){
//                        Plant_item temp;
//                        try {
//                            //Log.wtf("I AM HERE","");
//                            JSONObject temoObj = tempArray.getJSONObject(i);
//                            //Log.wtf("I AM HERE","");
//                            temp = gson.fromJson(temoObj.toString(), (Type) Plant_item.class);
//                            lst_plants.add(temp);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    setUpRecyclerView();
//                }, error -> {
//                    Log.wtf("GET WTF on Error",error.getMessage());
//                    error.printStackTrace();
//                }
//                );
//        mQueue.add(jsonArrayRequest);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, "https://grut-message-endpoint.azurewebsites.net/api/FetchAllPlants", null , response -> {
                    //TODO Reformat the data here
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
                    for(int i=0 ; i < length ; i++){
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
                    setUpRecyclerView();
                    }, error -> {
                    Log.wtf("WTF on Error",error.getMessage());
                    error.printStackTrace();
                }
                );

         //Access the RequestQueue through your singleton class.
        mQueue.add(jsObjRequest);

    }

    public void addPlant(String name, String type){
        Gson gson = new Gson();

        Plant_item plantItem = new Plant_item(Long.toString(System.currentTimeMillis()) ,name, type,0,0,0,0,0,0);
        String jsonStringParams = gson.toJson(plantItem);

        JsonObjectRequest jsObjRequest = null;
        try {
            jsObjRequest = new JsonObjectRequest
                    (Request.Method.POST, "https://grut-message-endpoint.azurewebsites.net/api/sqlTest", new JSONObject(jsonStringParams), response -> {
                        Log.wtf("WTF onResponse",response.toString());
                        Log.wtf("WTF onResponse",jsonStringParams);
                        //TODO inform the recycler view about the addition of a plant
                        lst_plants.add(plantItem);
                        adapter.notifyDataSetChanged();
                    }, error -> {
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
