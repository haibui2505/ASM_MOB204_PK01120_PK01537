package com.example.asmpk01120;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class thongBaoAdapter extends BaseAdapter {


    private Context context;
    private int layout;
    private List<ThongBao> thongBaoList;

    public thongBaoAdapter(Context context, int layout, List<ThongBao> thongBaoList) {
        this.context = context;
        this.layout = layout;
        this.thongBaoList = thongBaoList;
    }


    @Override
    public int getCount() {
        return thongBaoList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {

        TextView txt_tenThongBao, txt_ngayThongBao;

    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        thongBaoAdapter.ViewHolder holder;
        if (view == null) {
            holder = new thongBaoAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txt_tenThongBao = view.findViewById(R.id.tenThongBao);
            holder.txt_ngayThongBao = view.findViewById(R.id.ngayThongbao);
            view.setTag(holder);


        } else {
            holder = (thongBaoAdapter.ViewHolder) view.getTag();
        }

        final ThongBao thongBao = thongBaoList.get(i);
        holder.txt_tenThongBao.setText(thongBao.getTenThongBao());
        holder.txt_ngayThongBao.setText(thongBao.getNgayThongBao());

        return view;
    }
}
