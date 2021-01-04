package com.example.caipu.details;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.caipu.R;
import com.example.caipu.info.ShowCookersInfo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class CookStepListAdapter extends BaseAdapter {

    private List<ShowCookersInfo.Result.Data.Steps> cookSteps;

    public CookStepListAdapter(List<ShowCookersInfo.Result.Data.Steps> cookSteps, Context context) {
        this.cookSteps = cookSteps;

    }

    @Override
    public int getCount() {
        return cookSteps.size();
    }

    @Override
    public ShowCookersInfo.Result.Data.Steps getItem(int position) {
        return cookSteps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cook_step, null);
            holder = new ViewHolder();
            holder.stepNum = (TextView) convertView.findViewById(R.id.step_num);
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.text = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        }
            holder = (ViewHolder) convertView.getTag();

        ShowCookersInfo.Result.Data.Steps cookStep = getItem(position);
        holder.stepNum.setText(position+1+"");
        holder.text.setText(cookStep.getStep());
//        holder.text.setText(cookStep.getStep().substring(2));
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        try {
            holder.img.setImageBitmap(getBitmap(cookStep.getImg()));
        }catch (IOException e) {
            e.printStackTrace();
        }

        return convertView;
    }
    public static Bitmap getBitmap(String path) throws IOException {
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(5000);

            conn.setReadTimeout(30000);
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
    private class ViewHolder{
        TextView stepNum;
        ImageView img;
        TextView text;
    }
}
