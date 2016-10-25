package com.example.michael.maplocapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Michael on 2016/10/22.
 */
public class AddEneActivity extends Activity {
    private EditText tx1;
    private EditText tx2;
    private Button bt1;
    private Button bt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_enemy);

        bt1 = (Button)findViewById(R.id.btn_dialog_ok);
        bt2 = (Button)findViewById(R.id.btn_dialog_close);

        tx1 = (EditText)findViewById(R.id.txt_enemy_name);
        tx2 = (EditText)findViewById(R.id.txt_enemy_number);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();

                final String name = tx1.getText().toString();
                final String number = tx2.getText().toString();

                if( name.equals("") || number.equals("")) {
                    Toast.makeText(AddEneActivity.this,"没输全信息，已自动返回", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    intent.putExtra("pass_name", name);
                    intent.putExtra("pass_number", number);
                    intent.putExtra("pass_feature","enemy");

                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
