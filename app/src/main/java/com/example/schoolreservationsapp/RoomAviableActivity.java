package com.example.schoolreservationsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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
import static com.example.schoolreservationsapp.R.id.lwRoomAvailable;

public class RoomAviableActivity extends AppCompatActivity {

    public static final String BASE_URI = "http://anbo-roomreservationv3.azurewebsites.net/api/Reservations";
    ArrayList<Reservations> res = new ArrayList<>();
    ArrayAdapter<Reservations> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_aviable);

        getReservation();

        ListView lw = findViewById(lwRoomAvailable);
        adapter = new ArrayAdapter<Reservations>(this, android.R.layout.simple_list_item_1, res);
        lw.setAdapter(adapter);


    }


    public void getReservation() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(BASE_URI).build();
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


            }
        });
    }
}




