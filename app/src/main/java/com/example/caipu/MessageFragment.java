package com.example.caipu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.caipu.db.CooksDBManager;
import com.example.caipu.details.CookDetailsActivity;
import com.example.caipu.info.ShowCookersInfo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MessageFragment extends Fragment {
    private List<mes> mesList = new ArrayList<>();
    ListView mListview = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.message_layout, container, false);
        mListview = (ListView)view.findViewById(R.id.list_image);
        TextImageAdapter adapter = new TextImageAdapter(getActivity(),mesList);
        mListview.setAdapter(adapter);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        try {
            ShowCookersInfo meinfo =  CooksDBManager.getCooksDBManager(getActivity()).getData(false, true);
            for (int i = 0, lengh = meinfo.getResult().getData().size(); i < lengh; i++) {
                ShowCookersInfo.Result.Data dt = meinfo.getResult().getData().get(i);
                mesList.add(new mes(dt.getTitle(),getBitmap(dt.getAlbums())));

            }
            mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    CooksDBManager.getCooksDBManager(getActivity()).setData(meinfo.getResult().getData().get(position));
                    CooksDBManager.getCooksDBManager(getActivity()).insertData(meinfo.getResult().getData().get( position));
                    Intent intent = new Intent(getActivity(), CookDetailsActivity.class);
                    startActivity(intent);

                    Toast.makeText(getActivity(),mesList.get(position).getName(),Toast.LENGTH_SHORT).show();
                }
            });

        }catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
    public static Bitmap getBitmap(String path) throws IOException {
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            if(conn.getResponseCode() == 200){
                InputStream inputStream = conn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
                return bitmap;
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
