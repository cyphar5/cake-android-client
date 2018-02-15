package com.waracle.androidtest;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.waracle.androidtest.model.Cake;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by achau on 14-02-2018.
 */

public class CakeAdapter extends RecyclerView.Adapter<CakeAdapter.CakeViewHolder> {

    private List<Cake> mItems = new LinkedList<>();
    public Activity activity;

    public CakeAdapter(Activity activity) {
        this.activity = activity;
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(CakeViewHolder holder, int position) {
        Cake cake = mItems.get(position);
        holder.setCakeData(cake.getTitle(), cake.getDesc(), cake.getImg_url());
    }

    @Override
    public int getItemCount() {
        Log.d("Length", mItems.size() + "");
        Log.v("Length", mItems.size() + "");
        Log.e("Length", mItems.size() + "");
        Log.d("Length", mItems.size() + "");
        return mItems.size();
    }

    @Override
    public CakeViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout, parent, false);
        return new CakeViewHolder(v);

    }

    public class CakeViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView desc;
        private ImageView image;

        public CakeViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            desc = (TextView) itemView.findViewById(R.id.desc);
            image = (ImageView) itemView.findViewById(R.id.image);
            image.setImageDrawable(activity.getDrawable(R.drawable.ic_ic_placeholder));
        }

        public void setCakeData(String name, String t_desc, String img_url) {
            title.setText(name);
            desc.setText(t_desc);
         //   mImageLoader.load(img_url, image);
            new DownloadImageTask(image)
                    .execute(img_url);

        }

    }

    public void setItems(List<Cake> items) {
        if(items!=null) {
            mItems.clear();
            mItems.addAll(items);
            this.notifyDataSetChanged();
        }

    }
}

