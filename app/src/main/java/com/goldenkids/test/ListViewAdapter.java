package com.goldenkids.test;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    private TextView location;
    private TextView lat;
    private TextView lang;
    private TextView km;


    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<flood> floodArrayList = new ArrayList<flood>();

    // ListViewAdapter의 생성자
    public ListViewAdapter() {


    }

    // Adapter에 사용되는 데이터의 개수를 리턴
    @Override
    public int getCount() {
        return floodArrayList.size();
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        location = convertView.findViewById(R.id.location);
        lat =  convertView.findViewById(R.id.lat);
        lang = convertView.findViewById(R.id.lang);
        km = convertView.findViewById(R.id.km);

        flood listViewItem = floodArrayList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        location.setText(listViewItem.getLocation());
        lat.setText(listViewItem.getLat());
        lang.setText(listViewItem.getLang());
        km.setText(listViewItem.getKm());


        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴
    @Override
    public long getItemId(int position) {
        return position;
    }



    // 지정한 위치(position)에 있는 데이터 리턴
    @Override
    public Object getItem(int position) {
        return floodArrayList.get(position);
    }

    // 아이템 데이터 추가를 위한 함수.
    public void addItem(String lat, String lang, String location,String km) {
        flood item = new flood(lat,lang,location, km);

        floodArrayList.add(item);
    }
}
