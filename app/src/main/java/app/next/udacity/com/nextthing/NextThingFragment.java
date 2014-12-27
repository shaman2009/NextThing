package app.next.udacity.com.nextthing;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Shaman on 12/27/14.
 */
public class NextThingFragment extends Fragment {

    NextThingAdapter mNextThingAdapter ;

    @InjectView(R.id.listView)
    ListView mListView;

    public NextThingFragment() {
        mNextThingAdapter = new NextThingAdapter() ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, rootView);
        for (int i = 0; i < 20; i++) {
            String data = "title" ;
            mNextThingAdapter.addOneData(data);
        }
        mListView.setAdapter(mNextThingAdapter);
        return rootView;
    }
}

