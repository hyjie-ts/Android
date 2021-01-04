package com.example.caipu;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.List;

public class TextImageAdapter extends BaseAdapter {
    private Context mContext;
    private List<mes> mesList = new ArrayList<>();


    public TextImageAdapter(Context context, List<mes> list){
        this.mContext = context;
        this.mesList = list;
    }

    @Override
    public int getCount() {
        return mesList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item,null);
            ItemViewCache viewCache = new ItemViewCache();
            viewCache.mTextView = (TextView) convertView.findViewById(R.id.itemTxt);
            viewCache.mImageView = (ImageView) convertView.findViewById(R.id.itemImg);
            convertView.setTag(viewCache);
        }
        ItemViewCache cache = (ItemViewCache)convertView.getTag();
        cache.mImageView.setImageBitmap(mesList.get(position).getImageId());
        cache.mTextView.setText(mesList.get(position).getName());
        return convertView;
    }

    private class ItemViewCache{
        public TextView mTextView;
        public ImageView mImageView;
    }
}
