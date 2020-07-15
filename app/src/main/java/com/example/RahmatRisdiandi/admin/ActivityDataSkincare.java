package com.example.RahmatRisdiandi.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.RahmatRisdiandi.R;
import com.example.RahmatRisdiandi.adapter.AdapterSkincare;
import com.example.RahmatRisdiandi.model.ModelSkincare;
import com.example.RahmatRisdiandi.server.BaseURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ActivityDataSkincare extends AppCompatActivity {

    ProgressDialog pDialog;

    AdapterSkincare adapter;
    ListView list;

    ArrayList<ModelSkincare> newsList = new ArrayList<ModelSkincare>();
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_skincare);

        getSupportActionBar().setTitle("Data Skincare");
        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        list = (ListView) findViewById(R.id.array_list);
        newsList.clear();
        adapter = new AdapterSkincare(ActivityDataSkincare.this, newsList);
        list.setAdapter(adapter);
        getAllSkincare();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ActivityDataSkincare.this, HomeAdminActivity.class);
        startActivity(i);
        finish();
    }

    private void getAllSkincare() {
        // Pass second argument as "null" for GET requests
        pDialog.setMessage("Loading");
        showDialog();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, BaseURL.dataSkincare, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            boolean status = response.getBoolean("error");
                            if (status == false) {
                                Log.d("data skincare = ", response.toString());
                                String data = response.getString("data");
                                JSONArray jsonArray = new JSONArray(data);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    final ModelSkincare skincare = new ModelSkincare();
                                    final String _id = jsonObject.getString("_id");
                                    final String namaSkincare = jsonObject.getString("namaSkincare");
                                    final String kodeSkincare = jsonObject.getString("kodeSkincare");
                                    final String hargaSkincare = jsonObject.getString("hargaSkincare");
                                    final String gamabr = jsonObject.getString("gambar");
                                    skincare.setKodeSkincare(kodeSkincare);
                                    skincare.setNamaSkincare(namaSkincare);
                                    skincare.setHargaSkincare(hargaSkincare);
                                    skincare.setGambar(gamabr);
                                    skincare.set_id(_id);

                                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            // TODO Auto-generated method stub
                                            Intent a = new Intent(ActivityDataSkincare.this, EditSkincareDanHapusActivity.class);
                                            a.putExtra("kodeSkincare", newsList.get(position).getKodeSkincare());
                                            a.putExtra("_id", newsList.get(position).get_id());
                                            a.putExtra("namaSkincare", newsList.get(position).getNamaSkincare());
                                            a.putExtra("gambar", newsList.get(position).getGambar());
                                            startActivity(a);
                                        }
                                    });
                                    newsList.add(skincare);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                hideDialog();
            }
        });

        /* Add your Requests to the RequestQueue to execute */
        mRequestQueue.add(req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
