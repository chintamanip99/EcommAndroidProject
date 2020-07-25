package com.example.e_comm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoderActivity extends AppCompatActivity {
    String server_url=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                /////////////////////////////////////////////////////
                StringRequest r22 = new StringRequest(Request.Method.GET,getString(R.string.server_url)+"/get_server_url/", new Response.Listener<String>() {
            public void onResponse(String response) {
                try {
                    JSONObject json2 = new JSONObject(response);
                    Intent intent123 = new Intent(LoderActivity.this, MainActivity.class);
                    server_url=json2.getString("server_url");
                    intent123.putExtra("server_url", server_url);
                    startActivity(intent123);

                } catch (JSONException e) {
                    Toast.makeText(LoderActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoderActivity.this, "Please Turn ON your internet connection", Toast.LENGTH_LONG).show();
            }
        }) {
            /* access modifiers changed from: protected */
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("password", "mypassword123");
                return params;
            }
        };
        Volley.newRequestQueue(LoderActivity.this).add(r22);
        //////////////////////////////////////


            }
        }, 500);
    }
}
