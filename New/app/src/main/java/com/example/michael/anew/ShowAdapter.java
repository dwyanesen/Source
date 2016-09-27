
package com.example.michael.anew;

/**
 * Created by Michael on 2016/9/26.
 */

        import java.util.ArrayList;
        import java.util.List;

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.BaseAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;

public class ShowAdapter extends BaseAdapter {

    private static final int type_1=0;//Time Item's sign
    private static final int type_2=1;//Empty Item's sign

    private static final int view_type=2;//the number of items shown on the ListView

    private Context context;

    private ArrayList<Object> newobject=new ArrayList<Object>();

    public ShowAdapter(Context context,ArrayList<Object> newobject)
    {
        this.context=context;
        this.newobject=newobject;
    }

    @Override
    public int getItemViewType(int position) {
        int result=0;
        if (newobject.get(position) instanceof Empty)
            return type_2;
        else if(newobject.get(position) instanceof Time)
            return type_1;
        return result;
    }

    @Override
    public int getViewTypeCount() {
        return view_type;
    }

    @Override
    public int getCount() {
        return newobject.size();
    }

    @Override
    public Object getItem(int position) {
        return newobject.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder1{
        TextView t_event;
        TextView t_week;
        TextView t_day;
    }
    private static class ViewHolder2{
        ImageView kong;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        //创建两种不同种类的viewHolder变量
        ViewHolder1 holder1 = null;
        ViewHolder2 holder2 = null;
        //根据position获得View的type
        int type = getItemViewType(position);
        //if(convertView==null) {
        holder1 = new ViewHolder1();
        holder2 = new ViewHolder2();
        //根据不同的type 来inflate不同的item layout
        //然后设置不同的tag
        //这里的tag设置是用的资源ID作为Key
        switch (type) {
            case type_1:
                convertView = View.inflate(context, R.layout.show_item, null);
                holder1.t_event = (TextView) convertView.findViewById(R.id.sright);
                holder1.t_week = (TextView) convertView.findViewById(R.id.sleft1);
                holder1.t_day = (TextView) convertView.findViewById(R.id.sleft2);
                convertView.setTag(R.id.tag_first, holder1);
                break;
            case type_2:
                convertView = View.inflate(context, R.layout.empty_item, null);
                holder2.kong = (ImageView) convertView.findViewById(R.id.kong);
                convertView.setTag(R.id.tag_second, holder2);
                break;
        }
        //}//else{
        //根据不同的type来获得tag
        //switch (type) {
        //case type_1:
        //holder1=(ViewHolder1)convertView.getTag(R.id.tag_first);
        //case type_2:
        //holder1=(ViewHolder1)convertView.getTag(R.id.tag_second);
        //}
        //}
        //这里的ifelse判断将会造成ListView下拉刷新崩溃

        Object o = newobject.get(position);
        //根据不同的type设置数据
        switch (type) {
            case type_1:
                Time a = (Time) o;
                String sss;
                if(a.getWeek().equals("1"))
                    sss="周一";
                else if(a.getWeek().equals("2"))
                    sss="周二";
                else if(a.getWeek().equals("3"))
                    sss="周三";
                else if(a.getWeek().equals("4"))
                    sss="周四";
                else if(a.getWeek().equals("5"))
                    sss="周五";
                else if(a.getWeek().equals("6"))
                    sss="周六";
                else
                    sss="周日";
                holder1.t_week.setText(sss);
                holder1.t_day.setText(a.getDay());
                holder1.t_event.setText(a.getEvent());
                break;

            case type_2:
                Empty b = (Empty) o;
                holder2.kong.setImageResource(b.getEmpty());
                break;
        }
        return convertView;
    }

}

