package com.shinaier.laundry.snlfactory.manage.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
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
import com.shinaier.laundry.snlfactory.network.entity.Entity;
import com.shinaier.laundry.snlfactory.network.entity.OfflineRefluxEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.offlinecash.adapter.OfflineRefluxAdapter;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;
import com.shinaier.laundry.snlfactory.view.WrapHeightListView;

import java.util.IdentityHashMap;
import java.util.List;


/**
 * 返流
 * Created by 张家洛 on 2018/1/13.
 */

public class OfflineRefluxActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_REFLUX_LIST = 0x1;
    private static final int REQUEST_CODE_REFLUX_OPERATE = 0x2;

    @ViewInject(R.id.reflux_list)
    private WrapHeightListView refluxList;
    @ViewInject(R.id.repulse_btn)
    private TextView repulseBtn;
    @ViewInject(R.id.agree_btn)
    private TextView agreeBtn;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;

    private String backState;
    private String itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offline_reflux_act);
        ViewInjectUtils.inject(this);
        loadData();
        initView();
    }

    private void loadData() {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
        requestHttpData(Constants.Urls.URL_POST_REFLUX_LIST,
                REQUEST_CODE_REFLUX_LIST, FProtocol.HttpMethod.POST,params);
    }

    private void initView() {
        setCenterTitle("返流");
        initLoadingView(this);
        setLoadingStatus(LoadingStatus.LOADING);
        leftButton.setOnClickListener(this);
        repulseBtn.setOnClickListener(this);
        agreeBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_button:
                finish();
                break;
            case R.id.loading_layout:
                loadData();
                break;
            case R.id.agree_btn:
                //同意
                agreeOrRepulse("1");
                break;
            case R.id.repulse_btn:
                //打回
                agreeOrRepulse("2");
                break;
        }
    }

    private void agreeOrRepulse(String s) {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token",UserCenter.getToken(this));
        params.put("type",s);
        params.put("itemid",itemId);
        if (!TextUtils.isEmpty(backState)){
            if (s.equals("1")){
                params.put("back_state",backState);
            }
        }else {
            ToastUtil.shortShow(this,"请选择项目");
            return;
        }
        requestHttpData(Constants.Urls.URL_POST_REFLUX_OPERATE,REQUEST_CODE_REFLUX_OPERATE,
                FProtocol.HttpMethod.POST,params);
    }

    @Override
    protected void parseData(int requestCode, String data) {
        LogUtil.e("zhang","data = " + data);
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_REFLUX_LIST:
                if (data != null){
                    OfflineRefluxEntity offlineRefluxEntity = Parsers.getOfflineRefluxEntity(data);
                    setLoadingStatus(LoadingStatus.GONE);
                    if (offlineRefluxEntity != null){
                        final List<OfflineRefluxEntity.OfflineRefluxResult> refluxResults = offlineRefluxEntity.getRefluxResults();
                        if (refluxResults != null && refluxResults.size() > 0){
                            setLoadingStatus(LoadingStatus.GONE);
                            final OfflineRefluxAdapter offlineRefluxAdapter = new OfflineRefluxAdapter(this,refluxResults);
                            refluxList.setAdapter(offlineRefluxAdapter);
                            offlineRefluxAdapter.setOnSelectListener(new OfflineRefluxAdapter.OnSelectListener() {
                                @Override
                                public void onSelect(int position) {
                                    if (refluxResults.get(position).isSelect){
                                        refluxResults.get(position).isSelect = false;
                                    }else {
                                        refluxResults.get(position).isSelect = true;
                                        backState = refluxResults.get(position).getBackState();
                                        itemId = refluxResults.get(position).getId();
                                    }
                                    offlineRefluxAdapter.notifyDataSetChanged();
                                }
                            });
                        }else {
                            setLoadingStatus(LoadingStatus.EMPTY);
                        }
                    }else {
                        setLoadingStatus(LoadingStatus.EMPTY);
                    }
                }
                break;
            case REQUEST_CODE_REFLUX_OPERATE:
                if (data != null){
                    Entity entity = Parsers.getEntity(data);
                    if (entity.getRetcode() == 0){
                        ToastUtil.shortShow(this,"操作成功");
                        loadData();
                    }else {
                        ToastUtil.shortShow(this,entity.getStatus());
                    }
                }
                break;
        }
    }

    @Override
    public void mistake(int requestCode, FProtocol.NetDataProtocol.ResponseStatus status, String errorMessage) {
        super.mistake(requestCode, status, errorMessage);
        switch (requestCode){
            case REQUEST_CODE_REFLUX_LIST:
                loadData();
                break;
        }
    }
}
