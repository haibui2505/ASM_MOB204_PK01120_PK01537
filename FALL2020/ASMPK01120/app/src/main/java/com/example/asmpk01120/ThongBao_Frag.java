package com.example.asmpk01120;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.asmpk01120.adpter.DatabaseHelper;
import com.example.asmpk01120.adpter.ThongBao;
import com.example.asmpk01120.adpter.thongBaoAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThongBao_Frag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThongBao_Frag extends Fragment {

    DatabaseHelper db;
    ArrayList<ThongBao> arrayList;
    ListView lv;
    thongBaoAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ThongBao_Frag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThongBao_Frag.
     */
    // TODO: Rename and change types and number of parameters
    public static ThongBao_Frag newInstance(String param1, String param2) {
        ThongBao_Frag fragment = new ThongBao_Frag();
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
        View view = inflater.inflate(R.layout.fragment_thong_bao_, container, false);

        lv = view.findViewById(R.id.lv_thongBao);
        arrayList = new ArrayList<>();
        adapter = new thongBaoAdapter(getActivity(), R.layout.listview_thong_bao, arrayList);
        lv.setAdapter(adapter);
        db = new DatabaseHelper(getActivity(), "new.sqlite", null, 1);

        getThongBao();
        return view;
    }

    private void getThongBao() {
        Cursor cursor = db.GetData("SELECT * FROM ThongBao ORDER BY Id DESC");

        while (cursor.moveToNext()) {

            String ten = cursor.getString(1);
            String ngay = cursor.getString(2);
            arrayList.add(new ThongBao(null, ten, ngay));
        }
        adapter.notifyDataSetChanged();
    }
}