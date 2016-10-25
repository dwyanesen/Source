package com.example.michael.maplocapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by Michael on 2016/10/16.
 */
public class SfriendActivity extends Activity {

    private ArrayList<Object> newone = null;
    private ArrayList<Object> newoneo;

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

    private Button bt1;
    private Button bt2;
    private Button bt3;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case 1:
                if(RESULT_OK == resultCode)
                {
                    newone = (ArrayList<Object>) getObject("MapObject.dat");
                    String returnname = data.getStringExtra("pass_name");

                    //Toast.makeText(SfriendActivity.this,returnname, Toast.LENGTH_SHORT).show();
                    String returnnumber = data.getStringExtra("pass_number");

                    //Toast.makeText(SfriendActivity.this,returnnumber, Toast.LENGTH_SHORT).show();
                    Friend b = new Friend(returnname,returnnumber);

                    newone.add(b);
                    saveObject("MapObject.dat");

                    ArrayList<Object> newoneo =new ArrayList<>();
                    for(Object i:newone) {
                        if(i instanceof Friend){
                            newoneo.add(i);
                        }
                    }

                    ShowAdapter adapter = new ShowAdapter(SfriendActivity.this, newoneo);
                    ListView lv = (ListView) findViewById(R.id.lvw_friends_list);//得到ListView对象的引用 /*为ListView设置Adapter来绑定数据*/
                    lv.setAdapter(adapter);
                }
                break;
            case 2:
                if(RESULT_OK == resultCode)
                {
                    newone = (ArrayList<Object>) getObject("MapObject.dat");
                    ArrayList<Object> newoneo =new ArrayList<>();
                    for(Object i:newone) {
                        if(i instanceof Friend){
                            newoneo.add(i);
                        }
                    }
                    ShowAdapter adapter = new ShowAdapter(SfriendActivity.this, newoneo);
                    ListView lv = (ListView) findViewById(R.id.lvw_friends_list);//得到ListView对象的引用 /*为ListView设置Adapter来绑定数据*/
                    lv.setAdapter(adapter);
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_list);

        Intent intent = getIntent();
        newone = new ArrayList<>();
        if((ArrayList<Object>)getObject("MapObject.dat") == null){
//            Friend a = new Friend("ds","13169642628");
//            newone.add(a);
            saveObject("MapObject.dat");
        }else {
            newone = (ArrayList<Object>) getObject("MapObject.dat");
        }
        saveObject("MapObject.dat");

        newoneo =new ArrayList<>();
        for(Object i:newone) {
            if(i instanceof Friend){
                newoneo.add(i);
            }
        }

        ShowAdapter adapter = new ShowAdapter(SfriendActivity.this, newoneo);
        ListView lv = (ListView) findViewById(R.id.lvw_friends_list);//得到ListView对象的引用 /*为ListView设置Adapter来绑定数据*/
        lv.setAdapter(adapter);



        bt1 = (Button)findViewById(R.id.btn_friends_list_radar);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(SfriendActivity.this, MainActivity.class);
//                startActivity(intent);
                finish();
            }
        });

        bt2 = (Button)findViewById(R.id.btn_friends_list_add);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SfriendActivity.this, AddFriActivity.class);
                startActivityForResult(intent,1);
            }
        });



        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                newone = (ArrayList<Object>)getObject("MapObject.dat") ;
                newoneo =new ArrayList<>();
                for(Object i:newone) {
                    if(i instanceof Friend){
                        newoneo.add(i);
                    }
                }
                if(newoneo.get(position) instanceof Friend) {
                    Intent intent = new Intent(SfriendActivity.this, DelFriActivity.class);
                    Friend c = (Friend) newoneo.get(position);
                    intent.putExtra("numberdel", c.getPhonenum());
                    Toast.makeText(SfriendActivity.this, "要删除朋友了喔 :)", Toast.LENGTH_SHORT).show();
                    startActivityForResult(intent, 2);
                }
            }
        });

        bt3 = (Button)findViewById(R.id.btn_friends_list_enemies);
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SfriendActivity.this, SenemyActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
