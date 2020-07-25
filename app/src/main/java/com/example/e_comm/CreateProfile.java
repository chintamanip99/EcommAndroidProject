package com.example.e_comm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreateProfile extends AppCompatActivity {
    String server_url=null;
    EditText firstname=null,lastname=null,balance=null,age=null,address=null,email=null;
    Button createprofile=null;
    TextView textView3=null;
    String token=null;
    Intent intentm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        server_url=getString(R.string.server_url);
        address=findViewById(R.id.address);
        firstname=findViewById(R.id.firstname);
        lastname=findViewById(R.id.lastname);
        balance=findViewById(R.id.balance);
        age=findViewById(R.id.age);
        email=findViewById(R.id.email);
        textView3=findViewById(R.id.textView3);
        createprofile=findViewById(R.id.createprofile);
        intentm = getIntent();
        token = intentm.getStringExtra("token");

        //////////////GET SERVER URL CODE STARTS//////////////////////////////
//        StringRequest r22 = new StringRequest(Request.Method.GET,getString(R.string.server_url)+"/get_server_url/", new Response.Listener<String>() {
//            public void onResponse(String response) {
//                try {
//                    JSONObject json2 = new JSONObject(response);
//                    CreateProfile.this.server_url=json2.getString("server_url");
//
//                } catch (JSONException e) {
//                    Toast.makeText(CreateProfile.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
//        Volley.newRequestQueue(CreateProfile.this).add(r22);
        //////////////GET SERVER URL CODE ENDS////////////////////////////////

        if(intentm.getStringExtra("profile").equals("updateprofile")){
            ////////////////////////////////////////////////////////////////////
            StringRequest r2 = new StringRequest(Request.Method.GET, getString(R.string.server_url)+"/profiles_api/create_profile/", new Response.Listener<String>() {
                public void onResponse(String response) {
                    try {
                        JSONObject json2 = new JSONObject(response);
                        firstname.setText(json2.getString("firstname"));
                        lastname.setText(json2.getString("lastname"));
                        balance.setText(json2.getString("balance"));
                        age.setText(json2.getString("age"));
                        address.setText(json2.getString("address"));
                        email.setText(json2.getString("email"));
                        createprofile.setText("Update Profile");

                    } catch (JSONException e) {
                        textView3.setText("Something went wrong");

                    }
                }
            }, new Response.ErrorListener() {
                public void onErrorResponse(VolleyError error) {
                    textView3.setText("Something went wrong");
                }
            }) {
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Authorization", "Token " + token);
                    return params;
                }


            };
            Volley.newRequestQueue(CreateProfile.this).add(r2);
            //////////////////////////////////////////////////
        }
        if(token!=null || token.length()>0) {
            if (intentm.getStringExtra("profile").equals("createprofile")) {
                email.setVisibility(View.GONE);
            }
            createprofile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (intentm.getStringExtra("profile").equals("createprofile")) {
                        if (balance.getText().toString().length() < 0 || age.getText().toString().length() < 0 || firstname.getText().toString().length() < 0 || lastname.getText().toString().length() < 0) {
                            textView3.setText("Please Fillout all the fields");
                        } else {
                            ///////////////////////////////////////
                            StringRequest r2 = new StringRequest(Request.Method.POST, getString(R.string.server_url)+"/profiles_api/create_profile/", new Response.Listener<String>() {
                                public void onResponse(String response) {
                                    try {
                                        JSONObject json2 = new JSONObject(response);

                                        if (json2.has("profile_made_successfully")) {
                                            textView3.setText(json2.getString("profile_made_successfully"));
                                            Intent intent123 = new Intent(CreateProfile.this, MainActivity.class);
                                            intent123.putExtra("token", token);
                                            startActivity(intent123);
                                        }
                                        if (json2.has("error_creating_profile")) {
                                            textView3.setText(json2.getString("error_creating_profile"));
                                        }
                                    } catch (JSONException e) {
                                        textView3.setText("Something went wrong");

                                    }
                                }
                            }, new Response.ErrorListener() {
                                public void onErrorResponse(VolleyError error) {
                                    textView3.setText("Something went wrong");
                                }
                            }) {
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<>();
                                    params.put("Authorization", "Token " + token);
                                    return params;
                                }

                                /* access modifiers changed from: protected */
                                public Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<>();
                                    params.put("firstname", firstname.getText().toString());
                                    params.put("lastname", lastname.getText().toString());
                                    params.put("balance", balance.getText().toString());
                                    params.put("age", age.getText().toString());
                                    params.put("address", address.getText().toString());
                                    params.put("email", email.getText().toString());
                                    return params;
                                }
                            };
                            Volley.newRequestQueue(CreateProfile.this).add(r2);
                        }
                        ///////////////////////////////////////
                    }
                    if(intentm.getStringExtra("profile").equals("updateprofile")){
                        ///////////////////////////////////////
                        StringRequest r2 = new StringRequest(Request.Method.PUT, getString(R.string.server_url)+"/profiles_api/create_profile/", new Response.Listener<String>() {
                            public void onResponse(String response) {
                                try {
                                    JSONObject json2 = new JSONObject(response);
                                    if (json2.has("profile_updated_successfully")) {
                                        textView3.setText(json2.getString("profile_updated_successfully"));
                                        Intent intent123 = new Intent(CreateProfile.this, MainActivity.class);
                                        intent123.putExtra("token", token);
                                        startActivity(intent123);
                                    }
                                    if (json2.has("error_updating_profile")) {
                                        textView3.setText(json2.getString("error_updating_profile"));
                                    }
                                } catch (JSONException e) {
                                    textView3.setText("Something went wrong");

                                }
                            }
                        }, new Response.ErrorListener() {
                            public void onErrorResponse(VolleyError error) {
                                textView3.setText("Something went wrong");
                            }
                        }) {
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("Authorization", "Token " + token);
                                return params;
                            }

                            /* access modifiers changed from: protected */
                            public Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("firstname", firstname.getText().toString());
                                params.put("lastname", lastname.getText().toString());
                                params.put("balance", balance.getText().toString());
                                params.put("age", age.getText().toString());
                                params.put("address", address.getText().toString());
                                params.put("email", email.getText().toString());
                                return params;
                            }
                        };
                        Volley.newRequestQueue(CreateProfile.this).add(r2);
                    }
                    ///////////////////////////////////////
                }

            });

        }else{
            textView3.setText("Login needed");
        }
    }
}
