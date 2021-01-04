package com.example.caipu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class CookbookFragment extends Fragment {

    public Button bf_bt,lc_bt,dn_bt;
    public Bundle b;
    public Intent intent;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cookbook, container, false);
        bf_bt = (Button)view.findViewById(R.id.breakfast_bt);
        lc_bt = (Button)view.findViewById(R.id.lunch_bt);
        dn_bt = (Button)view.findViewById(R.id.dinner_bt);
        onClick click = new onClick();
        bf_bt.setOnClickListener(click);
        lc_bt.setOnClickListener(click);
        dn_bt.setOnClickListener(click);

        return view;
    }
    private class onClick implements View.OnClickListener{

        @Override
        public void onClick(View v){


            switch (v.getId()){
                case R.id.breakfast_bt:
                    b = new Bundle();
                    b.putInt("id",0);
                    intent = new Intent(getActivity(),
                            cookbook_list_Activity.class);
                    intent.putExtras(b);
                    startActivityForResult(intent, 1);
                    Toast.makeText(getActivity(),"早餐",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.lunch_bt:
                    b = new Bundle();
                    b.putInt("id",1);
                    intent = new Intent(getActivity(),
                            cookbook_list_Activity.class);
                    intent.putExtras(b);
                    startActivityForResult(intent, 1);
                    Toast.makeText(getActivity(),"午餐",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.dinner_bt:
                    b = new Bundle();
                    b.putInt("id",2);
                    intent = new Intent(getActivity(),
                            cookbook_list_Activity.class);
                    intent.putExtras(b);
                    startActivityForResult(intent, 1);
                    Toast.makeText(getActivity(),"晚餐",Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    }
}
