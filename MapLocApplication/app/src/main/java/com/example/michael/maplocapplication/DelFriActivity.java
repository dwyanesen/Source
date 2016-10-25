package com.example.michael.maplocapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by Michael on 2016/10/17.
 */
public class DelFriActivity extends Activity{

    private ArrayList<Object> newone;

    private void saveObject(String name){
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = this.openFileOutput(name, MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(newone);
        } catch (Exception e) {
            e.printStackTrace();
            //这里是保存文件产生异常
        } finally {
            if (fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    //fos流关闭异常
                    e.printStackTrace();
                }
            }
            if (oos != null){
                try {
                    oos.close();
                } catch (IOException e) {
                    //oos流关闭异常
                    e.printStackTrace();
                }
            }
        }
    }
    //以上为保存对象的代码

    private Object getObject(String name){
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = this.openFileInput(name);
            ois = new ObjectInputStream(fis);
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            //这里是读取文件产生异常
        } finally {
            if (fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    //fis流关闭异常
                    e.printStackTrace();
                }
            }
            if (ois != null){
                try {
                    ois.close();
                } catch (IOException e) {
                    //ois流关闭异常
                    e.printStackTrace();
                }
            }
        }
        //读取产生异常，返回null
        return null;
    }
    //以上为取得对象的代码

    Button bt1;
    Button bt2;
    TextView tv1;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_delete);

        Intent intent = getIntent();

        final String number = intent.getStringExtra("numberdel");
        tv1 = (TextView)findViewById(R.id.txt_friend_number);
        tv1.setText(number);

        bt1 = (Button)findViewById(R.id.btn_dialog_ok);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                newone = (ArrayList<Object>) getObject("MapObject.dat");
                Object temp = new Object();
                for(Object i:newone) {
                    if(i instanceof Friend) {
                        if ((((Friend) i).getPhonenum().toString()).equals(number)) {
                            //Toast.makeText(DelFriActivity.this, number, Toast.LENGTH_SHORT).show();
                            temp = i;
                        }
                    }
                }
                newone.remove(temp);
                //ArrayList<>删除单个项目

                //Toast.makeText(DelFriActivity.this, number, Toast.LENGTH_SHORT).show();
                saveObject("MapObject.dat");
                setResult(RESULT_OK, intent);
                //Toast.makeText(DelFriActivity.this,name, Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        bt2 = (Button)findViewById(R.id.btn_dialog_close);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
