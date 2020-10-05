package com.example.asmpk01120;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.support.v4.os.IResultReceiver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DonHang_Frag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DonHang_Frag extends Fragment {



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    ProgressDialog progressDialog;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DonHang_Frag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DonHang_Frag.
     */
    // TODO: Rename and change types and number of parameters
    public static DonHang_Frag newInstance(String param1, String param2) {
        DonHang_Frag fragment = new DonHang_Frag();
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
        View view = inflater.inflate(R.layout.fragment_don_hang_2, container, false);



        final TextView btngui = view.findViewById(R.id.btngui);
        final TextView btnnhan = view.findViewById(R.id.btnnhan);

        btngui.setOnClickListener(new clickbtn());
        btnnhan.setOnClickListener(new clickbtn());


        return view;
    }

    public class clickbtn implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Fragment fragment = null;
            switch (view.getId()){
                case R.id.btngui:
                    fragment = new listguihang_fragment();
                    Toast.makeText(getContext(), "Đã chuyển qua frag gửi hàng!", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btnnhan:
                    fragment = new listnhanhang_fragment();
                    Toast.makeText(getContext(), "Đã chuyển qua frag nhận hàng!", Toast.LENGTH_SHORT).show();
                    break;
                default:
            }
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_donhang,
                    fragment).commit();
        }
    }

}