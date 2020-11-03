package com.example.asmpk01120;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asmpk01120.adpter.DatabaseHelper;
import com.example.asmpk01120.adpter.TaiKhoan;
import com.example.asmpk01120.adpter.TaiKhoanAdap;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DanhMuc_Frag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DanhMuc_Frag extends Fragment {
    ListView lv;
    final int REQUEST_CODE_GALLERY = 999;
    ImageView img_avartar;
    TextView txt_ten;
    ArrayList<TaiKhoan> arrayList;
    TaiKhoanAdap taiKhoanAdap;
    DatabaseHelper db;
    int index = 1;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DanhMuc_Frag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DanhMuc_Frag.
     */
    // TODO: Rename and change types and number of parameters
    public static DanhMuc_Frag newInstance(String param1, String param2) {
        DanhMuc_Frag fragment = new DanhMuc_Frag();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_danh_muc_, container, false);
        img_avartar = view.findViewById(R.id.img_avatar);
        txt_ten = view.findViewById(R.id.txt_ten);
        lv = view.findViewById(R.id.lv);
        arrayList = new ArrayList<>();
        taiKhoanAdap = new TaiKhoanAdap(getActivity(), R.layout.list_taikhoan, arrayList);
        lv.setAdapter(taiKhoanAdap);
        db = new DatabaseHelper(getActivity(), "new.sqlite", null, 1);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                index = position;
                final int a = arrayList.get(index).getId();
                final String tendangnhap = arrayList.get(index).getHovaten();
                final String pass = arrayList.get(index).getPass();
                final String tennguoidung = arrayList.get(index).getTennguoidung();

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
                                DiaLogSua(a, tendangnhap, pass, tennguoidung);
                                break;
                            case R.id.menuChitiet:
                                Intent intent = new Intent(getActivity(), chitiettaikhoan.class);
                                intent.putExtra("ID", a + "");
                                startActivity(intent);
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
                return false;
            }


        });
        img_avartar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY

                );
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
        });

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("tennguoidung", MODE_PRIVATE);
        txt_ten.setText(sharedPreferences.getString("ten", ""));
        getTaiKhoan();
        return view;
    }
    public void DialogXoa(final int id) {
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(getActivity());
        dialogXoa.setMessage("Bạn muốn xóa tài khoản này?");
        dialogXoa.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Cursor cursorCheck = db.GetData("SELECT * FROM DanhSach");
                if(cursorCheck.getCount() == 1){
                    Toast.makeText(getActivity(), "Không thể xóa!", Toast.LENGTH_SHORT).show();
                }else {
                    db.QueryData("DELETE FROM DanhSach  WHERE Id = '" + id + "'");
                    getTaiKhoan();
                }

            }
        });
        dialogXoa.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogXoa.show();
    }

    public void DiaLogSua(final int id, String tendangnhap, final String pass, final String name) {
        final Dialog dialog = new Dialog(getActivity(), R.style.CustomAlertDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogsuataikhoan);

        final EditText edthovaten = dialog.findViewById(R.id.edthovaten);
        final EditText edttendangnhap = dialog.findViewById(R.id.edttendangnhap);
        final EditText edtmatkhau = dialog.findViewById(R.id.edtmatkhaumoi);

        Button btnXacNhan = dialog.findViewById(R.id.btncapnhat);

        edthovaten.setText(name);
        edttendangnhap.setText(tendangnhap);
        edtmatkhau.setText(pass);

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edthovaten.getText().toString().trim();
                String user = edttendangnhap.getText().toString().trim();
                String pass = edtmatkhau.getText().toString().trim();

                db.QueryData("UPDATE DanhSach SET Ten = '" + user + "', Pass = '" + pass + "', TenNguoiDung = '" + name + "' Where Id = '" + id + "'");
                dialog.dismiss();
                getTaiKhoan();
            }
        });


        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MODE_CHANGED);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();

            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                img_avartar.setImageBitmap(bitmap);
                img_avartar.setScaleType(ImageView.ScaleType.FIT_CENTER);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getTaiKhoan() {

        Cursor cursorCheck = db.GetData("SELECT * FROM DanhSach");
        while (cursorCheck.moveToNext()) {
            int id = cursorCheck.getInt(0);
            String ten = cursorCheck.getString(1);
            String manguoidung = cursorCheck.getString(3);
            String pass = cursorCheck.getString(2);

            arrayList.add(new TaiKhoan(id, ten, manguoidung, pass));
        }
        taiKhoanAdap.notifyDataSetChanged();
    }

}