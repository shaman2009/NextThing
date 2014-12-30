package app.next.udacity.com.nextthing;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import app.next.udacity.com.nextthing.model.NextThingPO;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Shaman on 12/27/14.
 */
public class NextThingAdapter extends BaseAdapter {
    private ArrayList<NextThingPO> arrayList;
    WebViewCallBack webViewCallBack;

    public NextThingAdapter() {
        arrayList = new ArrayList<>();
    }

    public void updateData(ArrayList<NextThingPO> datas) {
        arrayList = datas;
        notifyDataSetChanged();
    }

    public void addOneData(NextThingPO data) {
        arrayList.add(data);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ViewHolder holder = new ViewHolder(convertView);
        convertView.setTag(holder);


        final NextThingPO po = (NextThingPO) getItem(position);
        holder.mTitle.setText(po.getTitle());
        holder.mDescription.setText(po.getDescription());
        holder.mLikeCount.setText(String.valueOf(po.getVote()));

        holder.mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webViewCallBack != null) {
                    webViewCallBack.setWebViewVisible(po.getUrl());
                }
            }
        });

        holder.mDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailActivity.class);
                intent.putExtra("url", po.getUrl());
                intent.putExtra("title", po.getTitle());
                intent.putExtra("description", po.getDescription());
                intent.putExtra("vote", po.getVote());
                v.getContext().startActivity(intent);
            }
        });
        return convertView;
    }

    public void addCallBack(WebViewCallBack webViewCallBack) {
        this.webViewCallBack = webViewCallBack;
    }


    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'item.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Inmite Developers (http://inmite.github.io)
     */
    static class ViewHolder {
        @InjectView(R.id.like_count)
        TextView mLikeCount;
        @InjectView(R.id.like_button)
        TextView mLikeButton;
        @InjectView(R.id.title)
        TextView mTitle;
        @InjectView(R.id.description)
        TextView mDescription;
        @InjectView(R.id.url)
        TextView mUrl;
        @InjectView(R.id.image)
        ImageView mImage;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
