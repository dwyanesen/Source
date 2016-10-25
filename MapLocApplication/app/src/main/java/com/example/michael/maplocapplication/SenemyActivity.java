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
 * Created by Michael on 2016/10/22.
 */
public class SenemyActivity extends Activity {

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch(requestCode){
            case 1:
                if(RESULT_OK == resultCode)
                {

                    newone = (ArrayList<Object>) getObject("MapObject.dat");
                    String returnname = data.getStringExtra("pass_name");

                    //Toast.makeText(SfriendActivity.this,returnname, Toast.LENGTH_SHORT).show();
                    String returnnumber = data.getStringExtra("pass_number");
                    String returnfeature = data.getStringExtra("pass_feature");

                    //Toast.makeText(SfriendActivity.this,returnnumber, Toast.LENGTH_SHORT).show();
                    Enemy b = new Enemy(returnname,returnnumber,returnfeature);

                    newone.add(b);
                    saveObject("MapObject.dat");

                    ArrayList<Object> newoneo =new ArrayList<>();
                    for(Object i:newone) {
                        if(i instanceof Enemy){
                            newoneo.add(i);
                        }
                    }

                    ShowndAdapter adapter = new ShowndAdapter(SenemyActivity.this, newoneo);
                    ListView lv = (ListView) findViewById(R.id.lvw_enemies_list);//得到ListView对象的引用 /*为ListView设置Adapter来绑定数据*/
                    lv.setAdapter(adapter);

                }
                break;
            case 2:
                if(RESULT_OK == resultCode)
                {
                    newone = (ArrayList<Object>) getObject("MapObject.dat");
                    ArrayList<Object> newoneo =new ArrayList<>();
                    for(Object i:newone) {
                        if(i instanceof Enemy){
                            newoneo.add(i);
                        }
                    }
                    ShowndAdapter adapter = new ShowndAdapter(SenemyActivity.this, newoneo);
                    ListView lv = (ListView) findViewById(R.id.lvw_enemies_list);//得到ListView对象的引用 /*为ListView设置Adapter来绑定数据*/
                    lv.setAdapter(adapter);
                }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enemies_list);

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
            if(i instanceof Enemy){
                newoneo.add(i);
            }
        }

        ShowndAdapter adapter = new ShowndAdapter(SenemyActivity.this, newoneo);
        ListView lv = (ListView) findViewById(R.id.lvw_enemies_list);//得到ListView对象的引用 /*为ListView设置Adapter来绑定数据*/
        lv.setAdapter(adapter);





        bt2 = (Button)findViewById(R.id.btn_enemies_list_add);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SenemyActivity.this, AddEneActivity.class);
                startActivityForResult(intent,1);
            }
        });

        bt3 = (Button)findViewById(R.id.btn_enemies_list_radar);
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

//                Intent intent = new Intent(SenemyActivity.this, MainActivity.class);
//                startActivity(intent);
            }
        });



        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                newone = (ArrayList<Object>) getObject("MapObject.dat");
                newoneo =new ArrayList<>();
                for(Object i:newone) {
                    if(i instanceof Enemy){
                        newoneo.add(i);
                    }
                }
                if(newoneo.get(position) instanceof Enemy) {
                    Intent intent = new Intent(SenemyActivity.this, DelEneActivity.class);
                    Enemy c = (Enemy) newoneo.get(position);
                    intent.putExtra("numberdel", c.getPhonenum());
                    Toast.makeText(SenemyActivity.this, "要删除敌人了诶 XD", Toast.LENGTH_SHORT).show();
                    startActivityForResult(intent, 2);
                }
            }
        });

        bt1 = (Button)findViewById(R.id.btn_enemies_list_friends);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SenemyActivity.this, SfriendActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
