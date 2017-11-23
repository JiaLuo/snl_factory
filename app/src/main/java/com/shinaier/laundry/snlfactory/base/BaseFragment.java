package com.shinaier.laundry.snlfactory.base;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.interfaces.IActivityHelper;
import com.common.ui.FBaseFragment;
import com.common.widget.ProgressImageView;
import com.shinaier.laundry.snlfactory.R;


public class BaseFragment extends FBaseFragment implements IActivityHelper {
    protected View mLayoutLoading;
    protected ProgressImageView mImgLoading;
    protected TextView mTxtCardEmpty;
    protected TextView mTxtLoadingEmpty;
    protected TextView mTxtLoadingRetry;
    protected ImageView mImgLoadingRetry;
    protected ImageView mImgLoadingEmpty;

    private String baseTitle = "";

    public String getTitle() {
        return baseTitle;
    }

    public void setTitle(int titleId) {
        baseTitle = getString(titleId);
    }

    public void setTitle(String title) {
        baseTitle = title;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("baseTitle", baseTitle);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            baseTitle = savedInstanceState.getString("baseTitle", getTitle());
        }
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Override
    public void success(int requestCode, String data) {
        super.success(requestCode, data);
    }


    protected void initLoadingView(View.OnClickListener listener, View parentView) {
        mLayoutLoading = parentView.findViewById(R.id.loading_layout);
        mImgLoading = (ProgressImageView) parentView.findViewById(R.id.loading_img_anim);
        mTxtLoadingEmpty = (TextView) parentView.findViewById(R.id.loading_txt_empty);
        mTxtLoadingRetry = (TextView) parentView.findViewById(R.id.loading_txt_retry);
        mImgLoadingRetry = (ImageView) parentView.findViewById(R.id.loading_img_refresh);
        mImgLoadingEmpty = (ImageView) parentView.findViewById(R.id.loading_img_empty);
//        mTxtCardEmpty = (TextView) parentView.findViewById(R.id.loading_btn_card_empty);
//        mTxtCardEmpty.setOnClickListener(listener);
//        mLayoutLoading.setOnClickListener(listener);
//        mLayoutLoading.setClickable(false);
        mImgLoadingRetry.setOnClickListener(listener);
        mImgLoadingRetry.setClickable(false);
//        mTxtCardEmpty.setClickable(false);
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
//                mLayoutLoading.setClickable(true);
                mLayoutLoading.setVisibility(View.VISIBLE);
                mImgLoading.setVisibility(View.GONE);
                mImgLoadingEmpty.setVisibility(View.GONE);
                mTxtLoadingEmpty.setVisibility(View.GONE);
                mImgLoadingRetry.setVisibility(View.VISIBLE);
                mImgLoadingRetry.setClickable(true);
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

    public static enum LoadingStatus {
        INIT,
        LOADING,
        EMPTY,
        RETRY,
        GONE
    }
}
