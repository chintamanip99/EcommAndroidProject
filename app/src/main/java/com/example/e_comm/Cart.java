package com.example.e_comm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Cart extends AppCompatActivity {
    String token=null;
    int profile_id=-1;
    Button add_button=null,button11=null,login=null,signup=null,button12=null;
    JSONObject items,items1;
    EditText add_edittext=null;
    TextView textview1111=null,textview1112=null,textview1113=null,textview11=null,message=null,textview11yy=null;
    ImageView imageview111=null;
    LinearLayout main_vertical_linear_layout=null;
    JSONObject json2;
    JSONArray jar;
    String server_url=null;
    LinearLayout linear_layout1=null,linear_layout11=null,linear_layout111=null;


    @Override
    public void onBackPressed() {
        Intent intent123 = new Intent(Cart.this, MainActivity.class);
        intent123.putExtra("token", token);
        startActivity(intent123);
    }



    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Intent intentm = getIntent();
        server_url=getString(R.string.server_url);
        token = intentm.getStringExtra("token");
        main_vertical_linear_layout= findViewById(R.id.scrollview1);

        //////////////GET SERVER URL CODE STARTS//////////////////////////////
//        StringRequest r22 = new StringRequest(Request.Method.GET,getString(R.string.server_url)+"/get_server_url/", new Response.Listener<String>() {
//            public void onResponse(String response) {
//                try {
//                    JSONObject json2 = new JSONObject(response);
//                    Cart.this.server_url=json2.getString("server_url");
//
//                } catch (JSONException e) {
//                    Toast.makeText(Cart.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
//        Volley.newRequestQueue(Cart.this).add(r22);
        //////////////GET SERVER URL CODE ENDS////////////////////////////////

        if(token!=null) {
            /////////////////////////////////////////////////////////////////////////
            StringRequest r2 = new StringRequest(Request.Method.GET, getString(R.string.server_url)+"/cart_api/products_in_cart/", new Response.Listener<String>() {
                public void onResponse(String response) {
                    if (response.length() > 2) {
                        try {
//                    JSONObject jsonObject = new JSONObject(response);

//                    JSONObject json2 = new JSONObject(response);
                            jar = new JSONArray(response);
                            for (int i = 0; i < jar.length(); i++) {
                                try {
                                    items1 = jar.getJSONObject(i);
                                    items = items1.getJSONObject("product");
                                } catch (JSONException e) {
//                e.printStackTrace();
                                    login.setText(e.getMessage());
                                }
                                ///////////////////////////////////////////////////////////////////////////////////////
                                linear_layout1 = new LinearLayout(Cart.this);
                                linear_layout1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                                linear_layout1.setOrientation(LinearLayout.VERTICAL);
                                main_vertical_linear_layout.addView(linear_layout1);

                                linear_layout11 = new LinearLayout(Cart.this);
                                linear_layout11.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen._200dp)));
                                linear_layout11.setOrientation(LinearLayout.HORIZONTAL);
                                linear_layout1.addView(linear_layout11);

                                linear_layout111 = new LinearLayout(Cart.this);
                                linear_layout111.setLayoutParams(new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen._206dp), LinearLayout.LayoutParams.MATCH_PARENT));
                                linear_layout111.setOrientation(LinearLayout.VERTICAL);
                                linear_layout11.addView(linear_layout111);

                                textview1111 = new TextView(Cart.this);
                                textview1111.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen._86dp)));
                                try {
                                    textview1111.setText(items.getString("title"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                linear_layout111.addView(textview1111);

                                textview1112 = new TextView(Cart.this);
                                textview1112.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen._55dp)));
                                try {
                                    textview1112.setText("Price: " + items.getDouble("price"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                linear_layout111.addView(textview1112);

                                textview1113 = new TextView(Cart.this);
                                textview1113.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen._50dp)));
                                if (items1.getBoolean("is_buyed")) {
                                    textview1113.setText("Purchase Status: Bought");
                                } else {
                                    textview1113.setText("Purchase Status: Not Bought");
                                }
                                linear_layout111.addView(textview1113);

                                imageview111 = new ImageView(Cart.this);
                                imageview111.setLayoutParams(new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen._200dp), (int) getResources().getDimension(R.dimen._200dp)));
                                imageview111.setImageResource(R.drawable.ic_launcher_background);
                                Picasso.with(Cart.this)
                                        .load(getString(R.string.server_url) + items.getString("image"))
                                        .resize(200, 200)
                                        .into(imageview111);
                                linear_layout11.addView(imageview111);

                                textview11 = new TextView(Cart.this);
                                textview11.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen._67dp)));
                                try {
                                    textview11.setText("Details: " + items.getString("summary"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                linear_layout1.addView(textview11);
                                if(!items1.getBoolean("is_buyed")) {
                                    button11 = new Button(Cart.this);
                                    button11.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                                    button11.setText("Buy");
                                    button11.setBackgroundColor(getResources().getColor(R.color.Blue));
                                }

                                button12 = new Button(Cart.this);
                                button12.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                                button12.setText("Remove From Cart");
                                button12.setBackgroundColor(getResources().getColor(R.color.BottleGreen));

                                final int p_id = items1.getInt("id");

                                /////////////////delete from cart/////
                                button12.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        Intent intentmy123 = new Intent(Cart.this, ConfirmActivity.class);
                                        intentmy123.putExtra("token", Cart.this.token);
                                        intentmy123.putExtra("p_id", p_id);
                                        intentmy123.putExtra("link", "/cart_api/delete_a_product_from_cart/");
                                        Cart.this.startActivity(intentmy123);
//                                        Toast.makeText(Cart.this, "Deletion in Progress....", Toast.LENGTH_SHORT).show();
//                                        //////////////////////////////////////////////////////////////////////////////
//                                        StringRequest r3 = new StringRequest(Request.Method.DELETE, getString(R.string.server_url)+"/cart_api/delete_a_product_from_cart/" + p_id, new Response.Listener<String>() {
//                                            public void onResponse(String response) {
//                                                try {
//                                                    JSONObject json2 = new JSONObject(response);
//                                                    if (json2.has("message")) {
//                                                        Toast.makeText(Cart.this, json2.getString("message"), Toast.LENGTH_LONG).show();
//
//                                                    }
//
//                                                } catch (JSONException e) {
//                                                    Toast.makeText(Cart.this, "Transaction Doesnt Exist", Toast.LENGTH_LONG).show();
//
//                                                }
//                                            }
//                                        }, new Response.ErrorListener() {
//                                            public void onErrorResponse(VolleyError error) {
//                                                Toast.makeText(Cart.this, "Transaction Doesnt Exist", Toast.LENGTH_LONG).show();
//                                            }
//                                        }) {
//                                            /* access modifiers changed from: protected */
//                                            public Map<String, String> getHeaders() throws AuthFailureError {
//                                                Map<String, String> params = new HashMap<>();
//                                                params.put("Authorization", "Token " + token);
//                                                return params;
//                                            }
//                                        };
//                                        r3.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0,
//                                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//                                        Volley.newRequestQueue(Cart.this).add(r3);
                                        /////////////////////////////////////////////////////////////////////////////
                                    }
                                });
                                ////////////////delete from cart ends......./////
                                if(!items1.getBoolean("is_buyed")) {
                                    button11.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            Intent intentmy123 = new Intent(Cart.this, ConfirmActivity.class);
                                            intentmy123.putExtra("token", Cart.this.token);
                                            intentmy123.putExtra("p_id", p_id);
                                            intentmy123.putExtra("link", "/cart_api/purchase_a_product/");
                                            Cart.this.startActivity(intentmy123);
//                                            Toast.makeText(Cart.this, "Just a second,Product Purchase in Progress....", Toast.LENGTH_SHORT).show();
//                                            //////////////////////////////////////////////////////////////////////////////
//                                            StringRequest r3 = new StringRequest(Request.Method.GET, getString(R.string.server_url) + "/cart_api/purchase_a_product/" + p_id, new Response.Listener<String>() {
//                                                public void onResponse(String response) {
//                                                    try {
//                                                        JSONObject json2 = new JSONObject(response);
//                                                        if (json2.has("message")) {
//                                                            Toast.makeText(Cart.this, json2.getString("message"), Toast.LENGTH_LONG).show();
//
//                                                        }
//
//                                                    } catch (JSONException e) {
//                                                        Toast.makeText(Cart.this, "Something Went wrong", Toast.LENGTH_LONG).show();
//
//                                                    }
//                                                }
//                                            }, new Response.ErrorListener() {
//                                                public void onErrorResponse(VolleyError error) {
//                                                    Toast.makeText(Cart.this, "Something Went wrong", Toast.LENGTH_LONG).show();
//                                                }
//                                            }) {
//                                                /* access modifiers changed from: protected */
//                                                public Map<String, String> getHeaders() throws AuthFailureError {
//                                                    Map<String, String> params = new HashMap<>();
//                                                    params.put("Authorization", "Token " + token);
//                                                    return params;
//                                                }
//                                            };
//                                            r3.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0,
//                                                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//                                            Volley.newRequestQueue(Cart.this).add(r3);
                                            /////////////////////////////////////////////////////////////////////////////
                                        }
                                    });
                                }
                                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                if(!items1.getBoolean("is_buyed")) {
                                    linear_layout1.addView(button11);
                                }
                                linear_layout1.addView(button12);
                                View v = new View(Cart.this);
                                v.setLayoutParams(new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        20
                                ));
                                v.setBackgroundColor(Color.parseColor("#F8F8FF"));
                                linear_layout1.addView(v);
                                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                            }
                            textview11yy = new TextView(Cart.this);
                            textview11yy.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen._67dp)));
                            linear_layout1.addView(textview11yy);


                        } catch (JSONException e) {
                            login.setText(e.getMessage());

                        }
                    }else{
                        Toast.makeText(Cart.this, "No Products in Cart", Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                public void onErrorResponse(VolleyError error) {
                    login.setText(error.getMessage());
                }
            }) {
                /* access modifiers changed from: protected */

                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Authorization", "Token " + token);
                    return params;
                }
            };
            Volley.newRequestQueue(Cart.this).add(r2);
        }
    }
}
