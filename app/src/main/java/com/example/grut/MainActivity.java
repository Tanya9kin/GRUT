package com.example.grut;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    public PlantListRecyclerAdapter adapter;
//
//    /**
//     *  Client reference
//     **/
//    private MobileServiceClient mClient;
//
//    /**
//     * Table used to access data from the mobile app backend.
//     */
//    private MobileServiceTable<Plant_item> mPlantTable;
//
//    //Offline Sync
//    /**
//     * Table used to store data locally sync with the mobile app backend.
//     */
//    //private MobileServiceSyncTable<ToDoItem> mPlantTable;
//    private PlantListRecyclerAdapter mAdapter;
//    private ProgressBar mProgressBar;

    private TextView mView;

    private Context mContext;

    private RequestQueue mQueue;

    private JSONObject params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mView = findViewById(R.id.plant_test);
        mContext = getApplicationContext();
        mQueue = Volley.newRequestQueue(this);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add a plant", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                addPlant();
            }
        });

        //setUpConnection();
        //setUpRecyclerView();
    }

    public void setUpRequestQueue(){
// Instantiate the RequestQueue.
        mQueue = Volley.newRequestQueue(this);
        String url ="http://www.google.com";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //textView.setText("Response is: "+ response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //textView.setText("That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        mQueue.add(stringRequest);
    }

//    private void setUpConnection() {
//        try {
//            // Create the client instance, using the provided mobile app URL.
//            mClient = new MobileServiceClient(
//                    "https://grut-backend.azurewebsites.net",
//                    this).withFilter(new MainActivity.ProgressFilter());
//
//            // Extend timeout from default of 10s to 20s
//            mClient.setAndroidHttpClientFactory(new OkHttpClientFactory() {
//                @Override
//                public OkHttpClient createOkHttpClient() {
//                    OkHttpClient client = new OkHttpClient.Builder()
//                            .connectTimeout(20, TimeUnit.SECONDS)
//                            .readTimeout(20, TimeUnit.SECONDS)
//                            .build();
//
//                    return client;
//                }
//            });
//
//            // Get the remote table instance to use.
//            mPlantTable = mClient.getTable(Plant_item.class);
//
//            // Offline sync table instance.
//            //mPlantTable = mClient.getSyncTable("ToDoItem", Plant_item.class);
//
//            //Init local storage
//            initLocalStore().get();
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (MobileServiceLocalStoreException e) {
//            e.printStackTrace();
//        }
//    }

    private static void setUpRecyclerView(){
//        Query query = cr_inventoryItems.orderBy("stock",Query.Direction.ASCENDING);
//        FirestoreRecyclerOptions<StoreItem> options = new FirestoreRecyclerOptions.Builder<StoreItem>()
//                .setQuery(query,StoreItem.class).build();
//
        //adapter = new PlantListRecyclerAdapter(options);
//        recyclerView = findViewById(R.id.inventory_recycler);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
//
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

//    public void authenticate(){
//        mQueue.add(new StringRequest(Request.Method.POST,))
//    }

    public void addPlant(){

        Gson gson = new Gson();
        Plant_item plantItem = new Plant_item(10,"testplant");
        String jsonStringParams = gson.toJson(plantItem);
        Log.wtf("JSON WTF", jsonStringParams);

//        StringRequest testStringRequest = new StringRequest(Request.Method.POST, "https://grut-message-endpoint.azurewebsites.net/api/HttpTrigger1", new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });


        JsonObjectRequest jsObjRequest = null;
        try {
            jsObjRequest = new JsonObjectRequest
                    (Request.Method.POST, "https://grut-message-endpoint.azurewebsites.net/api/sqlTest", new JSONObject(jsonStringParams), new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("MY ERROR TAG", "we are here, not error");
                            mView.setText(String.format("Response: %s", response.toString()));
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub
                            mView.setText(error.getMessage());
                            error.printStackTrace();
                            Log.d("MY ERROR TAG", "Error1: " + error.getMessage());
                        }
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

//    /**
//     * Initialize local storage
//     * @return
//     * @throws MobileServiceLocalStoreException
//     * @throws ExecutionException
//     * @throws InterruptedException
//     */
//    private AsyncTask<Void, Void, Void> initLocalStore() throws MobileServiceLocalStoreException, ExecutionException, InterruptedException {
//
//        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected Void doInBackground(Void... params) {
//                try {
//
//                    MobileServiceSyncContext syncContext = mClient.getSyncContext();
//
//                    if (syncContext.isInitialized())
//                        return null;
//
//                    SQLiteLocalStore localStore = new SQLiteLocalStore(mClient.getContext(), "OfflineStore", null, 1);
//
//                    Map<String, ColumnDataType> tableDefinition = new HashMap<String, ColumnDataType>();
//                    tableDefinition.put("id", ColumnDataType.String);
//                    tableDefinition.put("text", ColumnDataType.String);
//                    tableDefinition.put("complete", ColumnDataType.Boolean);
//
//                    localStore.defineTable("ToDoItem", tableDefinition);
//
//                    SimpleSyncHandler handler = new SimpleSyncHandler();
//
//                    syncContext.initialize(localStore, handler).get();
//
//                } catch (final Exception e) {
//                    createAndShowDialogFromTask(e, "Error");
//                }
//
//                return null;
//            }
//        };
//
//        return runAsyncTask(task);
//    }
//
//    /**
//     * Creates a dialog and shows it
//     *
//     * @param exception
//     *            The exception to show in the dialog
//     * @param title
//     *            The dialog title
//     */
//    private void createAndShowDialogFromTask(final Exception exception, String title) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                createAndShowDialog(exception, "Error");
//            }
//        });
//    }
//
//    /**
//     * Creates a dialog and shows it
//     *
//     * @param exception
//     *            The exception to show in the dialog
//     * @param title
//     *            The dialog title
//     */
//    private void createAndShowDialog(Exception exception, String title) {
//        Throwable ex = exception;
//        if(exception.getCause() != null){
//            ex = exception.getCause();
//        }
//        createAndShowDialog(ex.getMessage(), title);
//    }
//
//    /**
//     * Creates a dialog and shows it
//     *
//     * @param message
//     *            The dialog message
//     * @param title
//     *            The dialog title
//     */
//    private void createAndShowDialog(final String message, final String title) {
//        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        builder.setMessage(message);
//        builder.setTitle(title);
//        builder.create().show();
//    }
//
//    /**
//     * Run an ASync task on the corresponding executor
//     * @param task
//     * @return
//     */
//    private AsyncTask<Void, Void, Void> runAsyncTask(AsyncTask<Void, Void, Void> task) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//            return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//        } else {
//            return task.execute();
//        }
//    }
//
//    private class ProgressFilter implements ServiceFilter {
//
//        @Override
//        public ListenableFuture<ServiceFilterResponse> handleRequest(ServiceFilterRequest request, NextServiceFilterCallback nextServiceFilterCallback) {
//
//            final SettableFuture<ServiceFilterResponse> resultFuture = SettableFuture.create();
//
//
//            runOnUiThread(new Runnable() {
//
//                @Override
//                public void run() {
//                    if (mProgressBar != null) mProgressBar.setVisibility(ProgressBar.VISIBLE);
//                }
//            });
//
//            ListenableFuture<ServiceFilterResponse> future = nextServiceFilterCallback.onNext(request);
//
//            Futures.addCallback(future, new FutureCallback<ServiceFilterResponse>() {
//                @Override
//                public void onFailure(Throwable e) {
//                    resultFuture.setException(e);
//                }
//
//                @Override
//                public void onSuccess(ServiceFilterResponse response) {
//                    runOnUiThread(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            if (mProgressBar != null) mProgressBar.setVisibility(ProgressBar.GONE);
//                        }
//                    });
//
//                    resultFuture.set(response);
//                }
//            });
//
//            return resultFuture;
//        }
//    }
}
