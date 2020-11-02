package com.example.asmpk01120;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class GuiHangAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<GuiHang> guiHangList;

    public GuiHangAdapter(Context context, int layout, List<GuiHang> guiHangList) {
        this.context = context;
        this.layout = layout;
        this.guiHangList = guiHangList;
    }

    @Override
    public int getCount() {
        return guiHangList.size();
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

        TextView txt_ten, txt_sdt, txt_thuho, txt_ngay, txt_diaChi, txt_trangThai;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txt_ten = view.findViewById(R.id.txt_TenNguoiNhan);
            holder.txt_sdt = view.findViewById(R.id.txt_soDienThoai);
            holder.txt_diaChi = view.findViewById(R.id.txt_diaChi);
            holder.txt_thuho = view.findViewById(R.id.txt_tienThuHo);
            holder.txt_ngay = view.findViewById(R.id.txt_ngayGui);
            holder.txt_trangThai = view.findViewById(R.id.txt_TrangThai);
            view.setTag(holder);


        } else {
            holder = (ViewHolder) view.getTag();
        }

        final GuiHang guiHang = guiHangList.get(i);
        holder.txt_ten.setText(guiHang.getTen());
        holder.txt_sdt.setText(guiHang.getPhone());
        holder.txt_diaChi.setText(guiHang.getDiaChi());
        holder.txt_ngay.setText(guiHang.getNgay());
        holder.txt_thuho.setText(guiHang.getThuho());
        Integer trangThai = Integer.valueOf(guiHang.getTrangThai());
        if (trangThai == 0) {
            holder.txt_trangThai.setText("Mới");
        } else if(trangThai == 1){
            holder.txt_trangThai.setText("Giao shipper!");
        }else if(trangThai == 2) {
            holder.txt_trangThai.setText("Đang giao. . .");
        } else if(trangThai == 3){
            holder.txt_trangThai.setText("Đã giao!");
        }else if(trangThai == 4){
            holder.txt_trangThai.setText("Hoàn trả!");
        }else{
            holder.txt_trangThai.setText("Mất hàng!");
            holder.txt_trangThai.setTextColor(Color.RED);
        }
        return view;
    }
}
