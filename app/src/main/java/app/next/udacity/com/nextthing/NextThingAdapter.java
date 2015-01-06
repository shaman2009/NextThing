package app.next.udacity.com.nextthing;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.DeleteCallback;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;

import java.util.ArrayList;
import java.util.List;

import app.next.udacity.com.nextthing.LeanCloud.NextThingObject;
import app.next.udacity.com.nextthing.LeanCloud.ThingLikeObject;
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
        final ViewHolder holder = new ViewHolder(convertView);
        convertView.setTag(holder);


        final NextThingPO po = (NextThingPO) getItem(position);
        holder.mTitle.setText(po.getTitle());
        holder.mDescription.setText(po.getDescription());
        holder.mLikeCount.setText(String.valueOf(po.getVote()));
        AVUser user = AVUser.getCurrentUser();
        if (user != null) {
            final String userId = user.getObjectId();
            final String thingId = po.getId();
            if (po.isLiked()) {
                holder.mLikeButton.setText(R.string.unlike);

            }
            holder.mLikeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    AVQuery<AVObject> query = new AVQuery<>(NextThingObject.NEXT_THING);
                    query.getInBackground(thingId, new GetCallback<AVObject>() {
                        @Override
                        public void done(AVObject avObject, AVException e) {
                            if (e == null) {
                                if (!po.isLiked()) {
                                    final int vote = avObject.getInt(NextThingObject.VOTE) + 1;
                                    avObject.put(NextThingObject.VOTE, vote);
                                    avObject.saveInBackground();
                                    ThingLikeObject.save(userId, thingId, new SaveCallback() {
                                        @Override
                                        public void done(AVException e) {
                                            Toast.makeText(v.getContext(), R.string.like_success, Toast.LENGTH_SHORT).show();
                                            holder.mLikeButton.setText(R.string.unlike);
                                            holder.mLikeCount.setText(String.valueOf(vote));
                                            po.setLiked(true);
                                        }
                                    });
                                } else {
                                    final int vote = avObject.getInt(NextThingObject.VOTE) - 1;
                                    avObject.put(NextThingObject.VOTE, vote);
                                    avObject.saveInBackground();
                                    ThingLikeObject.query(userId, thingId, new FindCallback() {
                                        @Override
                                        public void done(List list, AVException e) {
                                            int size = list.size();
                                            if (size == 0 || size > 1) {
                                                Toast.makeText(v.getContext(), R.string.http_error, Toast.LENGTH_SHORT).show();
                                            } else {
                                                AVObject object = (AVObject) list.get(0);
                                                object.deleteInBackground(new DeleteCallback() {
                                                    @Override
                                                    public void done(AVException e) {
                                                        holder.mLikeButton.setText(R.string.like);
                                                        holder.mLikeCount.setText(String.valueOf(vote));
                                                        po.setLiked(false);
                                                    }
                                                });
                                            }
                                        }
                                    });
                                }
                            } else {
                                Toast.makeText(v.getContext(), R.string.http_error, Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            });
        } else {
            holder.mLikeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), LoginActivity.class);
                    v.getContext().startActivity(intent);
                }
            });
        }


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
        @InjectView(R.id.like_layout)
        LinearLayout mLikeLayout;
        @InjectView(R.id.title)
        TextView mTitle;
        @InjectView(R.id.description)
        TextView mDescription;
        @InjectView(R.id.url)
        TextView mUrl;
        @InjectView(R.id.image)
        ImageView mImage;
        @InjectView(R.id.card_view)
        CardView mCardView;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
