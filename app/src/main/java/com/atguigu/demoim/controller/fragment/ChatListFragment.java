package com.atguigu.demoim.controller.fragment;

import android.content.Intent;

import com.atguigu.demoim.controller.activity.ChatActivity;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.ui.EaseConversationListFragment;

import java.util.List;

/**
 * Created by Administrator on 2016/8/8 0008.
 */
public class ChatListFragment extends EaseConversationListFragment {


    @Override
    protected void initView() {
        super.initView();

        EMClient.getInstance().chatManager().addMessageListener(emMessageListener);

        //跳转到会话页面
        setConversationListItemClickListener(new EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                //传递参数
                intent.putExtra(EaseConstant.EXTRA_USER_ID,conversation.conversationId());
                startActivity(intent);
            }
        });
    }

    private EMMessageListener emMessageListener = new EMMessageListener() {
        @Override
        public void onMessageReceived(List<EMMessage> list) {
            //刷新显示会话列表
            EaseUI.getInstance().getNotifier().onNewMesg(list);
            refresh();
        }
        @Override
        public void onCmdMessageReceived(List<EMMessage> list) {

        }

        @Override
        public void onMessageReadAckReceived(List<EMMessage> list) {

        }

        @Override
        public void onMessageDeliveryAckReceived(List<EMMessage> list) {

        }

        @Override
        public void onMessageChanged(EMMessage emMessage, Object o) {

        }
    };
}
