package com.shinaier.laundry.snlfactory.manage.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.common.network.FProtocol;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;
import com.common.widget.FootLoadingListView;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.manage.adapter.UserEvaluateAdapter;
import com.shinaier.laundry.snlfactory.manage.view.EvaluateReplyDialog;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.Entity;
import com.shinaier.laundry.snlfactory.network.entity.EvaluateEntities;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;

import java.util.IdentityHashMap;


/**
 * Created by 张家洛 on 2017/2/15.
 */

public class UserEvaluateActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_EVALUATE_SHOW = 0x1;
    private static final int REQUEST_CODE_EVALUATE_REPLY = 0x2;

    @ViewInject(R.id.publish_comment_list)
    private FootLoadingListView publishCommentList;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    String replyContent = (String)msg.obj;
                    IdentityHashMap<String,String> params = new IdentityHashMap<>();
                    params.put("token", UserCenter.getToken(UserEvaluateActivity.this));
                    params.put("id",evaluateData.getId());
                    params.put("mer_content",replyContent);
                    requestHttpData(Constants.Urls.URL_POST_EVALUATE_REPLY,REQUEST_CODE_EVALUATE_REPLY, FProtocol.HttpMethod.POST,params);
                    break;
            }
        }
    };
    private EvaluateEntities evaluateEntities;
    private EvaluateEntities.Data evaluateData;
    private EvaluateReplyDialog evaluateReplyDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_evaluate_act);
        ViewInjectUtils.inject(this);
        loadData();
        initView();
    }

    private void loadData() {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
        requestHttpData(Constants.Urls.URL_POST_EVALUATE_LIST_SHOW,REQUEST_CODE_EVALUATE_SHOW, FProtocol.HttpMethod.POST,params);
    }

    private void initView() {
        setCenterTitle("查看评价");
        initLoadingView(this);
        setLoadingStatus(LoadingStatus.LOADING);
        leftButton.setOnClickListener(this);
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_EVALUATE_SHOW:
                if(data != null){
                    evaluateEntities = Parsers.getEvaluateEntities(data);
                    if(evaluateEntities != null){
                        setLoadingStatus(LoadingStatus.GONE);
                        if(evaluateEntities.getRetcode() == 0){
                            UserEvaluateAdapter userEvaluateAdapter = new UserEvaluateAdapter(this, evaluateEntities.getDatas());
                            publishCommentList.setAdapter(userEvaluateAdapter);
                            publishCommentList.setCanMoreAndUnReFresh(false);
                            userEvaluateAdapter.setPositionListener(new UserEvaluateAdapter.PositionListener() {
                                @Override
                                public void onClick(int position) {
                                    evaluateData = evaluateEntities.getDatas().get(position);
                                    evaluateReplyDialog = new EvaluateReplyDialog(UserEvaluateActivity.this, R.style.timerDialog,handler);
                                    evaluateReplyDialog.setView();
                                    evaluateReplyDialog.show();
                                }
                            });
                        }
                    }else {
                        setLoadingStatus(LoadingStatus.EMPTY);
                    }
                }else {
                    setLoadingStatus(LoadingStatus.EMPTY);
                }
                break;
            case REQUEST_CODE_EVALUATE_REPLY:
                if(data != null){
                    Entity entity = Parsers.getEntity(data);
                    if(entity.getRetcode() == 0){
                        evaluateReplyDialog.dismiss();
                        setLoadingStatus(LoadingStatus.LOADING);
                        loadData();
                    }else {
                        ToastUtil.shortShow(this,entity.getStatus());
                    }
                }
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
            case REQUEST_CODE_EVALUATE_SHOW:
                setLoadingStatus(LoadingStatus.RETRY);
                break;
        }
    }
}
