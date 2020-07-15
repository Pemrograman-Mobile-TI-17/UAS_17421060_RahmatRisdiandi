package com.example.RahmatRisdiandi.pembeli;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.RahmatRisdiandi.R;
import com.example.RahmatRisdiandi.adapter.AdapterSkincare;
import com.example.RahmatRisdiandi.model.ModelSkincare;
import com.example.RahmatRisdiandi.server.BaseURL;
import com.example.RahmatRisdiandi.session.PrefSetting;
import com.example.RahmatRisdiandi.session.SessionManager;
import com.example.RahmatRisdiandi.users.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomePembeliActivity extends AppCompatActivity {

    ProgressDialog pDialog;

    AdapterSkincare adapter;
    ListView list;

    ArrayList<ModelSkincare> newsList = new ArrayList<ModelSkincare>();
    private RequestQueue mRequestQueue;

    FloatingActionButton floatingExit;

    SessionManager session;
    SharedPreferences prefs;
    PrefSetting prefSetting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_pembeli);
        prefSetting = new PrefSetting(this);
        prefs = prefSetting.getSharePreference();

        session = new SessionManager(HomePembeliActivity.this);

        prefSetting.isLogin(session, prefs);

        getSupportActionBar().setTitle("Data Buku");
        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        list = (ListView) findViewById(R.id.array_list);

        floatingExit = (FloatingActionButton) findViewById(R.id.exit);

        newsList.clear();
        adapter = new AdapterSkincare(HomePembeliActivity.this, newsList);
        list.setAdapter(adapter);
        getAllSkincare();

        floatingExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setLogin(false);
                session.setSessid(0);
                Intent i = new Intent(HomePembeliActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
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
                                            Intent a = new Intent(HomePembeliActivity.this, DetailPembeli.class);
                                            a.putExtra("kodeSkincare", newsList.get(position).getKodeSkincare());
                                            a.putExtra("_id", newsList.get(position).get_id());
                                            a.putExtra("namaSkincare", newsList.get(position).getNamaSkincare());
                                            a.putExtra("hargaSkincare", newsList.get(position).getHargaSkincare());
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
