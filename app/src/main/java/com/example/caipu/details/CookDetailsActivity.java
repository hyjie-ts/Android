package com.example.caipu.details;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.example.caipu.BaseActivity;
import com.example.caipu.R;
import com.example.caipu.db.CooksDBManager;
import com.example.caipu.info.ShowCookersInfo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class CookDetailsActivity extends BaseActivity {

    private ImageView header_img;

    private TextView name;

    private TextView text_intro;

    private LinearLayout materials_layout;

    private ListView listView;

    private Toolbar details_title;
    private CookStepListAdapter adapter;

    private ShowCookersInfo.Result.Data data;

    private CheckedTextView collect;


    public void collectClick(View view) {
        collect.toggle();
        CooksDBManager.getCooksDBManager(this).updateData(data, collect.isChecked());
    }

    private View view;
    private TextView dialog_number;
    private ImageView dialog_img;
    private TextView dialog_text_info;




    @Override
    public void initBefore() {
        details_title = (Toolbar)findViewById(R.id.details_title);

        header_img = (ImageView)findViewById(R.id.header_img);
        name = (TextView)findViewById(R.id.name);
        text_intro = (TextView)findViewById(R.id.text_intro);
        materials_layout = (LinearLayout)findViewById(R.id.materials_layout);
        listView = (ListView)findViewById(R.id.listView);
        collect = (CheckedTextView)findViewById(R.id.collect_tv_1);
        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collect.toggle();
                CooksDBManager.getCooksDBManager(CookDetailsActivity.this).updateData(data, collect.isChecked());
            }
        });
        data = CooksDBManager.getCooksDBManager(this).getData();
        details_title.setTitle(data.getTitle());
//        details_title.setTitle("2131");
        details_title.setNavigationIcon(R.drawable.ic_chevron_left_24dp1);

//        setSupportActionBar(details_title);
        details_title.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CookDetailsActivity.this.finish();
                overridePendingTransition(R.anim.new_to_right, R.anim.old_to_right);
            }
        });


    }

    @Override
    public void init() {
        view = LayoutInflater.from(this).inflate(R.layout.details_dialog_layout, null);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        new Thread() {
            public void run() {
                try {
                    header_img.setImageBitmap(getBitmap(data.getAlbums()));
                }catch (IOException e) {
                    e.printStackTrace();
                };
            }
        }.start();

        name.setText(data.getTitle());
        text_intro.setText(data.getImtro());
        collect.setChecked(CooksDBManager.getCooksDBManager(this).isLikeNowCook(data.getId()));
        addtime();
        adapter = new CookStepListAdapter(data.getSteps(), this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShowCookersInfo.Result.Data.Steps step = adapter.getItem(position);
                getDialog("第" + (position + 1) + "步", step.getImg(), step.getStep());
            }
        });
    }

    @Override
    public void initAfter() {

    }

    //添加食材
    private void addtime() {
        //食材明细
        String ingredients = data.getIngredients();//准备食材时间
        String burden = data.getBurden();//烹饪时间
        String materialsStr = ingredients + ";" + burden;

        View view = LayoutInflater.from(this).inflate(R.layout.item_material, null);
        materials_layout.addView(view);

        TextView tv1 = (TextView) view.findViewById(R.id.text1);
        tv1.setText("准备食材时间");
        TextView tv2 = (TextView) view.findViewById(R.id.text2);
        tv2.setText(ingredients);
        TextView tv3 = (TextView) view.findViewById(R.id.text3);
        tv3.setText("烹饪时间");
        TextView tv4 = (TextView) view.findViewById(R.id.text4);
        tv4.setText(burden);


    }

    private AlertDialog dialog;

    private void getDialog(String itemId, String img, String infoText) {
        if (dialog == null) {
            dialog = new AlertDialog.Builder(this).create();
            dialog.show();
            dialog.setContentView(view);
            dialog_number = (TextView) view.findViewById(R.id.dialog_number);
            dialog_img = (ImageView) view.findViewById(R.id.dialog_img);
            dialog_text_info = (TextView) view.findViewById(R.id.dialog_text_info);
        }

        if (dialog != null) {
            dialog_number.setText(itemId);
            try {
                dialog_img.setImageBitmap(getBitmap(img));
            }catch (IOException e) {
                e.printStackTrace();
            };
            dialog_text_info.setText(infoText);
            dialog.show();
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
                inputStream.close();
                return bitmap;
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
