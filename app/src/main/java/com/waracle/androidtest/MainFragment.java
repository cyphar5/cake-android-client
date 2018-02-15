package com.waracle.androidtest;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.waracle.androidtest.asynctask.AsyncTaskRunner;
import com.waracle.androidtest.constant.Constants;
import com.waracle.androidtest.interfaces.AsyncTaskListener;
import com.waracle.androidtest.interfaces.RefreshFragmentListener;
import com.waracle.androidtest.model.Cake;
import com.waracle.androidtest.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements RefreshFragmentListener, AsyncTaskListener {

    public static final String TAG = MainFragment.class.getSimpleName();

    private RecyclerView mListView;
    private CakeAdapter mAdapter;
    private List<Cake> list = new LinkedList<>();
    private boolean isProgressTrue = true;

    public MainFragment() { /**/ }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        setRetainInstance(true);
        mListView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        ((MainActivity) getActivity()).setRefreshFragmentListener(MainFragment.this);
        mListView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new CakeAdapter(getActivity());
        Log.d("MF_FRAG", "madapter " + mAdapter);
        mListView.setAdapter(mAdapter);

        if (savedInstanceState == null) {
            if (Util.isNetworkAvailable(getContext()))
                loadData();
            else
                Toast.makeText(getActivity(), "Network Not Available", Toast.LENGTH_SHORT).show();
        } else {
            list = savedInstanceState.getParcelableArrayList("cake");
            mAdapter.setItems(list);
            mAdapter.notifyDataSetChanged();
        }


        return rootView;
    }

    public void loadData() {
        if (isProgressTrue) {
            AsyncTaskRunner runner = new AsyncTaskRunner(getContext(), MainFragment.this, this);
            runner.execute();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Create and set the list adapter.


        // Load data from net.
    }

    public void setItems(JSONArray items) throws JSONException {
        list = Util.convertToArrayList(items);
        mAdapter.setItems(list);
        Log.d("MF_FRAG", "set items madapter " + mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        loadData();
    }

    @Override
    public void onProgress() {
        isProgressTrue = true;
    }

    @Override
    public void onFinish() {
        isProgressTrue = false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("cake", (ArrayList<? extends Parcelable>) list);

    }

    /**
     * Returns the charset specified in the Content-Type of this header,
     * or the HTTP default (ISO-8859-1) if none can be found.
     */


}
