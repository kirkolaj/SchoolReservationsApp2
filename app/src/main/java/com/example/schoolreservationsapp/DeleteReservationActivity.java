package com.example.schoolreservationsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.example.schoolreservationsapp.R.id.lwReservationbyUser;
import static com.example.schoolreservationsapp.R.id.roomId;

public class DeleteReservationActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    public static final String Delete_URI = "http://anbo-roomreservationv3.azurewebsites.net/api/Reservations/";
    public static final String BASE_URI = "http://anbo-roomreservationv3.azurewebsites.net/api/Reservations/user/";
    String userId;
    ArrayList<Reservations> res = new ArrayList<>();
    ArrayAdapter<Reservations> adapter;
    SwipeRefreshLayout sr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_reservation);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        userId = firebaseUser.getUid();
        getReservation();

        ListView lw = findViewById(lwReservationbyUser);
        adapter = new ArrayAdapter<Reservations>(this, android.R.layout.simple_list_item_1, res);
        lw.setAdapter(adapter);

        lw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                deleteReservation(res.get(position).getId());
                res.remove(position);
                adapter.notifyDataSetChanged();
            }
        });

        sr = findViewById(R.id.SwiperRefresh);
        sr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getReservation();
            }
        });

    }


    public void getReservation(){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(BASE_URI + userId).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Gson gson = new Gson();
                Reservations[] reservations = gson.fromJson(response.body().string(), Reservations[].class);
                res.clear();
                res.addAll(Arrays.asList(reservations));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
                sr.setRefreshing(false);

            }
        });
    }

    public void deleteReservation(final int deleteId){
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(Delete_URI + deleteId).delete().build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

            }
        });
    }







}
