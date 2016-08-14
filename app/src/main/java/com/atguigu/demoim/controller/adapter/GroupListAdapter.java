package com.atguigu.demoim.controller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hyphenate.chat.EMGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/14 0014.
 */
public class GroupListAdapter extends BaseAdapter {
    private Context mContext;
    private List<EMGroup> mEMGroups = new ArrayList<>();

    public GroupListAdapter(Context context) {
        mContext = context;
    }

    public void refresh(List<EMGroup> emGroups) {
        if(emGroups != null && emGroups.size() > 0) {
            mEMGroups.clear();
            mEMGroups.addAll(emGroups);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return 0;
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
        return null;
    }
}
