package com.shinaier.laundry.snlfactory.manage.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.common.network.FProtocol;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;
import com.common.widget.FootLoadingListView;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.activity.ToolBarActivity;
import com.shinaier.laundry.snlfactory.base.activity.WebViewActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.manage.adapter.MessageAdapter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.MessageEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;
import com.shinaier.laundry.snlfactory.view.SwipeMenuLayout;

import java.util.IdentityHashMap;


/**
 * Created by 张家洛 on 2017/2/16.
 */

public class MessageNoticeActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_MESSAGE_LIST = 0x1;
    private static final int REQUEST_CODE_CLICKED = 0x2;
    private static final int REQUEST_CODE_MESSAGE_DETAIL = 0x4;

    @ViewInject(R.id.message_notice_list)
    private FootLoadingListView messageNoticeList;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;

    private MessageEntity messageEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_notice_act);
        ViewInjectUtils.inject(this);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
        requestHttpData(Constants.Urls.URL_POST_MESSAGE_LIST,REQUEST_CODE_MESSAGE_LIST, FProtocol.HttpMethod.POST,params);
    }

    private void initView() {
        setCenterTitle("消息通知");
        initLoadingView(this);
        setLoadingStatus(LoadingStatus.LOADING);
        leftButton.setOnClickListener(this);
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_MESSAGE_LIST:
                if (data != null){
                    setLoadingStatus(LoadingStatus.GONE);
                    messageEntity = Parsers.getMessageEntity(data);
                    if (messageEntity != null){
                        if (messageEntity.getCode() == 0){
                            if (messageEntity.getResults()!= null && messageEntity.getResults().size() > 0){
                                setLoadingStatus(LoadingStatus.GONE);
                                final MessageAdapter messageAdapter = new MessageAdapter(this, messageEntity.getResults());
                                messageNoticeList.setAdapter(messageAdapter);
                                messageAdapter.setDeletePositionListener(new MessageAdapter.DeletePositionListener() {
                                    @Override
                                    public void onDelete(SwipeMenuLayout swipeMenuLayout, int position) {
                                        swipeMenuLayout.quickClose();
                                        setLoadingStatus(LoadingStatus.LOADING);
                                        deleteMessage(position);
                                        messageEntity.getResults().remove(position);
                                        messageAdapter.notifyDataSetChanged();
                                    }
                                });

                                messageAdapter.setOnNoticeListener(new MessageAdapter.OnNoticeListener() {
                                    @Override
                                    public void onclick(int position) {
                                        String type = messageEntity.getResults().get(position).getType(); //消息类型的判断标志
                                        if (type.equals("1")){
                                            Intent intent = new Intent(MessageNoticeActivity.this, WebViewActivity.class);
                                            intent.putExtra(WebViewActivity.EXTRA_URL, messageEntity.getResults().get(position).getUrl());
                                            intent.putExtra(WebViewActivity.EXTRA_TITLE, "用户协议");
                                            startActivity(intent);
                                            IdentityHashMap<String,String> params = new IdentityHashMap<String, String>();
                                            params.put("token",UserCenter.getToken(MessageNoticeActivity.this));
                                            params.put("id",messageEntity.getResults().get(position).getId());
                                            requestHttpData(Constants.Urls.URL_POST_MESSAGE_DETAIL,REQUEST_CODE_MESSAGE_DETAIL,
                                                    FProtocol.HttpMethod.POST,params);
                                        }else {
                                            Intent intent = new Intent(MessageNoticeActivity.this,MessageDetailActivity.class);
                                            intent.putExtra("id",messageEntity.getResults().get(position).getId());
                                            startActivity(intent);
                                        }
                                    }
                                });
                            }else {
                                setLoadingStatus(LoadingStatus.EMPTY);
                            }
                        }else {
                            ToastUtil.shortShow(this, messageEntity.getMsg());
                        }
                    }else {
                        setLoadingStatus(LoadingStatus.EMPTY);
                    }
                }else {
                    setLoadingStatus(LoadingStatus.EMPTY);
                }
                break;
            case REQUEST_CODE_CLICKED:
                break;
        }
    }

    /**
     * 删除
     * @param position 获取msgid需要的position
     */
    private void deleteMessage(int position) {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token",UserCenter.getToken(MessageNoticeActivity.this));
        params.put("id",messageEntity.getResults().get(position).getId());
        requestHttpData(Constants.Urls.URL_POST_MESSAGE_TYPE,REQUEST_CODE_CLICKED, FProtocol.HttpMethod.POST,params);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.left_button:
                finish();
                break;
            case R.id.loading_layout:
                setLoadingStatus(LoadingStatus.LOADING);
                loadData();
                break;
        }
    }

    @Override
    public void mistake(int requestCode, FProtocol.NetDataProtocol.ResponseStatus status, String errorMessage) {
        super.mistake(requestCode, status, errorMessage);
        switch (requestCode){
            case REQUEST_CODE_MESSAGE_LIST:
                setLoadingStatus(LoadingStatus.RETRY);
                break;
        }
    }
}
