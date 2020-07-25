package com.example.e_comm;


import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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

import androidx.appcompat.app.AppCompatActivity;

public class ConfirmActivity extends AppCompatActivity {
    Button ok=null,cancel=null;
    String token=null;
    TextView textView4=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_confirm);

        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().setLayout((int) (dm.widthPixels*1), (int) (dm.heightPixels*.2));
        WindowManager.LayoutParams params=getWindow().getAttributes();
        params.gravity= Gravity.CENTER;
        params.x=0;
        params.y=-20;
        getWindow().setAttributes(params);

        final Intent intentm=getIntent();
        token=intentm.getStringExtra("token");
        final String link=intentm.getStringExtra("link");
        final int p_id1=intentm.getIntExtra("p_id",0);
        ok=findViewById(R.id.ok);
        cancel=findViewById(R.id.cancel);
        textView4=findViewById(R.id.textView4);

        if(link.equals("/cart_api/add_product_in_cart/")) {
            textView4.setText("Do you Surely want to add   this product to cart?");
        }
        if(link.equals("/cart_api/delete_a_product_from_cart/")) {
            textView4.setText("Do you Surely want to delete this product from cart?");
        }
        if(link.equals("/cart_api/purchase_a_product/")) {
            textView4.setText("Do you Surely want to purchase this product?");
        }


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //////////////
                if(link.equals("/cart_api/add_product_in_cart/")) {
                    StringRequest r0 = new StringRequest(0,getString(R.string.server_url)+"/cart_api/add_product_in_cart/"+p_id1 , new Response.Listener<String>() {
                        public void onResponse(String response) {
                            String str = "detail";
                            String str2 = "message";
                            try {
                                JSONObject json2 = new JSONObject(response);
                                if (json2.has(str2)) {
                                    Toast.makeText(ConfirmActivity.this, json2.getString(str2), Toast.LENGTH_SHORT).show();
                                }
                                if (json2.has(str)) {
                                    Toast.makeText(ConfirmActivity.this, json2.getString(str), Toast.LENGTH_LONG).show();
                                }
                                onBackPressed();

                            } catch (JSONException e) {
                                Toast.makeText(ConfirmActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ConfirmActivity.this, "Login First", Toast.LENGTH_LONG).show();
                        }
                    }) {
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            StringBuilder sb = new StringBuilder();
                            sb.append(token);
                            params.put("Authorization", "Token "+token);
                            return params;
                        }
                    };
                    Volley.newRequestQueue(ConfirmActivity.this).add(r0);
                }
                if(link.equals("/cart_api/delete_a_product_from_cart/")){
                    Toast.makeText(ConfirmActivity.this, "Deletion in Progress....", Toast.LENGTH_SHORT).show();
                    //////////////////////////////////////////////////////////////////////////////
                    StringRequest r3 = new StringRequest(Request.Method.DELETE, getString(R.string.server_url)+"/cart_api/delete_a_product_from_cart/" + p_id1, new Response.Listener<String>() {
                        public void onResponse(String response) {
                            try {
                                JSONObject json2 = new JSONObject(response);
                                if (json2.has("message")) {
                                    Toast.makeText(ConfirmActivity.this, json2.getString("message"), Toast.LENGTH_LONG).show();

                                }

                                Intent intent=new Intent(ConfirmActivity.this,Cart.class);
                                intent.putExtra("token",token);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                ConfirmActivity.this.startActivity(intent);


                            } catch (JSONException e) {
                                Toast.makeText(ConfirmActivity.this, "Transaction Doesnt Exist", Toast.LENGTH_LONG).show();

                            }
                        }
                    }, new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ConfirmActivity.this, "Transaction Doesnt Exist", Toast.LENGTH_LONG).show();
                        }
                    }) {
                        /* access modifiers changed from: protected */
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("Authorization", "Token " + token);
                            return params;
                        }
                    };
                    r3.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    Volley.newRequestQueue(ConfirmActivity.this).add(r3);
                }
                if(link.equals("/cart_api/purchase_a_product/")){
                    //////////////////////////////////////
                    Toast.makeText(ConfirmActivity.this, "Just a second,Product Purchase in Progress....", Toast.LENGTH_SHORT).show();
                    //////////////////////////////////////////////////////////////////////////////
                    StringRequest r3 = new StringRequest(Request.Method.GET, getString(R.string.server_url) + "/cart_api/purchase_a_product/" + p_id1, new Response.Listener<String>() {
                        public void onResponse(String response) {
                            try {
                                JSONObject json2 = new JSONObject(response);
                                if (json2.has("message")) {
                                    Toast.makeText(ConfirmActivity.this, json2.getString("message"), Toast.LENGTH_LONG).show();

                                }
                                Intent intent=new Intent(ConfirmActivity.this,Cart.class);
                                intent.putExtra("token",token);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                ConfirmActivity.this.startActivity(intent);


                            } catch (JSONException e) {
                                Toast.makeText(ConfirmActivity.this, "Something Went wrong", Toast.LENGTH_LONG).show();

                            }
                        }
                    }, new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ConfirmActivity.this, "Something Went wrong", Toast.LENGTH_LONG).show();
                        }
                    }) {
                        /* access modifiers changed from: protected */
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("Authorization", "Token " + token);
                            return params;
                        }
                    };
                    r3.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    Volley.newRequestQueue(ConfirmActivity.this).add(r3);
                    /////////////////////////////////////
                }
                //////////
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

    }


}
