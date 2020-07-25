package com.example.e_comm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VerifyEmail extends AppCompatActivity {
    Button submitotp=null;
    TextView otpnotification=null;
    EditText enterotp=null;
    String token=null,otp=null;
    String server_url=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().setLayout((int) (dm.widthPixels*1), (int) (dm.heightPixels*.6));
        WindowManager.LayoutParams params=getWindow().getAttributes();
        params.gravity= Gravity.CENTER;
        params.x=0;
        params.y=-20;
        getWindow().setAttributes(params);
        setContentView(R.layout.activity_verify_email);
        server_url=getString(R.string.server_url);
        submitotp=findViewById(R.id.submitotp);
        otpnotification=findViewById(R.id.otpnotification);
        enterotp=findViewById(R.id.enterotp);
        Intent intentm=getIntent();
        token = intentm.getStringExtra("token");

        //////////////GET SERVER URL CODE STARTS//////////////////////////////
//        StringRequest r22 = new StringRequest(Request.Method.GET,getString(R.string.server_url)+"/get_server_url/", new Response.Listener<String>() {
//            public void onResponse(String response) {
//                try {
//                    JSONObject json2 = new JSONObject(response);
//                    VerifyEmail.this.server_url=json2.getString("server_url");
//
//                } catch (JSONException e) {
//                    Toast.makeText(VerifyEmail.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
//        Volley.newRequestQueue(VerifyEmail.this).add(r22);
        //////////////GET SERVER URL CODE ENDS////////////////////////////////

        submitotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ////////////////////////////////////////////////////////////////////
                StringRequest r2 = new StringRequest(Request.Method.POST, getString(R.string.server_url)+"/profiles_api/send_otp/", new Response.Listener<String>() {
                    public void onResponse(String response) {
                        try {
                            JSONObject json2 = new JSONObject(response);
                            if(json2.has("email_verification_success_message")){
                                otpnotification.setText(json2.getString("email_verification_success_message"));
                                Intent intent123 = new Intent(VerifyEmail.this, MainActivity.class);
                                intent123.putExtra("token", token);
                                startActivity(intent123);
                            }else if(json2.has("email_verification_failure_message")){
                                otpnotification.setText(json2.getString("email_verification_failure_message"));
                            }
                            else if(json2.has("detail")){
                                otpnotification.setText(json2.getString("detail"));
                            }

                        } catch (JSONException e) {
                            otpnotification.setText("Something went wrong"+otp+e.getMessage());

                        }
                    }
                }, new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        otpnotification.setText("Something went wrong");
                    }
                }) {
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("Authorization", "Token " + token);
                        return params;
                    }
                    public Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("otp1", otp);
                        params.put("otp2", enterotp.getText().toString());
                        return params;
                    }




                };

                Volley.newRequestQueue(VerifyEmail.this).add(r2);
                //////////////////////////////////////////////////
            }
        });

        ////////////////////////////////////////////////////////////////////
        StringRequest r2 = new StringRequest(Request.Method.GET, getString(R.string.server_url)+"/profiles_api/send_otp/", new Response.Listener<String>() {
            public void onResponse(String response) {
                try {
                    JSONObject json2 = new JSONObject(response);
                    if(json2.has("otp") && json2.has("otp_success_message")){
                        otp=json2.getString("otp");
                        otpnotification.setText(json2.getString("otp_success_message"));
                    }else if(json2.has("otp_error_message")){
                        otpnotification.setText(json2.getString("otp_error_message"));
                    }
                    else if(json2.has("detail")){
                        otpnotification.setText(json2.getString("detail"));
                    }

                } catch (JSONException e) {
                    otpnotification.setText("Something went wrong");

                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                otpnotification.setText("Something went wrong");
            }
        }) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "Token " + token);
                return params;
            }


        };
        r2.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(VerifyEmail.this).add(r2);
        //////////////////////////////////////////////////
    }
}
