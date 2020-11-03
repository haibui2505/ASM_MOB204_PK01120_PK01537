package com.example.asmpk01120.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.asmpk01120.R;

import java.util.List;

public class TaiKhoanAdap extends BaseAdapter {

    private Context context;
    private int layout;
    private List<TaiKhoan> guiHangList;

    public TaiKhoanAdap(Context context, int layout, List<TaiKhoan> guiHangList) {
        this.context = context;
        this.layout = layout;
        this.guiHangList = guiHangList;
    }

    @Override
    public int getCount() {
        return guiHangList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {

        TextView txt_ten,txt_ma;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new TaiKhoanAdap.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.txt_ten = convertView.findViewById(R.id.txtTen);
            holder.txt_ma = convertView.findViewById(R.id.txtma);
            convertView.setTag(holder);
        } else {
            holder = (TaiKhoanAdap.ViewHolder) convertView.getTag();
        }
        final TaiKhoan guiHang = guiHangList.get(position);
        holder.txt_ten.setText(guiHang.getTennguoidung());
        holder.txt_ma.setText(guiHang.getHovaten());
        return convertView;
    }
}
