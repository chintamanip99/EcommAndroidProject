package com.example.e_comm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    Button login=null;
    String token=null;
    TextView textView3=null;
    EditText username=null,password=null;
    String server_url=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        server_url=getString(R.string.server_url);
        textView3=findViewById(R.id.textView3);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        login=(Button) findViewById(R.id.login);

        //////////////GET SERVER URL CODE STARTS//////////////////////////////
//        StringRequest r22 = new StringRequest(Request.Method.GET,getString(R.string.server_url)+"/get_server_url/", new Response.Listener<String>() {
//            public void onResponse(String response) {
//                try {
//                    JSONObject json2 = new JSONObject(response);
//                    Login.this.server_url=json2.getString("server_url");
//
//                } catch (JSONException e) {
//                    Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_LONG).show();
//
//                }
//            }
//        }, new Response.ErrorListener() {
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        }) {
//            /* access modifiers changed from: protected */
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("password", "mypassword123");
//                return params;
//            }
//        };
//        Volley.newRequestQueue(Login.this).add(r22);
        //////////////GET SERVER URL CODE ENDS////////////////////////////////

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //////////////////////////////////////////////////////////////
                StringRequest r2 = new StringRequest(Request.Method.POST, getString(R.string.server_url)+"/profiles_api/login_user/", new Response.Listener<String>() {
                    public void onResponse(String response) {
                        try {
                            JSONObject json2 = new JSONObject(response);
                            if(json2.has("token") ) {
                                token = json2.getString("token");



                                textView3.setText("Logged in successfully");
                                Intent intent123 = new Intent(Login.this, MainActivity.class);
                                intent123.putExtra("token", token);
                                startActivity(intent123);


                            }else if(json2.has("non_field_errors") ){
                                textView3.setText(json2.getJSONArray("non_field_errors").getString(0));
                            }else{
                                textView3.setText("LAst Else");
                            }

                        } catch (JSONException e) {
                            Toast.makeText(Login.this,"jsoneccepyon", Toast.LENGTH_SHORT).show();
                                textView3.setText(e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        textView3.setText("Wrong Credentials entered");
                    }
                }) {
                    /* access modifiers changed from: protected */
                    public Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("username",username.getText().toString());
                        params.put("password",password.getText().toString());
                        return params;
                    }
                };
                Volley.newRequestQueue(Login.this).add(r2);
                //////////////////////////////////////////////////////////////


            }
        });
    }
}
