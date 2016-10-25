package com.example.michael.maplocapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Michael on 2016/10/25.
 */
public class FdetailActivity extends Activity {
    private TextView tvfn;
    private TextView tvfnu;
    private TextView tvll;
    private Button btrd;
    private Button bte;

    String name;
    String number;
    String wei;
    String jing;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_detail);

        tvfn = (TextView)findViewById(R.id.txt_friend_name);
        tvfnu = (TextView)findViewById(R.id.txt_friend_number);
        tvll = (TextView)findViewById(R.id.txt_friend_long_lang);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        number = intent.getStringExtra("number");
        wei = intent.getStringExtra("weip");
        jing = intent.getStringExtra("jingp");

        tvfn.setText(name);
        tvfnu.setText(number);
        tvll.setText(wei+"/"+jing);

        bte = (Button)findViewById(R.id.btn_radar);
        bte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btrd = (Button)findViewById(R.id.btn_enemies);
        btrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FdetailActivity.this,SenemyActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
