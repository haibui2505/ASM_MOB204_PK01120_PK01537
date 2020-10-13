package com.example.asmpk01120;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link listguihang_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class listguihang_fragment extends Fragment {

    DatabaseHelper db;
    ListView lv_guiHang;
    ArrayList<GuiHang> arrayList;
    GuiHangAdapter adapter;
    int index = 1;
    TextView txtTatCa;
    Integer trangThai;
    Boolean trangthai = false;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public listguihang_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment listguihang_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static listguihang_fragment newInstance(String param1, String param2) {
        listguihang_fragment fragment = new listguihang_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listguihang_fragment, container, false);

        txtTatCa = view.findViewById(R.id.txt_tatCa);

        lv_guiHang = view.findViewById(R.id.lv_guiHang);
        arrayList = new ArrayList<>();


        adapter = new GuiHangAdapter(getActivity(), R.layout.listview_gui_hang, arrayList);
        lv_guiHang.setAdapter(adapter);

        db = new DatabaseHelper(getActivity(), "new.sqlite", null, 1);
        // Inflate the layout for this fragment

        lv_guiHang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                index = i;
                final int a = arrayList.get(index).getId();

                final String name = arrayList.get(index).getTen();
                final String phone = arrayList.get(index).getPhone();
                final String adr = arrayList.get(index).getDiaChi();
                final String money = arrayList.get(index).getThuho();


                PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
                popupMenu.inflate(R.menu.menu_popup);
                popupMenu.setGravity(Gravity.RIGHT);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.menuXoa:
                                DialogXoa(a);
                                break;
                            case R.id.menuSua:
                                DiaLogSua(a, name, phone, adr, money);
                                break;
                        }

                        return false;
                    }
                });

                SharedPreferences sharedPreferencesID = getActivity().getSharedPreferences("IDnguoidung", MODE_PRIVATE);
                String id = sharedPreferencesID.getString("id", "").trim();
                SharedPreferences.Editor editor = sharedPreferencesID.edit();
                editor.putString("id", id);
                editor.commit();

                final Cursor cursor = db.GetData("SELECT * FROM GuiHang WHERE MaHangHoa = '" + id + "' ORDER BY Id DESC");
                while (cursor.moveToNext()) {
                    trangThai = cursor.getInt(7);

                    if (trangThai == 0){
                        popupMenu.show();
                    }
                }


                return false;
            }
        });
        getGuiHang();
        return view;
    }

    private void getGuiHang() {
        SharedPreferences sharedPreferencesID = getActivity().getSharedPreferences("IDnguoidung", MODE_PRIVATE);
        String id = sharedPreferencesID.getString("id", "").trim();
        SharedPreferences.Editor editor = sharedPreferencesID.edit();
        editor.putString("id", id);
        editor.commit();

        Cursor cursorCheck = db.GetData("SELECT * FROM GuiHang");
        if (cursorCheck.getCount() == 0) {
            Log.d("checkguihang", "Right!");
            Fragment fragment = new no_list();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_donhang,
                    fragment).commit();
        } else {
            final Cursor cursor = db.GetData("SELECT * FROM GuiHang WHERE MaNguoiDung = '" + id + "' ORDER BY Id DESC");
            txtTatCa.setText("Tất cả: " + cursor.getCount());
            arrayList.clear();
            while (cursor.moveToNext()) {

                final int idnv = cursor.getInt(0);
                String ten = cursor.getString(2);
                String phone = cursor.getString(3);
                String diachi = cursor.getString(4);
                String thuho = cursor.getString(5);
                String ngaygui = cursor.getString(6);
                trangThai = cursor.getInt(7);

                if (trangThai == 0){

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            db.QueryData("UPDATE GuiHang SET TrangThai = 1 Where Id = '" + idnv + "'");
                            getGuiHang();
                        }
                    },10000);
                }
                arrayList.add(new GuiHang(idnv, ten, phone, thuho, ngaygui, diachi, trangThai));
            }
            adapter.notifyDataSetChanged();
        }
    }

    public void DialogXoa(final int id) {
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(getActivity());
        dialogXoa.setMessage("Bạn muốn hủy đơn hàng này?");
        dialogXoa.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Date date1 = new Date();
                String date = new SimpleDateFormat("dd/MM/yyyy").format(date1);
                Cursor cursor = db.GetData("SELECT * FROM GuiHang WHERE Id = '" + id + "'");
                String name = "";
                while (cursor.moveToNext()) {
                    name = cursor.getString(2);
                }
                long val2 = db.addThongBao("1 đơn hàng đến người nhân '" + name + "' đã được hủy thành công!", date);
                if (val2 > 0) {
                    Log.d("thongbao", "Thêm thông báo thành công");
                } else Log.d("thongbao", "Thêm thông báo thất bại");
                db.QueryData("DELETE FROM GuiHang  WHERE Id = '" + id + "'");
                getGuiHang();
            }
        });
        dialogXoa.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogXoa.show();
    }

    public void DiaLogSua(final int id, String ten, final String sdt, final String adr, final String moneyy) {
        final Dialog dialog = new Dialog(getActivity(), R.style.CustomAlertDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogsua);

        final EditText edtname = dialog.findViewById(R.id.edt_hoVaTen);
        final EditText edtsdt = dialog.findViewById(R.id.edt_SDTNguoiNhan);
        final EditText addr = dialog.findViewById(R.id.edt_diaChiNguoiNhan);
        final EditText money = dialog.findViewById(R.id.edt_giaTriHangHoa);

        Button btnXacNhan = dialog.findViewById(R.id.btn_suaGuiHang);

        edtname.setText(ten);
        edtsdt.setText(sdt);
        addr.setText(adr);
        money.setText(moneyy);

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenMoi = edtname.getText().toString().trim();
                String sdtMoi = edtsdt.getText().toString().trim();
                String addrMoi = addr.getText().toString().trim();
                String moneyMoi = money.getText().toString().trim();

                db.QueryData("UPDATE GuiHang SET Ten = '" + tenMoi + "', Phone = '" + sdtMoi + "', DiaChi = '" + addrMoi + "', ThuHo = '" + moneyMoi + "' Where Id = '" + id + "'");
                Toast.makeText(getActivity(), "Chúc mừng bạn thêm nghiệp thành công!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                getGuiHang();
            }
        });


        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MODE_CHANGED);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();


    }
        public class mHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {

            switch (msg.what) {
                case 0:
                    trangThai = 0;
                    break;
                case 1:
                    trangThai = 1;
                    trangthai = false;
                    Toast.makeText(getActivity(), "" + trangThai, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}