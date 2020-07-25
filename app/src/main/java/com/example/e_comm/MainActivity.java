package com.example.e_comm;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.widget.GridLayout.HORIZONTAL;

public class MainActivity extends AppCompatActivity {
    Button add_button = null;
    EditText add_edittext = null,search_query=null,address=null;
    Button button11 = null,update_address=null,logout=null;
    int counter = 1;
    ImageView imageview111 = null;
    JSONObject items;
    JSONArray jar;
    JSONObject json2;
    LinearLayout for_address=null;
    LinearLayout linear_layout1 = null;
    LinearLayout linear_layout11 = null;
    LinearLayout linear_layout111 = null,image_text_linear=null;
    Button login = null,search_button=null;
    LinearLayout main_linear_layout = null;
    LinearLayout main_vertical_linear_layout = null;
    TextView message = null;
    int profile_id = -1;
    Button signup = null;
    TextView textview11 = null,text_under_image=null;
    TextView textview1111 = null;
    TextView textview1112 = null;
    TextView textview1113 = null;
    TextView textview11yy = null;
    String token = null;
    String server_url=null;

    @Override
    public void onBackPressed() {
        if(token!=null) {
            if(token.length()!=0) {
                Toast.makeText(this, "Logged out", Toast.LENGTH_LONG).show();
                Intent intent123 = new Intent(MainActivity.this, MainActivity.class);
                intent123.putExtra("token", "");
                startActivity(intent123);
            }else{
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory( Intent.CATEGORY_HOME );
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
            }
        }
        else{
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory( Intent.CATEGORY_HOME );
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        }
    }
    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main);
        Intent intentm = getIntent();
        server_url=getString(R.string.server_url);
        address=findViewById(R.id.address);
        update_address=findViewById(R.id.update_address);
        search_button=findViewById(R.id.search_button);
        logout=findViewById(R.id.logout);
        logout.setVisibility(View.GONE);
        this.main_vertical_linear_layout = (LinearLayout) findViewById(R.id.main_vertical_linear_layout);
        this.main_linear_layout = (LinearLayout) findViewById(R.id.main_linear_layout);
        this.signup = (Button) findViewById(R.id.signup);
        this.login = (Button) findViewById(R.id.login);
        update_address=findViewById(R.id.update_address);
        update_address.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Delivery is available in your area,continue the shopping", Toast.LENGTH_SHORT).show();
            }
        });
//        if(intentm.hasExtra("getString(R.string.server_url)")){
//            getString(R.string.server_url)=intentm.getStringExtra("server_url");
//        }
//        Toast.makeText(this, getString(R.string.server_url), Toast.LENGTH_SHORT).show();
        //////////////GET SERVER URL CODE STARTS//////////////////////////////
//        StringRequest r22 = new StringRequest(Request.Method.GET,getString(R.string.server_url)+"/get_server_url/", new Response.Listener<String>() {
//            public void onResponse(String response) {
//                try {
//                    JSONObject json2 = new JSONObject(response);
//                    server_url=json2.getString("server_url");
//                    Toast.makeText(MainActivity.this,server_url , Toast.LENGTH_SHORT).show();
//                } catch (JSONException e) {
//                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
//        Volley.newRequestQueue(MainActivity.this).add(r22);
        //////////////GET SERVER URL CODE ENDS////////////////////////////////


        this.signup.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, SignUp.class));
            }
        });
        this.login.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, Login.class));
            }
        });
        if (intentm != null && intentm.hasExtra("token")) {
            String stringExtra = intentm.getStringExtra("token");
            this.token = stringExtra;
            if (!(stringExtra == null || stringExtra.length() == 0)) {
                logout.setVisibility(View.VISIBLE);
                logout.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "Logged out", Toast.LENGTH_LONG).show();
                        Intent intent123 = new Intent(MainActivity.this, MainActivity.class);
                        intent123.putExtra("token", "");
                        startActivity(intent123);
                    }
                });
                StringRequest r2 = new StringRequest(1, getString(R.string.server_url)+"/products_api/get_customer_information/", new Listener<String>() {
                    @SuppressLint({"WrongConstant", "ResourceType"})
                    public void onResponse(String response) {
                        try {
                            JSONObject json2 = new JSONObject(response);
                            if (json2.getBoolean("has_profile")) {
                                address.setText("My Address");
                                update_address.setText("Update Address");
                                if(json2.has("address")) {
                                    address.setText(json2.getString("address"));
                                    update_address.setText("Update Address");

                                    update_address.setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            //Toast.makeText(MainActivity.this, "Click on Update Profile to update the address", Toast.LENGTH_LONG).show();
                                            ////////////////////////////start of Address Dynamic code////////////////////////////////////////
                                            StringRequest r1 = new StringRequest(Request.Method.PUT, getString(R.string.server_url) + "/profiles_api/update_address/", new Response.Listener<String>() {
                                                public void onResponse(String response) {
                                                    try {
                                                        JSONObject json1 = new JSONObject(response);
                                                        address.setText(json1.getString("address"));
                                                        Toast.makeText(MainActivity.this, json1.getString("profile_updated_successfully"), Toast.LENGTH_LONG).show();

                                                    } catch (JSONException e) {
                                                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            }, new Response.ErrorListener() {
                                                public void onErrorResponse(VolleyError error) {
                                                    Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }) {
                                                /* access modifiers changed from: protected */
                                                public Map<String, String> getHeaders() throws AuthFailureError {
                                                    Map<String, String> params = new HashMap<>();
                                                    params.put("Authorization", "Token " + token);
                                                    return params;
                                                }

                                                public Map<String, String> getParams() throws AuthFailureError {
                                                    Map<String, String> params = new HashMap<>();
                                                    params.put("address", address.getText().toString());
                                                    return params;
                                                }
                                            };
                                            Volley.newRequestQueue(MainActivity.this).add(r1);
                                            ///////////////////////////end of Address Dynamic code///////////////////////////////////////////
                                        }
                                    });
                                }

                                ////////////////////////////start of Address Dynamic code////////////////////////////////////////

//                                LinearLayout linearlayout_for_address = new LinearLayout(MainActivity.this);
//                                linearlayout_for_address.setId(R.id.linearlayout_for_address);
//                                linearlayout_for_address.setOrientation(HORIZONTAL);
//                                linearlayout_for_address.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
//                                main_linear_layout.addView(linearlayout_for_address);
//
//                                EditText address = new EditText(MainActivity.this);
//                                address.setId(R.id.address);
//                                address.setEms(10);
//                                address.setHint(getResources().getString(R.string.address));
//                                address.setTextSize((14/getApplicationContext().getResources().getDisplayMetrics().scaledDensity));
//                                address.setLayoutParams(new LayoutParams((int) MainActivity.this.getResources().getDimension(R.dimen._660dp),(int) MainActivity.this.getResources().getDimension(R.dimen._35dp)));
//                                linearlayout_for_address.addView(address);
//
//                                Button update_address = new Button(MainActivity.this);
//                                update_address.setId(R.id.update_address);
//                                update_address.setBackgroundResource(Color.parseColor("#A0B0E3"));
//                                update_address.setText(getResources().getString(R.string.update_address));
//                                update_address.setTextSize((12/getApplicationContext().getResources().getDisplayMetrics().scaledDensity));
//                                update_address.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
//                                linearlayout_for_address.addView(update_address);


                                ///////////////////////////end of Address Dynamic code////////////////////////////////////////
                                MainActivity.this.login.setText("Update Profile");
                                MainActivity.this.login.setOnClickListener(new OnClickListener() {
                                    public void onClick(View v) {
                                        Intent intent123 = new Intent(MainActivity.this, CreateProfile.class);
                                        intent123.putExtra("token", MainActivity.this.token);
                                        intent123.putExtra("profile", "updateprofile");
                                        MainActivity.this.startActivity(intent123);
                                    }
                                });
                                if (json2.getBoolean("is_email_verified")) {
                                    MainActivity.this.signup.setText("Go to Cart");
                                    MainActivity.this.signup.setOnClickListener(new OnClickListener() {
                                        public void onClick(View v) {
                                            Intent intent123 = new Intent(MainActivity.this, Cart.class);
                                            intent123.putExtra("token", MainActivity.this.token);
                                            MainActivity.this.startActivity(intent123);
                                        }
                                    });
                                } else {
                                    MainActivity.this.signup.setText("Verify Email");
                                    MainActivity.this.signup.setOnClickListener(new OnClickListener() {
                                        public void onClick(View v) {
                                            Intent intent123 = new Intent(MainActivity.this, VerifyEmail.class);
                                            intent123.putExtra("token", MainActivity.this.token);
                                            MainActivity.this.startActivity(intent123);
                                        }
                                    });
                                }
                                MainActivity.this.profile_id = json2.getInt("profile_id");
                                MainActivity mainActivity = MainActivity.this;
                                StringBuilder sb = new StringBuilder();
                                sb.append("");
                                sb.append(MainActivity.this.profile_id);
                                return;
                            }
                            else {
                                //heremytext
                                MainActivity.this.logout.setBackgroundColor(getResources().getColor(R.color.BottleGreen));
                                MainActivity.this.login.setText("Create Profile");
                                MainActivity.this.signup.setVisibility(View.GONE);
                                MainActivity.this.login.setOnClickListener(new OnClickListener() {
                                    public void onClick(View v) {
                                        Intent intent123 = new Intent(MainActivity.this, CreateProfile.class);
                                        intent123.putExtra("token", MainActivity.this.token);
                                        intent123.putExtra("profile", "createprofile");
                                        MainActivity.this.startActivity(intent123);
                                    }
                                });
                            }

                        } catch (JSONException e) {
                        }
                    }
                }, new ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        StringBuilder sb = new StringBuilder();
                        sb.append("Token ");
                        sb.append(MainActivity.this.token);
                        params.put("Authorization", sb.toString());
                        return params;
                    }
                };
                Volley.newRequestQueue(this).add(r2);
            }
        }

        StringRequest r3 = new StringRequest(0, getString(R.string.server_url)+"/products_api/all_products/", new Listener<String>() {
            @SuppressLint("ResourceType")
            public void onResponse(String response) {
                String str = "items_available";
                try {
                    MainActivity.this.jar = new JSONArray(response);
                    for (int i = 0; i < MainActivity.this.jar.length(); i++) {
                        try {
                            MainActivity.this.items = MainActivity.this.jar.getJSONObject(i);
                        } catch (JSONException e) {
                            MainActivity.this.login.setText(e.getMessage());
                        }
                        MainActivity.this.linear_layout1 = new LinearLayout(MainActivity.this);
                        MainActivity.this.linear_layout1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                        MainActivity.this.linear_layout1.setOrientation(LinearLayout.VERTICAL);
                        MainActivity.this.main_vertical_linear_layout.addView(MainActivity.this.linear_layout1);
                        MainActivity.this.linear_layout11 = new LinearLayout(MainActivity.this);
                        MainActivity.this.linear_layout11.setLayoutParams(new LayoutParams(-1, LayoutParams.WRAP_CONTENT));
                        MainActivity.this.linear_layout11.setOrientation(LinearLayout.HORIZONTAL);
                        MainActivity.this.linear_layout1.addView(MainActivity.this.linear_layout11);
                        MainActivity.this.linear_layout111 = new LinearLayout(MainActivity.this);
                        MainActivity.this.linear_layout111.setLayoutParams(new LayoutParams((int) MainActivity.this.getResources().getDimension(R.dimen._206dp), LayoutParams.WRAP_CONTENT));
                        MainActivity.this.linear_layout111.setOrientation(LinearLayout.VERTICAL);
                        MainActivity.this.linear_layout11.addView(MainActivity.this.linear_layout111);
                        MainActivity.this.textview1111 = new TextView(MainActivity.this);
                        MainActivity.this.textview1111.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, (int) getResources().getDimension(R.dimen._67dp)));
                        try {
                            MainActivity.this.textview1111.setText(MainActivity.this.items.getString("title"));
                        } catch (JSONException e2) {
                            e2.printStackTrace();
                        }
                        MainActivity.this.textview1111.setTextSize(2, 30.0f);
                        MainActivity.this.linear_layout111.addView(MainActivity.this.textview1111);
                        MainActivity.this.textview1112 = new TextView(MainActivity.this);
                        MainActivity.this.textview1112.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, (int) getResources().getDimension(R.dimen._67dp)));
                        try {
                            TextView textView = MainActivity.this.textview1112;
                            StringBuilder sb = new StringBuilder();
                            sb.append("Price: ");
                            sb.append(MainActivity.this.items.getDouble("price"));
                            textView.setText(sb.toString());
                        } catch (JSONException e3) {
                            e3.printStackTrace();
                        }
                        MainActivity.this.textview1112.setTextSize(2, 20.0f);
                        MainActivity.this.linear_layout111.addView(MainActivity.this.textview1112);

                        //////////////////////////////////
//                        MainActivity.this.textview1119 = new TextView(MainActivity.this);
//                        MainActivity.this.textview1119.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
//                        MainActivity.this.textview1119.setTextSize(2, 20.0f);
//                        MainActivity.this.textview1119.setText("To see all images of product Click on the image");
//                        MainActivity.this.linear_layout111.addView(MainActivity.this.textview1119);
                        ///////////////////////////////////////////

                        MainActivity.this.textview1113 = new TextView(MainActivity.this);
                        MainActivity.this.textview1113.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,(int) getResources().getDimension(R.dimen._67dp)));
                        MainActivity.this.textview1113.setTextSize(2, 20.0f);
                        MainActivity.this.linear_layout111.addView(MainActivity.this.textview1113);
                        if (MainActivity.this.items.getInt(str) > 0) {
                            MainActivity.this.textview1113.setText("Availability Status: Available");
                        } else {
                            MainActivity.this.textview1113.setText("Availability Status: Currently out of stock");
                        }
                        MainActivity.this.imageview111 = new ImageView(MainActivity.this);
                        MainActivity.this.imageview111.setLayoutParams(new LayoutParams((int) MainActivity.this.getResources().getDimension(R.dimen._200dp), (int) MainActivity.this.getResources().getDimension(R.dimen._200dp)));
                        MainActivity.this.imageview111.setImageResource(R.drawable.ic_launcher_background);
                        Picasso with = Picasso.with(MainActivity.this);
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(getString(R.string.server_url));
                        sb2.append(MainActivity.this.items.getString("image"));
                        final String image2=getString(R.string.server_url)+MainActivity.this.items.getString("image2");
                        final String image3=getString(R.string.server_url)+MainActivity.this.items.getString("image3");
                        final String image4=getString(R.string.server_url)+MainActivity.this.items.getString("image4");
                        final String image5=getString(R.string.server_url)+MainActivity.this.items.getString("image5");
                        imageview111.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent123 = new Intent(MainActivity.this, PopupActivity.class);
                                intent123.putExtra("token", "");
                                intent123.putExtra("image2",image2 );
                                intent123.putExtra("image3",image3 );
                                intent123.putExtra("image4",image4 );
                                intent123.putExtra("image5",image5 );
                                startActivity(intent123);
                            }
                        });
                        with.load(sb2.toString()).resize(200, 200).into(MainActivity.this.imageview111);
                        MainActivity.this.image_text_linear = new LinearLayout(MainActivity.this);
                        MainActivity.this.image_text_linear.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                        MainActivity.this.image_text_linear.setOrientation(LinearLayout.VERTICAL);
                        MainActivity.this.linear_layout11.addView(MainActivity.this.image_text_linear);
                        ////////////////////
                        MainActivity.this.text_under_image = new TextView(MainActivity.this);
                        MainActivity.this.text_under_image.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                        MainActivity.this.text_under_image.setTextSize(2, 20.0f);
                        MainActivity.this.text_under_image.setText("Click on above image to see all images");
                        ////////////////////

                        MainActivity.this.image_text_linear.addView(MainActivity.this.imageview111);
                        MainActivity.this.image_text_linear.addView(MainActivity.this.text_under_image);

                        MainActivity.this.textview11 = new TextView(MainActivity.this);
                        MainActivity.this.textview11.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                        try {
                            TextView textView2 = MainActivity.this.textview11;
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append("Details: ");
                            sb3.append(MainActivity.this.items.getString("summary"));
                            textView2.setText(sb3.toString());
                        } catch (JSONException e4) {
                            e4.printStackTrace();
                        }
                        MainActivity.this.textview11.setTextSize(2, 20.0f);
                        MainActivity.this.linear_layout1.addView(MainActivity.this.textview11);
                        if (MainActivity.this.items.getInt(str) > 0) {
                            MainActivity.this.button11 = new Button(MainActivity.this);
                            MainActivity.this.button11.setLayoutParams(new LayoutParams(-1, -2));
                            MainActivity.this.button11.setBackgroundColor(getResources().getColor(R.color.Blue));
//                            MainActivity.this.button11.setBackgroundResource(Color.parseColor(String.valueOf(R.color.addressButtonColor)));
                            MainActivity.this.button11.setText("Add to Cart");

                            //////////////////////////////////////

                            //////////////////////////////////////

                            final int p_id = MainActivity.this.items.getInt("id");
                            MainActivity.this.button11.setOnClickListener(new OnClickListener() {
                                public void onClick(View v) {
                                    if(!login.getText().equals("Create Profile")) {
                                        if (token != null && token.length() > 0) {
                                            if(signup.getText().equals("Verify Email")){
                                                Toast.makeText(MainActivity.this, "You have to verify the email to add the product to cart", Toast.LENGTH_LONG).show();
                                            }else {

                                                Intent intentmy123 = new Intent(MainActivity.this, ConfirmActivity.class);
                                                intentmy123.putExtra("token", token);
                                                intentmy123.putExtra("p_id", p_id);
                                                intentmy123.putExtra("link", "/cart_api/add_product_in_cart/");
                                                MainActivity.this.startActivity(intentmy123);
                                            }
                                        } else {
                                            Toast.makeText(MainActivity.this, "Login Needed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    else{
                                        Toast.makeText(MainActivity.this, "You have to Make a Profile to add the product to cart", Toast.LENGTH_LONG).show();
                                    }

                                }
                            });
                            MainActivity.this.linear_layout1.addView(MainActivity.this.button11);


                        }
                        View v = new View(MainActivity.this);
                        v.setLayoutParams(new LayoutParams(-1, 20));
                        v.setBackgroundColor(Color.parseColor("#F8F8FF"));
                        MainActivity.this.linear_layout1.addView(v);
                    }
                    MainActivity.this.textview11yy = new TextView(MainActivity.this);
                    MainActivity.this.textview11yy.setLayoutParams(new LayoutParams(-1, (int) MainActivity.this.getResources().getDimension(R.dimen._67dp)));
                    MainActivity.this.linear_layout1.addView(MainActivity.this.textview11yy);
                } catch (JSONException e5) {
                    MainActivity.this.login.setText(e5.getMessage());
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                MainActivity.this.login.setText(error.getMessage());
            }
        }) {
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("phone", "e");
                params.put("score", "k");
                return params;
            }
        };
        Volley.newRequestQueue(this).add(r3);
    }
}
