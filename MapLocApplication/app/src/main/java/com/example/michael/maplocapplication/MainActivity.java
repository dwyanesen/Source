package com.example.michael.maplocapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    public static double jing;
    public static double wei;
    //窃听相关
    private static final String strRes = "android.provider.Telephony.SMS_RECEIVED";

    ArrayList<Object> newone;
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
    MapView mMapView = null;
    private BaiduMap baiduMap;
    private LocationClient mLocationClient = null;

    private Button bt2;
    private Button bt3;
    private Button bt1;
    private ToggleButton bt4;

    double weijie;
    double jingjie;
    String sender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);

        baiduMap = mMapView.getMap();
        //普通地图
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        bt4 = (ToggleButton)findViewById(R.id.btn_refresh);
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String phone = phoneEt.getText().toString();
                //String context = contextEt.getText().toString();
                int n = 0;
                int m = 0;
                newone = (ArrayList<Object>)getObject("MapObject.dat");
                for(Object i:newone) {
                    if(i instanceof Friend) {
                        //String phone = "13169642628";
                        //String context = "where are you?";
                        String phone = ((Friend) i).getPhonenum();
                        String context = "where are you?";
                        SmsManager manager = SmsManager.getDefault();
                        ArrayList<String> list = manager.divideMessage(context);  //因为一条短信有字数限制，因此要将长短信拆分
                        for (String text : list) {
                            manager.sendTextMessage(phone, null, text, null, null);
                        }
                        n = n + 1;
                    }else{
                        //String phone = "13169642628";
                        //String context = "where are you?";
                        String phone = ((Enemy) i).getPhonenum();
                        String context = "where are you?";
                        SmsManager manager = SmsManager.getDefault();
                        ArrayList<String> list = manager.divideMessage(context);  //因为一条短信有字数限制，因此要将长短信拆分
                        for (String text : list) {
                            manager.sendTextMessage(phone, null, text, null, null);
                        }
                        m = m + 1;
                    }
                }
                Toast.makeText(getApplicationContext(), Integer.toString(n)+"个好友"+Integer.toString(m)+"个敌人"+"发送完毕", Toast.LENGTH_SHORT).show();

                ImageView infoOperatingIV = (ImageView)findViewById(R.id.imageview_sweep);
                Animation operatingAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.tips);
                LinearInterpolator lin = new LinearInterpolator();
                operatingAnim.setInterpolator(lin);
                if (operatingAnim != null) {
                    infoOperatingIV.startAnimation(operatingAnim);
                }



            }
        });


        BDLocationListener listener = new MyLocationListener();
        //此处需要注意：LocationClient类必须在主线程中声明。需要Context类型的参数。
        //Context需要时全进程有效的context,推荐用getApplicationConext获取全进程有效的context

        mLocationClient = new LocationClient(getApplicationContext());
        //注册位置监听器
        mLocationClient.registerLocationListener(listener);

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 5000; //5秒发送一次
        option.setScanSpan(0);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的，与上一条span联系
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        option.setNeedDeviceDirect(true); //返回的定位结果包含手机机头方向
        mLocationClient.setLocOption(option);
        mLocationClient.start(); //启动位置请求
        mLocationClient.requestLocation();//发送请求
        //定位自己的位置

        bt3 = (Button)findViewById(R.id.btn_locate);
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LocationClientOption option = new LocationClientOption();
                option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
                );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
                option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
                int span = 5000; //5秒发送一次
                option.setScanSpan(0);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的，与上一条span联系
                option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
                option.setOpenGps(true);//可选，默认false,设置是否使用gps
                option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
                option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
                option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
                option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
                option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
                option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
                option.setNeedDeviceDirect(true); //返回的定位结果包含手机机头方向
                mLocationClient.setLocOption(option);
                mLocationClient.start(); //启动位置请求
                mLocationClient.requestLocation();//发送请求
                //定位自己的位置

//                Marker marker;
//                BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.friend_marker);
//                LatLng latlng = new LatLng(23.11, 113.22);
//                OverlayOptions options = new MarkerOptions().position(latlng).icon(bitmapDescriptor);
//                OverlayOptions o2 = new TextOptions().position(latlng).text("13169642628").fontSize(35);
//                marker = (Marker) baiduMap.addOverlay(options);
//                baiduMap.addOverlay(o2);
//
//                String n = "ds";
//                Bundle bd =new Bundle();
//                bd.putString("number","13169642628");
//                bd.putString("wei",Double.toString(23.11));
//                bd.putString("jing",Double.toString(113.22));
//                bd.putString("name",n);
//                marker.setExtraInfo(bd);

                ImageView infoOperatingIV = (ImageView)findViewById(R.id.imageview_sweep);
                infoOperatingIV.clearAnimation();



//                int i=0;
//                double a[] = new double[10];
//                String body = "11.11/22.22";
//                if(body.matches("^(\\d*\\.)?\\d+\\/(\\d*\\.)?\\d+$")) {
//                    StringTokenizer st = new StringTokenizer(body, "/");
//                    while (st.hasMoreElements()) {
//                            a[i] = Double.valueOf(st.nextToken());
//                            i++;
//                    }
//                    weijie = a[0];
//                    jingjie = a[1];
//                    String bodytrue = Double.toString(weijie)+"/"+Double.toString(jingjie);
//                    Toast.makeText(getApplicationContext(), bodytrue, Toast.LENGTH_SHORT).show();
//                }
//                int i=0;
//                String d;
//                double a[] = new double[10];
//                double weijie,jingjie;
//                String body = "Location:11.11/22.22";
//                    StringTokenizer st = new StringTokenizer(body, ":/");
//                    while (st.hasMoreElements()) {
//                        if(0==i) {
//                            d = st.nextToken();
//                            i++;
//                        }
//                        else {
//                            a[i] = Double.valueOf(st.nextToken());
//                            i++;
//                        }
//                    }
//                    weijie = a[1];
//                    jingjie = a[2];
//                String bodytrue = "Location:"+Double.toString(weijie)+"/"+Double.toString(jingjie);
//                Toast.makeText(getApplicationContext(), bodytrue, Toast.LENGTH_SHORT).show();


//                int i = 0;
//                double a[] = new double[10];
//                double weijie,jingjie;
//                String body = "11.11/22.22";
//                StringTokenizer st = new StringTokenizer(body, "/");
//                while(st.hasMoreElements()){
//                    a[i] = Double.valueOf(st.nextToken());
//                    i++;
//                }
//                weijie = a[0];
//                jingjie = a[1];
//                Toast.makeText(getApplicationContext(), Double.toString(weijie)+"/"+Double.toString(jingjie), Toast.LENGTH_SHORT).show();
            }
        });

        bt2 = (Button) findViewById(R.id.btn_friends);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SfriendActivity .class);
                startActivity(intent);
            }
        });

        bt1 = (Button) findViewById(R.id.btn_enemies);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SenemyActivity .class);
                startActivity(intent);
            }
        });

        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                newone = (ArrayList<Object>) getObject("MapObject.dat");
                for (Object i : newone) {
                    if(i instanceof Friend) {
                        if (marker.getExtraInfo().get("number").toString().equals(((Friend)i).getPhonenum())) {
                            String name = marker.getExtraInfo().get("name").toString();
                            String number = marker.getExtraInfo().get("number").toString();
                            String weip = marker.getExtraInfo().get("wei").toString();
                            String jingp = marker.getExtraInfo().get("jing").toString();
                            Intent intent = new Intent(MainActivity.this, FdetailActivity.class);
                            intent.putExtra("name",name );
                            intent.putExtra("number",number);
                            intent.putExtra("jingp",jingp);
                            intent.putExtra("weip",weip );
                            startActivity(intent);
                        }
                    }else if(i instanceof Enemy){
                        if (marker.getExtraInfo().get("number").toString().equals(((Enemy)i).getPhonenum())){

                            String name = marker.getExtraInfo().get("name").toString();
                            String number = marker.getExtraInfo().get("number").toString();
                            String weip = marker.getExtraInfo().get("wei").toString();
                            String jingp = marker.getExtraInfo().get("jing").toString();

                            Intent intent = new Intent(MainActivity.this, EdetailActivity.class);
                            intent.putExtra("name",name );
                            intent.putExtra("number",number);
                            intent.putExtra("jingp",jingp);
                            intent.putExtra("weip",weip );
                            startActivity(intent);
                        }
                    }
                }
                    return false;
                }
        });

    }

    /**
     * 位置监听器
     * <p/>
     * BDLocationListener接口有1个方法需要实现： 1.接收异步返回的定位结果，参数是BDLocation类型参数。
     */
    class MyLocationListener implements BDLocationListener {
        /**
         * 接收位置的信息回调方法
         *
         * @param location
         */
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null) {
                return;
            }
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

                Toast.makeText(MainActivity.this,"GPS定位成功", Toast.LENGTH_SHORT).show();

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");

                Toast.makeText(MainActivity.this,"网络定位成功", Toast.LENGTH_SHORT).show();

            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");

                Toast.makeText(MainActivity.this,"离线定位成功，离线定位有细微偏差", Toast.LENGTH_SHORT).show();

            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");

                Toast.makeText(MainActivity.this,"服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因", Toast.LENGTH_SHORT).show();

            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");

                Toast.makeText(MainActivity.this,"网络不同导致定位失败，请检查网络是否通畅", Toast.LENGTH_SHORT).show();

            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");

                Toast.makeText(MainActivity.this,"无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机", Toast.LENGTH_SHORT).show();

            }
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息
            List<Poi> list = location.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            Log.d("BaiduLocationApiDem", sb.toString());

            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.icon_mark3);
            //创建一个图层选项
            Marker marker;
            wei = location.getLatitude();
            jing = location.getLongitude();
            LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
            OverlayOptions options = new MarkerOptions().position(latlng).icon(bitmapDescriptor);
            OverlayOptions o2 = new TextOptions().position(latlng).text("我的位置").fontSize(35);
            marker = (Marker) baiduMap.addOverlay(options);
            baiduMap.addOverlay(o2);

            Bundle bd =new Bundle();
            bd.putString("name","您的名字");
            bd.putString("number","本机号码");
            bd.putString("weip",Double.toString(wei));
            bd.putString("jingp",Double.toString(jing));
            marker.setExtraInfo(bd);

            MapStatus mMapStatus = new MapStatus.Builder()
                    .target(latlng)
                    .zoom(18f)
                    .build();
            //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化

            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
            //改变地图状态
            baiduMap.setMapStatus(mMapStatusUpdate);

        }
    }

    BroadcastReceiver SMSBroadcastReceiver = new BroadcastReceiver() {
        double a[] = new double[10];
        int i = 0;

        @Override
        public void onReceive(Context context, Intent intent) {
            Object[] pdus = (Object[]) intent.getExtras().get("pdus");   //接收数据
            for (Object p : pdus) {
                byte[] pdu = (byte[]) p;
                SmsMessage message = SmsMessage.createFromPdu(pdu); //根据获得的byte[]封装成SmsMessage
                String body = message.getMessageBody();             //发送内容
                String date = new Date(message.getTimestampMillis()).toLocaleString();//发送时间
                sender = message.getOriginatingAddress();    //短信发送方

                if (body.matches("^(\\d*\\.)?\\d+\\/(\\d*\\.)?\\d+$")) {


                    ImageView infoOperatingIV = (ImageView)findViewById(R.id.imageview_sweep);
                    Animation operatingAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.tips);
                    LinearInterpolator lin = new LinearInterpolator();
                    operatingAnim.setInterpolator(lin);
                    if (operatingAnim != null) {
                        infoOperatingIV.startAnimation(operatingAnim);
                    }



                    StringTokenizer st = new StringTokenizer(body, "/");
                    while (st.hasMoreElements()) {
                        a[i] = Double.valueOf(st.nextToken());
                        i++;
                    }
                    weijie = a[0];
                    Toast.makeText(MainActivity.this,"收到的纬度"+Double.toString(weijie), Toast.LENGTH_SHORT).show();
                    jingjie = a[1];

                    String n = new String();
                    newone = (ArrayList<Object>) getObject("MapObject.dat");


                    for(Object i:newone) {
                        if(i instanceof Friend) {
                            if (((Friend) i).getPhonenum().equals(sender)) {
                                n = ((Friend) i).getName();

                                if(0 != jingjie && 0 != weijie){
                                    Marker marker = null;
                                    BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.friend_marker);
                                    LatLng latlng = new LatLng(weijie, jingjie);
                                    OverlayOptions options = new MarkerOptions().position(latlng).icon(bitmapDescriptor);
                                    OverlayOptions o2 = new TextOptions().position(latlng).text(n+"\n"+sender).fontSize(35);
                                    marker = (Marker) baiduMap.addOverlay(options);
                                    baiduMap.addOverlay(o2);
                                    //Toast.makeText(getApplicationContext(),"friend showing!", Toast.LENGTH_SHORT).show();

                                    Bundle bd =new Bundle();
                                    bd.putString("number",sender);
                                    bd.putString("wei",Double.toString(weijie));
                                    bd.putString("jing",Double.toString(jingjie));
                                    bd.putString("name",n);
                                    marker.setExtraInfo(bd);
                                }

                                LatLng p1 = new LatLng(wei, jing);
                                LatLng p2 = new LatLng(weijie, jingjie);
                                List<LatLng> points = new ArrayList<LatLng>();
                                LatLng latlng = new LatLng((weijie+wei)/2, (jing+jingjie)/2);
                                points.add(p1);
                                points.add(p2);
                                OverlayOptions ooPolyline = new PolylineOptions().width(10).color(0xAA00AA00).points(points);
                                double cc= Distance(wei, jing, weijie, jingjie);
                                int dis = (int) cc;
                                OverlayOptions o2 = new TextOptions().position(latlng).text("距离"+Integer.toString(dis)+"米").fontSize(35).fontColor(getResources().getColor(R.color.green));
                                baiduMap.addOverlay(ooPolyline);
                                baiduMap.addOverlay(o2);

//                                infoOperatingIV = (ImageView)findViewById(R.id.imageview_sweep);
//                                infoOperatingIV.clearAnimation();
                            }
                        }else{
                            if (((Enemy) i).getPhonenum().equals(sender)) {
                                n = ((Enemy) i).getName();

                                if(0 != jingjie && 0 != weijie){
                                    Marker marker = null;

                                    BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.enemy_marker);
                                    LatLng latlng = new LatLng(weijie, jingjie);
                                    OverlayOptions options = new MarkerOptions().position(latlng).icon(bitmapDescriptor);
                                    OverlayOptions o2 = new TextOptions().position(latlng).text(n+"\n"+sender).fontSize(35);
                                    marker = (Marker) baiduMap.addOverlay(options);
                                    baiduMap.addOverlay(o2);
                                    //Toast.makeText(getApplicationContext(),"friend showing!", Toast.LENGTH_SHORT).show();

                                    Bundle bd =new Bundle();
                                    bd.putString("number",sender);
                                    bd.putString("wei",Double.toString(weijie));
                                    bd.putString("jing",Double.toString(jingjie));
                                    bd.putString("name",n);
                                    marker.setExtraInfo(bd);
                                }

                                LatLng p1 = new LatLng(wei, jing);
                                LatLng p2 = new LatLng(weijie, jingjie);
                                List<LatLng> points = new ArrayList<LatLng>();
                                LatLng latlng = new LatLng((weijie+wei)/2, (jing+jingjie)/2);
                                points.add(p1);
                                points.add(p2);
                                OverlayOptions ooPolyline = new PolylineOptions().width(10).color(0xAAAA0000).points(points);
                                double cc= Distance(wei, jing, weijie, jingjie);
                                int dis = (int) cc;
                                OverlayOptions o2 = new TextOptions().position(latlng).text("距离"+Integer.toString(dis)+"米").fontSize(35).fontColor(getResources().getColor(R.color.red));
                                baiduMap.addOverlay(ooPolyline);
                                baiduMap.addOverlay(o2);
                            }
//                            infoOperatingIV = (ImageView)findViewById(R.id.imageview_sweep);
//                            infoOperatingIV.clearAnimation();
                        }
                    }



                }

                //String bodytrue = "Location:"+Double.toString(wei)+"/"+Double.toString(jing);
                String bodytrue = Double.toString(wei) + "/" + Double.toString(jing);
                if (body.equals("where are you?")) {
                    SmsManager manager = SmsManager.getDefault();
                    ArrayList<String> list = manager.divideMessage(bodytrue);  //因为一条短信有字数限制，因此要将长短信拆分
                    for (String text : list) {
                        manager.sendTextMessage(sender, null, text, null, null);
                    }
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理

        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
    @Override
    protected void onStart(){
        super.onStart();
        //onstart
        IntentFilter filter = new IntentFilter();
        filter.addAction(strRes);
        registerReceiver(SMSBroadcastReceiver,filter);
    }

    public double Distance(double lat1, double lng1,double lat2, double lng2) {

        double R=6370996.81;  //地球的半径

        /*
         * 获取两点间x,y轴之间的距离
         */
        Double x = (lng2 - lng1)*Math.PI*R*Math.cos(((lat1+lat2)/2)*Math.PI/180)/180;
        Double y = (lat2 - lat1)*Math.PI*R/180;

        Double distance = Math.hypot(x, y);   //得到两点之间的直线距离
        return   distance;
    }

}


