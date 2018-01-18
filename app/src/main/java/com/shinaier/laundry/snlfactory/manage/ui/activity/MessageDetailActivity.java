package com.shinaier.laundry.snlfactory.manage.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.utils.LogUtil;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.activity.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.MessageDetailEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.util.TimeUtils;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;

import java.util.IdentityHashMap;


/**
 * 消息详情
 * Created by 张家洛 on 2017/11/8.
 */

public class MessageDetailActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_MESSAGE_DETAIL = 0x1;

    @ViewInject(R.id.msg_detail_title)
    private TextView msgDetailTitle; //消息提醒标题
    @ViewInject(R.id.msg_detail_time)
    private TextView msgDetailTime; //消息提醒时间
    @ViewInject(R.id.msg_detail_order_num)
    private TextView msgDetailOrderNum; //消息提醒订单号
    @ViewInject(R.id.msg_detail_clothes_num)
    private TextView msgDetailClothesNum; //消息提醒衣物编码
    @ViewInject(R.id.msg_detail_clothes_name)
    private TextView msgDetailClothesName; //消息提醒衣物名称
    @ViewInject(R.id.msg_detail_take_clothes_time)
    private TextView msgDetailTakeClothesTime; //消息提醒取衣时间
    @ViewInject(R.id.msg_detail_notice)
    private TextView msgDetailNotice; //消息提醒提示
    @ViewInject(R.id.left_button)
    private ImageView leftButton;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_detail_act);
        ViewInjectUtils.inject(this);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        loadData(id);
        initView();
    }

    private void loadData(String id) {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
        params.put("id",id);
        requestHttpData(Constants.Urls.URL_POST_MESSAGE_DETAIL_WARN,REQUEST_CODE_MESSAGE_DETAIL,
                FProtocol.HttpMethod.POST,params);
    }

    private void initView() {
        setCenterTitle("消息通知");
        initLoadingView(this);
        setLoadingStatus(LoadingStatus.LOADING);
        leftButton.setOnClickListener(this);

    }

    @Override
    protected void parseData(int requestCode, String data) {
        LogUtil.e("zhang","data = " +data);
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_MESSAGE_DETAIL:
                if (data != null){
                    MessageDetailEntity messageDetailEntity = Parsers.getMessageDetailEntity(data);
                    setLoadingStatus(LoadingStatus.GONE);
                    if (messageDetailEntity != null){
                        if (messageDetailEntity.getCode() == 0){
                            if (messageDetailEntity.getResult() != null){
                                setLoadingStatus(LoadingStatus.GONE);
                                MessageDetailEntity.MessageDetailResult result = messageDetailEntity.getResult();
                                msgDetailTitle.setText(result.getTitle());
                                msgDetailOrderNum.setText("订单号：" + result.getOrdersn());
                                msgDetailClothesNum.setText("衣物编码：" + result.getCleanNumber());
                                msgDetailClothesName.setText("衣物：" + result.getName());
                                msgDetailTakeClothesTime.setText("取衣时间：" + result.getTakeTime());
                                msgDetailNotice.setText("提示：" + result.getContent());
                                msgDetailTime.setText(TimeUtils.formatTime(result.getTime()));
                            }else {
                                setLoadingStatus(LoadingStatus.EMPTY);
                            }
                        }else {
                            ToastUtil.shortShow(this,messageDetailEntity.getMsg());
                        }
                    }else {
                        setLoadingStatus(LoadingStatus.EMPTY);
                    }
                }else {
                    setLoadingStatus(LoadingStatus.EMPTY);
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loading_layout:
                loadData(id);
                break;
            case R.id.left_button:
                finish();
                break;
        }
    }

    @Override
    public void mistake(int requestCode, FProtocol.NetDataProtocol.ResponseStatus status, String errorMessage) {
        super.mistake(requestCode, status, errorMessage);
        switch (requestCode){
            case REQUEST_CODE_MESSAGE_DETAIL:
                setLoadingStatus(LoadingStatus.RETRY);
                break;
        }
    }
}
