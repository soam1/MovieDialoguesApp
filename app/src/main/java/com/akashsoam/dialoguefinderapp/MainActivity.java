package com.akashsoam.dialoguefinderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
//    TextView txtTest;

    private EditText edtCharacterName, edtMovieName;
    private Button btnGetDialogue;
    TextView txtDialogue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        txtTest = findViewById(R.id.txtTest);


        edtCharacterName = findViewById(R.id.edtArtistName);
        edtMovieName = findViewById(R.id.edtMovieName);
        btnGetDialogue = findViewById(R.id.btnGetDialogue);
        txtDialogue = findViewById(R.id.txtDialogue);


        String dialoguesUrl = "https://marveldialoguesfinderapp.000webhostapp.com/dialogues.json";

        RequestQueue queue = Volley.newRequestQueue(this);
       /*
        String dialoguesUrl = "https://marveldialoguesfinderapp.000webhostapp.com/dialogues.json";
        StringRequest request = new StringRequest(Request.Method.GET, dialoguesUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                txtTest.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
        */


//        queue.add(request);

        btnGetDialogue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String characterName = edtCharacterName.getText().toString().trim();
                String movieName = edtMovieName.getText().toString().trim();

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, dialoguesUrl, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                txtTest.setText(response.toString());
                        try {
                            JSONArray songs = response.getJSONArray("songs");
                            for (int i = 0; i < songs.length(); ++i) {
                                JSONObject s = (JSONObject) songs.get(i);
                                if (s.getString("artist_name").equalsIgnoreCase(characterName)) {
                                    if (s.getString("movie_name").equalsIgnoreCase(movieName)) {
                                        txtDialogue.setText(s.getString("quote"));
                                        Toast.makeText(getApplicationContext(), "all set", Toast.LENGTH_LONG).show();
                                    }

                                }
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                });

                queue.add(jsonObjectRequest);

            }
        });

    }
}