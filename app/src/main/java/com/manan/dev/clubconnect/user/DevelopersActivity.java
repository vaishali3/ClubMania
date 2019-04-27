package com.manan.dev.clubconnect.user;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.manan.dev.clubconnect.CircleTransform;
import com.manan.dev.clubconnect.R;
import com.squareup.picasso.Picasso;

public class DevelopersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developers);
       ImageView archana = (ImageView) findViewById(R.id.img_archana);
        ImageView prerna = (ImageView) findViewById(R.id.img_prerna);

        Picasso.with(DevelopersActivity.this).load("https://i.ibb.co/phmrt79/91-95551-04435-20180808-160918.jpg").transform(new CircleTransform()).into(archana);
        Picasso.with(DevelopersActivity.this).load("https://i.ibb.co/2q1RWsQ/Whats-App-Image-2019-04-04-at-8-33-41-PM.jpg").transform(new CircleTransform()).into(prerna);

    }
}
