package com.shinaier.laundry.snlfactory.offlinecash.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.utils.LogUtil;
import com.common.utils.ToastUtil;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.fragment.BaseFragment;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.manage.ui.activity.MessageNoticeActivity;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.Entity;
import com.shinaier.laundry.snlfactory.network.entity.OfflineAuthorityEntity;
import com.shinaier.laundry.snlfactory.network.entity.TakeClothesEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.offlinecash.adapter.OfflineAuthorityAdapter;
import com.shinaier.laundry.snlfactory.offlinecash.adapter.SpacesItemDecoration;
import com.shinaier.laundry.snlfactory.offlinecash.ui.activity.MemberInfoActivity;
import com.shinaier.laundry.snlfactory.offlinecash.ui.activity.OfflineAddVisitorActivity;
import com.shinaier.laundry.snlfactory.offlinecash.ui.activity.ScanActivity;
import com.shinaier.laundry.snlfactory.offlinecash.ui.activity.TakeClothesActivity;
import com.shinaier.laundry.snlfactory.setting.view.CollectClothesDialog;
import com.shinaier.laundry.snlfactory.util.CommonTools;
import com.shinaier.laundry.snlfactory.view.CommonDialog;

import java.util.IdentityHashMap;

import static android.app.Activity.RESULT_OK;

/**
 * Created by 张家洛 on 2017/7/18.
 */

public class OfflineCashFragment extends BaseFragment implements View.OnClickListener {
    private static final int REQUEST_CODE_TAKE_CLOTHES_LIST = 0x2;
    private static final int REQUEST_CODE_ORDER_QRCODE = 0x4;
    private static final int REQUEST_CODE_OFFLINE_AUTHORITY = 0x5;
    private static final int REQUEST_CODE_IS_MEMBER = 0x7;
    public static final int IRONING = 0x6;
    public static final int DRYING = 0x8;

    private Context context;
    private CollectClothesDialog collectClothesDialog;
    private EditText edPhoneOrderNum;
    private int position; //判断收件还是取衣
    private String number;
    // 自定dialog
    private CommonDialog dialog;

    private RecyclerView offlineAuthority;
    private OfflineAuthorityEntity offlineAuthorityEntity;
    private ImageView ivMessageNotice;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.offline_cash_frag,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        loadData();
        initView(view);
    }

    private void loadData() {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token",UserCenter.getToken(context));
        requestHttpData(Constants.Urls.URL_POST_OFFLINE_AUTHORITY,REQUEST_CODE_OFFLINE_AUTHORITY, FProtocol.HttpMethod.POST,params);
    }

    private void initView(View view) {
        initLoadingView(this,view);
        setLoadingStatus(LoadingStatus.LOADING);
        offlineAuthority = (RecyclerView) view.findViewById(R.id.offline_authority);
        ivMessageNotice = (ImageView) view.findViewById(R.id.iv_message_notice);
        ivMessageNotice.setOnClickListener(this);
        //设置layoutManager
        offlineAuthority.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        //设置item之间的间隔
        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        offlineAuthority.addItemDecoration(decoration);
        dialog = new CommonDialog(context);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_message_notice: //线下收银消息
                startActivity(new Intent(context, MessageNoticeActivity.class));
                break;
            case R.id.loading_img_refresh:
                loadData();
                setLoadingStatus(LoadingStatus.LOADING);
        }
    }

    private void inputNumOrScan(int witch) {
        position = witch;
        View view = View.inflate(context, R.layout.collect_clothes_view,null);
        TextView tvInputNum = (TextView) view.findViewById(R.id.tv_input_num);  //显示标题
        edPhoneOrderNum = (EditText) view.findViewById(R.id.ed_phone_order_num);
        ImageView ivScan = (ImageView) view.findViewById(R.id.iv_scan);
        if(witch == 1){
            ivScan.setVisibility(View.GONE);
            tvInputNum.setText("请输入客户手机号/会员卡号");
            edPhoneOrderNum.setHint("请输入客户手机号/会员卡号");
        }else if(witch == 2){
            tvInputNum.setText("请输入客户手机号/扫描订单号");
            edPhoneOrderNum.setHint("请输入客户手机号/扫描订单号");
            ivScan.setVisibility(View.VISIBLE);
            ivScan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult(new Intent(context,ScanActivity.class),REQUEST_CODE_ORDER_QRCODE);
                }
            });
        }
        TextView revisePhoneCancel = (TextView) view.findViewById(R.id.revise_phone_cancel); //取消按钮
        TextView revisePhoneConfirm = (TextView) view.findViewById(R.id.revise_phone_confirm); //确定按钮
        collectClothesDialog = new CollectClothesDialog(context, R.style.DialogTheme,view);
        collectClothesDialog.show();

        revisePhoneCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collectClothesDialog.dismiss();
            }
        });
        revisePhoneConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = edPhoneOrderNum.getText().toString();
                if(!TextUtils.isEmpty(number)){
                    if (CommonTools.checkPhoneNumber(context,false,number)){
                        if (position == 1){ // 收件

                            dialog.setContent("加载中");
                            dialog.show();
                            //判断是否是会员 用get接口
                            isMember();
                        }else if(position == 2){ // 取衣
                            dialog.setContent("加载中");
                            dialog.show();
                            takeOrderData(number); //请求网络判断 输入电话或订单号 有没有订单
                        }
                    }
                }else {
                    ToastUtil.shortShow(context,"请输入手机号/会员卡号");
                }
            }
        });

    }

    private void isMember() {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(context));
        params.put("number",number);
        requestHttpData(Constants.Urls.URL_POST_CUSTOM_INFO,REQUEST_CODE_IS_MEMBER, FProtocol.HttpMethod.POST,params);
    }

    private void takeOrderData(String number) {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token",UserCenter.getToken(context));
        params.put("number",number);
        requestHttpData(Constants.Urls.URL_POST_TAKE_CLOTHES_LIST,REQUEST_CODE_TAKE_CLOTHES_LIST, FProtocol.HttpMethod.POST,params);
    }

    @Override
    public void success(int requestCode, String data) {
        LogUtil.e("zhang","data = " + data);
        super.success(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_TAKE_CLOTHES_LIST:
                if (data != null){
                    TakeClothesEntity takeClothesEntity = Parsers.getTakeClothesEntity(data);
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    if (takeClothesEntity != null){
                        if (takeClothesEntity.getCode() == 0){
                            if (takeClothesEntity.getResult() != null){
                                int countTotal = takeClothesEntity.getResult().getCountTotal();
                                if (countTotal == 0){
                                    ToastUtil.shortShow(context,takeClothesEntity.getMsg());
                                }else {

                                    Intent intent = new Intent(context,TakeClothesActivity.class);
                                    intent.putExtra("phoneNum",number);
                                    startActivity(intent); //进入取衣页面
                                    collectClothesDialog.dismiss(); //加载dialog显示
                                }
                            }
                        }else {
                            ToastUtil.shortShow(context,takeClothesEntity.getMsg());
                        }
                    }
                }
                break;
            case REQUEST_CODE_OFFLINE_AUTHORITY:
                if (data != null){
                    offlineAuthorityEntity = Parsers.getOfflineAuthorityEntity(data);
                    if (offlineAuthorityEntity != null){
                        if (offlineAuthorityEntity.getCode() == 0){
                            setLoadingStatus(LoadingStatus.GONE);
                            if (!offlineAuthorityEntity.getResult().getMessageCount().equals("0")){
                                ivMessageNotice.setSelected(true);
                            }else {
                                ivMessageNotice.setSelected(false);
                            }
                            if (offlineAuthorityEntity.getResult() != null && offlineAuthorityEntity.getResult().getMights().size() > 0){

                                OfflineAuthorityAdapter adapter = new OfflineAuthorityAdapter(context, offlineAuthorityEntity.getResult().getMights());
                                offlineAuthority.setAdapter(adapter);
                                adapter.setOnItemClickListener(new OfflineAuthorityAdapter.OnRecyclerItemClickListener() {
                                    @Override
                                    public void onItemClick(TextView textView,int position) {
                                        switch (offlineAuthorityEntity.getResult().getMights().get(position).getModule()){
                                            case "1":
                                                //收件
                                                LogUtil.e("zhang","收件");
                                                inputNumOrScan(1);
                                                break;
                                            case "8":
                                                //清洗
                                                LogUtil.e("zhang","入厂");
//                                                startActivity(new Intent(context,OfflineWashActivity.class));
                                                break;
                                            case "2":
                                                LogUtil.e("zhang","清洗");
                                                break;
                                            case "50":
                                                //烘干
                                                LogUtil.e("zhang","烘干");
//                                                Intent intent = new Intent(context,OfflineWashingActivity.class);
//                                                intent.putExtra("extra_from",DRYING);
//                                                startActivity(intent);
                                                break;
                                            case "51":
                                                //熨烫
                                                LogUtil.e("zhang","熨烫");
//                                            Intent intent = new Intent(context,OfflineWashingActivity.class);
//                                            intent.putExtra("extra_from",IRONING);
//                                            startActivity(intent);
                                                break;
                                            case "52":
                                                //质检
                                                LogUtil.e("zhang","质检");
//                                            startActivity(new Intent(context,OfflineWashingActivity.class));
                                                break;
                                            case "3":
                                                //上挂
                                                LogUtil.e("zhang","上挂");
//                                            startActivity(new Intent(context, OfflineHangOnActivity.class));
                                                break;
                                            case "9":
                                                LogUtil.e("zhang","出厂");
                                                break;
                                            case "4":
                                                //取衣
                                                LogUtil.e("zhang","取衣");
//                                            inputNumOrScan(2);
                                                break;
                                            case "5":
                                                //业务统计
                                                LogUtil.e("zhang","业务统计");
//                                            startActivity(new Intent(context,StatisticsActivity.class));
                                                break;
                                            case "6":
                                                //会员管理
                                                LogUtil.e("zhang","会员管理");
//                                            Intent intent = new Intent(context,OfflineMemberManageActivity.class);
//                                            intent.putExtra("m_name",offlineAuthorityEntity.getResult().getMerName());
//                                            intent.putExtra("m_logo",offlineAuthorityEntity.getResult().getMerLogo());
//                                            startActivity(intent);
                                                break;
                                            case "7":
                                                //返流审核
                                                LogUtil.e("zhang","返流审核");
//                                            startActivity(new Intent(context,OfflineRefluxActivity.class));
                                                break;
                                        }
                                    }
                                });
                            }else {
                                setLoadingStatus(LoadingStatus.GONE);
                            }
                        }else {
                            setLoadingStatus(LoadingStatus.GONE);
                            ToastUtil.shortShow(context, offlineAuthorityEntity.getMsg());
                        }
                    }
                }
                break;
            case REQUEST_CODE_IS_MEMBER:
                if (data != null){
                    Entity entity = Parsers.getEntity(data);
                    if (entity != null){
                        if (entity.getRetcode() == 0){
                            //有会员存在
                            Intent intent = new Intent(context,MemberInfoActivity.class);
                            intent.putExtra("phone_num",number);
                            startActivity(intent);
                            collectClothesDialog.dismiss();
                            dialog.dismiss();
                        }else if (entity.getRetcode() == 1000){ //code 等于1000说明是不存在的会员
                            //会员不存在 新增散客
                            if (CommonTools.checkPhoneNumber(context,true,number)){
                                Intent intent = new Intent(context,OfflineAddVisitorActivity.class);
                                intent.putExtra("phone_num",number);
                                startActivity(intent);
                                collectClothesDialog.dismiss();
                                dialog.dismiss();
                            }
                        }else {
                            ToastUtil.shortShow(context,entity.getStatus());
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_ORDER_QRCODE:
                if (resultCode == RESULT_OK){
                    if (data != null){
                        String payCode = data.getStringExtra("pay_code");
                        Intent intent = new Intent(context,TakeClothesActivity.class);
                        intent.putExtra("phoneNum",payCode);
                        startActivity(intent); //进入取衣页面
                        collectClothesDialog.dismiss(); //加载dialog显示
                    }
                }
                break;
        }
    }
}
