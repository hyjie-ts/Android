package com.example.caipu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.example.caipu.db.CooksDBManager;
import com.example.caipu.details.CookDetailsActivity;
import com.example.caipu.info.ShowCookersInfo;
import com.example.caipu.util.HttpUtil;
import com.example.caipu.util.NetworkUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class cookbook_list_Activity extends AppCompatActivity {

    private List<mes> mesList = new ArrayList<>();
    ListView mListview = null;
    private Bundle b;
    public Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cookbook_list_);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_24dp1);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cookbook_list_Activity.this.finish();
                overridePendingTransition(R.anim.new_to_right, R.anim.old_to_right);
            }
        });
        mListview = (ListView)findViewById(R.id.list_image);
        TextImageAdapter adapter = new TextImageAdapter(cookbook_list_Activity.this,mesList);
        mListview.setAdapter(adapter);
        b = getIntent().getExtras();
        //加个ifelse语句判断第几个按钮
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        if(b.getInt("id")==0){
            String url = NetworkUtil.getURL(289,0,10);
            String result = null;
            getSupportActionBar().setTitle("早餐");
            try {
                result = HttpUtil.sendGet(url, "utf-8");
                Gson gson = new Gson();
                ShowCookersInfo loadInfo = gson.fromJson(result, ShowCookersInfo.class);
                for (int i = 0, lengh = loadInfo.getResult().getData().size(); i < lengh; i++) {
                    ShowCookersInfo.Result.Data dt = loadInfo.getResult().getData().get(i);
                    mesList.add(new mes(dt.getTitle(),getBitmap(dt.getAlbums())));

                }
                mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        CooksDBManager.getCooksDBManager(cookbook_list_Activity.this).setData(loadInfo.getResult().getData().get(position));
                        CooksDBManager.getCooksDBManager(cookbook_list_Activity.this).insertData(loadInfo.getResult().getData().get(position));
                        intent = new Intent(cookbook_list_Activity.this, CookDetailsActivity.class);
                        startActivityForResult(intent, 1);

                        Toast.makeText(cookbook_list_Activity.this,mesList.get(position).getName(),Toast.LENGTH_SHORT).show();
                    }
                });
            }catch (Exception e) {
                e.printStackTrace();
            }

        }else if(b.getInt("id")==1){
            String url = NetworkUtil.getURL(20,0,10);
            String result = null;
            getSupportActionBar().setTitle("午餐");
            try {
                result = HttpUtil.sendGet(url, "utf-8");
                Gson gson = new Gson();
                ShowCookersInfo loadInfo = gson.fromJson(result, ShowCookersInfo.class);
                for (int i = 0, lengh = loadInfo.getResult().getData().size(); i < lengh; i++) {
                    ShowCookersInfo.Result.Data dt = loadInfo.getResult().getData().get(i);
                    mesList.add(new mes(dt.getTitle(),getBitmap(dt.getAlbums())));

                }
                mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        CooksDBManager.getCooksDBManager(cookbook_list_Activity.this).setData(loadInfo.getResult().getData().get(position));
                        CooksDBManager.getCooksDBManager(cookbook_list_Activity.this).insertData(loadInfo.getResult().getData().get( position));
                        intent = new Intent(cookbook_list_Activity.this, CookDetailsActivity.class);
                        startActivity(intent);

                        Toast.makeText(cookbook_list_Activity.this,mesList.get(position).getName(),Toast.LENGTH_SHORT).show();
                    }
                });
            }catch (Exception e) {
                e.printStackTrace();
            }

        }else{
            String url = NetworkUtil.getURL(41,0,10);
            String result = null;
            getSupportActionBar().setTitle("晚餐");
            try {
                result = HttpUtil.sendGet(url, "utf-8");
                Gson gson = new Gson();
                ShowCookersInfo loadInfo = gson.fromJson(result, ShowCookersInfo.class);
                for (int i = 0, lengh = loadInfo.getResult().getData().size(); i < lengh; i++) {
                    ShowCookersInfo.Result.Data dt = loadInfo.getResult().getData().get(i);
                    mesList.add(new mes(dt.getTitle(),getBitmap(dt.getAlbums())));

                }
                mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        CooksDBManager.getCooksDBManager(cookbook_list_Activity.this).setData(loadInfo.getResult().getData().get(position));
                        CooksDBManager.getCooksDBManager(cookbook_list_Activity.this).insertData(loadInfo.getResult().getData().get(position));
                        intent = new Intent(cookbook_list_Activity.this, CookDetailsActivity.class);
                        startActivity(intent);

                        Toast.makeText(cookbook_list_Activity.this,mesList.get(position).getName(),Toast.LENGTH_SHORT).show();
                    }
                });
            }catch (Exception e) {
                e.printStackTrace();
            }
        }


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
                return bitmap;
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
