package com.example.e_comm;


import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;

public class PopupActivity extends AppCompatActivity {
    ImageView image2=null,image3=null,image4=null,image5=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_popup);

        image2=findViewById(R.id.image2);
        image3=findViewById(R.id.image3);
        image4=findViewById(R.id.image4);
        image5=findViewById(R.id.image5);


        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().setLayout((int) (dm.widthPixels*.7), (int) (dm.heightPixels*.85));
        WindowManager.LayoutParams params=getWindow().getAttributes();
        params.gravity= Gravity.CENTER;
        params.x=0;
        params.y=-20;
        getWindow().setAttributes(params);
        Intent intentm=getIntent();

        Picasso with2 = Picasso.with(PopupActivity.this);
        StringBuilder sb2 = new StringBuilder();
        sb2.append(intentm.getStringExtra("image2"));
        with2.load(sb2.toString()).resize(200, 200).into(PopupActivity.this.image2);

        Picasso with3 = Picasso.with(PopupActivity.this);
        StringBuilder sb3 = new StringBuilder();
        sb3.append(intentm.getStringExtra("image3"));
        with3.load(sb3.toString()).resize(200, 200).into(PopupActivity.this.image3);

        Picasso with4 = Picasso.with(PopupActivity.this);
        StringBuilder sb4 = new StringBuilder();
        sb4.append(intentm.getStringExtra("image4"));
        with4.load(sb4.toString()).resize(200, 200).into(PopupActivity.this.image4);

        Picasso with5 = Picasso.with(PopupActivity.this);
        StringBuilder sb5 = new StringBuilder();
        sb5.append(intentm.getStringExtra("image5"));
        with5.load(sb5.toString()).resize(200, 200).into(PopupActivity.this.image5);


    }
}
