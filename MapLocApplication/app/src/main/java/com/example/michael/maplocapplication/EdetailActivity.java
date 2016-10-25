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
public class EdetailActivity extends Activity {
    private TextView tvfn;
    private TextView tvfnu;
    private TextView tvll;
    private Button btrd;
    private Button btf;

    String name;
    String number;
    String wei;
    String jing;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enemy_detail);

        tvfn = (TextView)findViewById(R.id.txt_enemy_name);
        tvfnu = (TextView)findViewById(R.id.txt_enemy_number);
        tvll = (TextView)findViewById(R.id.txt_enemy_long_lang);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        number = intent.getStringExtra("number");
        wei = intent.getStringExtra("weip");
        jing = intent.getStringExtra("jingp");

        tvfn.setText(name);
        tvfnu.setText(number);
        tvll.setText(wei+"/"+jing);

        btrd = (Button)findViewById(R.id.btn_radar);
        btrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btf = (Button)findViewById(R.id.btn_friends);
        btf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EdetailActivity.this,SfriendActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
