package com.example.asmpk01120;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrangChu_Frag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrangChu_Frag extends Fragment {

    TextView edittext_tenNguoiDung;
    LinearLayout vanDon, taoDon, taoDonQte, excel, chatLuong, more;
    DatabaseHelper db;
    SharedPreferences sharedPreferences, sharedPreferencesID;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TrangChu_Frag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TrangChu_Frag.
     */
    // TODO: Rename and change types and number of parameters
    public static TrangChu_Frag newInstance(String param1, String param2) {
        TrangChu_Frag fragment = new TrangChu_Frag();
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
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trang_chu_2, container, false);

        //Intent Xin chào, ...
        edittext_tenNguoiDung = view.findViewById(R.id.edittext_tenNguoiDung);


        final Intent intent = getActivity().getIntent();
        final String myname = intent.getStringExtra("hvt");
        final Integer myId = intent.getIntExtra("id", 1);
        final String myStringId = myId + "";

        //lưu tên người dùng vào bộ nhớ tạm
        sharedPreferences = getActivity().getSharedPreferences("tennguoidung", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ten", myname + "");
        editor.commit();

        SharedPreferences sharedPreferencesID = getActivity().getSharedPreferences("IDnguoidung", MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sharedPreferencesID.edit();
        editor1.putString("id", myStringId);
        editor1.commit();



        edittext_tenNguoiDung.setText("Xin chào, " + sharedPreferences.getString("ten", ""));

//        Tạo database
        db = new DatabaseHelper(getActivity(), "new.sqlite", null, 1);

        db.QueryData("CREATE TABLE IF NOT EXISTS DiaChi (Id INTEGER PRIMARY KEY AUTOINCREMENT, MaNguoiDung VARCHAR(5) , Ten VARCHAR(200), Phone VARCHAR(10), DiaChi VARCHAR(200))");
        db.QueryData("CREATE TABLE IF NOT EXISTS GuiHang (Id INTEGER PRIMARY KEY AUTOINCREMENT, MaHangHoa VARCHAR(5), Ten VARCHAR(30), Phone VARCHAR(10), DiaChi VARCHAR(200), ThuHo VARCHAR(15), NgayGui VARCHAR(200), TrangThai INTEGER) ");
        db.QueryData("CREATE TABLE IF NOT EXISTS ThongBao (Id INTEGER PRIMARY KEY AUTOINCREMENT, TenThongBao VARCHAR(100), NgayThongBao VARCHAR(10)) ");

        //Layout click
        vanDon = view.findViewById(R.id.LiL_vanDon);
        taoDon = view.findViewById(R.id.lil_taoDon);
        taoDonQte = view.findViewById(R.id.lil_taoDonQte);
        excel = view.findViewById(R.id.lil_Excel);
        chatLuong = view.findViewById(R.id.lil_chatLuong);
        more = view.findViewById(R.id.lil_more);

        vanDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Chức năng sắp được bổ sung!", Toast.LENGTH_SHORT).show();
            }
        });
        taoDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor cursor = db.getMaNguoiDung(myStringId.trim());
                if (cursor.getCount() == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Trước tiên bạn phải có địa chỉ, bạn có muốn thêm không?");
                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(getActivity(), DiaChi.class);
                            intent.putExtra("ten", myname);
                            intent.putExtra("id", myId);
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();


                } else {
                    Intent intent = new Intent(getActivity(), TaoDonHang.class);
                    intent.putExtra("ten", myname);
                    intent.putExtra("id", myId);
                    startActivity(intent);
                }


            }
        });
        taoDonQte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Chức năng sắp được bổ sung!", Toast.LENGTH_SHORT).show();

            }
        });
        excel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Chức năng sắp được bổ sung!", Toast.LENGTH_SHORT).show();
            }
        });
        chatLuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Chức năng sắp được bổ sung!", Toast.LENGTH_SHORT).show();
            }
        });
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Chức năng sắp được bổ sung!", Toast.LENGTH_SHORT).show();
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
        } else {
            final Cursor cursor = db.GetData("SELECT * FROM GuiHang WHERE MaHangHoa = '" + id + "' ORDER BY Id DESC");
            while (cursor.moveToNext()) {

                final int idnv = cursor.getInt(0);
                Integer trangThai = cursor.getInt(7);

                if (trangThai == 0) {

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            db.QueryData("UPDATE GuiHang SET TrangThai = 1 Where Id = '" + idnv + "'");
                        }
                    }, 10000);
                }
            }
        }
    }


}