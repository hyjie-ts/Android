package com.example.caipu;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.caipu.db.MyDBHelper;

public class MeFragment extends Fragment {

    private MyDBHelper dbHelper;
    private TextView name;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.our_layout, container, false);
        name = (TextView)view.findViewById(R.id.user_val);
        dbHelper = new MyDBHelper(getActivity(), "UserStore.db", null, 2);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("userData",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            name.setText(cursor.getString(0));
        }
        cursor.close();
        return view;
    }
}
