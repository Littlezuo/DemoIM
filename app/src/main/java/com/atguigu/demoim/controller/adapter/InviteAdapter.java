package com.atguigu.demoim.controller.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.atguigu.demoim.R;
import com.atguigu.demoim.model.bean.EMGroup;
import com.atguigu.demoim.model.bean.InvitationInfo;

import java.util.List;

/**
 * Created by Administrator on 2016/8/11 0011.
 */
public class InviteAdapter extends BaseAdapter {
    private Context mContext;
    private List<InvitationInfo> mInvitationInfoList;
    public InviteAdapter(Context context, List<InvitationInfo> invitationInfoList,OnInviteListener onInviteListener) {
        if(invitationInfoList==null|| invitationInfoList.size()==0) {
            return;
        }
        mContext = context;
        mInvitationInfoList = invitationInfoList;
        mOnInviteListener = onInviteListener;
    }

    @Override
    public int getCount() {
        return mInvitationInfoList==null? 0:mInvitationInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return mInvitationInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.e("kkkk", "getView1");
        ViewHolder holder;
        if(convertView==null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_invite,null);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_invite_name);
            holder.tvReason = (TextView) convertView.findViewById(R.id.tv_invite_reason);
            holder.btAccept = (Button) convertView.findViewById(R.id.bt_invite_accept);
            holder.btReject = (Button) convertView.findViewById(R.id.bt_invite_reject);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        //获取当前的item数据
        final InvitationInfo invitationInfo = mInvitationInfoList.get(position);
        //设置显示
        EMGroup group = invitationInfo.getGroup();
        if(group==null) {//个人
            Log.e("kkkk", "view2");
            ///设置名称
            holder.tvName.setText(invitationInfo.getUser().getName());
            //设置原因
            if(invitationInfo.getStatus() == InvitationInfo.InvitationStatus.NEW_INVITE) {
                Log.e("kkkk", "view3");
                //设置接受按钮和拒绝按钮可见
                holder.btAccept.setVisibility(View.VISIBLE);
                holder.btReject.setVisibility(View.VISIBLE);
                holder.tvReason.setText("和我做个朋友吧");
            }else if(invitationInfo.getStatus() == InvitationInfo.InvitationStatus.INVITE_ACCEPT) {
                Log.e("kkkk", "view4");
                holder.btAccept.setVisibility(View.GONE);
                holder.btReject.setVisibility(View.GONE);
                holder.tvReason.setText("您的邀请被"+invitationInfo.getUser().getName()+"接受");
            }else if(invitationInfo.getStatus() == InvitationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER) {
                // 设置接受和拒绝按钮不可见
                Log.e("kkkk", "view5");
                holder.btAccept.setVisibility(View.GONE);
                holder.btReject.setVisibility(View.GONE);
                holder.tvReason.setText("您接受了"+invitationInfo.getUser().getName()+"的邀请");
            }
        }else{//群

        }

        //设置button的点击事件
        holder.btAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnInviteListener.onReccept(invitationInfo);
            }
        });

        holder.btReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnInviteListener.onReject(invitationInfo);
            }
        });

        return convertView;
    }

    private class ViewHolder {
        TextView tvName;
        TextView tvReason;
        Button btReject;
        Button btAccept;
    }
    private OnInviteListener mOnInviteListener;

    /*public InviteAdapter(OnInviteListener onInviteListener) {
        mOnInviteListener = onInviteListener;
    }*/

    public interface OnInviteListener{
        void onReccept(InvitationInfo invitationInfo);
        void onReject(InvitationInfo invitationInfo);
    }
}
