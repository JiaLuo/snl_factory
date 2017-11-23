package com.shinaier.laundry.snlfactory.base;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.interfaces.IActivityHelper;
import com.common.network.FProtocol;
import com.common.ui.FBaseActivity;
import com.common.utils.ToastUtil;
import com.common.widget.ProgressImageView;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.util.ExitManager;


/**
 * @author songxudong
 */
public class BaseActivity extends FBaseActivity implements IActivityHelper {

    protected Resources res;
    protected View mLayoutLoading;
    protected ProgressImageView mImgLoading;
    protected TextView mTxtCardEmpty;
    protected TextView mTxtLoadingEmpty;
    protected TextView mTxtLoadingRetry;
    protected ImageView mImgLoadingRetry;
    protected ImageView mImgLoadingEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitManager.instance.addActivity(this);
        res = getResources();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public void success(int requestCode, String data) {
            parseData(requestCode, data);
    }

    /**
     * 请求成功后实际处理数据的方法
     */
    protected void parseData(int requestCode, String data) {
    }

    @Override
    public void mistake(int requestCode, FProtocol.NetDataProtocol.ResponseStatus status, String errorMessage) {
        closeProgressDialog();
        ToastUtil.shortShow(this, errorMessage);
    }

//    protected boolean checkUpdateVersion() {
//        int versionCode = VersionInfoUtils.getVersionCode(this);
//        IdentityHashMap<String, String> params = new IdentityHashMap<>();
//        params.put("version", "" + versionCode);
//        params.put(Constants.TOKEN, "");
//        requestHttpData(Constants.Urls.URL_CHECK_UPDATE_VERSION, REQUEST_UPDATE_VERSION_CODE, FProtocol.HttpMethod.POST, params);
//        return false;
//    }




    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    protected void initLoadingView(View.OnClickListener listener) {
        mLayoutLoading = findViewById(R.id.loading_layout);
        mImgLoading = (ProgressImageView) findViewById(R.id.loading_img_anim);
        mTxtLoadingEmpty = (TextView) findViewById(R.id.loading_txt_empty);
        mTxtLoadingRetry = (TextView) findViewById(R.id.loading_txt_retry);
        mImgLoadingRetry = (ImageView) findViewById(R.id.loading_img_refresh);
        mImgLoadingEmpty = (ImageView) findViewById(R.id.loading_img_empty);
//        mTxtCardEmpty = (TextView) findViewById(R.id.loading_btn_card_empty);
//        if (mTxtCardEmpty != null) {
//            mTxtCardEmpty.setOnClickListener(listener);
//            mTxtCardEmpty.setClickable(false);
//        }
        if (mLayoutLoading != null) {
            mLayoutLoading.setOnClickListener(listener);
            mLayoutLoading.setClickable(false);
        }
    }


    protected void setLoadingStatus(LoadingStatus status) {
        if (mLayoutLoading == null || mImgLoading == null || mImgLoadingEmpty == null
                || mImgLoadingRetry == null || mTxtLoadingEmpty == null || mTxtLoadingRetry == null) {
            return;
        }
        switch (status) {
            case LOADING: {
                mLayoutLoading.setClickable(false);
                mLayoutLoading.setVisibility(View.VISIBLE);
                mImgLoading.setVisibility(View.VISIBLE);
                mImgLoadingEmpty.setVisibility(View.GONE);
                mImgLoadingRetry.setVisibility(View.GONE);
                mTxtLoadingEmpty.setVisibility(View.GONE);
                mTxtLoadingRetry.setVisibility(View.GONE);
//                mTxtCardEmpty.setVisibility(View.GONE);
//                mTxtCardEmpty.setClickable(false);
                break;
            }
            case EMPTY: {
//                mTxtCardEmpty.setClickable(false);
                mLayoutLoading.setClickable(false);
                mLayoutLoading.setVisibility(View.VISIBLE);
                mImgLoading.setVisibility(View.GONE);
                mImgLoadingEmpty.setVisibility(View.VISIBLE);
                mTxtLoadingEmpty.setVisibility(View.VISIBLE);
                mImgLoadingRetry.setVisibility(View.GONE);
                mTxtLoadingRetry.setVisibility(View.GONE);
//                mTxtCardEmpty.setVisibility(View.GONE);
                break;
            }
            case RETRY: {
//                mTxtCardEmpty.setClickable(false);
                mLayoutLoading.setClickable(true);
                mLayoutLoading.setVisibility(View.VISIBLE);
                mImgLoading.setVisibility(View.GONE);
                mImgLoadingEmpty.setVisibility(View.GONE);
                mTxtLoadingEmpty.setVisibility(View.GONE);
                mImgLoadingRetry.setVisibility(View.VISIBLE);
                mTxtLoadingRetry.setVisibility(View.VISIBLE);
//                mTxtCardEmpty.setVisibility(View.GONE);
                break;
            }
            case GONE: {
//                mTxtCardEmpty.setClickable(false);
                mLayoutLoading.setClickable(false);
                mLayoutLoading.setVisibility(View.GONE);
                mImgLoading.setVisibility(View.GONE);
                mTxtLoadingEmpty.setVisibility(View.GONE);
                mTxtLoadingRetry.setVisibility(View.GONE);
                mImgLoadingEmpty.setVisibility(View.GONE);
                mImgLoadingRetry.setVisibility(View.GONE);
//                mTxtCardEmpty.setVisibility(View.GONE);
                break;
            }
        }
    }

    public enum LoadingStatus {
        INIT,
        LOADING,
        EMPTY,
        RETRY,
        GONE
    }
}

