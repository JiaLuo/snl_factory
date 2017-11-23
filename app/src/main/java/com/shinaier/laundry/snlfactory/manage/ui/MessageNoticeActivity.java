package com.shinaier.laundry.snlfactory.manage.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.common.network.FProtocol;
import com.common.viewinject.annotation.ViewInject;
import com.common.widget.FootLoadingListView;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.manage.adapter.MessageNoticeAdapter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.MessageNoticeEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;

import java.util.IdentityHashMap;
import java.util.List;


/**
 * Created by 张家洛 on 2017/2/16.
 */

public class MessageNoticeActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_MSG_NOTICE = 0x1;
    private static final int REQUEST_CODE_CLICKED = 0x2;

    @ViewInject(R.id.message_notice_list)
    private FootLoadingListView messageNoticeList;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_notice_act);
        ViewInjectUtils.inject(this);
        loadData();
        initView();
    }

    private void loadData() {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
        requestHttpData(Constants.Urls.URL_POST_MSG_NOTICE_LIST,REQUEST_CODE_MSG_NOTICE, FProtocol.HttpMethod.POST,params);
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
            case REQUEST_CODE_MSG_NOTICE:
                if(data != null){
                    final List<MessageNoticeEntity> messageNoticeEntity = Parsers.getMessageNoticeEntity(data);
                    if(messageNoticeEntity != null && messageNoticeEntity.size() > 0){
                        setLoadingStatus(LoadingStatus.GONE);
                        final MessageNoticeAdapter messageNoticeAdapter = new MessageNoticeAdapter(this,messageNoticeEntity);
                        messageNoticeList.setAdapter(messageNoticeAdapter);
                        messageNoticeAdapter.setOnNoticeListener(new MessageNoticeAdapter.OnNoticeListener() {
                            @Override
                            public void onclick(int position) {
                                IdentityHashMap<String,String> params = new IdentityHashMap<>();
                                params.put("token",UserCenter.getToken(MessageNoticeActivity.this));
                                params.put("message",messageNoticeEntity.get(position).getId());
                                requestHttpData(Constants.Urls.URL_POST_MSG_NOTICE_LIST,REQUEST_CODE_CLICKED, FProtocol.HttpMethod.POST,params);
                            }
                        });
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
            case REQUEST_CODE_MSG_NOTICE:
                setLoadingStatus(LoadingStatus.RETRY);
                break;
        }
    }
}
