package app.next.udacity.com.nextthing;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Shaman on 12/27/14.
 */
public class NextThingAdapter extends BaseAdapter {
    private ArrayList<String> arrayList = new ArrayList<>();

    public void updateData(ArrayList<String> datas){
        arrayList.addAll(datas);
        notifyDataSetChanged();
    }

    public void addOneData(String data){
        arrayList.add(data);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return convertView;
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
        @InjectView(R.id.title)
        TextView mTitle;
        @InjectView(R.id.description)
        TextView mDescription;
        @InjectView(R.id.image)
        ImageView mImage;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
